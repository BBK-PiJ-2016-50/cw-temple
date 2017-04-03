package student;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;

import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    Stack<GraphNode> nodeStack = new Stack<>();  //keeps track of nodes that have been visited
    Graph searchGraph = new Graph();
    GraphNode currentNode;

    //create root node and add to graph and stack
    GraphNode rootNode = new GraphNode(state.getCurrentLocation(),
            state.getDistanceToTarget());
    currentNode = rootNode;
    searchGraph.addNode(rootNode);
    nodeStack.push(rootNode);

    while (!orbFound(state)) {

      //add and connect currentNode's neighbours if they haven't already been added/connected
      Collection<NodeStatus> currentNodeNeighbours = state.getNeighbours();
      for (NodeStatus neighbour : currentNodeNeighbours) {
        if (!searchGraph.idExists(neighbour.getId())) {
          GraphNode newNode = new GraphNode(neighbour.getId(),
                  neighbour.getDistanceToTarget());
          searchGraph.connectNode(currentNode, newNode);
        }
      }


      //if current node has been visited before then assign to currentNode and add to stack
      // if not visited before then create a new node and add it to the graph, add to stack and assign to currentNode
      GraphNode tileNode = searchGraph.findNodeById(state.getCurrentLocation());
      if (searchGraph.getNodesInGraph().contains(tileNode)) {
        currentNode = tileNode;
        nodeStack.push(tileNode);
      } else {
        GraphNode newNode = new GraphNode(state.getCurrentLocation(),
                state.getDistanceToTarget(),
                state.getNeighbours());
        searchGraph.addNode(newNode);
        currentNode = newNode;
        nodeStack.push(newNode);
      }

      //find information about neighbours of current tile
      List<NodeStatus> unvisitedNeighbours = searchGraph.getUnvisitedNeighbours(currentNode);
      //see if the node has any unvisited neighbours
      if (!unvisitedNeighbours.isEmpty()) {
        //if yes make a decision as to which neighbour would be most appropriate to move to
        unvisitedNeighbours.sort(Comparator.comparing(NodeStatus::getDistanceToTarget));
        NodeStatus closestTileToOrb = unvisitedNeighbours.get(0);
        //moveTo() tile with id that was determined as the best next option
        state.moveTo(closestTileToOrb.getId());
      } else {
        //if dead-end then stack.pop until you get to a node that has neighbours which haven't been visited yet
        System.out.println("placeholder");
      }

      //connect current node with node we are moving to (i.e. the parent to the child)
      searchGraph.connectNode(currentNode, closestNodeToOrb);
    }

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
