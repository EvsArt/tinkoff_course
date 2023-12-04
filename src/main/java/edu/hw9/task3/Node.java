package edu.hw9.task3;

import java.util.ArrayList;
import java.util.List;

public class Node {

    int value;
    List<Node> childs = new ArrayList<>();

    public Node(int value) {
        this.value = value;
    }

    public void addChilds(List<Node> childs) {
        this.childs.addAll(childs);
    }

    public List<Node> getChilds() {
        return childs;
    }

}
