# Proyecto Final Programación 3 — Integrante B

## Información General

**Proyecto:** Organigrama dinámico con múltiples estrategias de almacenamiento y recorrido de árboles.

**Curso:** Programación 3
**Rol:** Integrante B
**Responsabilidades principales:**

* Estrategia Collections
* Persistencia PostgreSQL
* Integración Spring Boot
* Endpoints REST
* Documentación técnica
* Pruebas end-to-end

---

# Arquitectura del Proyecto

El proyecto fue desarrollado utilizando una arquitectura multimódulo basada en Maven y Spring Boot.

```text
organigrama-parent
│
├── tree-engine
│   ├── TreeAlgorithmStrategy
│   ├── CollectionsTreeStrategy
│   └── CustomTreeStrategy
│
└── tree-app
    ├── controllers
    ├── services
    ├── repositories
    ├── entities
    ├── configuration
    └── resources
```

## Descripción de módulos

### tree-engine

Módulo encargado de implementar las estrategias de manejo del árbol.

### tree-app

Módulo Spring Boot encargado de:

* API REST
* Persistencia
* Integración
* Swagger/OpenAPI
* Configuración de perfiles

---

# Patrón de Diseño Utilizado

El proyecto implementa el patrón:

```text
Strategy Pattern
```

## Objetivo

Desacoplar:

* lógica del árbol
* almacenamiento
* recorrido
* persistencia

permitiendo cambiar implementaciones sin modificar controllers ni servicios.

---

# Trabajo realizado como Integrante B

## 1. Implementación de estrategia Collections

Se implementó la clase:

```text
CollectionsTreeStrategy
```

utilizando Java Collections Framework.

## Estructuras utilizadas

| Estructura                | Uso                    |
| ------------------------- | ---------------------- |
| HashMap<Long, String>     | Valores de nodos       |
| HashMap<Long, List<Long>> | Relaciones padre-hijos |
| HashMap<Long, Long>       | Relaciones hijo-padre  |
| ArrayDeque                | BFS                    |
| Queue                     | Recorridos por niveles |
| HashSet                   | Validación de ciclos   |
| Recursividad              | DFS y altura           |

---

# Operaciones implementadas

## BFS (Breadth First Search)

Recorrido por niveles utilizando cola.

## DFS (Depth First Search)

Recorrido en profundidad utilizando recursividad.

## Altura del árbol

Cálculo de la máxima profundidad del árbol.

## Profundidad de nodos

Obtención de la distancia desde la raíz.

## Ancestros

Obtención de padres de un nodo.

## Path

Ruta desde raíz hasta nodo solicitado.

## Subárbol

Obtención parcial del árbol desde un nodo.

## Validación de ciclos

Verificación mediante control de visitados.

---

# Persistencia PostgreSQL

## Configuración realizada

Se configuró:

* Spring Data JPA
* Hibernate
* PostgreSQL
* profiles Spring Boot
* datasource

## Base de datos

```text
tree_db
```

## Tabla utilizada

```sql
nodes (
    id BIGINT,
    value VARCHAR(150),
    parent_id BIGINT
)
```

## Entidades implementadas

### NodeEntity

Entidad JPA para persistencia.

### NodeJpaRepository

Repositorio Spring Data JPA.

### PostgresTreeRepository

Repositorio especializado del árbol.

## Operaciones implementadas

* guardar raíz
* guardar hijos
* listar nodos
* buscar nodos
* obtener hijos
* reconstrucción del árbol

---

# Integración Spring Boot

## TreeService

Se implementó:

```text
TreeService
```

encargado de integrar:

* CollectionsTreeStrategy
* PostgreSQL
* lógica del árbol
* endpoints REST

## Funciones principales

* reconstrucción del árbol desde PostgreSQL
* carga de nodos
* sincronización de estrategia
* integración con controllers

---

# Configuración de Beans

Se configuró:

```text
TreeAlgorithmConfig
```

para registrar:

```text
TreeAlgorithmStrategy
```

como Bean de Spring.

---

# Endpoints REST Implementados

## Crear raíz

```http
POST /api/v1/tree/root
```

## Agregar hijo

```http
POST /api/v1/tree/{parentId}/child
```

## Obtener árbol

```http
GET /api/v1/tree
```

## Obtener subárbol

```http
GET /api/v1/tree/subtree/{id}
```

## BFS

```http
GET /api/v1/tree/bfs
```

## DFS

```http
GET /api/v1/tree/dfs
```

## Altura

```http
GET /api/v1/tree/height
```

## Profundidad

```http
GET /api/v1/tree/depth/{id}
```

## Ancestros

```http
GET /api/v1/tree/ancestors/{id}
```

## Validación

```http
GET /api/v1/tree/validate
```

---

# Swagger/OpenAPI

Se integró Swagger para:

* documentación automática
* pruebas REST
* validación funcional
* demostraciones

## Acceso

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Configuración PostgreSQL

## application-postgres.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tree_db
spring.datasource.username=postgres
spring.datasource.password=*****
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Ejecución del Proyecto

## Compilar proyecto

```bash
mvn clean install
```

## Ejecutar aplicación

```bash
mvn -pl tree-app spring-boot:run "-Dspring-boot.run.profiles=postgres"
```

---

# Pruebas End-to-End

## Crear raíz

```http
POST /api/v1/tree/root?value=Gerencia
```

## Crear hijo

```http
POST /api/v1/tree/1/child?value=Jefatura TI
```

## Crear nieto

```http
POST /api/v1/tree/2/child?value=Desarrollador
```

## Obtener árbol

```http
GET /api/v1/tree
```

## BFS

```http
GET /api/v1/tree/bfs
```

## DFS

```http
GET /api/v1/tree/dfs
```

## Altura

```http
GET /api/v1/tree/height
```

## Profundidad

```http
GET /api/v1/tree/depth/3
```

## Ancestros

```http
GET /api/v1/tree/ancestors/3
```

## Validación

```http
GET /api/v1/tree/validate
```

---

# Evidencias Realizadas

Se documentaron pruebas funcionales de:

* Swagger
* PostgreSQL
* creación de nodos
* BFS
* DFS
* profundidad
* altura
* ancestros
* subárboles
* validación
* árbol completo

---

# Problemas resueltos durante desarrollo

## Duplicados en memoria

Se corrigió limpieza de estructuras Collections antes de reconstruir el árbol.

## Configuración Swagger duplicada

Se limitaron endpoints visibles mediante:

```properties
springdoc.paths-to-match=/api/v1/**
```

## Conflictos de múltiples clases main

Se definió clase principal correcta para Spring Boot.

## Integración PostgreSQL

Se resolvieron problemas de datasource, JPA y profiles.

---

# Rama de Trabajo

```text
feature/B-postgres-collections
```

---

# Tecnologías Utilizadas

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Swagger/OpenAPI
* Maven
* Hibernate
* Java Collections Framework
* Git/GitHub

---

# Conclusión

Como Integrante B se desarrolló la estrategia basada en Collections Framework y la integración completa con PostgreSQL y Spring Boot.

Se implementaron recorridos BFS y DFS, persistencia de nodos, endpoints REST, documentación Swagger y pruebas end-to-end, permitiendo que el sistema pueda gestionar organigramas dinámicos utilizando una arquitectura desacoplada basada en Strategy Pattern.
