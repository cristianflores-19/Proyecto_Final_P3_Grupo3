package com.p3.app.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

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