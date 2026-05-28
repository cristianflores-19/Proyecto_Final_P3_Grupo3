# Proyecto Final - Programación 3 (UMG)
## Caso 2: Organigrama Empresarial

Este es un proyecto multimódulo desarrollado en Spring Boot utilizando una arquitectura limpia para la gestión de estructuras jerárquicas (Gerencia > Jefatura TI > Desarrollador).

### 📋 Gestión del Proyecto
Puedes seguir el avance de nuestras tareas, entregas semanales y la organización del equipo en nuestro tablero oficial:

👉 [Acceder al Tablero de Trello](https://trello.com/b/vRjPylhZ/proyecto-programacion-3)

### 👥 Integrantes del Grupo 3
* **Integrante A:** Cristian Flores (Estructura, Motor General y API)
* **Integrante B:** Jose (Estrategia Collections y PostgreSQL)
* **Integrante C:** Elder (MongoDB y Beans Condicionales)

## Integrante C - MongoDB y Beans Condicionales

Como Integrante C se trabajó la parte de persistencia NoSQL utilizando MongoDB para el caso del organigrama empresarial.

### Archivos trabajados

* `application-mongo.properties`: archivo de configuración para activar la persistencia con MongoDB.
* `MongoNodeDocument.java`: documento que representa un nodo del organigrama dentro de MongoDB.
* `MongoNodeRepository.java`: repositorio basado en Spring Data MongoDB.
* `MongoTreeRepository.java`: repositorio encargado de guardar, buscar, listar, crear raíz, agregar hijos, contar y eliminar nodos en MongoDB.
* `MongoDemoDataRunner.java`: clase de prueba para cargar datos iniciales del caso de uso.

### Configuración MongoDB

Para activar la persistencia con MongoDB se utiliza la siguiente configuración:

```properties
app.storage=mongo
app.tree-strategy=custom
app.mongo.demo-data=true
```

La configuración general puede mantenerse en memoria para las pruebas del equipo, mientras que MongoDB se maneja desde el archivo `application-mongo.properties`.

### Caso de prueba

Se utilizó el caso 2: Organigrama empresarial.

Ejemplo de jerarquía:

```text
Gerencia > Jefatura TI > Desarrollador
```

### Validación

Se validó que el proyecto compilara correctamente con el comando:

```bash
mvn clean compile
```

El resultado esperado es:

```text
BUILD SUCCESS
```

### Funcionalidad implementada

La persistencia MongoDB permite:

* Guardar nodos del organigrama.
* Buscar nodos por identificador.
* Verificar si un nodo existe.
* Listar todos los nodos.
* Buscar subordinados por `parentId`.
* Crear el nodo raíz.
* Agregar nodos hijos.
* Contar los nodos almacenados.
* Eliminar nodos o limpiar la colección.
