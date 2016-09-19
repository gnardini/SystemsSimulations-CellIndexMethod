package cell_index_method.output;

/**
 * Created by FranDepascuali on 8/24/16.
 */
public class JavaVisualizerFormatter {

//    public static List<String> boardToLines(State state) {
//        List<String> lines = new LinkedList<>();
//
//        Board board = state.getBoard();
//
//        lines.add(String.valueOf(board.getM()));
//        lines.add(String.valueOf(board.getL()));
//        lines.add(String.valueOf(state.getInteractionRadius());
//
//        for (int i = 0; i < board.getM(); i++) {
//            for (int j = 0; j < board.getM(); j++) {
//                for (Particle particle: board.particlesAt(i, j)) {
//                    lines.add(particleToLine(particle));
//                }
//            }
//        }
//        return lines;
//    }
//
//    public static Board linesToBoard(List<String> lines) {
//
//        int M = Integer.valueOf(lines.get(0));
//        int L = Integer.valueOf(lines.get(1));
//        int radius = Integer.valueOf(lines.get(2));
//
//        List<Particle> particles = new ArrayList<>();
//
//        for (int i = 3; i < lines.size(); i++) {
//            particles.add(lineToParticle(lines.get(i)));
//        }
//
//        return new Board(M, L, radius, particles);
//    }
//
//    private static String particleToLine(Particle particle) {
//        return particle.getId() + " " + particle.getX() + " " + particle.getY();
//    }
//
//    private static Particle lineToParticle(String line) {
//        String[] raw = line.split(" ");
//
//        int id = Integer.valueOf(raw[0]);
//        Double x = Double.valueOf(raw[1]);
//        Double y = Double.valueOf(raw[2]);
//
//        return new Particle(id, x, y, 1.0, 2.0);
//    }

}
