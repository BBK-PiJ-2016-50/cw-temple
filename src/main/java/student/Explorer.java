package student;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * provides the explorer with the ability to explore and escape from the cavern.
 */
public class Explorer {

  /**
   * allows the explorer to explore the cavern, trying to find the orb in as
   * few steps as possible. Once the orb is found the escape phase begins.
   *
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

    while (!exploreGraph.orbFound(state)) {

      //adds and connects the current node's neighbours
      final Collection<NodeStatus> neighbours = state.getNeighbours();
      exploreGraph.addAndConnectNeighbours(currentNode, neighbours);

      //finds neighbour nodes that haven't been visited
      final List<GraphNode> unvNeighbours = exploreGraph.getUnvisitedNeighbours(currentNode);

      //determine where to move next
      if (unvNeighbours.isEmpty()) { //if no unvisited neighbours then go back
        nodeStack.pop();
        final GraphNode prevNode = nodeStack.peek();
        state.moveTo(prevNode.getNodeId());
        currentNode = prevNode;
      } else { //if there are unvisited neighbours find best on to move to
        final GraphNode closestNodeToOrb = exploreGraph.getClosestNode(unvNeighbours);
        state.moveTo(closestNodeToOrb.getNodeId());
        closestNodeToOrb.setHasBeenVisited(true);
        nodeStack.push(closestNodeToOrb);
        currentNode = closestNodeToOrb;
      }

    }

  }

  /**
   * allows the explorer to escape from the cavern, collecting as much gold
   * as possible before the ceiling collapses.
   * time is measured in the number of steps taken, and for each step the
   * time remaining is decremented by the weight of the edge taken.
   * the method returns once the exit is reached.
   *
   * @param state the information available at the current state.
   */
  public void escape(final EscapeState state) {

    //create the escape route
    final EscapeRoute escapeRoute = new EscapeRouteImpl(
            state.getCurrentNode(),
            state.getExit(),
            state.getVertices(),
            state.getTimeRemaining()
    );

    //work out the best route for gold
    List<Node> bestGoldRoute = escapeRoute.bestGoldRoute();

    //follow this route and escape the cavern
    escapeRoute.takeRoute(bestGoldRoute, state);
  }

}
