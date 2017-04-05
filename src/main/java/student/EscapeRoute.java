package student;

import game.Node;

import java.util.*;

public class EscapeRoute {

  private Map<Node, Node> pathNodes;
  private Map<Node, Integer> distanceToNode;

  public void findRoute(Node startNode) {
    distanceToNode = new HashMap<>();
    pathNodes = new HashMap<>();
    distanceToNode.put(startNode, 0);
    Set<Node> unvisited = new HashSet<>();
    unvisited.add(startNode);
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
      Set<Node> visited = new HashSet<>();
      visited.add(closestNode);
      unvisited.remove(closestNode);
      //for the closest node find the minimal distances to unvisited neighbours
      List<Node> unvisitedNeighbours = new ArrayList<>();
      for (Node n : closestNode.getNeighbours()) {
        if (!visited.contains(n)) {
          unvisitedNeighbours.add(n);
        }
      }
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

  //method to find the shortest distance
  private int getShortestDistance(Node target) {
    Integer dist = distanceToNode.get(target);
    if (dist == null) {
      return Integer.MAX_VALUE;
    } else {
      return dist;
    }
  }

  public List<Node> getRoute(Node endNode) {
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
