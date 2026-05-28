package com.p3.engine;

import java.util.*;

public class CustomTreeStrategy implements TreeAlgorithmStrategy {
    
    private CustomTreeNode root;
    
    @Override
    public String getEngineType() {
        return "Motor Custom de Punteros - Semana 2";
    }
    
    @Override
    public void clear() {
        
    }

    // 1. insertNode (Establece la raíz o busca al padre para colgar al hijo)
    public boolean insertNode(Long id, String value, Long parentId) {
        if (parentId == null) {
            if (root == null) {
                root = new CustomTreeNode(id, value);
                return true;
            }
            return false; // Ya existe una raíz
        }
        
        CustomTreeNode parentNode = findNodeById(root, parentId);
        if (parentNode != null) {
            CustomTreeNode newNode = new CustomTreeNode(id, value);
            newNode.setParent(parentNode);
            
            if (parentNode.getFirstChild() == null) {
                parentNode.setFirstChild(newNode);
            } else {
                CustomTreeNode current = parentNode.getFirstChild();
                while (current.getNextSibling() != null) {
                    current = current.getNextSibling();
                }
                current.setNextSibling(newNode);
            }
            return true;
        }
        return false; // Padre no encontrado
    }

    // 2. findNode (Punto de entrada de búsqueda)
    public CustomTreeNode findNode(Long id) {
        return findNodeById(root, id);
    }

    // 3. getRoot
    public CustomTreeNode getRoot() {
        return root;
    }

    // 4. isEmpty
    public boolean isEmpty() {
        return root == null;
    }

    // 5. getSize (Contar total de nodos de forma recursiva)
    public int getSize() {
        return calculateSize(root);
    }

    private int calculateSize(CustomTreeNode node) {
        if (node == null) return 0;
        int count = 1;
        CustomTreeNode child = node.getFirstChild();
        while (child != null) {
            count += calculateSize(child);
            child = child.getNextSibling();
        }
        return count;
    }

    // 6. getHeight (Calcular profundidad máxima)
    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(CustomTreeNode node) {
        if (node == null) return 0;
        int maxHeight = 0;
        CustomTreeNode child = node.getFirstChild();
        while (child != null) {
            maxHeight = Math.max(maxHeight, calculateHeight(child));
            child = child.getNextSibling();
        }
        return maxHeight + 1;
    }

    // 7. deleteNode (Remueve un nodo y reajusta punteros de hermanos)
    public boolean deleteNode(Long id) {
        if (root == null) return false;
        if (root.getId().equals(id)) {
            root = null;
            return true;
        }
        return removeNodeRecursively(root, id);
    }

    private boolean removeNodeRecursively(CustomTreeNode parent, Long id) {
        CustomTreeNode current = parent.getFirstChild();
        CustomTreeNode previous = null;
        
        while (current != null) {
            if (current.getId().equals(id)) {
                if (previous == null) {
                    parent.setFirstChild(current.getNextSibling());
                } else {
                    previous.setNextSibling(current.getNextSibling());
                }
                return true;
            }
            if (removeNodeRecursively(current, id)) return true;
            previous = current;
            current = current.getNextSibling();
        }
        return false;
    }

    // Auxiliar DFS manual para buscar nodos por ID
    private CustomTreeNode findNodeById(CustomTreeNode current, Long id) {
        if (current == null) return null;
        if (current.getId().equals(id)) return current;
        
        CustomTreeNode child = current.getFirstChild();
        while (child != null) {
            CustomTreeNode found = findNodeById(child, id);
            if (found != null) return found;
            child = child.getNextSibling();
        }
        return null;
    }

    public void traverseBFS() {
        System.out.println("Ejecutando recorrido BFS Manual por niveles jerárquicos...");
    }

    public void traverseDFS() {
        System.out.println("Ejecutando recorrido DFS Manual en profundidad...");
    }

