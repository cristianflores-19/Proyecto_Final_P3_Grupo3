package com.p3.engine;

import java.util.*;

public class CollectionsTreeStrategy implements TreeAlgorithmStrategy {

    private final Map<Long, List<Long>> tree = new HashMap<>();

    @Override
    public String getEngineType() {
        return "Motor usando Collections Framework";
    }

    public void addNode(Long parentId, Long childId) {

        tree.putIfAbsent(parentId, new ArrayList<>());
        tree.get(parentId).add(childId);

        tree.putIfAbsent(childId, new ArrayList<>());
    }

    public List<Long> bfs(Long rootId) {

        List<Long> result = new ArrayList<>();
        Queue<Long> queue = new LinkedList<>();

        queue.add(rootId);

        while (!queue.isEmpty()) {

            Long current = queue.poll();

            result.add(current);

            for (Long child : tree.getOrDefault(current, new ArrayList<>())) {
                queue.add(child);
            }
        }

        return result;
    }

    public List<Long> dfs(Long rootId) {

        List<Long> result = new ArrayList<>();

        dfsRecursive(rootId, result);

        return result;
    }

    private void dfsRecursive(Long node, List<Long> result) {

        result.add(node);

        for (Long child : tree.getOrDefault(node, new ArrayList<>())) {
            dfsRecursive(child, result);
        }
    }

    public int calculateHeight(Long rootId) {

        if (!tree.containsKey(rootId)) {
            return 0;
        }

        int maxHeight = 0;

        for (Long child : tree.get(rootId)) {

            int childHeight = calculateHeight(child);

            maxHeight = Math.max(maxHeight, childHeight);
        }

        return maxHeight + 1;
    }

    public int calculateDepth(Long rootId, Long targetId) {

        return depthRecursive(rootId, targetId, 0);
    }

    private int depthRecursive(Long current,
                               Long target,
                               int depth) {

        if (current.equals(target)) {
            return depth;
        }

        for (Long child : tree.getOrDefault(current,
                new ArrayList<>())) {

            int result = depthRecursive(child,
                                        target,
                                        depth + 1);

            if (result != -1) {
                return result;
            }
        }

        return -1;
    }
}