package com.p3.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.p3.app.model.MongoNodeDocument;

@Repository
@ConditionalOnProperty(name = "app.storage", havingValue = "mongo")
public class MongoTreeRepository {

    private final MongoNodeRepository mongoNodeRepository;

    public MongoTreeRepository(MongoNodeRepository mongoNodeRepository) {
        this.mongoNodeRepository = mongoNodeRepository;
    }

    public MongoNodeDocument save(String id, String value, String parentId) {
        MongoNodeDocument node = new MongoNodeDocument(id, value, parentId);
        return mongoNodeRepository.save(node);
    }

    public MongoNodeDocument save(MongoNodeDocument node) {
        return mongoNodeRepository.save(node);
    }

    public Optional<MongoNodeDocument> findById(String id) {
        return mongoNodeRepository.findById(id);
    }

    public boolean existsById(String id) {
        return mongoNodeRepository.existsById(id);
    }

    public List<MongoNodeDocument> findAll() {
        return mongoNodeRepository.findAll();
    }

    public List<MongoNodeDocument> findByParentId(String parentId) {
        return mongoNodeRepository.findByParentId(parentId);
    }

    public MongoNodeDocument createRoot(String id, String value) {
        return save(id, value, null);
    }

    public MongoNodeDocument addChild(String id, String value, String parentId) {
        return save(id, value, parentId);
    }

    public long count() {
        return mongoNodeRepository.count();
    }

    public void deleteById(String id) {
        mongoNodeRepository.deleteById(id);
    }

    public void deleteAll() {
        mongoNodeRepository.deleteAll();
    }
}