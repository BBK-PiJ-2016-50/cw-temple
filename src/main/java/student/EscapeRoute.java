package student;

import game.EscapeState;
import game.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

  /**
   * allows the explorer to look around for gold on nodes that aren't
   * on the immediate escape route by checking neighbour nodes and their
   * neighbour nodes.
   * a stack is maintained to ensure the explorer can find their way back
   * to the main escape route path
   * @param state the information available at the current state.
   * @param pathToTake the escape route path
   * @param pathNode the current node from which the explorer will leave the
   *                 escape route path to llok for gold.
   */
  void lookAroundForGold(EscapeState state, Queue<Node> pathToTake, Node pathNode);


}
