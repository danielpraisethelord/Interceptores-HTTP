# Documentación de la Aplicación

## Descripción General

Esta aplicación es una demostración del uso de interceptores HTTP en Spring Boot. Los interceptores HTTP permiten ejecutar lógica antes y después de que una solicitud sea manejada por un controlador. La aplicación mide el tiempo de procesamiento de las solicitudes y maneja accesos no autorizados.

## Componentes Principales

### 1. `FooController`

`FooController` es un controlador REST que maneja tres endpoints: `/app/foo`, `/app/bar` y `/app/baz`. Cada endpoint responde con un mensaje simple que indica qué método del controlador ha sido invocado.

- **Endpoint `/app/foo`**: Devuelve un mensaje "handler foo del controlador".
- **Endpoint `/app/bar`**: Devuelve un mensaje "handler bar del controlador".
- **Endpoint `/app/baz`**: Devuelve un mensaje "handler baz del controlador".

### 2. `LoadingTimeInterceptor`

`LoadingTimeInterceptor` es un interceptor que implementa la interfaz `HandlerInterceptor`. Este interceptor realiza las siguientes funciones:

- **`preHandle`**: Se ejecuta antes de que el controlador maneje la solicitud. 
  - Registra el tiempo de inicio de la solicitud.
  - Introduce un retraso aleatorio para simular tiempo de procesamiento.
  - Devuelve una respuesta de error (401) simulada si se cumplen ciertas condiciones.

- **`postHandle`**: Se ejecuta después de que el controlador ha manejado la solicitud, pero antes de que se genere la vista.
  - Calcula y registra el tiempo transcurrido desde que se inició la solicitud.

- **`afterCompletion`**: Se ejecuta después de que la vista se ha generado y la respuesta se ha enviado al cliente.
  - Calcula y registra el tiempo total transcurrido desde que se inició la solicitud.
  - Maneja y registra cualquier excepción que haya ocurrido durante el procesamiento de la solicitud.

### 3. `AppInterceptorApplication`

`AppInterceptorApplication` es la clase principal de la aplicación que arranca la aplicación Spring Boot.

### 4. `MvcConfig`

`MvcConfig` es una clase de configuración que implementa la interfaz `WebMvcConfigurer` para personalizar el comportamiento de Spring MVC. En esta clase se registra el `LoadingTimeInterceptor` para interceptar solicitudes a los endpoints `/app/bar` y `/app/foo`.

## Funcionamiento de la Aplicación

1. **Inicio de la Aplicación**:
   - La aplicación se inicia mediante la clase `AppInterceptorApplication`.

2. **Intercepción de Solicitudes**:
   - Cuando se realiza una solicitud a `/app/bar` o `/app/foo`, `LoadingTimeInterceptor` intercepta la solicitud.
   - En el método `preHandle`, se registra el tiempo de inicio y se introduce un retraso aleatorio. Si se cumplen ciertas condiciones, se devuelve una respuesta de error y se detiene el procesamiento.
   - Si la solicitud continúa, `postHandle` calcula y registra el tiempo transcurrido desde el inicio de la solicitud hasta el momento en que el controlador ha terminado de manejarla.
   - Finalmente, `afterCompletion` calcula y registra el tiempo total transcurrido desde el inicio de la solicitud hasta que la respuesta ha sido enviada al cliente, y maneja cualquier excepción que haya ocurrido.

3. **Respuestas del Controlador**:
   - El controlador `FooController` maneja las solicitudes a `/app/foo`, `/app/bar` y `/app/baz`, devolviendo mensajes simples en formato JSON que indican qué método del controlador fue llamado.

## Propósito de Cada Clase

- **`FooController`**: Provee endpoints REST que son interceptados por el interceptor para medir el tiempo de procesamiento y manejar accesos.
- **`LoadingTimeInterceptor`**: Mide el tiempo de procesamiento de las solicitudes, introduce retrasos simulados, y maneja accesos no autorizados.
- **`AppInterceptorApplication`**: Clase principal que arranca la aplicación Spring Boot.
- **`MvcConfig`**: Configura la aplicación Spring MVC y registra el interceptor para que se aplique a las rutas especificadas.

Esta estructura permite demostrar cómo los interceptores pueden ser usados para medir el tiempo de procesamiento y manejar la lógica de autorización en una aplicación Spring Boot.
