package student;

import game.EscapeState;
import game.Node;

import java.util.List;
import java.util.Queue;

/**
 * builds up a picture of the map for use when escaping using
 * Dijkstra's algorithm.  This find the shortest distance between two points.
 *
 * @author Ian Robinson
 */
public interface EscapeRoute {

  List<Node> bestGoldRoute();
  void takeRoute(List<Node> escapeRoute, EscapeState state);

  /**
   * checks to see if exit found.  Exit is found if current node equals exit node.
   * @param state the information available at the current state.
   * @return boolean indicating whether the exit has been found or not.
   */
  boolean exitFound(EscapeState state);


}
