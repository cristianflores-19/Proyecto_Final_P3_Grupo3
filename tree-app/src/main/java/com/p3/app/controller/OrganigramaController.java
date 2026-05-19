package com.p3.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.p3.app.api.NodesApi;
import com.p3.app.api.TreeApi;
import com.p3.app.dto.CreateNodeRequest;
import com.p3.app.dto.NodeResponse;
import com.p3.app.service.OrganigramaService;

@RestController
public class OrganigramaController implements NodesApi, TreeApi {

    @Autowired
    private OrganigramaService organigramaService;

    @Override
    public ResponseEntity<NodeResponse> createRoot(CreateNodeRequest createNodeRequest) {
        NodeResponse response = organigramaService.registrarRaiz(createNodeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NodeResponse> addChild(Long parentId, CreateNodeRequest createNodeRequest) {
        NodeResponse response = organigramaService.agregarSubordinado(parentId, createNodeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<NodeResponse> getTree() {
        NodeResponse response = organigramaService.obtenerArbol();
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}