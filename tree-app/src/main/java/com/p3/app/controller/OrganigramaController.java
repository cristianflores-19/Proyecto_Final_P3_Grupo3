package com.p3.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

// El nuevo import mágico para manejar los nulos de OpenAPI
import org.openapitools.jackson.nullable.JsonNullable;

import com.p3.app.api.NodesApi;
import com.p3.app.api.TreeApi;
import com.p3.app.dto.CreateNodeRequest;
import com.p3.app.dto.NodeResponse;

@RestController
public class OrganigramaController implements NodesApi, TreeApi {

    @Override
    public ResponseEntity<NodeResponse> createRoot(CreateNodeRequest createNodeRequest) {
        System.out.println("====== API FIRST ======");
        System.out.println("Creando nodo raíz principal: " + createNodeRequest.getValue());
        
        NodeResponse response = new NodeResponse();
        response.setId(1L); 
        response.setValue(createNodeRequest.getValue());
        
        // Envolvemos el nulo usando JsonNullable
        response.setParentId(JsonNullable.of(null)); 
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NodeResponse> addChild(Long parentId, CreateNodeRequest createNodeRequest) {
        System.out.println("====== API FIRST ======");
        System.out.println("Insertando nodo subordinado bajo el padre ID: " + parentId);
        
        NodeResponse response = new NodeResponse();
        response.setId(2L); 
        response.setValue(createNodeRequest.getValue());
        
        // Envolvemos el número usando JsonNullable
        response.setParentId(JsonNullable.of(parentId));
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NodeResponse> getTree() {
        System.out.println("====== API FIRST ======");
        System.out.println("Cargando la estructura completa del organigrama jerárquico...");
        
        NodeResponse rootNode = new NodeResponse();
        rootNode.setId(1L);
        rootNode.setValue("Gerencia General");
        
        // Envolvemos el nulo
        rootNode.setParentId(JsonNullable.of(null));
        
        return new ResponseEntity<>(rootNode, HttpStatus.OK);
    }
}