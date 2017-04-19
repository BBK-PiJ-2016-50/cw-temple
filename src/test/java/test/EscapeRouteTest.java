package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import game.EscapeState;
import game.Node;
import org.junit.Before;
import org.junit.Test;
import student.EscapeRoute;
import student.EscapeRouteImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Unit tests for {@see EscapeRoute}.
 *
 * @author Ian Robinson
 */
public class EscapeRouteTest {

  private EscapeState escape;
  private Node currentNode;
  private Node exitNode;
  private Collection<Node> vertices;

  @Before
  public void setUp() {
    escape = mock(EscapeState.class);
    currentNode = mock(Node.class);
    exitNode = mock(Node.class);
    vertices = new HashSet<>();
  }

  //best gold route test
  //take route test

  @Test
  public void testExitFound() {
    vertices.add(currentNode);
    vertices.add(exitNode);
    when(escape.getCurrentNode()).thenReturn(exitNode);
    when(escape.getExit()).thenReturn(exitNode);
    EscapeRoute route = new EscapeRouteImpl(currentNode, exitNode, vertices, 100);
    assertTrue(route.exitFound(escape));
  }

  @Test
  public void testExitNotFound() {
    vertices.add(currentNode);
    vertices.add(exitNode);
    when(escape.getCurrentNode()).thenReturn(currentNode);
    when(escape.getExit()).thenReturn(exitNode);
    EscapeRoute route = new EscapeRouteImpl(currentNode, exitNode, vertices, 100);
    assertFalse(route.exitFound(escape));
  }

  //time to node test
  //add nodes to route test
  //collect gold test

}
