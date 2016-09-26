package solar_system;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {

    private static final String DISTANCE_OVER_TIME = "files/distance_over_time_%d.txt";

    public void saveDistancesOverTime(List<String> distances, double initialSpeed) {
        writeTo(String.format(DISTANCE_OVER_TIME, (int) initialSpeed), distances);
    }

    private void writeTo(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, lines, Charset.forName("UTF-8"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
