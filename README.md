## Soklet Guice

#### What Is It?

[Guice](https://github.com/google/guice) integration for [Soklet](http://soklet.com), a minimalist infrastructure for Java webapps and microservices.

#### License

[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

#### Maven Installation

Coming soon
<!--
```xml
<dependency>
  <groupId>com.soklet</groupId>
  <artifactId>soklet</artifactId>
  <version>1.0.0</version>
</dependency>
```
-->
#### Direct Download

Coming soon
<!-- [https://www.soklet.com/releases/soklet-1.0.0.jar](https://www.soklet.com/releases/soklet-1.0.0.jar) -->

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
  @Inject
  @Provides
  @Singleton
  public Server provideServer(InstanceProvider instanceProvider) {
    return new JettyServer(JettyServerConfiguration.builder(instanceProvider).port(8080).build());
  }
}
```