package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import student.GraphNode;

/**
 * Unit tests for {@see GraphNode}.
 *
 * @author Ian Robinson
 */
public class GraphNodeTest {

  private GraphNode node;

  @Before
  public void setUp() {
     node = new GraphNode(56, 15, false);
  }

  @Test
  public void testSetHasBeenVisited() {
    node.setHasBeenVisited(true);
    assertEquals(true, node.getHasBeenVisited());
  }

  @Test
  public void testGetNodeId() {
    assertEquals(56, node.getNodeId());
  }

  @Test
  public void testGetDistanceToOrb() {
    assertEquals(15, node.getDistanceToOrb());
  }

  @Test
  public void testGetHasBeenVisited() {
    assertEquals(false, node.getHasBeenVisited());
  }

}
