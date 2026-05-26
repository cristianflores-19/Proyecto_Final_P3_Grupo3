package com.p3.app.service;

import com.p3.app.entity.NodeEntity;
import com.p3.app.repository.NodeRepository; // 👈 Cambiado: Usamos la interfaz común superior
import com.p3.app.repository.TreeDataRepository;
import com.p3.engine.TreeAlgorithmStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {

    // 🌟 Una sola variable limpia apuntando a la interfaz común
    private final TreeDataRepository repository; 
    private final TreeAlgorithmStrategy strategy; // (Asegúrate de que se llame TreeAlgorithmStrategy o TreeAlgorithmStrategy según tu interfaz de motor)

    // 🌟 El constructor recibe únicamente la interfaz común superior
    public TreeService(TreeDataRepository repository, TreeAlgorithmStrategy strategy) {
        this.repository = repository;
        this.strategy = strategy;
    }

    // ... Todo el resto de tus métodos (createRoot, addChild, reloadStrategy) se quedan tal cual los tienes.


    public NodeEntity createRoot(String value) {
        NodeEntity root = repository.saveRoot(value);
        reloadStrategy();
        return root;
    }

    public NodeEntity addChild(Long parentId, String value) {
        NodeEntity child = repository.saveChild(parentId, value);
        reloadStrategy();
        return child;
    }

    public Object getTree() {
        reloadStrategy();
        return strategy.getTree();
    }

    public Object getSubTree(Long nodeId) {
        reloadStrategy();
        return strategy.getSubTree(nodeId);
    }

    public List<String> getPath(Long nodeId) {
        reloadStrategy();
        return strategy.getPath(nodeId);
    }

    public List<String> dfs() {
        reloadStrategy();
        return strategy.dfsTraversal();
    }

    public List<String> bfs() {
        reloadStrategy();
        return strategy.bfsTraversal();
    }

    public int height() {
        reloadStrategy();
        return strategy.getTreeHeight();
    }

    public int depth(Long nodeId) {
        reloadStrategy();
        return strategy.getNodeDepth(nodeId);
    }

    public List<String> ancestors(Long nodeId) {
        reloadStrategy();
        return strategy.getAncestors(nodeId);
    }

    public boolean validate() {
        reloadStrategy();
        return strategy.validateNoCycles();
    }

    private void reloadStrategy() {
        List<NodeEntity> nodes = repository.findAll();

        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        NodeEntity root = nodes.stream()
                .filter(n -> n.getParentId() == null)
                .findFirst()
                .orElse(null);

        if (root == null) {
            return; 
        }

        strategy.createRoot(root.getId(), root.getValue());

        nodes.sort(java.util.Comparator.comparing(NodeEntity::getId));

        for (NodeEntity node : nodes) {
            if (node.getParentId() != null) {
                strategy.addChild(node.getParentId(), node.getId(), node.getValue());
            }
        }
    }
}