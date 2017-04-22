package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import student.GraphNode;
import student.GraphNodeImpl;

/**
 * Unit tests for {@see GraphNode}.
 *
 * @author Ian Robinson
 */
public class GraphNodeTest {

  private GraphNode node;

  @Before
  public void setUp() {
    node = new GraphNodeImpl(56, 15, false);
  }

  @Test
  public void testSetHasBeenVisited() {
    node.setHasBeenVisited(true);
    assertTrue(node.getHasBeenVisited());
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
    assertFalse(node.getHasBeenVisited());
  }

}
