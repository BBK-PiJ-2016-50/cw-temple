package student;

/**
 * used in the explore phase of {@see Explorer}.
 * the nodes are used to build up a graph of points.
 * a combination of the {@see distanceToOrb} and {@see hasBeenVisited} parameters
 * is used to help determine the route to take.
 *
 * @author Ian Robinson
 */
public interface GraphNode {

  /**
   * sets the hasBeenVisited flag to true or false.
   * @param hasBeenVisited the new value for the hasBeenVisited flag.
   */
  void setHasBeenVisited(boolean hasBeenVisited);

  /**
   * returns the node's id.
   * @return the id value for the node.
   */
  long getNodeId();

  /**
   * returns the distance between the node and the orb.
   * @return the distanceToOrb value.
   */
  int getDistanceToOrb();

  /**
   * indicates whether the node has been visited before or not.
   * @return boolean value stating whether node has been visited or not.
   */
  boolean getHasBeenVisited();

}
