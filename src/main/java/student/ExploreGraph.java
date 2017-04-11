package student;

import java.util.List;
import java.util.Map;

/**
 * graph keeps track of nodes {@see GraphNode} found in the explore phase.
 * it connects nodes together to help create a map representing the cavern.
 *
 * @author Ian Robinson
 */
public interface ExploreGraph {

  /**
   * returns the list of nodes that exist in the graph.
   * @return the list of nodes that exist for the graph.
   */
  List<GraphNode> getNodesInGraph();

  /**
   * returns the list of node connections that make up the graph.
   * @return the list of node connections that exist for the graph.
   */
  Map<GraphNode, List<GraphNode>> getNodeConnections();

  /**
   * adds a node to the graph by storing it in the NodesInGraph variable.
   * @param node the node that will be added to the graph.
   */
  void addNode(GraphNode node);

  /**
   * connects a node to a neighbour node, thus allowing the map to be built.
   * @param parent the parent node of the connection.
   * @param child the child node of the connection.
   */
  void connectNode(GraphNode parent, GraphNode child);

  /**
   * determines whether a node exists in the graph or not.
   * if the id can be found then it returns true, otherwise false.
   * @param nodeId the id of the node to be searched for.
   * @return boolean indicating whether the node exists in the graph or not.
   */
  boolean idExists(long nodeId);

  /**
   * finds a node's neighbours which haven't yet been visited and returns them.
   * helps to determine which node to visit next in the the explore phase.
   * @param node the node that the explorer is currently on.
   * @return a list of neighbour nodes that haven't yet been visited.
   */
  List<GraphNode> getUnvisitedNeighbours(GraphNode node);

}
