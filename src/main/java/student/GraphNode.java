package student;


//used for both explore and escape phase.  Different constructors provided for each
public class GraphNode {

  private long id;
  private int distanceToOrb; //explore phase only
  private boolean hasBeenVisited; //explore phase only

  //explore phase constructor
  public GraphNode(final long id,
                   final int distanceToOrb,
                   final boolean hasBeenVisited) {
    this.id = id;
    this.distanceToOrb = distanceToOrb;
    this.hasBeenVisited = hasBeenVisited;
  }

  //explore phase only
  public void setHasBeenVisited(final boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  public long getId() {
    return id;
  }

  //explore phase only
  public int getDistanceToOrb() {
    return distanceToOrb;
  }

  //explore phase only
  public boolean getHasBeenVisited() {
    return hasBeenVisited;
  }

}
