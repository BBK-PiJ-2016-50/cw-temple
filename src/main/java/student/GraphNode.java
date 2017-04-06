package student;

/**
 * used in the explore phase of {@see Explorer}.
 * the nodes are used to build up a graph of points.
 * a combination of the {@see distanceToOrb} and {@see hasBeenVisited} parameters
 * is used to help determine the route to take.
 *
 * @author Ian Robinson
 */
public class GraphNode {

  /**
   * the unique long value assigned to the node.
   */
  private long id;

  /**
   * the distance from this node to the orb as an int value.
   */
  private int distanceToOrb;

  /**
   * boolean flag stating whether node has been visited or not.
   */
  private boolean hasBeenVisited;

  /**
   * constructor for generating the graph node.
   * @param id the unique id for the node.
   * @param distanceToOrb the distance the node is from the orb.
   * @param hasBeenVisited indicates whether the node has been visited before or not.
   */
  public GraphNode(final long id,
                   final int distanceToOrb,
                   final boolean hasBeenVisited) {
    this.id = id;
    this.distanceToOrb = distanceToOrb;
    this.hasBeenVisited = hasBeenVisited;
  }

  /**
   * sets the hasBeenVisited flag to true or false.
   * @param hasBeenVisited the new value for the hasBeenVisited flag.
   */
  public void setHasBeenVisited(final boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  /**
   * returns the node's id.
   * @return the id value for the node.
   */
  public long getId() {
    return id;
  }

  /**
   * returns the distance between the node and the orb.
   * @return the distanceToOrb value.
   */
  public int getDistanceToOrb() {
    return distanceToOrb;
  }

  /**
   * indicates whether the node has been visited before or not.
   * @return boolean value stating whether node has been visited or not.
   */
  public boolean getHasBeenVisited() {
    return hasBeenVisited;
  }

}
