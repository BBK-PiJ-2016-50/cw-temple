package student;

import game.NodeStatus;
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

  public List<GraphNode> getNodesInGraph() {
    return nodesInGraph;
  }

  public GraphNode findNodeById(long id) {
    for (GraphNode node : nodesInGraph) {
      if (node.getId() == id) {
        return node;
      }
    }
    return null;
  }

  public List<NodeStatus> getUnvisitedNeighbours(GraphNode node) {
    List<NodeStatus> unvisitedNeighbours  = new ArrayList<>();
    for (NodeStatus neighbour : node.getNeighbours()) {
      boolean connected = false;
      for (GraphNode visited : nodeConnections.get(node)) {
        if (neighbour.getId() == visited.getId()) {
          connected = true;
        }
      }
      if (!connected) {
        unvisitedNeighbours.add(neighbour);
      }
    }
    return unvisitedNeighbours;
  }

}
