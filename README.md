# Banco del Austro - Servicio REST

Servicio REST desarrollado en Quarkus que implementa múltiples patrones de diseño y mejores prácticas de desarrollo.

## 🚀 Características Principales

### Funcionalidades
- **Concatenación de Parámetros**: Endpoint POST que concatena 5 parámetros con validaciones
- **Cliente Pokemon API**: Endpoint GET que consume API externa con tolerancia a fallos
- **Tarea Periódica**: Scheduler configurable que ejecuta llamadas automáticas
- **Bus de Eventos**: Eventos síncronos y asíncronos para auditoría
- **Interceptores**: Logging automático de entrada y salida
- **Documentación API**: Swagger/OpenAPI integrado

### Tecnologías
- **Quarkus 3.24.3**: Framework principal
- **MicroProfile**: REST Client, Fault Tolerance, OpenAPI
- **Mutiny**: Programación reactiva
- **Lombok**: Reducción de boilerplate
- **Apache Commons**: Utilidades
- **Maven**: Gestión de dependencias

## 🏗️ Arquitectura y Patrones

### Patrones de Diseño Implementados
1. **Strategy Pattern**: Validaciones y tolerancia a fallos intercambiables
2. **Factory Pattern**: Creación de sanitizadores y estrategias
3. **Builder Pattern**: Construcción de respuestas HTTP
4. **Template Method Pattern**: Flujo estandarizado de procesamiento
5. **Observer Pattern**: Sistema de eventos para auditoría
6. **Service Layer Pattern**: Separación de lógica de negocio
7. **Interceptor Pattern**: AOP para logging transversal

### Capas de la Aplicación
```
┌─────────────────────────────────────┐
│           Resource Layer            │ ← REST Endpoints
├─────────────────────────────────────┤
│          Processor Layer            │ ← Request Processing
├─────────────────────────────────────┤
│           Service Layer             │ ← Business Logic
├─────────────────────────────────────┤
│         Validation Layer            │ ← Input Validation
├─────────────────────────────────────┤
│           Client Layer              │ ← External APIs
├─────────────────────────────────────┤
│            Event Layer              │ ← Event Handling
├─────────────────────────────────────┤
│       Infrastructure Layer          │ ← Cross-cutting Concerns
└─────────────────────────────────────┘
```

## 📋 Endpoints

### 1. Concatenación de Parámetros
```http
POST /api/v1/test
Content-Type: application/x-www-form-urlencoded

param1=Hola&param2=Mundo&param3=Banco&param4=Del&param5=Austro
```

**Respuesta**: `HolaMundoBancoDelAustro`

**Validaciones**:
- Parámetros no nulos ni vacíos
- Prevención de SQL injection
- Sanitización de entrada

### 2. Pokemon API
```http
GET /api/v2/move?strategy=retry
```

**Parámetros**:
- `strategy`: `retry` | `circuitbreaker` (default: `retry`)

**Tolerancia a Fallos**:
- **Retry**: 3 reintentos con delay de 1 segundo
- **Circuit Breaker**: Fallback automático tras fallos

## ⚙️ Configuración

### Puertos
- **Desarrollo**: 15050
- **Testing**: 15055

### Logs
- **Tamaño máximo**: 10MB
- **Rotaciones**: 3 archivos
- **Modo**: Asíncrono
- **Ubicación**: `logs/application.log`

### Tarea Periódica
```properties
# Cada 5 minutos (configurable)
pokemon.scheduler.cron=0 */5 * * * ?
pokemon.scheduler.strategy=retry
```

### CORS
- **Habilitado**: Sí
- **Orígenes**: `*` (configurable)
- **Métodos**: GET, POST, PUT, DELETE, OPTIONS

## 🛠️ Instalación y Despliegue

### Prerrequisitos
- Java 21+
- Maven 3.8+

### Compilación
```bash
mvn clean package
```

### Ejecución

#### Configuración por defecto
```bash
# Linux/Mac
./start.sh

# Windows
start.bat
```

#### Configuración personalizada
```bash
# Linux/Mac
./start.sh application-custom.properties

# Windows
start.bat application-custom.properties
```

### Modo desarrollo
```bash
mvn quarkus:dev
```

## 📚 Documentación API

### Swagger UI (Solo desarrollo)
- **URL**: http://localhost:15050/q/swagger-ui/
- **OpenAPI Spec**: http://localhost:15050/q/openapi

### Dev UI
- **URL**: http://localhost:15050/q/dev/

## 🧪 Testing

### Ejecutar tests
```bash
mvn test
```

### Tests incluidos
- **TestResourceTest**: Validación de concatenación
- **PokemonResourceTest**: Tolerancia a fallos
- **PokemonSchedulerTest**: Tarea periódica

### Ejemplos de prueba

