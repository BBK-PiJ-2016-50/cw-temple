package student;

import game.Edge;
import game.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * a set of tools for constructing a set of shortest possible routes from
 * a given start point.
 */
public class EscapeRouteUtils {

  /**
   * stores each visited node and the closest node that it is connected to.
   */
  private final Map<Node, Node> pathNodes;

  /**
   * map of all visited nodes and their distances to other nodes.
   */
  private final Map<Node, Integer> distanceToNode;

  /**
   * a set of nodes which haven't been evaluated.
   */
  private final Set<Node> unvisited;

  /**
   * a set of nodes which have been evaluated.
   */
  private final Set<Node> visited;

  /**
   * the closest node to a node selected from the unvisited list.
   */
  private Node closestNode;

  /**
   * constructor sets up the framework required for finding
   * the shortest route.
   */
  public EscapeRouteUtils() {
    this.pathNodes = new HashMap<>();
    this.distanceToNode = new HashMap<>();
    this.unvisited = new HashSet<>();
    this.visited = new HashSet<>();
    this.closestNode = null;
  }

  /**
   * finds all shortest possible routes from the current node to every other
   * available node on the map.  The shortest distances are stored in a map
   * which is then used by the {@see getRoute} method to construct a route to
   * a specified node.
   * @param startNode the node from which routes to all other nodes will be
   *                  calculated.
   */
  public void findRoute(final Node startNode) {

    distanceToNode.put(startNode, 0);
    unvisited.add(startNode);

    while (!unvisited.isEmpty()) {

      closestNode = findClosestNode(unvisited);
      visited.add(closestNode);
      unvisited.remove(closestNode);

      //for the closest node find all the closest node's neighbour nodes
      final Set<Node> neighbours = closestNode.getNeighbours();
      final List<Node> unvNeighbours = findUnvNeighbours(neighbours);

      //use the unvisited neighbours to see if the shortest distance for
      //the closest node can be improved upon.
      updateShortestDistance(unvNeighbours);

    }

  }

  /**
   * returns the shortest path between the current node and the specified
   * end node.
   * @param endNode the node to which the shortest path should be built.
   * @return a list of nodes representing the shortest path between the
   *         current node and the end node.
   */
  public List<Node> getRoute(final Node endNode) {

    final List<Node> route = new LinkedList<>();
    Node nextNode = endNode;
    route.add(nextNode); //add the end node first

    //build the route in reverse order from end to start
    while (pathNodes.get(nextNode) != null) {
      nextNode = pathNodes.get(nextNode);
      route.add(nextNode);
    }

    //ensure the path is returned with the start node first
    Collections.reverse(route);
    return route;

  }

  /**
   * find the node closest to the currentNode picked from the unvisited list.
   * @param unvisited the current set of unvisited nodes.
   * @return the node which is closest to the current node.
   */
  private Node findClosestNode(final Set<Node> unvisited) {
    Node closestNode = unvisited.iterator().next();
    for (final Node node : unvisited) {
      if (getShortestDistance(node) < getShortestDistance(closestNode)) {
        closestNode = node;
      }
    }
    return closestNode;
  }

  /**
   * find the neighbours for a given node that haven't yet been visited.
   * @param neighbours the set of neighbour nodes to check.
   * @return the list of unvisited neighbour nodes.
   */
  private List<Node> findUnvNeighbours(final Set<Node> neighbours) {
    final List<Node> unvNeighbours = new ArrayList<>();
    for (final Node n : neighbours) {
      if (!visited.contains(n)) {
        unvNeighbours.add(n);
      }
    }
    return unvNeighbours;
  }

  /**
   * update the shortest distance possible to a neighbour node.
   * then check if the distance to this neighbour can be reduced.
   * if yes then update distance and add node to unvisited nodes.
   */
  private void updateShortestDistance(final List<Node> unvNeighbours) {
    for (final Node neighbour : unvNeighbours) {
      final Edge edge = closestNode.getEdge(neighbour);
      if (getShortestDistance(neighbour) > getShortestDistance(closestNode) + edge.length()) {
        distanceToNode.put(neighbour, getShortestDistance(closestNode) + edge.length());
        pathNodes.put(neighbour, closestNode);
        unvisited.add(neighbour);
      }
    }
  }

  /**
   * find the shortest distance to a node.
   * if it doesn't exist then assign it a maximum value.
   * @param target the node for which shortest distance is to be deduced.
   * @return the shortest distance.
   */
  private int getShortestDistance(final Node target) {
    Integer dist = distanceToNode.get(target);
    if (dist == null) {
      dist = Integer.MAX_VALUE;
    }
    return dist;
  }

}
