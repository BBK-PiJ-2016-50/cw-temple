package student;

import game.Node;
import game.NodeStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * graph keeps track of nodes {@see GraphNode} found in the explore phase.
 * it connects nodes together to help create a map representing the cavern.
 *
 * @author Ian Robinson
 */
public class ExploreGraph {

  /**
   * list of all nodes that have been added to the graph.
   */
  private static final List<GraphNode> NodesInGraph = new ArrayList<>();

  /**
   * map of each node and their child nodes.
   * provides a complete picture of the graph's node connections.
   */
  private static final Map<GraphNode, List<GraphNode>> NodeConnections = new ConcurrentHashMap<>();

  /**
   * returns the list of nodes that exist in the graph.
   * @return the list of nodes that exist for the graph.
   */
  public List<GraphNode> getNodesInGraph() {
    return NodesInGraph;
  }

  /**
   * returns the list of node connections that make up the graph.
   * @return the list of node connections that exist for the graph.
   */
  public Map<GraphNode, List<GraphNode>> getNodeConnections() {
    return NodeConnections;
  }

  /**
   * adds a node to the graph by storing it in the NodesInGraph variable.
   * @param node the node that will be added to the graph.
   */
  public void addNode(final GraphNode node) {
    NodesInGraph.add(node);
  }

  /**
   * connects a node to a neighbour node, thus allowing the map to be built.
   * @param parent the parent node of the connection.
   * @param child the child node of the connection.
   */
  public void connectNode(final GraphNode parent, final GraphNode child) {
    List<GraphNode> listOfChildNodes = NodeConnections.get(parent);
    if (listOfChildNodes == null) {
      listOfChildNodes = new ArrayList<>();
    }
    NodeConnections.put(parent, listOfChildNodes);
    listOfChildNodes.add(child);
  }

  /**
   * determines whether a node exists in the graph or not.
   * if the id can be found then it returns true, otherwise false.
   * @param nodeId the id of the node to be searched for.
   * @return boolean indicating whether the node exists in the graph or not.
   */
  public boolean idExists(final long nodeId) {
    boolean exists = false;
    for (final GraphNode node : NodesInGraph) {
      if (node.getNodeId() == nodeId) {
        exists = true;
        break;
      }
    }
    return exists;
  }

  /**
   * finds a node's neighbours which haven't yet been visited and returns them.
   * helps to determine which node to visit next in the the explore phase.
   * @param node the node that the explorer is currently on.
   * @return a list of neighbour nodes that haven't yet been visited.
   */
  public List<GraphNode> getUnvisitedNeighbours(final GraphNode node) {
    final List<GraphNode> unvNeighbours = new ArrayList<>();
    final List<GraphNode> neighbourNodes = NodeConnections.get(node);
    if (neighbourNodes != null) { //without this statement a nullPointerException could be raised
      for (final GraphNode neighbour : neighbourNodes) {
        if (!neighbour.getHasBeenVisited()) {
          unvNeighbours.add(neighbour);
        }
      }
    }
    return unvNeighbours;
  }

  /**
   * finds the node closest to the orb that has not yet been visited.
   * @param unvNeighbours a list of unvisited neighbours which may be moved to.
   * @return the unvisited neighbour node that is closest to the orb.
   */
  public GraphNode getClosestNode(final List<GraphNode> unvNeighbours) {
    //grab any unvisited node and compare the rest of them to it
    GraphNode closestNode = unvNeighbours.iterator().next();
    for (final GraphNode neighbour : unvNeighbours) {
      if (neighbour.getDistanceToOrb() < closestNode.getDistanceToOrb()) {
        closestNode = neighbour;
      }
    }
    return closestNode;
  }

}
