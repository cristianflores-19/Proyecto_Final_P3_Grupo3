package com.p3.engine;

import java.util.*;

public class CollectionsTreeStrategy implements TreeAlgorithmStrategy {

    private Long rootId;
    private final Map<Long, String> values = new HashMap<>();
    private final Map<Long, List<Long>> children = new HashMap<>();
    private final Map<Long, Long> parents = new HashMap<>();

    @Override
    public String getEngineType() {
        return "Motor usando Collections Framework";
    }

    @Override
    public void createRoot(Long id, String value) {
        values.clear();
        children.clear();
        parents.clear();

        rootId = id;
        values.put(id, value);
        children.putIfAbsent(id, new ArrayList<>());
        parents.put(id, null);
    }
    
    @Override
    public void clear() {
        rootId = null;
        values.clear();
        children.clear();
        parents.clear();
    }

    @Override
    public void addChild(Long parentId, Long childId, String value) {
        if (!values.containsKey(parentId)) {
            throw new IllegalArgumentException("El nodo padre no existe");
        }
        
        if (values.containsKey(childId)) {
            return;
        }

        values.put(childId, value);
        children.putIfAbsent(parentId, new ArrayList<>());
        children.putIfAbsent(childId, new ArrayList<>());
        children.get(parentId).add(childId);
        parents.put(childId, parentId);
    }

    @Override
    public Object getTree() {
        if (rootId == null) {
            return null;
        }
        return buildTree(rootId);
    }

    @Override
    public Object getSubTree(Long nodeId) {
        if (!values.containsKey(nodeId)) {
            throw new IllegalArgumentException("El nodo no existe");
        }
        return buildTree(nodeId);
    }

    @Override
    public List<String> getPath(Long nodeId) {
        if (!values.containsKey(nodeId)) {
            throw new IllegalArgumentException("El nodo no existe");
        }

        LinkedList<String> path = new LinkedList<>();
        Long current = nodeId;

        while (current != null) {
            path.addFirst(values.get(current));
            current = parents.get(current);
        }

        return path;
    }

    @Override
    public List<String> dfsTraversal() {
        List<String> result = new ArrayList<>();
        dfs(rootId, result, new HashSet<>());
        return result;
    }

    @Override
    public List<String> bfsTraversal() {
        List<String> result = new ArrayList<>();

        if (rootId == null) {
            return result;
        }

        Queue<Long> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();

        queue.add(rootId);
        visited.add(rootId);

        while (!queue.isEmpty()) {
            Long current = queue.poll();
            result.add(values.get(current));

            for (Long child : children.getOrDefault(current, Collections.emptyList())) {
                if (visited.add(child)) {
                    queue.add(child);
                }
            }
        }

        return result;
    }

    @Override
    public int getTreeHeight() {
        if (rootId == null) {
            return 0;
        }
        return height(rootId);
    }

    @Override
    public int getNodeDepth(Long nodeId) {
        if (!values.containsKey(nodeId)) {
            return -1;
        }

        int depth = 0;
        Long current = nodeId;

        while (parents.get(current) != null) {
            depth++;
            current = parents.get(current);
        }

        return depth;
    }

    @Override
    public List<String> getAncestors(Long nodeId) {
        if (!values.containsKey(nodeId)) {
            throw new IllegalArgumentException("El nodo no existe");
        }

        List<String> ancestors = new ArrayList<>();
        Long current = parents.get(nodeId);

        while (current != null) {
            ancestors.add(values.get(current));
            current = parents.get(current);
        }

        Collections.reverse(ancestors);
        return ancestors;
    }

    @Override
    public boolean validateNoCycles() {
        if (rootId == null) {
            return true;
        }

        Set<Long> visited = new HashSet<>();
        Set<Long> recursionStack = new HashSet<>();

        return !hasCycle(rootId, visited, recursionStack);
    }

    private void dfs(Long nodeId, List<String> result, Set<Long> visited) {
        if (nodeId == null || !visited.add(nodeId)) {
            return;
        }

        result.add(values.get(nodeId));

        for (Long child : children.getOrDefault(nodeId, Collections.emptyList())) {
            dfs(child, result, visited);
        }
    }

    private int height(Long nodeId) {
        List<Long> nodeChildren = children.getOrDefault(nodeId, Collections.emptyList());

        if (nodeChildren.isEmpty()) {
            return 1; 
        }

        int max = 0;
        for (Long child : nodeChildren) {
            max = Math.max(max, height(child));
        }

        return max + 1;
    }

    private boolean hasCycle(Long nodeId, Set<Long> visited, Set<Long> recursionStack) {
        if (recursionStack.contains(nodeId)) {
            return true;
        }

        if (visited.contains(nodeId)) {
            return false;
        }

        visited.add(nodeId);
        recursionStack.add(nodeId);

        for (Long child : children.getOrDefault(nodeId, Collections.emptyList())) {
            if (hasCycle(child, visited, recursionStack)) {
                return true;
            }
        }

        recursionStack.remove(nodeId);
        return false;
    }

    private Map<String, Object> buildTree(Long nodeId) {
        Map<String, Object> node = new LinkedHashMap<>();
        node.put("id", nodeId);
        node.put("value", values.get(nodeId));

        List<Map<String, Object>> childNodes = new ArrayList<>();

        for (Long child : children.getOrDefault(nodeId, Collections.emptyList())) {
            childNodes.add(buildTree(child));
        }

        node.put("children", childNodes);
        return node;
    }
}