package com.p3.engine;

import java.util.List;

public interface TreeAlgorithmStrategy {

    // Identificador de estrategia
    String getEngineType();

    // 1. Crear raíz
    void createRoot(Long id, String value);

    // 2. Agregar hijo
    void addChild(Long parentId, Long childId, String value);

    // 3. Obtener árbol completo
    Object getTree();

    // 4. Obtener subárbol
    Object getSubTree(Long nodeId);

    // 5. Ruta desde raíz a un nodo
    List<String> getPath(Long nodeId);

    // 6. Recorrido DFS
    List<String> dfsTraversal();

    // 7. Recorrido BFS
    List<String> bfsTraversal();

    // 8. Altura del árbol
    int getTreeHeight();

    // 9. Profundidad de un nodo
    int getNodeDepth(Long nodeId);

    // 10. Ancestros de un nodo
    List<String> getAncestors(Long nodeId);

    // 11. Validar ciclos
    boolean validateNoCycles();
}