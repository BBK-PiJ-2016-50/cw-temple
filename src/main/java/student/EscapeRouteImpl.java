package student;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Implementation of {@see EscapeRoute}.
 *
 * @author Ian Robinson
 */
public class EscapeRouteImpl implements EscapeRoute {

  /**
   * the node that the explorer starts from when escaping.
   */
  private Node startNode;

  /**
   * the node the explorer needs to get to in order to escape.
   */
  private Node exitNode;

  /**
   * a collection of all nodes that make up the escape map.
   * provides information about gold and therefore which route to take
   * when escaping.
   */
  private Collection<Node> vertices;

  /**
   * the time given to the explorer in which to escape from the cavern.
   */
  private int escapeTime;

  /**
   * constructor sets up the framework required for finding the best route
   * out of the cavern within time, whilst collecting as much gold as possible.
   */
  public EscapeRouteImpl(
          Node startNode,
          Node exitNode,
          Collection<Node> vertices,
          int escapeTime
  ) {
    this.startNode = startNode;
    this.exitNode = exitNode;
    this.vertices = vertices;
    this.escapeTime = escapeTime;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Node> bestGoldRoute() {

    List<Node> bestRoute = new LinkedList<>(); //this gets added to
    Set<Node> visited = new HashSet<>(); //stores nodes that have already been validated for gold
    int remainingTime = escapeTime; //this gets decremented
    Node currentNode = startNode;

    bestRoute.add(currentNode);
    visited.add(currentNode);
    boolean goToExit = false;
    while (!goToExit) {
      EscapeRouteUtils pathUtils = new EscapeRouteUtils();
      pathUtils.findRoute(currentNode); //get all routes from current node
      //find closest node with gold
      Node closestGoldNode = vertices.iterator().next();
      int bestTimeToNode = Integer.MAX_VALUE;  //start off with infinite value
      for (Node n : vertices) {
        //ensure only nodes that have not been checked and that have gold are validated
        if (!visited.contains(n) && n.getTile().getGold() > 0) {
          List<Node> routeToNode = pathUtils.getRoute(n);
          //work out time it would take to get to the node
          int time = timeToNode(routeToNode);
          if (time <= bestTimeToNode) {
            bestTimeToNode = time;
            closestGoldNode = n;
          }

        }
      }
      //work out time it would take to get to the exit from the node with gold
      EscapeRouteUtils pathUtilsToExit = new EscapeRouteUtils();
      pathUtilsToExit.findRoute(closestGoldNode); //get all routes from best gold node
      List<Node> routeToExit = pathUtilsToExit.getRoute(exitNode);
      int timeToExit = timeToNode(routeToExit);
      //add these times together
      int totalTime = bestTimeToNode + timeToExit;
      //if this total comes to more than the time remaining then get the route to the exit and add it to the route list
      List<Node> getOutNow = pathUtils.getRoute(exitNode);
      if (totalTime > remainingTime || bestTimeToNode == Integer.MAX_VALUE) {
        for (int i = 1; i < getOutNow.size(); i++) {
          bestRoute.add(getOutNow.get(i));
        }
        goToExit = true;
      } else {

        currentNode = closestGoldNode;
        //else add the route to the route list minus the first node
        //this is because otherwise the same node could be added twice to the escape route
        List<Node> routeToNode = pathUtils.getRoute(closestGoldNode);
        for (int i = 1; i < routeToNode.size(); i++) {
          bestRoute.add(routeToNode.get(i));
        }
        visited.addAll(routeToNode); //ensures nodes that have been checked are not checked again
        //subtract the time it took to get to the node with gold from the total time
        remainingTime -= bestTimeToNode;
      }
    }
    return bestRoute;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void takeRoute(List<Node> escapeRoute, EscapeState state) {
    Queue<Node> pathToTake = new LinkedList<>(escapeRoute);
    Node startNode = pathToTake.remove();
    Node pathNode;
    collectGold(startNode.getTile(), state);
    while (!exitFound(state)) {
      pathNode = pathToTake.remove();
      state.moveTo(pathNode);
      collectGold(pathNode.getTile(), state);
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean exitFound(final EscapeState state) {
    return state.getExit() == state.getCurrentNode();
  }

  /**
   * works out the time it would take to traverse the map from one node to
   * another.  The time is based on the weights associated with each edge.
   * @param route the list of nodes from the current node to the end node
   *              for which the time will be calculated.
   * @return the time it would take, based on the sum of the edges, to traverse
   *         the map from the start node to the end node.
   */
  private int timeToNode(List<Node> route) {
    int timeToNode = 0;
    for (int i = 0; i < route.size() - 1; i++) {
      Node curNode = route.get(i);
      Node nextNode = route.get(i + 1);
      timeToNode += curNode.getEdge(nextNode).length();
    }
    return timeToNode;
  }

  /**
   * picks up gold from a tile.
   * @param tile the tile to pick gold up from.
   * @param state the state of the escape phase.
   */
  private void collectGold(final Tile tile, final EscapeState state) {
    if (tile.getGold() > 0) {
      state.pickUpGold();
    }
  }

}