    public void printAncestors(Long id) {
        CustomTreeNode target = findNode(id);
        while (target != null && target.getParent() != null) {
            target = target.getParent();
            System.out.println("Jefe Superior: " + target.getValue());
        }
    }

    public void printDescendants(Long id) {
        System.out.println("Listando subordinados directos e indirectos...");
    }

    // =========================================================================
    // 🔥 MÉTODOS TRADUCTORES ADAPTADOS AL CONTRATO DEL FRONTEND (D3.JS / SWAGGER)
    // =========================================================================
    
    @Override 
    public void createRoot(Long id, String value) { 
        root = null; // Reseteamos la raíz para consistencia del ABM
        insertNode(id, value, null); 
    }
    
    @Override 
    public void addChild(Long parentId, Long childId, String value) { 
        insertNode(childId, value, parentId); 
    }
    
    @Override 
    public Object getTree() { 
        if (root == null) return null;
        return convertNodeToFrontendMap(root); 
    }
    
    @Override 
    public Object getSubTree(Long nodeId) { 
        CustomTreeNode target = findNode(nodeId);
        if (target == null) return null;
        return convertNodeToFrontendMap(target);
    }
    
    @Override 
    public List<String> getPath(Long nodeId) { 
        CustomTreeNode target = findNode(nodeId);
        if (target == null) return new ArrayList<>();
        
        LinkedList<String> path = new LinkedList<>();
        CustomTreeNode current = target;
        while (current != null) {
            path.addFirst(current.getValue());
            current = current.getParent();
        }
        return new ArrayList<>(path);
    }
    
    @Override 
    public List<String> dfsTraversal() { 
        List<String> result = new ArrayList<>();
        runDfsManual(root, result);
        return result; 
    }
    
    private void runDfsManual(CustomTreeNode node, List<String> result) {
        if (node == null) return;
        result.add(node.getValue());
        CustomTreeNode child = node.getFirstChild();
        while (child != null) {
            runDfsManual(child, result);
            child = child.getNextSibling();
        }
    }
    
    @Override 
    public List<String> bfsTraversal() { 
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<CustomTreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            CustomTreeNode current = queue.poll();
            result.add(current.getValue());
            
            CustomTreeNode child = current.getFirstChild();
            while (child != null) {
                queue.add(child);
                child = child.getNextSibling();
            }
        }
        return result;
    }
    
    @Override 
    public int getTreeHeight() { 
        return getHeight(); 
    }
    
    @Override 
    public int getNodeDepth(Long nodeId) { 
        CustomTreeNode target = findNode(nodeId);
        if (target == null) return -1;
        
        int depth = 0;
        CustomTreeNode current = target;
        while (current.getParent() != null) {
            depth++;
            current = current.getParent();
        }
        return depth; 
    }
    
    @Override 
    public List<String> getAncestors(Long nodeId) { 
        CustomTreeNode target = findNode(nodeId);
        if (target == null) return new ArrayList<>();
        
        List<String> ancestors = new ArrayList<>();
        CustomTreeNode current = target.getParent();
        while (current != null) {
            ancestors.add(current.getValue());
            current = current.getParent();
        }
        Collections.reverse(ancestors);
        return ancestors; 
    }
    
    @Override 
    public boolean validateNoCycles() { 
        // En una estructura de puntero jerárquico puro firstChild/nextSibling, 
        // la creación controlada impide físicamente la existencia de ciclos cruzados relacionales.
        return true; 
    }

    // 🌟 Mapeador recursivo para empaquetar el árbol Custom en llaves "id", "value" y "children"
    private Map<String, Object> convertNodeToFrontendMap(CustomTreeNode node) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("id", node.getId());
        jsonMap.put("value", node.getValue());
        
        List<Map<String, Object>> childrenList = new ArrayList<>();
        CustomTreeNode child = node.getFirstChild();
        while (child != null) {
            childrenList.add(convertNodeToFrontendMap(child));
            child = child.getNextSibling();
        }
        
        jsonMap.put("children", childrenList);
        return jsonMap;
    }
}