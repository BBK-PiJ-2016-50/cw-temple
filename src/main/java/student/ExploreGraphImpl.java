package student;

import game.NodeStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@see ExploreGraph}.
 *
 * @author Ian Robinson
 */
public class ExploreGraphImpl implements ExploreGraph {

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
   * {@inheritDoc}.
   */
  @Override
  public List<GraphNode> getNodesInGraph() {
    return NodesInGraph;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public Map<GraphNode, List<GraphNode>> getNodeConnections() {
    return NodeConnections;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void addNode(final GraphNode node) {
    NodesInGraph.add(node);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void connectNode(final GraphNode parent, final GraphNode child) {
    List<GraphNode> listOfChildNodes = NodeConnections.get(parent);
    if (listOfChildNodes == null) {
      listOfChildNodes = new ArrayList<>();
    }
    NodeConnections.put(parent, listOfChildNodes);
    listOfChildNodes.add(child);
  }

  /**
   * {@inheritDoc}.
   */
  @Override
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
   * {@inheritDoc}.
   */
  @Override
  public List<GraphNode> getUnvisitedNeighbours(final GraphNode node) {
    final List<GraphNode> unvNeighbours = new ArrayList<>();
    final List<GraphNode> neighbourNodes = NodeConnections.get(node);
    if (neighbourNodes != null) {
      for (final GraphNode neighbour : neighbourNodes) {
        if (!neighbour.getHasBeenVisited()) {
          unvNeighbours.add(neighbour);
        }
      }
    }
    return unvNeighbours;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void addAndConnectNeighbours(GraphNode currentNode, Collection<NodeStatus> neighbours) {
    for (final NodeStatus neighbour : neighbours) {
      if (!idExists(neighbour.getId())) {
        final GraphNode newNode = new GraphNodeImpl(
                neighbour.getId(),
                neighbour.getDistanceToTarget(),
                false
        );
        addNode(newNode);
        connectNode(currentNode, newNode);
      }
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public GraphNode getClosestNode(final List<GraphNode> unvNeighbours) {
    GraphNode closestNode = unvNeighbours.iterator().next();
    for (final GraphNode neighbour : unvNeighbours) {
      if (neighbour.getDistanceToOrb() < closestNode.getDistanceToOrb()) {
        closestNode = neighbour;
      }
    }
    return closestNode;
  }

}
