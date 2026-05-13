package com.p3.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.p3.app.model.MongoNodeDocument;

public interface MongoNodeRepository extends MongoRepository<MongoNodeDocument, String> {

    List<MongoNodeDocument> findByParentId(String parentId);
}