#### Concatenación exitosa
```bash
curl -X POST http://localhost:15050/api/v1/test \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "param1=Hola&param2=Mundo&param3=Banco&param4=Del&param5=Austro"
```

#### Pokemon API con retry
```bash
curl http://localhost:15050/api/v2/move?strategy=retry
```

#### Pokemon API con circuit breaker
```bash
curl http://localhost:15050/api/v2/move?strategy=circuitbreaker
```

## 📊 Monitoreo y Auditoría

### Eventos del Sistema
- **Concatenación**: Eventos síncronos para auditoría inmediata
- **Pokemon API**: Eventos asíncronos para monitoreo no bloqueante

### Logging
- **Interceptores**: Log automático de entrada/salida de métodos
- **Eventos**: Auditoría de operaciones de negocio
- **Errores**: Captura y logging de excepciones

### Métricas disponibles
- Tiempo de respuesta de APIs
- Conteo de operaciones exitosas/fallidas
- Uso de estrategias de tolerancia a fallos

## 🔧 Configuraciones Avanzadas

### Archivo application.properties
```properties
# Puerto HTTP
quarkus.http.port=15050
quarkus.http.test-port=15055

# CORS
quarkus.http.cors=true
quarkus.http.cors.origins=*

# Logs
quarkus.log.file.enable=true
quarkus.log.file.path=logs/application.log
quarkus.log.file.rotation.max-file-size=10M
quarkus.log.file.rotation.max-backup-index=3
quarkus.log.file.async=true

# Pokemon API Client
pokemon-api/mp-rest/url=https://pokeapi.co
quarkus.rest-client.pokemon-api.connect-timeout=5000
quarkus.rest-client.pokemon-api.read-timeout=10000

# Scheduler
pokemon.scheduler.cron=0 */5 * * * ?
pokemon.scheduler.strategy=retry

# OpenAPI
quarkus.smallrye-openapi.info-title=Banco del Austro API
%dev.quarkus.swagger-ui.always-include=true
%prod.quarkus.swagger-ui.always-include=false
```

## 🚀 Despliegue en Producción

### Compilación nativa
```bash
mvn package -Dnative
```

### Docker
```bash
# Crear imagen
docker build -f src/main/docker/Dockerfile.jvm -t banco-austro .

# Ejecutar contenedor
docker run -i --rm -p 15050:15050 banco-austro
```

### Variables de entorno
```bash
export QUARKUS_HTTP_PORT=15050
export POKEMON_SCHEDULER_CRON="0 */10 * * * ?"
export POKEMON_SCHEDULER_STRATEGY=circuitbreaker
```

## 📈 Rendimiento

### Optimizaciones implementadas
- **Eventos asíncronos**: No bloquean operaciones principales
- **Circuit Breaker**: Evita cascada de fallos
- **Logs asíncronos**: Mejor rendimiento de I/O
- **Sanitización eficiente**: Regex optimizados
- **Conexiones reutilizables**: Pool de conexiones HTTP

### Métricas esperadas
- **Concatenación**: < 50ms
- **Pokemon API**: < 2s (con tolerancia a fallos)
- **Throughput**: > 1000 req/s

## 🔒 Seguridad

### Validaciones implementadas
- **SQL Injection**: Detección y sanitización
- **Input Validation**: Parámetros no nulos/vacíos
- **CORS**: Configuración restrictiva en producción
- **Headers**: Propagación controlada

### Mejores prácticas
- Sanitización de entrada
- Logging de seguridad
- Timeouts configurables
- Fallbacks seguros

## 🤝 Contribución

### Estructura del código
```
src/main/java/com/banco/austro/
├── resource/          # REST Endpoints
├── service/           # Business Logic
├── processor/         # Request Processing
├── validation/        # Input Validation
├── sanitizer/         # Input Sanitization
├── faulttolerance/    # Fault Tolerance
├── client/            # External Clients
├── events/            # Event Handling
├── interceptor/       # AOP Interceptors
├── scheduler/         # Scheduled Tasks
├── dto/               # Data Transfer Objects
└── config/            # Configuration
```

### Principios aplicados
- **SOLID**: Principios de diseño orientado a objetos
- **Clean Code**: Código limpio y mantenible
- **DRY**: Don't Repeat Yourself
- **KISS**: Keep It Simple, Stupid
- **YAGNI**: You Aren't Gonna Need It

## 📞 Soporte

Para soporte técnico o consultas:
- **Documentación**: Ver archivos `CLASS_DIAGRAM.md` y `CRON_CONFIG.md`
- **Logs**: Revisar `logs/application.log`
- **Health Check**: http://localhost:15050/q/health

git remote add origin https://github.com/joaoRDev/bancoAustro.git
git branch -M main
git push -u origin main