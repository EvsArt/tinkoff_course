package edu.hw9.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

// Map<Integer, Node> is map where key is node value and value is its parent node
@Slf4j
public class MultithreadDFS extends RecursiveTask<Map<Integer, Node>> {

    private final Node startNode;
    private final int needNodeValue;

    public MultithreadDFS(Node startNode, int needNodeValue) {
        this.startNode = startNode;
        this.needNodeValue = needNodeValue;
    }

    public List<Integer> getSolution() {
        Map<Integer, Node> map = compute();

        LinkedList<Integer> res = new LinkedList<>();

        int prevKey = needNodeValue;
        while (map.containsKey(prevKey)) {
            res.addFirst(map.get(prevKey).getValue());
            prevKey = map.get(prevKey).getValue();
        }

        res.add(needNodeValue);

        if (res.size() == 1 && res.get(0) != startNode.getValue()) {
            throw new RuntimeException("Solution was not found");
        }

        return res;

    }

    @Override
    protected Map<Integer, Node> compute() {
        Map<Integer, Node> res = new HashMap<>();
        res.putAll(startNode.getChilds().stream()
            .collect(Collectors.toMap(
                Node::getValue,
                it -> startNode
            )));

        if (res.containsKey(needNodeValue)) {
            return res;
        }

        List<MultithreadDFS> subTasks = new ArrayList<>();
        for (Node child : startNode.getChilds()) {
            MultithreadDFS task = new MultithreadDFS(child, needNodeValue);
            task.fork();
            subTasks.add(task);
        }

        for (MultithreadDFS task : subTasks) {
            res.putAll(task.join());
        }

        return res;

    }

}
