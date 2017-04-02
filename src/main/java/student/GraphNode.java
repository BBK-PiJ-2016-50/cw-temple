package student;

import game.NodeStatus;
import java.util.Collection;

public class GraphNode {

  private long id;
  private Collection<NodeStatus> neighbours;
  private int distanceToOrb;

  public GraphNode(final long id,
                   final int distanceToOrb,
                   final Collection<NodeStatus> neighbours) {
    this.id = id;
    this.neighbours = neighbours;
    this.distanceToOrb = distanceToOrb;
  }

  public void setId(final long id) {
    this.id = id;
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

  public Collection<NodeStatus> getNeighbours() {
    return neighbours;
  }

  public int getDistanceToOrb() {
    return distanceToOrb;
  }

}
