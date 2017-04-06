package student;

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
 * builds up a picture of the map for use when escaping.
 * uses Dijstra's algorithm to find shortest distance between two points.
 *
 * @author Ian Robinson
 */
public class EscapeRoute {

  /**
   * stores each visited node and the closest node that it is connected to.
   */
  private Map<Node, Node> pathNodes;

  /**
   * map of all visited nodes and the distance to another node.
   */
  private Map<Node, Integer> distanceToNode;

  /**
   * finds the shortest route from the start node to the exit node.
   * @param startNode the node that the explorer starts from when starting the escape.
   */
  public void findRoute(final Node startNode) {
    distanceToNode = new HashMap<>();
    pathNodes = new HashMap<>();
    distanceToNode.put(startNode, 0);
    Set<Node> unvisited = new HashSet<>();
    unvisited.add(startNode);
    //loops through the unvisited nodes until it is empty.
    //initially this will be added to multiple times, but then gradually emptied.
    while (unvisited.size() > 0) {
      //find the node closest to the currentNode picked from the unvisited list
      Node closestNode = null;
      for (Node node : unvisited) {
        if (closestNode == null) {
          closestNode = node;
        } else {
          if (getShortestDistance(node) < getShortestDistance(closestNode)) {
            closestNode = node;
          }
        }
      }
      //move the unvisited node to the visited nodes set
      Set<Node> visited = new HashSet<>();
      visited.add(closestNode);
      unvisited.remove(closestNode);
      //for the closest node find all its neighbour nodes
      List<Node> unvisitedNeighbours = new ArrayList<>();
      for (Node n : closestNode.getNeighbours()) {
        if (!visited.contains(n)) {
          unvisitedNeighbours.add(n);
        }
      }
      //then for each neighbour node find the minimal distance by looking at all edges
      //then check if the distance to this neighbour can be reduced
      //if it can then the distance is updated and the node is added unvisited nodes
      for (Node neighbour : unvisitedNeighbours) {
        int edgeLength = closestNode.getEdge(neighbour).length();
        if (getShortestDistance(neighbour) > getShortestDistance(closestNode) + edgeLength) {
          distanceToNode.put(neighbour, getShortestDistance(closestNode) + edgeLength);
          pathNodes.put(neighbour, closestNode);
          unvisited.add(neighbour);
        }
      }
    }
  }

  /**
   * find the shortest distance to a node.
   * @param target the node for which shortest distance is to be deduced.
   * @return the shortest distance.
   */
  private int getShortestDistance(final Node target) {
    Integer dist = distanceToNode.get(target);
    if (dist == null) {
      return Integer.MAX_VALUE;
    } else {
      return dist;
    }
  }

  /**
   * uses the pathNodes map to construct the shortest path between the start node and the exit node.
   * @param endNode the node which the explorer wants to get to.
   * @return a list of nodes containing the complete shortest route from start to exit.
   */
  public List<Node> getRoute(final Node endNode) {
    List<Node> route = new LinkedList<>();
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

}
