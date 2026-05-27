package com.p3.app.controller;

import com.p3.app.entity.NodeEntity;
import com.p3.app.service.TreeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/tree")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @PostMapping("/root")
    public NodeEntity createRoot(@RequestParam String value) {
        return treeService.createRoot(value);
    }

    @PostMapping("/{parentId}/child")
    public NodeEntity addChild(@PathVariable Long parentId,
                               @RequestParam String value) {

        return treeService.addChild(parentId, value);
    }

    @GetMapping
    public Object getTree() {
        return treeService.getTree();
    }

    @GetMapping("/subtree/{id}")
    public Object getSubTree(@PathVariable Long id) {
        return treeService.getSubTree(id);
    }

    @GetMapping("/dfs")
    public List<String> dfs() {
        return treeService.dfs();
    }

    @GetMapping("/bfs")
    public List<String> bfs() {
        return treeService.bfs();
    }

    @GetMapping("/height")
    public int height() {
        return treeService.height();
    }

    @GetMapping("/depth/{id}")
    public int depth(@PathVariable Long id) {
        return treeService.depth(id);
    }

    @GetMapping("/ancestors/{id}")
    public List<String> ancestors(@PathVariable Long id) {
        return treeService.ancestors(id);
    }

    @GetMapping("/validate")
    public boolean validate() {
        return treeService.validate();
    }
    
    @GetMapping("/path/{id}")
    public List<String> getPath(@PathVariable Long id) {
        return treeService.getPath(id);
    }
}