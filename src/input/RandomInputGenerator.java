package input;

import models.Particle;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class RandomInputGenerator {

	private static final String DEFAULT_PATH = "files/randomInput.txt";

	public static void generateInput(int particleCount, int boardSize) {
		writeTo(DEFAULT_PATH, createLines(particleCount, boardSize));
	}

	public static void generateInput(String path, int particleCount, int boardSize) {
		writeTo(path, createLines(particleCount, boardSize));
	}

	private static List<String> createLines(int particleCount, int boardSize) {
		final List<String> lines = new LinkedList<>();
		for (int i = 0; i < particleCount; i++) {
			Particle particle = Particle.random(i, boardSize);
			lines.add(particle.getId() + "\t" + particle.getX() + "\t" + particle.getY());
		}
		return lines;
	}
	
	private static void writeTo(String fileName, List<String> lines) {
		  Path path = Paths.get(fileName);
		  try {
			  Files.write(path, lines, Charset.forName("UTF-8"));
		  } catch (IOException ioException) {
			  ioException.printStackTrace();
		  }
	  }

}
