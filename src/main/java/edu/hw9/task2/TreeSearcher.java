package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// По факту реализовал один класс, который может выполнять функции обоих алгоритмов из условия
// в зависимости от передаваемого в конструктор Predicate<Path>, который определяет условия отбора
// Оба алгоритма выполняются в тесте
public class TreeSearcher extends RecursiveTask<Set<Path>> {

    private final Path rootPath;
    private final Predicate<Path> searchCondition;

    public TreeSearcher(Path rootPath, Predicate<Path> searchCondition) {
        this.rootPath = rootPath;
        this.searchCondition = searchCondition;
    }

    @Override
    public Set<Path> compute() {
        Set<Path> paths = new HashSet<>();
        try {
            paths.addAll(
                Files.list(rootPath)
                    .filter(searchCondition)
                    .collect(Collectors.toSet())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<TreeSearcher> subTasks = new ArrayList<>();
        try {
            for (Path child : Files.list(rootPath).filter(Files::isDirectory).toList()) {
                TreeSearcher task = new TreeSearcher(child, searchCondition);
                task.fork();
                subTasks.add(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (TreeSearcher task : subTasks) {
            paths.addAll(task.join());
        }

        return paths;
    }
}
