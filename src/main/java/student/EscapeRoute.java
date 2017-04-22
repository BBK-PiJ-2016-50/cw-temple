package student;

import game.EscapeState;
import game.Node;
import java.util.List;

/**
 * builds up a picture of the map using Dijkstra's algorithm.  This finds the
 * shortest distance between two points.  The algorithm is employed to find an
 * optimal path for exiting the cavern whilst finding as much gold as possible.
 * see {@see EscapeRouteUtils} for Dijkstra methods.
 *
 * @author Ian Robinson
 */
public interface EscapeRoute {

  /**
   * puts together a list of nodes that will allow the explorer to gather
   * as much gold as possible and still make it out of the cavern in time.
   * This method makes use of dijkstra's algorithm to put together the route.
   *
   * @return a list of nodes constituting an optimal route for escaping
   *          whilst collecting as much gold as possible.
   */
  List<Node> bestGoldRoute();

  /**
   * executes the route to take whilst escaping.
   * this performs both movement of the explorer from node to node as well as
   * picking up the gold.
   *
   * @param escapeRoute the list of nodes that constitutes the escape route.
   * @param state the current state of the escape phase.
   */
  void takeRoute(List<Node> escapeRoute, EscapeState state);

  /**
   * checks to see if exit found.  Exit is found if current node equals exit node.
   *
   * @param state the information available at the current state.
   * @return boolean indicating whether the exit has been found or not.
   */
  boolean exitFound(EscapeState state);


}
