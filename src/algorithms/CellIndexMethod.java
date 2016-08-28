package algorithms;

import models.Board;
import models.Cell;
import models.Particle;
import models.State;

import java.awt.*;

public class CellIndexMethod implements DistanceCalculator {

    @Override
    public void calculateDistanceWithEdges(State state) {
        for (int i = 0; i < state.getM(); i++) {
            for (int j = 0; j < state.getM(); j++) {
                updateCellNeighbours(state.getBoard().getCell(i, j), state.getInteractionRadius());

                maybeAddCellToListWithEdges(state, new Point(i, j), new Point(1, 0));
                maybeAddCellToListWithEdges(state, new Point(i, j), new Point(1, 1));
                maybeAddCellToListWithEdges(state, new Point(i, j), new Point(0, 1));
                maybeAddCellToListWithEdges(state, new Point(i, j), new Point(-1, 1));
            }
        }
    }

    @Override
    public void calculateDistanceWithoutEdges(State state) {
        for (int i = 0; i < state.getM(); i++) {
            for (int j = 0; j < state.getM(); j++) {
                updateCellNeighbours(state.getBoard().getCell(i, j), state.getInteractionRadius());

                maybeAddCellToListWithoutEdges(state, new Point(i, j), new Point(1, 0));
                maybeAddCellToListWithoutEdges(state, new Point(i, j), new Point(1, 1));
                maybeAddCellToListWithoutEdges(state, new Point(i, j), new Point(0, 1));
                maybeAddCellToListWithoutEdges(state, new Point(i, j), new Point(-1, 1));

            }
        }
    }

    private void updateCellNeighbours(Cell cell, int interactionRadius) {
        for (Particle particle1 : cell.getParticles()) {
            for (Particle particle2 : cell.getParticles()) {
                if (particle1.getId() != particle2.getId() && particle1.isInRadius(particle2, interactionRadius)) {
                    particle1.getNeighbours().add(particle2);
                }
            }
        }
    }

    private void maybeAddCellToListWithoutEdges(State state, Point current, Point increment) {
        int newX = current.x + increment.x;
        int newY = current.y + increment.y;
        boolean xModified = false;
        boolean yModified = false;
        int M = state.getM();
        Board board = state.getBoard();

        if (newX >= 0 && newX < M && newY >= 0 && newY < M) {
            doUpdate(board.getCell(current.x, current.y), board.getCell(newX, newY), xModified, yModified, state);
        }
    }

    private void maybeAddCellToListWithEdges(State state, Point current, Point increment) {
        int newX = current.x + increment.x;
        int newY = current.y + increment.y;
        boolean xModified = false;
        boolean yModified = false;

        int M = state.getM();

        if (newX > M - 1) {
            xModified = true;
            newX = 0;
        } else if (newX < 0) {
            xModified = true;
            newX = M - 1;
        }
        if (newY > M - 1) {
            yModified = true;
            newY = 0;
        } else if (newY < 0) {
            yModified = true;
            newY = M - 1;
        }

        if (newX >= 0 && newX < M && newY >= 0 && newY < M) {
            doUpdate(state.getBoard().getCell(current.x, current.y), state.getBoard().getCell(newX, newY), xModified, yModified, state);
        }
    }

    private void updateParticleAngle(Particle particle, float amplitude) {
        double sinSum = Math.sin(particle.getAngle());
        double cosSum = Math.cos(particle.getAngle());

        for (Particle neighbour : particle.getNeighbours()) {
            sinSum += Math.sin(neighbour.getAngle());
            cosSum += Math.cos(neighbour.getAngle());
        }

        int size = particle.getNeighbours().size() + 1;
        double newAngle = Math.atan2(sinSum / size, cosSum / size);

        newAngle += (Math.random() - .5) * amplitude;

        if (newAngle < 0) {
            newAngle += Math.PI * 2;
        } else if (newAngle > Math.PI * 2) {
            newAngle -= Math.PI * 2;
        }

        particle.setAngle(newAngle);
    }

    private void doUpdate(Cell cell1, Cell cell2, boolean xModified, boolean yModified, State state) {
        for (Particle particle1 : cell1.getParticles()) {
            for (Particle particle2 : cell2.getParticles()) {

                Particle particle1ToCompare = particle1;
                Particle particle2ToCompare = particle2;
                if (xModified && particle1.getX() > particle2ToCompare.getX()) {
                    particle2ToCompare = new Particle(
                            particle2.getId(),
                            particle2.getX() + state.getL(),
                            particle2.getY(),
                            particle2.getRadius());
                } else if (xModified && particle1.getX() < particle2ToCompare.getX()) {
                    particle1ToCompare = new Particle(
                            particle1.getId(),
                            particle1.getX() + state.getL(),
                            particle1.getY(),
                            particle1.getRadius());
                }
                if (yModified && particle1.getY() > particle2ToCompare.getY()) {
                    particle2ToCompare = new Particle(
                            particle2.getId(),
                            particle2ToCompare.getX(),
                            particle2ToCompare.getY() + state.getL(),
                            particle2.getRadius());
                }

                if (particle1ToCompare.isInRadius(particle2ToCompare, state.getInteractionRadius())) {
                    particle1.getNeighbours().add(particle2);
                    particle2.getNeighbours().add(particle1);
                }
            }
        }
    }

    public void nextStep(State state, float amplitude) {
        calculateDistanceWithEdges(state);
        Board board = state.getBoard();

        for (int i = 0; i < board.getM(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                for (Particle particle : board.getCell(i, j).getParticles()) {
                    particle.move(state.getL());
                    updateParticleAngle(particle, amplitude);
                    particle.clearNeighbours();
                }
            }
        }
    }

}
