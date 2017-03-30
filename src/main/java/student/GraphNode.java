package student;

import game.NodeStatus;
import java.util.Collection;

public class GraphNode {

  private long id;
  private boolean hasBeenVisited = false;
  private Collection<NodeStatus> neighbours;
  private int distanceToOrb;

  public GraphNode(long id, 
                   boolean hasBeenVisited,
                   Collection<NodeStatus> neighbours,
                   int distanceToOrb) {
    this.id = id;
    this.hasBeenVisited = hasBeenVisited;
    this.neighbours = neighbours;
    this.distanceToOrb = distanceToOrb;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setHasBeenVisited(boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  public void setNeighbours(Collection<NodeStatus> neighbours) {
    this.neighbours = neighbours;
  }

  public void setDistanceToOrb(int distanceToOrb) {
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
