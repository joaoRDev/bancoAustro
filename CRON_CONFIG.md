# Configuración de Tarea Periódica

## Expresiones Cron Configurables

La tarea periódica para llamar al servicio Pokemon API se configura mediante la propiedad:

```properties
pokemon.scheduler.cron=0 */5 * * * ?
```

## Formato Cron

```
Segundo Minuto Hora Día Mes Día_Semana
   |      |     |   |   |      |
   |      |     |   |   |      +-- Día de la semana (0-7, 0 y 7 = Domingo)
   |      |     |   |   +--------- Mes (1-12)
   |      |     |   +------------- Día del mes (1-31)
   |      |     +----------------- Hora (0-23)
   |      +----------------------- Minuto (0-59)
   +------------------------------ Segundo (0-59)
```

## Ejemplos de Configuración

```properties
# Cada 5 minutos (por defecto)
pokemon.scheduler.cron=0 */5 * * * ?

# Cada 2 minutos
pokemon.scheduler.cron=0 */2 * * * ?

# Cada hora
pokemon.scheduler.cron=0 0 * * * ?

# Cada día a las 9:00 AM
pokemon.scheduler.cron=0 0 9 * * ?

# Cada lunes a las 8:30 AM
pokemon.scheduler.cron=0 30 8 * * 1
```

## Configuración de Estrategia

```properties
# Estrategia por defecto (retry o circuitbreaker)
pokemon.scheduler.strategy=retry
```

## Uso

1. **Configuración por defecto**: La tarea se ejecuta cada 5 minutos
2. **Configuración personalizada**: Usar archivo `application-custom.properties`
3. **Ejecución**: `./start.sh application-custom.properties`