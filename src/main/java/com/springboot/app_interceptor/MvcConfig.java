package com.springboot.app_interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * La interfaz WebMvcConfigurer en Spring Boot es una interfaz que proporciona
 * métodos de configuración opcional para Spring MVC. Implementarla te permite
 * personalizar el comportamiento del marco de trabajo MVC de Spring sin
 * necesidad de anular las configuraciones predeterminadas. Es útil para tareas
 * como añadir interceptores, configurar rutas, manejar CORS, configurar la
 * resolución de vistas, y más.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("timeInterceptor")
    private HandlerInterceptor timeInterceptor;

    /**
     * addInterceptors(InterceptorRegistry registry):
     * 
     * Se usa para añadir interceptores personalizados. Los interceptores pueden
     * interceptar y manipular solicitudes y respuestas HTTP.
     */
    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        // registry.addInterceptor(timeInterceptor).addPathPatterns("/app/**");
        // registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/bar",
        // "/app/foo");
        registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar", "/app/foo");
    }

}
