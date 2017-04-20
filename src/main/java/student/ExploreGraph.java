package student;

import game.ExplorationState;
import game.NodeStatus;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * a graph to keep track of {@see GraphNode} found in the explore phase.
 * it connects nodes together to help create a map representing the cavern.
 * this helps the explorer to plot their route and find the orb.
 *
 * @author Ian Robinson
 */
public interface ExploreGraph {

  /**
   * returns the list of nodes that exist in the graph.
   *
   * @return the list of nodes that exist for the graph.
   */
  List<GraphNode> getNodesInGraph();

  /**
   * returns the list of node connections that make up the graph.
   *
   * @return the list of node connections that exist for the graph.
   */
  Map<GraphNode, List<GraphNode>> getNodeConnections();

  /**
   * adds a node to the graph.
   *
   * @param node the node that will be added to the graph.
   */
  void addNode(GraphNode node);

  /**
   * connects a node to a neighbour node, thus allowing the map to be built.
   *
   * @param parent the parent node of the connection.
   * @param child the child node of the connection.
   */
  void connectNode(GraphNode parent, GraphNode child);

  /**
   * determines whether a node exists in the graph or not.
   * if the id can be found then it returns true, otherwise false.
   *
   * @param nodeId the id of the node to be searched for.
   * @return boolean indicating whether the node exists in the graph or not.
   */
  boolean idExists(long nodeId);

  /**
   * finds a node's neighbours which haven't yet been visited and returns them.
   * helps to determine which node to visit next in the the explore phase.
   *
   * @param node the node that the explorer is currently on.
   * @return a list of neighbour nodes that haven't yet been visited.
   */
  List<GraphNode> getUnvisitedNeighbours(GraphNode node);

  /**
   * adds and connects a node's neighbours if they don't already exist as
   * node's in the graph.
   *
   * @param current the node that the explorer is currently on.
   * @param neighbours the current node's neighbours
   */
  void addAndConnectNeighbours(GraphNode current, Collection<NodeStatus> neighbours);

  /**
   * finds the node closest to the orb that has not yet been visited.
   *
   * @param unvNeighbours a list of unvisited neighbours which may be moved to.
   * @return the unvisited neighbour node that is closest to the orb.
   */
  GraphNode getClosestNode(List<GraphNode> unvNeighbours);

  /**
   * checks to see if orb found.  Orb is found if distance to target is 0.
   *
   * @param state the information available at the current state.
   * @return boolean indicating whether the orb has been found or not.
   */
  boolean orbFound(ExplorationState state);

}
