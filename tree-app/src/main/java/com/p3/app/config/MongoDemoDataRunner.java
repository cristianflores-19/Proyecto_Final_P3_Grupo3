package com.p3.app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.p3.app.repository.MongoTreeRepository;

@Component
@ConditionalOnProperty(name = "app.mongo.demo-data", havingValue = "true")
public class MongoDemoDataRunner implements CommandLineRunner {

    private final MongoTreeRepository mongoTreeRepository;

    public MongoDemoDataRunner(MongoTreeRepository mongoTreeRepository) {
        this.mongoTreeRepository = mongoTreeRepository;
    }

    @Override
    public void run(String... args) {
        mongoTreeRepository.deleteAll();

        mongoTreeRepository.createRoot("1", "Gerencia");
        mongoTreeRepository.addChild("2", "Jefatura TI", "1");
        mongoTreeRepository.addChild("3", "Desarrollador", "2");

        System.out.println("=== DEMO MONGODB ORGANIGRAMA ===");
        System.out.println("Total de nodos guardados: " + mongoTreeRepository.count());
        System.out.println("Nodo raiz: " + mongoTreeRepository.findById("1").orElse(null));
        System.out.println("Hijos de Gerencia: " + mongoTreeRepository.findByParentId("1"));
        System.out.println("Todos los nodos: " + mongoTreeRepository.findAll());
        System.out.println("================================");
    }
}