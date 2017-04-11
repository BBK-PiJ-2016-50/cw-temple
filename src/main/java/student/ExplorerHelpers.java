package student;

import game.EscapeState;
import game.ExplorationState;
import game.Tile;

import java.util.List;

/**
 * set of static methods for use in the {@see Explorer} class.
 *
 * @author Ian Robinson
 */
public class ExplorerHelpers {

  /**
   * checks to see if orb found.  Orb is found if distance to target is 0.
   * @param state the information available at the current state.
   * @return boolean indicating whether the orb has been found or not.
   */
  public static boolean orbFound(final ExplorationState state) {
    return state.getDistanceToTarget() == 0;
  }

  /**
   * checks to see if exit found.  Exit is found if current node equals exit node.
   * @param state the information available at the current state.
   * @return boolean indicating whether the exit has been found or not.
   */
  public static boolean exitFound(final EscapeState state) {
    return state.getExit() == state.getCurrentNode();
  }

  /**
   * picks up gold from a tile.
   * @param tile the tile to pick gold up from.
   * @param state the state of the escape phase.
   */
  public static void collectGold(final Tile tile, final EscapeState state) {
    if (tile.getGold() > 0) {
      state.pickUpGold();
    }
  }

  /**
   * finds the node closest to the orb that has not yet been visited.
   * @param unvNeighbours a list of unvisited neighbours which may be moved to.
   * @return the unvisited neighbour node that is closest to the orb.
   */
  public static GraphNode getClosestNode(final List<GraphNode> unvNeighbours) {
    //grab any unvisited node and compare the rest of them to it
    GraphNode closestNode = unvNeighbours.iterator().next();
    for (final GraphNode neighbour : unvNeighbours) {
      if (neighbour.getDistanceToOrb() < closestNode.getDistanceToOrb()) {
        closestNode = neighbour;
      }
    }
    return closestNode;
  }

}
