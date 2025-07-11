package com.banco.austro.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Interceptor para logging de entrada y salida de métodos
 * Registra parámetros de entrada, tiempo de ejecución y resultado
 */
@Interceptor
@Logged
@Priority(1000)
public class LoggingInterceptor {
    
    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);
    
    /**
     * Intercepta la ejecución de métodos anotados con @Logged
     * @param context Contexto de invocación del método
     * @return Resultado de la ejecución del método
     * @throws Exception Si ocurre error durante la ejecución
     */
    @AroundInvoke
    public Object logExecution(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String className = context.getTarget().getClass().getSimpleName();
        Object[] parameters = context.getParameters();
        
        long startTime = System.currentTimeMillis();
        
        // Log entrada
        LOG.infof("[ENTRADA] %s.%s() - Parámetros: %s - Timestamp: %s", 
                 className, methodName, 
                 Arrays.toString(parameters), 
                 LocalDateTime.now());
        
        try {
            Object result = context.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Log salida exitosa
            LOG.infof("[SALIDA] %s.%s() - Resultado: %s - Tiempo: %dms - Timestamp: %s", 
                     className, methodName, 
                     StringUtils.abbreviate(String.valueOf(result), 100),
                     executionTime, LocalDateTime.now());
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Log salida con error
            LOG.errorf("[ERROR] %s.%s() - Error: %s - Tiempo: %dms - Timestamp: %s", 
                      className, methodName, e.getMessage(), 
                      executionTime, LocalDateTime.now());
            
            throw e;
        }
    }
}