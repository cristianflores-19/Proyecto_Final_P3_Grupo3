package com.p3.app.repository;

import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.p3.app.entity.NodeEntity; // 👈 Importamos la entidad global

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(name = "app.storage", havingValue = "memory")
public class MemoryTreeRepository implements TreeDataRepository { // 👈 Implementa la interfaz puente

    // Guardamos los nodos simulando una tabla en la memoria RAM (ID -> NodeEntity)
    private final Map<Long, NodeEntity> storage = new HashMap<>();
    private long idSequence = 1; // Contador para generar IDs secuenciales automáticos (1, 2, 3...)

    @Override
    public NodeEntity saveRoot(String value) {
        // Al crear una raíz nueva, se limpia la memoria anterior de RAM por seguridad
        storage.clear();
        idSequence = 1;

        NodeEntity root = new NodeEntity();
        root.setId(idSequence++);
        root.setValue(value);
        root.setParentId(null); // Es raíz, no tiene padre

        storage.put(root.getId(), root);
        return root;
    }

    @Override
    public NodeEntity saveChild(Long parentId, String value) {
        NodeEntity child = new NodeEntity();
        child.setId(idSequence++);
        child.setValue(value);
        child.setParentId(parentId); // Enlazamos con el ID de su jefe directo

        storage.put(child.getId(), child);
        return child;
    }

    @Override
    public List<NodeEntity> findAll() {
        // Devolvemos la lista completa de todos los nodos guardados en RAM
        return new ArrayList<>(storage.values());
    }
}