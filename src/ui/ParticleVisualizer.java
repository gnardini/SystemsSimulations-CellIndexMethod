package ui;

import output.JavaVisualizerFormatter;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class ParticleVisualizer {

    private WatchService watcher;
    private Path directory;

    private JavaVisualizerFormatter formatter = new JavaVisualizerFormatter();

    public static void main(String[] args) {
        ParticleVisualizer visualizer = new ParticleVisualizer("filesToVisualize");
        visualizer.startVisualization();
    }

    public ParticleVisualizer(String directoryPath) {
        directory = Paths.get(directoryPath);

        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            directory.register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public void startVisualization() {
        ParticlePrinter particlePrinter = new ParticlePrinter();

        while (true) {
            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // The filename is the
                // context of the event.
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();

                System.out.println(filename);
                System.out.println(directory);
                // Resolve the filename against the directory.
                // If the filename is "test" and the directory is "foo",
                // the resolved name is "test/foo".
                Path filePath = directory.resolve(filename);
                if (kind == ENTRY_CREATE || kind == ENTRY_MODIFY) {
                    update(filePath, particlePrinter);
                }
            }

            // Reset the key -- this step is critical if you want to
            // receive further watch events.  If the key is no longer valid,
            // the directory is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    private void update(Path path, ParticlePrinter printer) {
//        List<String> lines = null;
//        try {
//            lines = Files.readAllLines(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Board board = formatter.linesToBoard(lines);
//        printer.setBoard(board);
    }

    public void stopVisualization() {
        try {
            watcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
