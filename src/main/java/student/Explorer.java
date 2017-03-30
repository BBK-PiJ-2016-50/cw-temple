package student;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;

import java.util.Collection;
import java.util.Stack;

public class Explorer {

  /**
   * Explore the cavern, trying to find the orb in as few steps as possible.
   * Once you find the orb, you must return from the function in order to pick
   * it up. If you continue to move after finding the orb rather
   * than returning, it will not count.
   * If you return from this function while not standing on top of the orb,
   * it will count as a failure.
   *   
   * <p>There is no limit to how many steps you can take, but you will receive
   * a score bonus multiplier for finding the orb in fewer steps.</p>
   * 
   * <p>At every step, you only know your current tile's ID and the ID of all
   * open neighbor tiles, as well as the distance to the orb at each of these tiles
   * (ignoring walls and obstacles).</p>
   * 
   * <p>To get information about the current state, use functions
   * getCurrentLocation(),
   * getNeighbours(), and
   * getDistanceToTarget()
   * in ExplorationState.
   * You know you are standing on the orb when getDistanceToTarget() is 0.</p>
   *
   * <p>Use function moveTo(long id) in ExplorationState to move to a neighboring
   * tile by its ID. Doing this will change state to reflect your new position.</p>
   *
   * <p>A suggested first implementation that will always find the orb, but likely won't
   * receive a large bonus multiplier, is a depth-first search.</p>
   *
   * @param state the information available at the current state
   */
  public void explore(ExplorationState state) {

    //create a stack.  This will keep track of nodes that have been visited
    //create a new graph. Nodes will be added to graph and connected to each other via graph
    //need to keep track of current node.  This gets updated at the end of each loop - may not need this. Could use stack.peek at end of while loop
    //add root node to stack = new GraphNode()

    //while (!orbFound(state)) - check to see if distance to target is 0.  If yes then orb found and return

    //if dead-end then stack.pop until you get to a node that has neighbours which haven't been visited yet
    //else:
    //create new Node with tile id, neighbours and hasBeenVisited information

    //find information about neighbours of current tile Node.getNeighbours
    //if tile has an edge then keep following the edge if possible for subsequent nodes
    //make a decision as to which neighbour would be most appropriate to move to using NodeStatus.compareTo()

    //connect current node with node we are moving to
    //moveTo() tile with id that was determined as the best next option
    //stack.push operation
    //update current node with node moved to (or could this be done using stack.peek, rather than keeping separate current node variable?


    //return; - executes when distance to target is 0

  }

  //checks to see if orb found.  Orb is found if distance to target is 0
  private boolean orbFound(ExplorationState state) {
    return state.getDistanceToTarget() == 0;
  }

  /**
   * Escape from the cavern before the ceiling collapses, trying to collect as much
   * gold as possible along the way. Your solution must ALWAYS escape before time runs
   * out, and this should be prioritized above collecting gold.
   *
   * <p>You now have access to the entire underlying graph, which can be accessed 
   * through EscapeState.
   * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
   * will return a collection of all nodes on the graph.</p>
   * 
   * <p>Note that time is measured entirely in the number of steps taken, and for each step
   * the time remaining is decremented by the weight of the edge taken. You can use
   * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
   * on your current tile (this will fail if no such gold exists), and moveTo() to move
   * to a destination node adjacent to your current node.</p>
   * 
   * <p>You must return from this function while standing at the exit. Failing to do so before time
   * runs out or returning from the wrong location will be considered a failed run.</p>
   * 
   * <p>You will always have enough time to escape using the shortest path from the starting
   * position to the exit, although this will not collect much gold.</p>
   *
   * @param state the information available at the current state
   */
  public void escape(EscapeState state) {
    //TODO: Escape from the cavern before time runs out
  }
}
