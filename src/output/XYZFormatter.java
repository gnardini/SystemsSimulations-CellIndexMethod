package output;
import java.util.LinkedList;
import java.util.List;


public class XYZFormatter implements Formatter {

	@Override
	public List<String> format() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> fformat(Board board, int particlesCount, int chosenParticleId) {
		List<String> lines = new LinkedList<>();

		lines.add(String.valueOf(particlesCount + 1));
		lines.add("This is a comment");
		
		Particle chosenParticle = null;
		
		for (int i = 0; i < board.getCellCount(); i++) {
			for (int j = 0; j < board.getCellCount(); j++) {
				for (Particle particle: board.at(i, j)) {
					boolean isParticle = particle.id == chosenParticleId;
					if (isParticle) {
						chosenParticle = particle;
					}
					boolean isNieghbourOfParticle = particle.isNeighbourOf(chosenParticleId);
					int R = isNieghbourOfParticle ? 255 : 0;
					int G = isParticle ? 255 : 0;
					int B = isNieghbourOfParticle || isParticle ? 0 : 255;
					lines.add(particle.id 
							+ "\t" + particle.x 
							+ "\t" + particle.y 
							+ "\t" + particle.getTwoDecimalRadius()
							+ "\t" + R
							+ "\t" + G
							+ "\t" + B
							+ "\t" + 0);
				}
			}
		}
		lines.add(-1
				+ "\t" + chosenParticle.x 
				+ "\t" + chosenParticle.y 
				+ "\t" + String.format("%.02f", chosenParticle.radius + Test.CONVERGENCE_RADIUS)
				+ "\t" + 0
				+ "\t" + 255
				+ "\t" + 0
				+ "\t" + .8);
		
		return lines;
	}
}
