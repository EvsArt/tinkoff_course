package edu.hw9.task3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultithreadDFSTest {

    Node getTree() {

        Node root = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n11 = new Node(4);
        Node n12 = new Node(5);
        Node n13 = new Node(6);
        Node n21 = new Node(7);
        Node n22 = new Node(8);
        Node n31 = new Node(9);
        Node n211 = new Node(10);
        Node n212 = new Node(11);

        root.addChilds(List.of(n1, n2, n3));
        n1.addChilds(List.of(n11, n12, n13));
        n2.addChilds(List.of(n21, n22));
        n3.addChilds(List.of(n31));
        n21.addChilds(List.of(n211, n212));

        return root;

    }

    @Test
    void compute() {
        // given
        Node tree = getTree();
        MultithreadDFS dfs1 = new MultithreadDFS(tree, 8);
        MultithreadDFS dfs2 = new MultithreadDFS(tree, 0);
        MultithreadDFS dfs3 = new MultithreadDFS(tree, 12);

        // when
        List<Integer> solution1 = dfs1.getSolution();
        List<Integer> solution2 = dfs2.getSolution();

        // then
        assertEquals(List.of(0, 2, 8), solution1);
        assertEquals(List.of(0), solution2);

        assertThrows(RuntimeException.class, dfs3::getSolution);

    }
}
