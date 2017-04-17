package student;

import game.EscapeState;
import game.ExplorationState;
import game.Tile;

/**
 * set of static methods for use in the {@see Explorer} class.
 *
 * @author Ian Robinson
 */
public final class Utils {

  private Utils() { }

  /**
   * used in the explore phase.
   * checks to see if orb found.  Orb is found if distance to target is 0.
   * @param state the information available at the current state.
   * @return boolean indicating whether the orb has been found or not.
   */
  public static boolean orbFound(final ExplorationState state) {
    return state.getDistanceToTarget() == 0;
  }

  /**
   * used in the the escape phase.
   * checks to see if exit found.  Exit is found if current node equals exit node.
   * @param state the information available at the current state.
   * @return boolean indicating whether the exit has been found or not.
   */
  public static boolean exitFound(final EscapeState state) {
    return state.getExit() == state.getCurrentNode();
  }

//  /**
//   * used in the escape phase.
//   * picks up gold from a tile.
//   * @param tile the tile to pick gold up from.
//   * @param state the state of the escape phase.
//   */
//  public static void collectGold(final Tile tile, final EscapeState state) {
//    if (tile.getGold() > 0) {
//      state.pickUpGold();
//    }
//  }

}
