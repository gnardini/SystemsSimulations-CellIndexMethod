package cell_index_method.output;

import cell_index_method.models.Board;
import cell_index_method.models.Particle;

import java.util.LinkedList;
import java.util.List;


public class XYZFormatter implements Formatter {

	@Override
	public List<String> format(Board board, int particlesCount, int chosenParticleId, int interactionRadius) {
		List<String> lines = new LinkedList<>();

		lines.add(String.valueOf(particlesCount + 1));
		lines.add("This is a comment");
		
		Particle chosenParticle = null;
		
		for (int i = 0; i < board.getM(); i++) {
			for (int j = 0; j < board.getM(); j++) {
				for (Particle particle: board.particlesAt(i, j)) {
					boolean isParticle = particle.getId() == chosenParticleId;
					if (isParticle) {
						chosenParticle = particle;
					}
					boolean isNieghbourOfParticle = particle.isNeighbourOf(chosenParticleId);
					int R = getRed(isParticle, isNieghbourOfParticle);
					int G = getGreen(isParticle, isNieghbourOfParticle);
					int B = getBlue(isParticle, isNieghbourOfParticle);
					lines.add(particle.getId()
							+ "\t" + particle.getX()
							+ "\t" + particle.getY()
							+ "\t" + particle.getTwoDecimalRadius()
							+ "\t" + R
							+ "\t" + G
							+ "\t" + B
							+ "\t" + 0);
				}
			}
		}
		lines.add(-1
				+ "\t" + chosenParticle.getX()
				+ "\t" + chosenParticle.getY()
				+ "\t" + chosenParticle.getTwoDecimalRadius()
				+ "\t" + getRed(true, false)
				+ "\t" + getGreen(true, false)
				+ "\t" + getBlue(true, false)
				+ "\t" + 0);
		
		return lines;
	}

	private static int getRed(boolean isParticle, boolean isNeighbour) {
		if(isParticle) {
			return 255;
		} else {
			return 0;
		}
	}

	private static int getGreen(boolean isParticle, boolean isNeighbour) {
		if(isNeighbour) {
			return 255;
		} else {
			return 0;
		}
	}

	private static int getBlue(boolean isParticle, boolean isNeighbour) {
		if(isParticle || isNeighbour) {
			return 0;
		} else {
			return 255;
		}
	}
}
