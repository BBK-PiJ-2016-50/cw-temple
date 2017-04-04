package student;

import game.EscapeState;
import game.ExplorationState;
import game.NodeStatus;

import java.util.Collection;
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
    //create root node and add to graph and stack
    GraphNode currentNode = new GraphNode(state.getCurrentLocation(),
            state.getDistanceToTarget(),
            true);
    searchGraph.addNode(currentNode);
    nodeStack.push(currentNode);

    while (!orbFound(state)) {

      //System.out.println(state.getCurrentLocation());

      //add and connect currentNode's neighbours if they haven't already been added/connected
      Collection<NodeStatus> currentNodeNeighbours = state.getNeighbours();
      for (NodeStatus neighbour : currentNodeNeighbours) {
        if (!searchGraph.idExists(neighbour.getId())) {
          GraphNode newNode = new GraphNode(neighbour.getId(),
                  neighbour.getDistanceToTarget(),
                  false);
          searchGraph.addNode(newNode);
          searchGraph.connectNode(currentNode, newNode);
        }
      }

      //find neighbour nodes that haven't been visited
      List<GraphNode> unvisitedNeighbours = searchGraph.getUnvisitedNeighbours(currentNode);

      if (unvisitedNeighbours.isEmpty()) {
        //if all neighbours visited then stack.pop until you get to a node that has unvisited neighbours
        nodeStack.pop();
        state.moveTo(nodeStack.peek().getId());
        currentNode = nodeStack.peek();
      } else {
        //find closest unvisited node to orb
        GraphNode closestNodeToOrb = searchGraph.getClosestNode(unvisitedNeighbours);
        //move to this unvisited neighbour
        state.moveTo(closestNodeToOrb.getId());
        //update status
        closestNodeToOrb.setHasBeenVisited(true);
        nodeStack.push(closestNodeToOrb);
        currentNode = closestNodeToOrb;
      }

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

    //call the state.getTimeRemaining method to see how many steps can be used
    //rootNode = state.getCurrentNode
    //get all nodes in the graph by calling state.getVertices
    //create a new graph object
    //build a massive map using graph.connectNode
    //get all routes in tree that start from currentNode and end at getExit node and compare
    //then look at route which get most gold, steer clear of worst edges and get back within time.
    //nodes have .getEdge method which can be used to get the edge weight
    //use this queue to to pop off the next tile to moveTo

    while (!exitFound(state)) {

      //when moving to node check its hasGold status.
      //if yes then check its goldCollected status.
      //if no then state.pickUpGold
      //moveTo next node in queue

    }

  }

  //checks to see if exit found.  Exit is found if node is not equal to getExit
  private boolean exitFound(EscapeState state) {
    return state.getExit() == state.getCurrentNode();
  }

}
