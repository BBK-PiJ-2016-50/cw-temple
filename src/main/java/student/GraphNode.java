package student;


//used for both explore and escape phase.  Different constructors provided for each
public class GraphNode {

  private long id;
  private int distanceToOrb; //explore phase only
  private boolean hasBeenVisited; //explore phase only
  private boolean hasGold; //escape phase only
  private boolean goldCollected; //escape phase only

  //explore phase constructor
  public GraphNode(final long id,
                   final int distanceToOrb,
                   final boolean hasBeenVisited) {
    this.id = id;
    this.distanceToOrb = distanceToOrb;
    this.hasBeenVisited = hasBeenVisited;
  }

  //escape phase constructor
  public GraphNode(final long id,
                   final boolean hasGold,
                   final boolean goldCollected) {
    this.id = id;
    this.hasGold = hasGold;
    this.goldCollected = goldCollected;
  }

  //explore phase only
  public void setHasBeenVisited(final boolean hasBeenVisited) {
    this.hasBeenVisited = hasBeenVisited;
  }

  //escape phase only
  public void setHasGold(final boolean hasGold) {
    this.hasGold = hasGold;
  }

  //escape phase only
  public void setGoldCollected(final boolean goldCollected) {
    this.hasGold = goldCollected;
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

  //escape phase only
  public boolean getHasGold() {
    return hasGold;
  }

  //escape phase only
  public boolean getGoldCollected() {
    return goldCollected;
  }

}
