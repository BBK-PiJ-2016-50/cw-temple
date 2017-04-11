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
 * provides the explorer with the ability to explore and escape from the cavern.
 */
public class Explorer {

  /**
   * the time at which to abandon gold collecting in the escape phase.
   * this allows the explorer to escape whilst there is still time.
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
    //keeps track of graph nodes and their connections
    final ExploreGraph exploreGraph = new ExploreGraphImpl();
    //creates root node and adds to graph and stack
    GraphNode currentNode = new GraphNodeImpl(
            state.getCurrentLocation(),
            state.getDistanceToTarget(),
            true
    );
    exploreGraph.addNode(currentNode);
    nodeStack.push(currentNode);

    while (!orbFound(state)) {

      //adds and connects the current node's neighbours
      final Collection<NodeStatus> neighbours = state.getNeighbours();
      exploreGraph.addAndConnectNeighbours(currentNode, neighbours);

      //finds neighbour nodes that haven't been visited and determines where to move next
      final List<GraphNode> unvNeighbours = exploreGraph.getUnvisitedNeighbours(currentNode);
      if (unvNeighbours.isEmpty()) { //no unvisited neighbours
        nodeStack.pop();
        final GraphNode prevNode = nodeStack.peek();
        state.moveTo(prevNode.getNodeId());
        currentNode = prevNode;
      } else { //there are unvisited neighbours
        final GraphNode closestNodeToOrb = exploreGraph.getClosestNode(unvNeighbours);
        state.moveTo(closestNodeToOrb.getNodeId());
        closestNodeToOrb.setHasBeenVisited(true);
        nodeStack.push(closestNodeToOrb);
        currentNode = closestNodeToOrb;
      }

    }

  }

  /**
   * allows the explorer to escape from the cavern, collecting as much gold as possible
   * before the ceiling collapses.
   * time is measured in the number of steps taken, and for each step the time remaining is
   * decremented by the weight of the edge taken.
   * the method returns once the exit is reached.
   * @param state the information available at the current state.
   */
  public void escape(final EscapeState state) {

    //create a new escape route to the exit node
    final EscapeRoute escapeRoute = new EscapeRouteImpl();
    escapeRoute.findRoute(state.getCurrentNode());
    final List<Node> shortestRoute = escapeRoute.getRoute(state.getExit());
    final Queue<Node> pathToTake = new LinkedList<>(shortestRoute);

    //begin taking the escape route path
    final Node startNode = pathToTake.remove();
    final Tile startTile = startNode.getTile();
    collectGold(startTile, state);

    while (!exitFound(state)) {

      //move to the next node in the path
      final Node pathNode = pathToTake.remove();
      final Tile pathTile = pathNode.getTile();
      state.moveTo(pathNode);
      collectGold(pathTile, state);

      //look for additional gold
      if (state.getTimeRemaining() > STOP_COLLECTION_TIME) {
        escapeRoute.lookAroundForGold(state, pathToTake, pathNode);
      }

    }

  }

}
