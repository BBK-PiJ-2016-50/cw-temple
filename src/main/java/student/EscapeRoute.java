package student;

import game.Node;

import java.util.List;

/**
 * builds up a picture of the map for use when escaping.
 * uses Dijstra's algorithm to find shortest distance between two points.
 *
 * @author Ian Robinson
 */
public interface EscapeRoute {

  /**
   * finds the shortest route from the start node to the exit node.
   * @param startNode the node that the explorer starts from.
   */
  void findRoute(Node startNode);

  /**
   * uses the pathNodes map to construct the shortest path.
   * @param endNode the node which the explorer wants to get to.
   * @return the shortest route from start to exit as a list of nodes.
   */
  List<Node> getRoute(Node endNode);


}
