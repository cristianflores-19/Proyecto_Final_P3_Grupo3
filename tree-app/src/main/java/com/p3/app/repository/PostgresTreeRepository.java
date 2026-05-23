package com.p3.app.repository;

import com.p3.app.entity.NodeEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty; 
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "app.storage", havingValue = "postgres") 
public class PostgresTreeRepository {

    private final NodeJpaRepository nodeJpaRepository;

    public PostgresTreeRepository(NodeJpaRepository nodeJpaRepository) {
        this.nodeJpaRepository = nodeJpaRepository;
    }

    public NodeEntity saveRoot(String value) {
        NodeEntity root = new NodeEntity(value, null);
        return nodeJpaRepository.save(root);
    }

    public NodeEntity saveChild(Long parentId, String value) {
        NodeEntity child = new NodeEntity(value, parentId);
        return nodeJpaRepository.save(child);
    }

    public List<NodeEntity> findAll() {
        return nodeJpaRepository.findAll();
    }

    public Optional<NodeEntity> findById(Long id) {
        return nodeJpaRepository.findById(id);
    }

    public List<NodeEntity> findChildren(Long parentId) {
        return nodeJpaRepository.findByParentId(parentId);
    }

    public void deleteAll() {
        nodeJpaRepository.deleteAll();
    }
}
