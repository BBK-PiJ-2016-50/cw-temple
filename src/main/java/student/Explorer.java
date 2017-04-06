package student;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;
import searchexample.Graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    //stack to keep track of visited graph nodes
    Stack<GraphNode> nodeStack = new Stack<>();

    //graph to keep track of nodes and their connections
    ExploreGraph exploreGraph = new ExploreGraph();

    //create root node and add to graph and stack
    GraphNode currentNode = new GraphNode(state.getCurrentLocation(),
                                          state.getDistanceToTarget(),
                                          true);
    exploreGraph.addNode(currentNode);
    nodeStack.push(currentNode);

    while (!orbFound(state)) {

      //add and connect the current node's neighbours if they don't exist as nodes
      Collection<NodeStatus> currentNodeNeighbours = state.getNeighbours();
      for (NodeStatus neighbour : currentNodeNeighbours) {
        if (!exploreGraph.idExists(neighbour.getId())) {
          GraphNode newNode = new GraphNode(neighbour.getId(),
                                            neighbour.getDistanceToTarget(),
                                            false);
          exploreGraph.addNode(newNode);
          exploreGraph.connectNode(currentNode, newNode);
        }
      }

      //find neighbour nodes that haven't been visited
      List<GraphNode> unvisitedNeighbours = exploreGraph.getUnvisitedNeighbours(currentNode);

      //determine which node to move to next
      if (unvisitedNeighbours.isEmpty()) {
        //if the current node has no unvisited neighbours then return to the previous one
        //update the stack and the current node
        nodeStack.pop();
        GraphNode prevNode = nodeStack.peek();
        state.moveTo(prevNode.getId());
        currentNode = prevNode;
      } else {
        //if the node has unvisited neighbours find the closest one to the orb and move to it
        //update the stack, current node and node status
        GraphNode closestNodeToOrb = exploreGraph.getClosestNode(unvisitedNeighbours);
        state.moveTo(closestNodeToOrb.getId());
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

    //use Dijkstra algorithm to solve problem of shortest distance between two points
    //http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
    //returns a shortest path, convert this to a queue and then use this in the while loop

    EscapeRoute escapeRoute = new EscapeRoute();
    escapeRoute.findRoute(state.getCurrentNode());
    Queue<Node> pathToTake = new LinkedList<>(escapeRoute.getRoute(state.getExit()));
    int totalTime = state.getTimeRemaining();


    pathToTake.remove();  // ensures the explorer moves from the start position
    while (!exitFound(state)) {

      Node routeNode = pathToTake.remove();
      state.moveTo(routeNode);
      if (routeNode.getTile().getGold() > 0) {
        state.pickUpGold();
      }

      //optimise to get route which gets most gold gets back within time
      //current solution still doesn't finish on some runs - keep it as looking at immediate neighbour only?
      for (Node neighbour : routeNode.getNeighbours()) {
        Stack<Node> goldTrail = new Stack<>();
        goldTrail.push(routeNode);
        Node current = neighbour;
        while (current.getTile().getGold() > 0 && !pathToTake.contains(current) && goldTrail.size() < 2) {
          state.moveTo(current);
          state.pickUpGold();
          goldTrail.push(current);
          current = current.getNeighbours().iterator().next();
        }
        goldTrail.pop();
        while (!goldTrail.isEmpty()) {
          state.moveTo(goldTrail.pop());
        }
      }
    }
  }

  //checks to see if exit found.  Exit is found if node is not equal to getExit
  private boolean exitFound(EscapeState state) {
    return state.getExit() == state.getCurrentNode();
  }

}
