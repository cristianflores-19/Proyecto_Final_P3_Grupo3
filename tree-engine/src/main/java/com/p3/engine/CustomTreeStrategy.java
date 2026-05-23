package com.p3.engine;

import java.util.Collections;
import java.util.List;

public class CustomTreeStrategy implements TreeAlgorithmStrategy {

    @Override
    public String getEngineType() {
        return "Motor usando estructura propia";
    }

    @Override
    public void createRoot(Long id, String value) {
        throw new UnsupportedOperationException("Pendiente de implementar por Integrante A");
    }

    @Override
    public void addChild(Long parentId, Long childId, String value) {
        throw new UnsupportedOperationException("Pendiente de implementar por Integrante A");
    }

    @Override
    public Object getTree() {
        return null;
    }

    @Override
    public Object getSubTree(Long nodeId) {
        return null;
    }

    @Override
    public List<String> getPath(Long nodeId) {
        return Collections.emptyList();
    }

    @Override
    public List<String> dfsTraversal() {
        return Collections.emptyList();
    }

    @Override
    public List<String> bfsTraversal() {
        return Collections.emptyList();
    }

    @Override
    public int getTreeHeight() {
        return 0;
    }

    @Override
    public int getNodeDepth(Long nodeId) {
        return -1;
    }

    @Override
    public List<String> getAncestors(Long nodeId) {
        return Collections.emptyList();
    }

    @Override
    public boolean validateNoCycles() {
        return true;
    }
}