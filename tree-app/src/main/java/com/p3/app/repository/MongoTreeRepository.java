package com.p3.app.repository;

import java.util.List;

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

    public List<MongoNodeDocument> findAll() {
        return mongoNodeRepository.findAll();
    }

    public List<MongoNodeDocument> findByParentId(String parentId) {
        return mongoNodeRepository.findByParentId(parentId);
    }

    public void deleteAll() {
        mongoNodeRepository.deleteAll();
    }
}