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
   * the best route for the explorer to take when escaping the cavern.
   */
  private List<Node> bestRoute;

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
    this.bestRoute = new LinkedList<>();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Node> bestGoldRoute() {

    //stores nodes that have already been validated for gold.
    Set<Node> visited = new HashSet<>();

    //used to work out if explorer should abandon gold collection.
    int remainingTime = escapeTime;

    //define and add the current node to the route.
    Node currentNode = startNode;
    bestRoute.add(currentNode);
    visited.add(currentNode);

    boolean goToExit = false;
    while (!goToExit) {

      //get all paths from current node to all other nodes.
      EscapeRouteUtils curNodePaths = new EscapeRouteUtils();
      curNodePaths.findRoute(currentNode);

      //find closest node with gold from current position.
      Node closestGoldNode = vertices.iterator().next();
      int bestTimeToNode = Integer.MAX_VALUE;  //start off with max value.
      for (Node n : vertices) {
        //ensure only nodes that have not been checked and that have gold are validated
        if (!visited.contains(n) && n.getTile().getGold() > 0) {
          List<Node> routeToNode = curNodePaths.getRoute(n);
          //work out time it would take to get to the node
          int time = timeToNode(routeToNode);
          if (time <= bestTimeToNode) {
            bestTimeToNode = time;
            closestGoldNode = n;
          }
        }
      }

      //check how long it would take to get to the exit from the closestGoldNode
      EscapeRouteUtils pathUtilsToExit = new EscapeRouteUtils();
      pathUtilsToExit.findRoute(closestGoldNode);
      List<Node> routeToExit = pathUtilsToExit.getRoute(exitNode);
      int timeToExit = timeToNode(routeToExit);

      //add times to find out if the route is doable before time runs out.
      int totalTime = bestTimeToNode + timeToExit;

      //head to exit straight away if the route to the next gold node is not
      //achievable, or if there are no nodes remaining with gold.
      List<Node> getOutNow = curNodePaths.getRoute(exitNode);
      if (totalTime > remainingTime || bestTimeToNode == Integer.MAX_VALUE) {
        addNodesToRoute(getOutNow);
        goToExit = true;
      } else {
        //if doable then get the route to the node and add to the bestRoute.
        currentNode = closestGoldNode;
        List<Node> routeToNode = curNodePaths.getRoute(closestGoldNode);
        addNodesToRoute(routeToNode);
        //ensures nodes that have been assessed are not checked again.
        visited.addAll(routeToNode);
        //update the remaining time
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
   * this adds the nodes to the bestRoute list.
   * the first node is not added as otherwise it would be duplicated in the
   * escape route and the explorer wouldn't be able to move.
   * @param route the list of nodes to add to the bestRoute.
   */
  private void addNodesToRoute(List<Node> route) {
    for (int i = 1; i < route.size(); i++) {
      bestRoute.add(route.get(i));
    }
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
