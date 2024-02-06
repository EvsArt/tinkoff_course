package edu.hw9.task3;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Node {

    @Getter
    @Setter
    private int value;
    @Getter
    private final List<Node> childs = new ArrayList<>();

    public Node(int value) {
        this.value = value;
    }

    public void addChilds(List<Node> childs) {
        this.childs.addAll(childs);
    }

}
