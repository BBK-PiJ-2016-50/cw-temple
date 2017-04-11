package student;

import static student.ExplorerHelpers.collectGold;
import static student.ExplorerHelpers.exitFound;
import static student.ExplorerHelpers.orbFound;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;
import game.Tile;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * provides the explorer with the ability to explore the cavern
 * and to escape from the cavern.
 */
public class Explorer {

  /**
   * the time at which to abandon gold collecting in the escape phase.
   * this allows explorer to escape whilst there is still time.
   */
  private static final int STOP_COLLECTION_TIME = 50;

  /**
   * allows the explorer to explore the cavern, trying to find the orb in as
   * few steps as possible. Once the orb is found the escape phase begins.
   * @param state the information available at the current state.
   */
  public void explore(final ExplorationState state) {

    //keeps track of visited graph nodes
    final Stack<GraphNode> nodeStack = new Stack<>();
    //keeps track of nodes and their connections
    final ExploreGraph exploreGraph = new ExploreGraphImpl();
    //create root node and add to graph and stack
    GraphNode currentNode = new GraphNodeImpl(
            state.getCurrentLocation(),
            state.getDistanceToTarget(),
            true
    );
    exploreGraph.addNode(currentNode);
    nodeStack.push(currentNode);

    while (!orbFound(state)) {

      //add and connect the current node's neighbours if they don't exist as nodes
      final Collection<NodeStatus> neighbours = state.getNeighbours();
      exploreGraph.addAndConnectNeighbours(currentNode, neighbours);

      //find neighbour nodes that haven't been visited
      final List<GraphNode> unvNeighbours = exploreGraph.getUnvisitedNeighbours(currentNode);

      //determine which node to move to next
      if (unvNeighbours.isEmpty()) {
        //if the current node has no unvisited neighbours then return to the previous one
        //update the stack and the current node
        nodeStack.pop();
        final GraphNode prevNode = nodeStack.peek();
        state.moveTo(prevNode.getNodeId());
        currentNode = prevNode;
      } else {
        //if the node has unvisited neighbours find the closest one to the orb
        //update the stack, current node and node status
        final GraphNode closestNodeToOrb = exploreGraph.getClosestNode(unvNeighbours);
        state.moveTo(closestNodeToOrb.getNodeId());
        closestNodeToOrb.setHasBeenVisited(true);
        nodeStack.push(closestNodeToOrb);
        currentNode = closestNodeToOrb;
      }

    }

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
  public void escape(final EscapeState state) {

    //create a new escape route
    final EscapeRoute escapeRoute = new EscapeRouteImpl();

    //using the start node, find the shortest distance to all other nodes
    escapeRoute.findRoute(state.getCurrentNode());

    //find the shortest route to the exit node
    final List<Node> shortestRoute = escapeRoute.getRoute(state.getExit());

    //convert the shortestPath into a queue
    final Queue<Node> pathToTake = new LinkedList<>(shortestRoute);

    //remove the first item from the queue
    //this ensures the explorer can move from the start position
    final Node startNode = pathToTake.remove();

    //collect gold from this tile
    final Tile startTile = startNode.getTile();
    collectGold(startTile, state);

    while (!exitFound(state)) {

      //move to the next node in the path to take
      final Node routeNode = pathToTake.remove();
      final Tile routeTile = routeNode.getTile();
      state.moveTo(routeNode);

      //ensure that gold is collected if there is any
      collectGold(routeTile, state);

      //find optimal route to collect most gold within given time
      //whilst walking the path to exit, check neighbour nodes for gold
      //if they do have gold, then move to the node and collect it
      //then collect from any subsequent neighbours if they also have gold
      //stack used to ensure explorer can get back to the escape path
      if (state.getTimeRemaining() > STOP_COLLECTION_TIME) {
        final Stack<Node> goldTrail = new Stack<>();
        for (final Node neighbour : routeNode.getNeighbours()) {
          goldTrail.push(routeNode);
          Node current = neighbour;
          final Tile currentTile = current.getTile();
          //if the current tile has gold, and isn't part of the pathToTake
          //the move to tile and pick up the gold
          while (currentTile.getGold() > 0 && !pathToTake.contains(current)) {
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
  }

}
