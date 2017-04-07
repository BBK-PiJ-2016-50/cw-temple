package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import student.ExploreGraph;
import student.GraphNode;
import java.util.List;


/**
 * Unit tests for {@see ExploreGraph}.
 *
 * @author Ian Robinson
 */
public class ExploreGraphTest {

  private ExploreGraph graph;

  @Before
  public void setUp() {
    graph = new ExploreGraph();
  }

  @Test
  public void testAddNodes() {
    graph.addNode(new GraphNode(10, 20, false));
    graph.addNode(new GraphNode(15, 10, true));
    graph.addNode(new GraphNode(100, 100, true));
    assertEquals(3, graph.getNodesInGraph().size());
  }

  @Test
  public void testConnectNode() {
    GraphNode parent = new GraphNode(10, 20, false);
    graph.addNode(parent);
    GraphNode child = new GraphNode(15, 10, true);
    graph.addNode(child);
    graph.connectNode(parent, child);
    assertTrue(graph.getNodeConnections().containsKey(parent));
    assertTrue(graph.getNodeConnections().get(parent).contains(child));
  }

  @Test
  public void testIdExists() {
    graph.addNode(new GraphNode(100, 100, true));
    assertTrue(graph.idExists(100));
  }

  @Test
  public void testIdDoesntExist() {
    graph.addNode(new GraphNode(100, 100, true));
    assertFalse(graph.idExists(50));
  }

  @Test
  public void testGetUnvisitedNeighbours() {
    GraphNode nodeA = new GraphNode(100, 10, true);
    GraphNode nodeB = new GraphNode(101, 15, false);
    GraphNode nodeC = new GraphNode(102, 20, false);
    GraphNode nodeD = new GraphNode(103, 5, true);
    graph.connectNode(nodeA, nodeB);
    graph.connectNode(nodeA, nodeC);
    graph.connectNode(nodeA, nodeD);
    List<GraphNode> unvNeighbours = graph.getUnvisitedNeighbours(nodeA);
    assertEquals(2, unvNeighbours.size());
  }

  @Test
  public void testNoUnvisitedNeighbours() {
    GraphNode nodeA = new GraphNode(100, 10, true);
    GraphNode nodeB = new GraphNode(101, 15, true);
    GraphNode nodeC = new GraphNode(102, 20, true);
    GraphNode nodeD = new GraphNode(103, 5, true);
    graph.connectNode(nodeA, nodeB);
    graph.connectNode(nodeA, nodeC);
    graph.connectNode(nodeA, nodeD);
    List<GraphNode> unvNeighbours = graph.getUnvisitedNeighbours(nodeA);
    assertTrue(unvNeighbours.isEmpty());
  }

  @Test
  public void testGetClosestNode() {
    GraphNode nodeA = new GraphNode(100, 10, true);
    GraphNode nodeB = new GraphNode(101, 15, false);
    GraphNode nodeC = new GraphNode(102, 20, false);
    GraphNode nodeD = new GraphNode(103, 5, false);
    graph.connectNode(nodeA, nodeB);
    graph.connectNode(nodeA, nodeC);
    graph.connectNode(nodeA, nodeD);
    List<GraphNode> unvNeighbours = graph.getUnvisitedNeighbours(nodeA);
    assertEquals(nodeD, graph.getClosestNode(unvNeighbours));
  }

}
