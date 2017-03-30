package student;

import game.NodeStatus;
import java.util.Collection;

public class GraphNode {

  private long id;
  private boolean hasBeenVisited = false;
  private Collection<NodeStatus> neighbours;
  private int distanceToOrb;

  public GraphNode(final long id,
                   final boolean hasBeenVisited,
                   final Collection<NodeStatus> neighbours,
                   final int distanceToOrb) {
    this.id = id;
    this.hasBeenVisited = hasBeenVisited;
    this.neighbours = neighbours;
    this.distanceToOrb = distanceToOrb;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public void setHasBeenVisited(final boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  public void setNeighbours(final Collection<NodeStatus> neighbours) {
    this.neighbours = neighbours;
  }

  public void setDistanceToOrb(final int distanceToOrb) {
    this.distanceToOrb = distanceToOrb;
  }

  public long getId() {
    return id;
  }

  public boolean getHasBeenVisited() {
    return hasBeenVisited;
  }

  public Collection<NodeStatus> getNeighbours() {
    return neighbours;
  }

  public int getDistanceToOrb() {
    return distanceToOrb;
  }

}
