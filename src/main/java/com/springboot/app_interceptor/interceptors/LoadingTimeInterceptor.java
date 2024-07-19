package com.springboot.app_interceptor.interceptors;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * En Spring Boot, la interfaz HandlerInterceptor es una parte clave del sistema
 * de interceptores de Spring MVC. Proporciona una manera de interceptar y
 * manipular solicitudes HTTP antes de que lleguen a los controladores, y
 * después de que hayan sido procesadas por los controladores, pero antes de que
 * se envíe la respuesta al cliente. Los interceptores permiten realizar tareas
 * comunes de pre y post-procesamiento, como la autenticación, autorización,
 * registro de auditorías, manejo de excepciones, y más.
 * 
 * Usos Comunes de HandlerInterceptor
 * Autenticación y Autorización: Verificar si un usuario está autenticado o
 * autorizado para acceder a un recurso específico.
 * Registro y Monitoreo: Registrar solicitudes y respuestas para monitoreo y
 * auditoría.
 * Modificación de Solicitudes/Respuestas: Añadir o modificar atributos en las
 * solicitudes o respuestas antes de que lleguen a los controladores o al
 * cliente.
 * Manejo de Excepciones: Capturar y manejar excepciones globalmente después de
 * que un controlador haya procesado la solicitud.
 * Implementar HandlerInterceptor en Spring Boot es una forma poderosa y
 * flexible de gestionar solicitudes y respuestas, proporcionando puntos de
 * control en todo el ciclo de vida de una solicitud HTTP.
 */
@Component("timeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    /**
     * Se puecen observar advertencias al usar los métodos a sobreescribir y son
     * debido a que las anotaciones de
     * no-nulidad (@NonNull) no están presentes en los parámetros de tus métodos
     * sobrescritos (preHandle, postHandle, y afterCompletion). Esto ocurre porque
     * la interfaz HandlerInterceptor especifica que estos parámetros no deben ser
     * nulos, y tu implementación no tiene estas anotaciones explícitas.
     * 
     * Para resolver estas advertencias, debes añadir las anotaciones @NonNull a los
     * parámetros en tus métodos. A continuación, te muestro cómo actualizar tu
     * código:
     */

    /**
     * preHandle(HttpServletRequest request, HttpServletResponse response, Object
     * handler):
     * 
     * Este método se ejecuta antes de que el controlador maneje la solicitud.
     * Se usa típicamente para realizar tareas de pre-procesamiento como
     * autenticación, autorización, o registro.
     * Devuelve un valor booleano: true para continuar la ejecución de la cadena de
     * interceptores y el controlador, o false para detener la ejecución.
     */
    @Override
    public boolean preHandle(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") Object handler)
            throws Exception {
        HandlerMethod controller = ((HandlerMethod) handler);
        logger.info("LoadingTimeInterceptor: preHandle() entrando ..." + controller.getMethod().getName());

        Long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        Random random = new Random();
        int delay = random.nextInt(500);
        Thread.sleep(delay);

        Map<String, String> json = new HashMap<>();
        json.put("error", "no tienes acceso  a esta pagina!");
        json.put("date", new Date().toString());

        ObjectMapper mapper = new ObjectMapper();
        String jsoString = mapper.writeValueAsString(json);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(jsoString);

        // return true;
        return false;
    }

    /**
     * postHandle(HttpServletRequest request, HttpServletResponse response, Object
     * handler, ModelAndView modelAndView):
     * 
     * Este método se ejecuta después de que el controlador ha manejado la
     * solicitud, pero antes de que se genere la vista.
     * Se usa para añadir datos adicionales al modelo y vista, o para modificar la
     * vista antes de que se genere.
     */
    @Override
    public void postHandle(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        Long end = System.currentTimeMillis();
        Long start = (Long) request.getAttribute("start");
        Long result = end - start;

        logger.info("Tiempo transcurrido: " + result + " milisegundos");

        logger.info(
                "LoadingTimeInterceptor: postHandle() saliendo ..." + ((HandlerMethod) handler).getMethod().getName());

        // HandlerInterceptor.super.postHandle(request, response, handler,
        // modelAndView);
    }

    /**
     * afterCompletion(HttpServletRequest request, HttpServletResponse response,
     * Object handler, Exception ex):
     * 
     * Este método se ejecuta después de que la vista se ha generado y la respuesta
     * se ha enviado al cliente.
     * Se usa para realizar tareas de limpieza, como liberar recursos, registrar
     * actividades, o manejar excepciones.
     */

    @SuppressWarnings("null")
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        Long end = System.currentTimeMillis();
        Long start = (Long) request.getAttribute("start");
        Long result = end - start;

        logger.info("Tiempo total transcurrido: " + result + " milisegundos");

        if (ex != null) {
            logger.error("Exception occurred: ", ex);
        }

        logger.info("LoadingTimeInterceptor: afterCompletion() saliendo ..."
                + ((HandlerMethod) handler).getMethod().getName());
    }

}
