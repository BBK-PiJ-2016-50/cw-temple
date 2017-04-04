package student;

import game.Edge;
import game.Node;

import java.util.List;

public class EscapeMap {

  private final List<Node> nodes;
  private final List<Edge> edges;

  public EscapeMap(List<Node> nodes, List<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public List<Edge> getEdges() {
    return edges;
  }

}