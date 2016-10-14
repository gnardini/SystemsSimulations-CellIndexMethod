package GranularMedium;

import GranularMedium.models.Cell;
import GranularMedium.models.Particle;
import GranularMedium.models.State;

import java.awt.*;

public class CellIndexMethod {

    public void calculateDistance(State state) {
        for (int i = 0; i < state.getM(); i++) {
            for (int j = 0; j < state.getM(); j++) {
                updateCellNeighbours(state.getBoard().getCell(i, j), state.getInteractionRadius());

                maybeAddCellToList(state, new Point(i, j), new Point(1, 0));
                maybeAddCellToList(state, new Point(i, j), new Point(1, 1));
                maybeAddCellToList(state, new Point(i, j), new Point(0, 1));
                maybeAddCellToList(state, new Point(i, j), new Point(-1, 1));
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

    private void maybeAddCellToList(State state, Point current, Point increment) {
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

    private void doUpdate(Cell cell1, Cell cell2, boolean xModified, boolean yModified, State state) {
        for (Particle particle1 : cell1.getParticles()) {
            for (Particle particle2 : cell2.getParticles()) {

                Particle particle1ToCompare = particle1;
                Particle particle2ToCompare = particle2;
                if (xModified && particle1.getX() > particle2ToCompare.getX()) {
                    particle2ToCompare = particle2.withPositions(
                            particle2.getId(),
                            particle2.getX() + state.getL(),
                            particle2.getY(),
                            particle2.getRadius());
                } else if (xModified && particle1.getX() < particle2ToCompare.getX()) {
                    particle1ToCompare = particle1.withPositions(
                            particle1.getId(),
                            particle1.getX() + state.getL(),
                            particle1.getY(),
                            particle1.getRadius());
                }
                if (yModified && particle1.getY() > particle2ToCompare.getY()) {
                    particle2ToCompare = particle2.withPositions(
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

}
