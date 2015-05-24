## Soklet Guice

#### What Is It?

[Guice](https://github.com/google/guice) integration for [Soklet](http://soklet.com), a minimalist infrastructure for Java webapps and microservices.

#### License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

#### Maven Installation

```xml
<dependency>
  <groupId>com.soklet</groupId>
  <artifactId>soklet-guice</artifactId>
  <version>1.0.2</version>
</dependency>
```

#### Direct Download

If you don't use Maven, you can drop [soklet-guice-1.0.2.jar](http://central.maven.org/maven2/com/soklet/soklet-guice/1.0.2/soklet-guice-1.0.2.jar) directly into your project.  You'll also need [Guice 4.0](https://github.com/google/guice) as a dependency.

## Example Code

```java
// SokletModule provides default implementations for Soklet interfaces like ResponseHandler.
// You can override them in your own module as needed
public static void main(String[] args) throws Exception {
  Injector injector = createInjector(Modules.override(new SokletModule()).with(new AppModule()));
  Server server = injector.getInstance(Server.class);
  server.start();
  System.in.read(); // Wait for keypress
  server.stop();
}

class AppModule extends AbstractModule {
  // SokletMatchers.httpMethodMatcher() is a Guice Matcher which matches any resource method.
  // For example, methods annotated with @GET("/test") and @DELETE("/users/{id}") would be matched.
  // This is useful for applying method interceptors to handle security, database transactions, and more
  @Override
  protected void configure() {
    bindInterceptor(Matchers.annotatedWith(Resource.class),
      SokletMatchers.httpMethodMatcher(), new MyCustomInterceptor());
  }

  // Use Guice as you would normally
  @Provides
  @Singleton
  public Server provideServer(InstanceProvider instanceProvider) {
    return JettyServer.forInstanceProvider(instanceProvider).port(8080).build();
  }  

  // Override Soklet's default implementations as needed
  @Provides
  @Singleton
  public PageResponseWriter providePageResponseWriter() {
    // My app should use a custom Mustache.java-backed page response writer
    return new MyMustachePageResponseWriter();
  }
}
```

## About

Soklet Guice was created by [Mark Allen](http://revetkn.com) and sponsored by [Transmogrify, LLC.](http://xmog.com)