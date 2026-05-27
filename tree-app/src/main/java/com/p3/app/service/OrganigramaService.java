//package com.p3.app.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.openapitools.jackson.nullable.JsonNullable;
//import com.p3.engine.CustomTreeStrategy;
//import com.p3.app.dto.CreateNodeRequest;
//import com.p3.app.dto.NodeResponse;
//import com.p3.app.repository.MongoTreeRepository; // Importas el de Elder
//import java.util.concurrent.atomic.AtomicLong;
//
//@Service
//public class OrganigramaService {
//
//    // 1. Inyectamos de forma opcional el repositorio de Elder
//    @Autowired(required = false)
//    private MongoTreeRepository mongoTreeRepository;
//
//    private final CustomTreeStrategy treeEngine = new CustomTreeStrategy();
//    private final AtomicLong idGenerator = new AtomicLong(4); // Empezamos en 4 porque la demo ya usó 1, 2 y 3
//
//    public NodeResponse registrarRaiz(CreateNodeRequest request) {
//        if (!treeEngine.isEmpty()) {
//            throw new RuntimeException("El organigrama ya posee una Gerencia General establecida.");
//        }
//        
//        Long nuevoId = idGenerator.getAndIncrement();
//        String idString = String.valueOf(nuevoId);
//        
//        // Guardamos en tu motor lógico en memoria
//        treeEngine.insertNode(nuevoId, request.getValue(), null);
//        
//        // ENLACE EN CALIENTE: Si Mongo está activo, guardamos físicamente en Docker
//        if (mongoTreeRepository != null) {
//            mongoTreeRepository.createRoot(idString, request.getValue());
//        }
//        
//        NodeResponse response = new NodeResponse();
//        response.setId(nuevoId);
//        response.setValue(request.getValue());
//        response.setParentId(JsonNullable.of(null));
//        return response;
//    }
//
//    public NodeResponse agregarSubordinado(Long parentId, CreateNodeRequest request) {
//        if (treeEngine.isEmpty()) {
//            throw new RuntimeException("No se puede agregar subordinados sin un puesto raíz definido.");
//        }
//        
//        Long nuevoId = idGenerator.getAndIncrement();
//        String idString = String.valueOf(nuevoId);
//        String parentIdString = String.valueOf(parentId);
//        
//        // Guardamos en tu motor lógico en memoria
//        boolean insertado = treeEngine.insertNode(nuevoId, request.getValue(), parentId);
//        
//        if (!insertado) {
//            throw new RuntimeException("Error: No se encontró el puesto superior con ID: " + parentId);
//        }
//
//        // ENLACE EN CALIENTE: Guardamos físicamente el hijo en el Docker de Mongo
//        if (mongoTreeRepository != null) {
//            mongoTreeRepository.addChild(idString, request.getValue(), parentIdString);
//        }
//
//        NodeResponse response = new NodeResponse();
//        response.setId(nuevoId);
//        response.setValue(request.getValue());
//        response.setParentId(JsonNullable.of(parentId));
//        return response;
//    }
//
//    public NodeResponse obtenerArbol() {
//        if (treeEngine.isEmpty()) {
//            return null;
//        }
//        
//        NodeResponse response = new NodeResponse();
//        response.setId(treeEngine.getRoot().getId());
//        response.setValue(treeEngine.getRoot().getValue());
//        response.setParentId(JsonNullable.of(null));
//        return response;
//    }
//}