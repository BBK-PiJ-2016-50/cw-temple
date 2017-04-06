package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * graph keeps track of nodes {@see GraphNode} that have been found in the explore phase.
 * it connects nodes together to help create a map representing the cavern.
 *
 * @author Ian Robinson
 */
public class ExploreGraph {

  /**
   * list of all nodes that have been added to the graph.
   */
  private final List<GraphNode> nodesInGraph = new ArrayList<>();

  /**
   * map of each node and their child nodes.
   * provides a complete picture of the graph's node connections.
   */
  private final Map<GraphNode, List<GraphNode>> nodeConnections = new ConcurrentHashMap<>();

  /**
   * adds a node to the graph by storing it in the nodesInGraph variable.
   * @param node the node that will be added to the graph.
   */
  public void addNode(final GraphNode node) {
    nodesInGraph.add(node);
  }

  /**
   * allows a node to connect to a neighbour node, thus allowing the map to be built.
   * @param parent the parent node of the connection.
   * @param child the child node of the connection
   */
  public void connectNode(final GraphNode parent, final GraphNode child) {
    List<GraphNode> listOfChildNodes = nodeConnections.get(parent);
    if (listOfChildNodes == null) {
      listOfChildNodes = new ArrayList<>();
    }
    nodeConnections.put(parent, listOfChildNodes);
    listOfChildNodes.add(child);
  }

  /**
   * determines whether a node exists in the graph or not.
   * if the id can be found then it returns true, otherwise false.
   * @param id the id of the node to be searched for.
   * @return boolean indicating whether the node exists in the graph or not.
   */
  public boolean idExists(final long id) {
    for (GraphNode node : nodesInGraph) {
      if (node.getId() == id) {
        return true;
      }
    }
    return false;
  }

  /**
   * finds a node's neighbours which haven't yet been visited and returns them.
   * it helps the explore phase in {@see Explorer} to determine which node to visit next.
   * @param node the node that the explorer is currently on.
   * @return a list of neighbour nodes that haven't yet been visited.
   */
  public List<GraphNode> getUnvisitedNeighbours(final GraphNode node) {
    List<GraphNode> unvisitedNeighbours = new ArrayList<>();
    List<GraphNode> neighbourNodes = nodeConnections.get(node);
    if (neighbourNodes != null) { //without this statement a nullPointerException could be raised
      for (GraphNode neighbour : neighbourNodes) {
        if (!neighbour.getHasBeenVisited()) {
          unvisitedNeighbours.add(neighbour);
        }
      }
    }
    return unvisitedNeighbours;
  }

  /**
   * finds the node closest to the orb that has not yet been visited.
   * @param unvisitedNeighbours a list of unvisited neighbours which may be moved to.
   * @return the unvisited neighbour node that is closest to the orb
   */
  public GraphNode getClosestNode(final List<GraphNode> unvisitedNeighbours) {
    //grab any unvisited node and compare the rest of them to it
    GraphNode closestNode = unvisitedNeighbours.iterator().next();
    for (GraphNode neighbour : unvisitedNeighbours) {
      if (neighbour.getDistanceToOrb() < closestNode.getDistanceToOrb()) {
        closestNode = neighbour;
      }
    }
    return closestNode;
  }

}
