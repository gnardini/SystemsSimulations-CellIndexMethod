package input;

import models.Particle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {
  private int particleCount;
  private int cellSize;
  private List<Particle> particles;

  public InputFileReader(String staticInputPath, String dynamicInputPath) {
    this.particles = new ArrayList<>();
    readFiles(staticInputPath, dynamicInputPath);
  }

  private void readFiles(String staticInputPath, String dynamicInputPath) {
    try {

      File staticFile = new File(staticInputPath);
      FileReader staticReader = new FileReader(staticFile);
      BufferedReader staticBuffer = new BufferedReader(staticReader);

      File dynamicFile = new File(dynamicInputPath);
      FileReader dynamicReader = new FileReader(dynamicFile);
      BufferedReader dynamicBuffer = new BufferedReader(dynamicReader);

      if (staticBuffer.ready() && dynamicBuffer.ready()) {
        this.particleCount = Integer.parseInt(staticBuffer.readLine().trim());
        this.cellSize = Integer.parseInt(staticBuffer.readLine().trim());

        // The first line indicates time
        dynamicBuffer.readLine();

        this.particles = new ArrayList<>();
        for (int id = 0; id < this.particleCount; id++) {
          String[] properties = staticBuffer.readLine().trim().split(" +");
          String[] position = dynamicBuffer.readLine().trim().split(" +");
          Particle p = new Particle(id + 1, Double.parseDouble(position[0]), Double.parseDouble(position[1]),
                  Double.parseDouble(properties[0]), Double.parseDouble(properties[1]));
          this.particles.add(p);
        }
      }

      dynamicBuffer.close();
      staticBuffer.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  public int getParticleCount() {
    return particleCount;
  }

  public int getCellSize() {
    return cellSize;
  }

  public List<Particle> getParticles() {
    return particles;
  }
}
