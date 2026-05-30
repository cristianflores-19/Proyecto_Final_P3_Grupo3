package com.p3.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.p3.app.entity.NodeEntity; // 👈 Importamos tu entidad global
import com.p3.app.model.MongoNodeDocument;

@Repository
@ConditionalOnProperty(name = "app.storage", havingValue = "mongo")
public class MongoTreeRepository implements TreeDataRepository {

    private final MongoNodeRepository mongoNodeRepository;

    public MongoTreeRepository(MongoNodeRepository mongoNodeRepository) {
        this.mongoNodeRepository = mongoNodeRepository;
    }

    // ===================================================================
    // 🏁 IMPLEMENTACIÓN DE TU INTERFAZ PUENTE (TreeDataRepository)
    // ===================================================================

    @Override
    public NodeEntity saveRoot(String value) {
        // 🚨 COMENTAMOS EL DELETEALL PARA QUE NO BORRE TUS DATOS DEL FRONTEND
        // mongoNodeRepository.deleteAll(); 
        
        // La raíz siempre tendrá el ID "1" fijo por consistencia
        String rootId = "1";
        MongoNodeDocument doc = new MongoNodeDocument(rootId, value, null);
        MongoNodeDocument savedDoc = mongoNodeRepository.save(doc);
        
        return convertToEntity(savedDoc);
    }

    @Override
    public NodeEntity saveChild(Long parentId, String value) {
        // 🚀 SECUENCIA ESTABLE: Contamos cuántos nodos hay en Mongo y le sumamos 1
        // Esto imita perfectamente el comportamiento autoincrementable de Postgres (1, 2, 3, 4...)
        String nextId = String.valueOf(mongoNodeRepository.count() + 1);
        
        MongoNodeDocument doc = new MongoNodeDocument(nextId, value, String.valueOf(parentId));
        MongoNodeDocument savedDoc = mongoNodeRepository.save(doc);
        
        return convertToEntity(savedDoc);
    }

    @Override
    public List<NodeEntity> findAll() {
        // 🔥 LA CLAVE: Traemos los documentos NoSQL de Elder y los transformamos uno a uno a tu NodeEntity
        return mongoNodeRepository.findAll().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    // ===================================================================
    // 🔄 MÉTODO AUXILIAR: Transforma un documento NoSQL a tu Entidad Relacional
    // ===================================================================
    private NodeEntity convertToEntity(MongoNodeDocument doc) {
        if (doc == null) return null;
        
        NodeEntity entity = new NodeEntity();
        // Convertimos el String de Mongo a Long para que no rompa tu motor
        entity.setId(Long.parseLong(doc.getId()));
        entity.setValue(doc.getValue());
        
        if (doc.getParentId() != null && !doc.getParentId().isEmpty()) {
            entity.setParentId(Long.parseLong(doc.getParentId()));
        } else {
            entity.setParentId(null);
        }
        
        return entity;
    }

    // ===================================================================
    // 🍃 Métodos nativos de Elder (Se quedan por si el frontend de él los usa)
    // ===================================================================
    public MongoNodeDocument save(String id, String value, String parentId) {
        MongoNodeDocument node = new MongoNodeDocument(id, value, parentId);
        return mongoNodeRepository.save(node);
    }

    public MongoNodeDocument save(MongoNodeDocument node) {
        return mongoNodeRepository.save(node);
    }
 // 🍃 Agrega este método si el Runner lo pide para crear la raíz
    public MongoNodeDocument createRoot(String id, String value) {
        MongoNodeDocument node = new MongoNodeDocument(id, value, null);
        return mongoNodeRepository.save(node);
    }

    // 🍃 Agrega este método si el Runner lo pide para colgar hijos
    public MongoNodeDocument addChild(String id, String value, String parentId) {
        MongoNodeDocument node = new MongoNodeDocument(id, value, parentId);
        return mongoNodeRepository.save(node);
    }

    public Optional<MongoNodeDocument> findById(String id) {
        return mongoNodeRepository.findById(id);
    }

    public boolean existsById(String id) {
        return mongoNodeRepository.existsById(id);
    }

    public List<MongoNodeDocument> findByParentId(String parentId) {
        return mongoNodeRepository.findByParentId(parentId);
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