package output;

import models.Board;
import models.Particle;

import java.util.LinkedList;
import java.util.List;


public class XYZFormatter implements Formatter {

	@Override
	public List<String> format() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> fformat(Board board, int particlesCount, int chosenParticleId, int convergenceRadius) {
		List<String> lines = new LinkedList<>();

		lines.add(String.valueOf(particlesCount + 1));
		lines.add("This is a comment");
		
		Particle chosenParticle = null;
		
		for (int i = 0; i < board.getBoardM(); i++) {
			for (int j = 0; j < board.getBoardM(); j++) {
				for (Particle particle: board.particlesAt(i, j)) {
					boolean isParticle = particle.getId() == chosenParticleId;
					if (isParticle) {
						chosenParticle = particle;
					}
					boolean isNieghbourOfParticle = particle.isNeighbourOf(chosenParticleId);
					int R = isNieghbourOfParticle ? 255 : 0;
					int G = isParticle ? 255 : 0;
					int B = isNieghbourOfParticle || isParticle ? 0 : 255;
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
				+ "\t" + String.format("%.02f", chosenParticle.getRadius() + convergenceRadius)
				+ "\t" + 0
				+ "\t" + 255
				+ "\t" + 0
				+ "\t" + .8);
		
		return lines;
	}
}
