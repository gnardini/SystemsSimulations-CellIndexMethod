import java.util.LinkedList;
import java.util.List;


public class XYZFormatter implements Formatter {

	@Override
	public List<String> format() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> fformat(Board board, int particlesCount) {
		List<String> lines = new LinkedList<>();

		lines.add(String.valueOf(particlesCount));
		lines.add("This is a comment");
		
		for (int i = 0; i < board.getCellCount(); i++) {
			for (int j = 0; j < board.getCellCount(); j++) {
				for (Particle particle: board.at(i, j)) {
					lines.add(particle.id + "\t" + particle.x + "\t" + particle.y);
				}
			}
		}
		
		return lines;
	}
}
