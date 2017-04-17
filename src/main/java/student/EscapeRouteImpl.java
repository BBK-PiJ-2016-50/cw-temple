package student;

import static student.Utils.exitFound;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.*;

/**
 * Implementation of {@see EscapeRoute}.
 *
 * @author Ian Robinson
 */
public class EscapeRouteImpl implements EscapeRoute {

  private Node startNode;
  private Node exitNode;
  private Collection<Node> vertices;
  private int escapeTime;

  /**
   * constructor sets up the framework required for finding
   * the shortest route.
   */
  public EscapeRouteImpl(Node startNode, Node exitNode, Collection<Node> vertices, int escapeTime) {
    this.startNode = startNode;
    this.exitNode = exitNode;
    this.vertices = vertices;
    this.escapeTime = escapeTime;
  }

  @Override
  public List<Node> bestGoldRoute() {
    //somewhere i'm not cutting enough time off the remaining time
    //causes the explorer to not get to the exit in time on some occasions

    List<Node> bestRoute = new LinkedList<>(); //this gets added to
    Set<Node> visited = new HashSet<>(); //stores nodes that have already been validated for gold
    int remainingTime = escapeTime; //this gets decremented
    Node currentNode = startNode;

    bestRoute.add(currentNode);

    boolean goToExit = false;
    while (!goToExit) {
      ShortestPathUtils pathUtils = new ShortestPathUtils();
      pathUtils.findRoute(currentNode); //get all routes from current node
      //find closest node with gold
      Node closestGoldNode = vertices.iterator().next();
      int bestTimeToNode = 100000;
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
      List<Node> routeToExit = pathUtils.getRoute(exitNode);
      int timeToExit = timeToNode(routeToExit);
      //add these times together
      int totalTime = bestTimeToNode + timeToExit;
      //if this total comes to more than the time remaining then get the route to the exit and add it to the route list
      if (totalTime > remainingTime) {
        for (int i = 1; i < routeToExit.size(); i++) {
          bestRoute.add(routeToExit.get(i));
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

  private int timeToNode(List<Node> route) {
    int timeToNode = 0;
    for (int i = 0; i < route.size() - 1; i++) {
      Node curNode = route.get(i);
      Node nextNode = route.get(i + 1);
      timeToNode += curNode.getEdge(nextNode).length();
    }
    return timeToNode;
  }

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
