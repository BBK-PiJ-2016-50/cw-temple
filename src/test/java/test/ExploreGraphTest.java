package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import game.ExplorationState;
import game.NodeStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import student.ExploreGraph;
import student.ExploreGraphImpl;
import student.GraphNode;
import student.GraphNodeImpl;

/**
 * Unit tests for {@see ExploreGraph}.
 *
 * @author Ian Robinson
 */
public class ExploreGraphTest {

  private ExploreGraph graph;

  @Before
  public void setUp() {
    graph = new ExploreGraphImpl();
  }

  @Test
  public void testAddNodes() {
    graph.addNode(new GraphNodeImpl(10, 20, false));
    graph.addNode(new GraphNodeImpl(15, 10, true));
    graph.addNode(new GraphNodeImpl(100, 100, true));
    assertEquals(3, graph.getNodesInGraph().size());
  }

  @Test
  public void testConnectNode() {
    final GraphNode parent = new GraphNodeImpl(10, 20, false);
    final GraphNode child = new GraphNodeImpl(15, 10, true);
    graph.addNode(parent);
    graph.addNode(child);
    graph.connectNode(parent, child);
    assertTrue(graph.getNodeConnections().containsKey(parent));
    assertTrue(graph.getNodeConnections().get(parent).contains(child));
  }

  @Test
  public void testIdExists() {
    graph.addNode(new GraphNodeImpl(100, 100, true));
    assertTrue(graph.idExists(100));
  }

  @Test
  public void testIdDoesntExist() {
    graph.addNode(new GraphNodeImpl(100, 100, true));
    assertFalse(graph.idExists(50));
  }

  @Test
  public void testGetUnvisitedNeighbours() {
    final GraphNode nodeA = new GraphNodeImpl(100, 10, true);
    final GraphNode nodeB = new GraphNodeImpl(101, 15, false);
    final GraphNode nodeC = new GraphNodeImpl(102, 20, false);
    final GraphNode nodeD = new GraphNodeImpl(103, 5, true);
    graph.connectNode(nodeA, nodeB);
    graph.connectNode(nodeA, nodeC);
    graph.connectNode(nodeA, nodeD);
    List<GraphNode> unvNeighbours = graph.getUnvisitedNeighbours(nodeA);
    assertEquals(2, unvNeighbours.size());
  }

  @Test
  public void testNoUnvisitedNeighbours() {
    final GraphNode nodeA = new GraphNodeImpl(100, 10, true);
    final GraphNode nodeB = new GraphNodeImpl(101, 15, true);
    final GraphNode nodeC = new GraphNodeImpl(102, 20, true);
    final GraphNode nodeD = new GraphNodeImpl(103, 5, true);
    graph.connectNode(nodeA, nodeB);
    graph.connectNode(nodeA, nodeC);
    graph.connectNode(nodeA, nodeD);
    List<GraphNode> unvNeighbours = graph.getUnvisitedNeighbours(nodeA);
    assertTrue(unvNeighbours.isEmpty());
  }

  @Test
  public void testGetClosestNode() {
    final GraphNode nodeA = new GraphNodeImpl(100, 10, true);
    final GraphNode nodeB = new GraphNodeImpl(101, 15, false);
    final GraphNode nodeC = new GraphNodeImpl(102, 20, false);
    final GraphNode nodeD = new GraphNodeImpl(103, 5, false);
    graph.connectNode(nodeA, nodeB);
    graph.connectNode(nodeA, nodeC);
    graph.connectNode(nodeA, nodeD);
    List<GraphNode> unvNeighbours = graph.getUnvisitedNeighbours(nodeA);
    assertEquals(nodeD, graph.getClosestNode(unvNeighbours));
  }

  @Test
  public void testAddAndConnectNeighbours() {
    final GraphNode nodeA = new GraphNodeImpl(1, 5, false);
    graph.addNode(nodeA);
    final Collection<NodeStatus> neighbours = new ArrayList<>();
    int total = 0;
    long nodeId = 20;
    while (total < 5) {
      NodeStatus neighbour = mock(NodeStatus.class);
      neighbours.add(neighbour);
      when(neighbour.getId()).thenReturn(nodeId);
      when(neighbour.getDistanceToTarget()).thenReturn(total + 1);
      total++;
      nodeId++;
    }
    graph.addAndConnectNeighbours(nodeA, neighbours);

    assertEquals(5, graph.getUnvisitedNeighbours(nodeA).size());
    assertTrue(graph.idExists(21));
    assertFalse(graph.idExists(30));
    assertEquals(6, graph.getNodesInGraph().size());
    assertEquals(1, graph.getNodeConnections().size());

  }

  @Test
  public void testOrbFound() {
    final ExplorationState state = mock(ExplorationState.class);
    when(state.getDistanceToTarget()).thenReturn(0);
    assertTrue(graph.orbFound(state));
  }

  @Test
  public void testOrbNotFound() {
    final ExplorationState state = mock(ExplorationState.class);
    when(state.getDistanceToTarget()).thenReturn(100);
    assertFalse(graph.orbFound(state));
  }

}
