package input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import output.Particle;
import output.Test;

public class InputGenerator {

	public static void main(String[] args) {
		final List<String> lines = new LinkedList<>();
		for (int i = 0; i < Test.PARTICLE_COUNT; i++) {
			Particle particle = Particle.random(i, Test.BOARD_SIZE);
			lines.add(particle.id + "\t" + particle.x + "\t" + particle.y);
		}
		writeTo("input.txt", lines);
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
