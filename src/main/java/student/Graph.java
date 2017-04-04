package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Graph {

  private final List<GraphNode> nodesInGraph = new ArrayList<>();
  private final Map<GraphNode, List<GraphNode>> nodeConnections = new ConcurrentHashMap<>();

  public void addNode(final GraphNode node) {
    nodesInGraph.add(node);
  }

  public void connectNode(final GraphNode parent, final GraphNode child) {
    List<GraphNode> listOfChildNodes = nodeConnections.get(parent);
    if (listOfChildNodes == null) {
      listOfChildNodes = new ArrayList<>();
    }
    nodeConnections.put(parent, listOfChildNodes);
    listOfChildNodes.add(child);
  }

  public boolean idExists(long id) {
    for (GraphNode node : nodesInGraph) {
      if (node.getId() == id) {
        return true;
      }
    }
    return false;
  }

  //could replace with stream and lambda stuff
  public List<GraphNode> getUnvisitedNeighbours(GraphNode node) {
    List<GraphNode> unvisitedNeighbours = new ArrayList<>();
    List<GraphNode> neighbourNodes = nodeConnections.get(node);
    if (neighbourNodes != null) {
      for (GraphNode neighbour : neighbourNodes) {
        if (!neighbour.getHasBeenVisited()) {
          unvisitedNeighbours.add(neighbour);
        }
      }
    }
    return unvisitedNeighbours;
  }

  public GraphNode getClosestNode(List<GraphNode> unvisitedNeighbours) {
    GraphNode closestNode = unvisitedNeighbours.iterator().next();
    for (GraphNode neighbour : unvisitedNeighbours) {
      if (neighbour.getDistanceToOrb() < closestNode.getDistanceToOrb()) {
        closestNode = neighbour;
      }
    }
    return closestNode;
  }

}
