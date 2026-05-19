package com.p3.app.service;

import org.springframework.stereotype.Service;
import org.openapitools.jackson.nullable.JsonNullable;
import com.p3.engine.CustomTreeStrategy;
import com.p3.app.dto.CreateNodeRequest;
import com.p3.app.dto.NodeResponse;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrganigramaService {

    // Instanciamos motor custom integrado directamente en memoria para esta fase
    private final CustomTreeStrategy treeEngine = new CustomTreeStrategy();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public NodeResponse registrarRaiz(CreateNodeRequest request) {
        if (!treeEngine.isEmpty()) {
            throw new RuntimeException("El organigrama ya posee una Gerencia General establecida.");
        }
        
        Long nuevoId = idGenerator.getAndIncrement();
        treeEngine.insertNode(nuevoId, request.getValue(), null);
        
        NodeResponse response = new NodeResponse();
        response.setId(nuevoId);
        response.setValue(request.getValue());
        response.setParentId(JsonNullable.of(null));
        return response;
    }

    public NodeResponse agregarSubordinado(Long parentId, CreateNodeRequest request) {
        if (treeEngine.isEmpty()) {
            throw new RuntimeException("No se puede agregar subordinados sin un puesto raíz definido.");
        }
        
        Long nuevoId = idGenerator.getAndIncrement();
        boolean insertado = treeEngine.insertNode(nuevoId, request.getValue(), parentId);
        
        if (!insertado) {
            throw new RuntimeException("Error: No se encontró el puesto superior con ID: " + parentId);
        }

        NodeResponse response = new NodeResponse();
        response.setId(nuevoId);
        response.setValue(request.getValue());
        response.setParentId(JsonNullable.of(parentId));
        return response;
    }

    public NodeResponse obtenerArbol() {
        if (treeEngine.isEmpty()) {
            return null;
        }
        
        NodeResponse response = new NodeResponse();
        response.setId(treeEngine.getRoot().getId());
        response.setValue(treeEngine.getRoot().getValue());
        response.setParentId(JsonNullable.of(null));
        return response;
    }
}