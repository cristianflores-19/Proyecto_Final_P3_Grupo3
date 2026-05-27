package com.p3.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import com.p3.app.repository.MongoNodeRepository; // 👈 Inyectamos el repositorio CRUD directo de documentos

@Component
@ConditionalOnExpression("'${app.storage:}' == 'mongo' and '${app.mongo.demo-data:false}' == 'true'")
public class MongoDemoDataRunner implements CommandLineRunner {

    private final MongoNodeRepository mongoNodeRepository; // 👈 Cambiado al CRUD directo

    public MongoDemoDataRunner(MongoNodeRepository mongoNodeRepository) {
        this.mongoNodeRepository = mongoNodeRepository;
    }

    @Override
    public void run(String... args) {
        mongoNodeRepository.deleteAll();

        // Guardamos los documentos NoSQL usando el método nativo de Mongo
        mongoNodeRepository.save(new com.p3.app.model.MongoNodeDocument("1", "Gerencia", null));
        mongoNodeRepository.save(new com.p3.app.model.MongoNodeDocument("2", "Jefatura TI", "1"));
        mongoNodeRepository.save(new com.p3.app.model.MongoNodeDocument("3", "Desarrollador", "2"));

        System.out.println("=== DEMO MONGODB ORGANIGRAMA ===");
        System.out.println("Total de nodos guardados: " + mongoNodeRepository.count());
        System.out.println("Nodo raiz: " + mongoNodeRepository.findById("1").orElse(null));
        System.out.println("Hijos de Gerencia: " + mongoNodeRepository.findByParentId("1"));
        System.out.println("Todos los nodos: " + mongoNodeRepository.findAll());
        System.out.println("================================");
    }
}