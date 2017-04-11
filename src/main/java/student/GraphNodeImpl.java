package student;

/**
 * Implementation of {@see GraphNode}.
 *
 * @author Ian Robinson
 */
public class GraphNodeImpl implements GraphNode {

  /**
   * the unique long value id assigned to the node.
   */
  private final long nodeId;

  /**
   * the distance from this node to the orb as an int value.
   */
  private final int distanceToOrb;

  /**
   * boolean flag stating whether node has been visited or not.
   */
  private boolean hasBeenVisited;

  /**
   * constructor for creating the graph node.
   * @param nodeId the unique id for the node.
   * @param distanceToOrb the distance the node is from the orb.
   * @param hasBeenVisited indicates if the node has been visited or not.
   */
  public GraphNodeImpl(
          final long nodeId,
          final int distanceToOrb,
          final boolean hasBeenVisited
  ) {
    this.nodeId = nodeId;
    this.distanceToOrb = distanceToOrb;
    this.hasBeenVisited = hasBeenVisited;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void setHasBeenVisited(final boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public long getNodeId() {
    return nodeId;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int getDistanceToOrb() {
    return distanceToOrb;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean getHasBeenVisited() {
    return hasBeenVisited;
  }

}
