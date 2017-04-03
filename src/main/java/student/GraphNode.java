package student;

public class GraphNode {

  private long id;
  private int distanceToOrb;
  private boolean hasBeenVisited;

  public GraphNode(final long id,
                   final int distanceToOrb,
                   final boolean hasBeenVisited) {
    this.id = id;
    this.distanceToOrb = distanceToOrb;
    this.hasBeenVisited = hasBeenVisited;
  }

  public void setHasBeenVisited(final boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  public long getId() {
    return id;
  }

  public int getDistanceToOrb() {
    return distanceToOrb;
  }

  public boolean getHasBeenVisited() {
    return hasBeenVisited;
  }

}
