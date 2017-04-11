package student;

import game.Edge;
import game.EscapeState;
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
 * Implementation of {@see EscapeRoute}.
 *
 * @author Ian Robinson
 */
public class EscapeRouteImpl implements EscapeRoute {

  /**
   * stores each visited node and the closest node that it is connected to.
   */
  private static Map<Node, Node> pathNodes;

  /**
   * map of all visited nodes and the distance to another node.
   */
  private static Map<Node, Integer> distanceToNode;

  /**
   * {@inheritDoc}.
   */
  @Override
  public void findRoute(final Node startNode) {
    distanceToNode = new HashMap<>();
    pathNodes = new HashMap<>();
    distanceToNode.put(startNode, 0);
    final Set<Node> unvisited = new HashSet<>();
    final Set<Node> visited = new HashSet<>();
    unvisited.add(startNode);
    //loops through the unvisited nodes until it is empty.
    //initially this will be added to multiple times, but then gradually emptied.
    while (!unvisited.isEmpty()) {
      //find the node closest to the currentNode picked from the unvisited list
      Node closestNode = null;
      for (final Node node : unvisited) {
        if (closestNode == null) {
          closestNode = node;
        } else {
          if (getShortestDistance(node) < getShortestDistance(closestNode)) {
            closestNode = node;
          }
        }
      }
      //move the unvisited node to the visited nodes set
      visited.add(closestNode);
      unvisited.remove(closestNode);
      //for the closest node find all its neighbour nodes
      final List<Node> unvNeighbours = new ArrayList<>();
      final Set<Node> neighbours = closestNode.getNeighbours();
      for (final Node n : neighbours) {
        if (!visited.contains(n)) {
          unvNeighbours.add(n);
        }
      }
      //then for each neighbour node find the minimal distance by looking at all edges
      //then check if the distance to this neighbour can be reduced
      //if it can then the distance is updated and the node is added to the unvisited nodes
      for (final Node neighbour : unvNeighbours) {
        final Edge edge = closestNode.getEdge(neighbour);
        if (getShortestDistance(neighbour) > getShortestDistance(closestNode) + edge.length()) {
          distanceToNode.put(neighbour, getShortestDistance(closestNode) + edge.length());
          pathNodes.put(neighbour, closestNode);
          unvisited.add(neighbour);
        }
      }
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Node> getRoute(final Node endNode) {
    final List<Node> route = new LinkedList<>();
    Node nextNode = endNode;
    route.add(nextNode);
    while (pathNodes.get(nextNode) != null) {
      nextNode = pathNodes.get(nextNode);
      route.add(nextNode);
    }
    //ensure the path is returned with the start node first
    Collections.reverse(route);
    return route;
  }

  /**
   * find the shortest distance to a node.
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
