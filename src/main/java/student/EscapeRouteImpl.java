package student;

import game.Edge;
import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Implementation of {@see EscapeRoute}.
 *
 * @author Ian Robinson
 */
public class EscapeRouteImpl implements EscapeRoute {

  /**
   * stores each visited node and the closest node that it is connected to.
   */
  private Map<Node, Node> pathNodes;

  /**
   * map of all visited nodes and their distances to other nodes.
   */
  private Map<Node, Integer> distanceToNode;

  /**
   * a set of nodes which haven't been evaluated.
   */
  private Set<Node> unvisited;

  /**
   * a set of nodes which have been evaluated.
   */
  private Set<Node> visited;

  /**
   * the closest node to a node selected from the unvisited list.
   */
  private Node closestNode;

  /**
   * constructor sets up the framework required for finding
   * the shortest route.
   */
  public EscapeRouteImpl() {
    this.pathNodes = new HashMap<>();
    this.distanceToNode = new HashMap<>();
    this.unvisited = new HashSet<>();
    this.visited = new HashSet<>();
    this.closestNode = null;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void findRoute(final Node startNode) {
    distanceToNode.put(startNode, 0);
    unvisited.add(startNode);

    while (!unvisited.isEmpty()) {

      closestNode = findClosestNode(unvisited);
      visited.add(closestNode);
      unvisited.remove(closestNode);

      //for the closest node find all the closest node's neighbour nodes
      final Set<Node> neighbours = closestNode.getNeighbours();
      final List<Node> unvNeighbours = findUnvNeighbours(neighbours);

      //use the unvisited neighbours to see if the shortest distance for
      //the closest node can be improved upon.
      updateShortestDistance(unvNeighbours);

    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<Node> getRoute(final Node endNode) {
    final List<Node> route = new LinkedList<>();
    Node nextNode = endNode;
    route.add(nextNode);
    while (pathNodes.get(nextNode) != null) {
      nextNode = pathNodes.get(nextNode);
      route.add(nextNode);
    }
    //ensure the path is returned with the start node first
    Collections.reverse(route);
    return route;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void lookAroundForGold(
          final EscapeState state,
          final Queue<Node> pathToTake,
          final Node pathNode
  ) {
    final Stack<Node> goldTrail = new Stack<>();
    for (final Node neighbour : pathNode.getNeighbours()) {
      goldTrail.push(pathNode);
      Node current = neighbour;
      final Tile currentTile = current.getTile();
      //if the current tile has gold, and isn't part of the pathToTake
      //then move to tile and pick up the gold
      while (currentTile.getGold() > 0 && !pathToTake.contains(current)) {
        state.moveTo(current);
        state.pickUpGold();
        goldTrail.push(current);
        current = current.getNeighbours().iterator().next();
      }
      //move back to original escape route
      goldTrail.pop();
      while (!goldTrail.isEmpty()) {
        state.moveTo(goldTrail.pop());
      }
    }
  }

  /**
   * find the node closest to the currentNode picked from the unvisited list.
   * @param unvisited the current set of unvisited nodes.
   * @return the node which is closest to the current node.
   */
  private Node findClosestNode(final Set<Node> unvisited) {
    Node closestNode = unvisited.iterator().next();
    for (final Node node : unvisited) {
      if (getShortestDistance(node) < getShortestDistance(closestNode)) {
        closestNode = node;
      }
    }
    return closestNode;
  }

  /**
   * find the neighbours for a given node that haven't yet been visited.
   * @param neighbours the set of neighbour nodes to check.
   * @return the list of unvisited neighbour nodes.
   */
  private List<Node> findUnvNeighbours(final Set<Node> neighbours) {
    final List<Node> unvNeighbours = new ArrayList<>();
    for (final Node n : neighbours) {
      if (!visited.contains(n)) {
        unvNeighbours.add(n);
      }
    }
    return unvNeighbours;
  }

  /**
   * update the shortest distance possible to a neighbour node by looking at all edges.
   * then check if the distance to this neighbour can be reduced.
   * if it can then the distance is updated and the node is added to the unvisited nodes.
   */
  private void updateShortestDistance(List<Node> unvNeighbours) {
    for (final Node neighbour : unvNeighbours) {
      final Edge edge = closestNode.getEdge(neighbour);
      if (getShortestDistance(neighbour) > getShortestDistance(closestNode) + edge.length()) {
        distanceToNode.put(neighbour, getShortestDistance(closestNode) + edge.length());
        pathNodes.put(neighbour, closestNode);
        unvisited.add(neighbour);
      }
    }
  }

  /**
   * find the shortest distance to a node.
   * if it doesn't exist then assign it a maximum value.
   * @param target the node for which shortest distance is to be deduced.
   * @return the shortest distance.
   */
  private int getShortestDistance(final Node target) {
    Integer dist = distanceToNode.get(target);
    if (dist == null) {
      dist = Integer.MAX_VALUE;
    }
    return dist;
  }

}
