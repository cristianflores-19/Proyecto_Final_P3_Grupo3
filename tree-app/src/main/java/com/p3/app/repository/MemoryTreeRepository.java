package com.p3.app.repository;

import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty; // <-- NUEVO IMPORT
import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(name = "app.storage", havingValue = "memory") // <-- NUEVA ANOTACIÓN (Sin ;)
@Repository
public class MemoryTreeRepository {
    // Esto simula tu base de datos para el Organigrama
    // Guardamos ID del puesto -> Nombre del puesto
    // Revisión final de estructura - Semana 1
    private final Map<Long, String> storage = new HashMap<>();

    public void save(Long id, String name) {
        storage.put(id, name);
    }

    public Map<Long, String> findAll() {
        return storage;
    }
}