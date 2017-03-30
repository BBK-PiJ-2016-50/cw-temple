package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Graph {

  private final List<GraphNode> nodesInGraph = new ArrayList<>();
  private final Map<GraphNode, List<GraphNode>> nodeConnections = new ConcurrentHashMap<>();
  private GraphNode root;

  public GraphNode getRoot() {
    return root;
  }

  public void setRoot(final GraphNode node) {
    root = node;
  }

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

}
