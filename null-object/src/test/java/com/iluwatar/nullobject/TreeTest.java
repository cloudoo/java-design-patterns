package com.iluwatar.nullobject;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Date: 12/26/15 - 11:44 PM
 *
 * @author Jeroen Meulemeester
 */
public class TreeTest extends StdOutTest {

  /**
   * During the tests, the same tree structure will be used, shown below. End points will be
   * terminated with the {@link NullNode} instance.
   *
   * <pre>
   * root
   * ├── level1_a
   * │   ├── level2_a
   * │   │   ├── level3_a
   * │   │   └── level3_b
   * │   └── level2_b
   * └── level1_b
   * </pre>
   */
  private static final Node TREE_ROOT;

  static {
    final NodeImpl level1B = new NodeImpl("level1_b", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level2B = new NodeImpl("level2_b", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level3A = new NodeImpl("level3_a", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level3B = new NodeImpl("level3_b", NullNode.getInstance(), NullNode.getInstance());
    final NodeImpl level2A = new NodeImpl("level2_a", level3A, level3B);
    final NodeImpl level1A = new NodeImpl("level1_a", level2A, level2B);
    TREE_ROOT = new NodeImpl("root", level1A, level1B);
  }

  /**
   * Verify the number of items in the tree. The root has 6 children so we expect a {@link
   * Node#getTreeSize()} of 7 {@link Node}s in total.
   */
  @Test
  public void testTreeSize() {
    assertEquals(7, TREE_ROOT.getTreeSize());
  }

  /**
   * Walk through the tree and verify if every item is handled
   */
  @Test
  public void testWalk() {
    TREE_ROOT.walk();

    final InOrder inOrder = Mockito.inOrder(getStdOutMock());
    inOrder.verify(getStdOutMock()).println("root");
    inOrder.verify(getStdOutMock()).println("level1_a");
    inOrder.verify(getStdOutMock()).println("level2_a");
    inOrder.verify(getStdOutMock()).println("level3_a");
    inOrder.verify(getStdOutMock()).println("level3_b");
    inOrder.verify(getStdOutMock()).println("level2_b");
    inOrder.verify(getStdOutMock()).println("level1_b");
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testGetLeft() throws Exception {
    final Node level1 = TREE_ROOT.getLeft();
    assertNotNull(level1);
    assertEquals("level1_a", level1.getName());
    assertEquals(5, level1.getTreeSize());

    final Node level2 = level1.getLeft();
    assertNotNull(level2);
    assertEquals("level2_a", level2.getName());
    assertEquals(3, level2.getTreeSize());

    final Node level3 = level2.getLeft();
    assertNotNull(level3);
    assertEquals("level3_a", level3.getName());
    assertEquals(1, level3.getTreeSize());
    assertSame(NullNode.getInstance(), level3.getRight());
    assertSame(NullNode.getInstance(), level3.getLeft());
  }

  @Test
  public void testGetRight() throws Exception {
    final Node level1 = TREE_ROOT.getRight();
    assertNotNull(level1);
    assertEquals("level1_b", level1.getName());
    assertEquals(1, level1.getTreeSize());
    assertSame(NullNode.getInstance(), level1.getRight());
    assertSame(NullNode.getInstance(), level1.getLeft());
  }

}
