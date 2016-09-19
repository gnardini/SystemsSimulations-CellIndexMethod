package cell_index_method.output;
import cell_index_method.models.Board;

import java.util.List;


public interface Formatter {

	List<String> format(Board board, int particlesCount, int chosenParticleId, int interactionRadius);
}
