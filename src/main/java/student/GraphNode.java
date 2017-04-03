package student;

public class GraphNode {

  private long id;
  private int distanceToOrb;
  private boolean hasBeenVisited;

  public GraphNode(final long id,
                   final int distanceToOrb) {
    this.id = id;
    this.distanceToOrb = distanceToOrb;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public void setDistanceToOrb(final int distanceToOrb) {
    this.distanceToOrb = distanceToOrb;
  }

  public long getId() {
    return id;
  }

  public int getDistanceToOrb() {
    return distanceToOrb;
  }

}
