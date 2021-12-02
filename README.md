# Problem
Your system can not handle concurrent user when high traffic. 
Usually happen when flash sale or event campaign. 
The purpose is to boost transaction which is good for your company.

# Solution
There is no perfect answer for this question and all depends on details.
To prevent above problem I propose use redis for validate the stock.
With decrement function, Redis will guarantee the consistency. And also fast as it running in memory.
Elasticsearch will bring best user experience when search the product. 
But we still need RDBMS as source of truth. In this case we will use MYSQL.

# Functional Suite

This needs [Java 11](https://java.com/en/download/help/download_options.xml), [Maven](https://maven.apache.org/install.html), [Elasticsearch](https://www.elastic.co/downloads/elasticsearch), and [Redis](https://redis.io/download) to be installed, followed by some libraries. The steps are listed below.

## Setup

- You should have [Elasticsearch](https://www.elastic.co/downloads/elasticsearch) and [Redis](https://redis.io/download) running in your machine.
- Install [Java](https://java.com/en/download/help/download_options.xml) and [Maven](https://maven.apache.org/install.html). Copy project into your home directory. Then run the following commands under project root.

```
~ $ java -version # confirm Java present
java version "11.0.12" 2021-07-20 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.12+8-LTS-237)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.12+8-LTS-237, mixed mode)


~ $ mvn -version # confirm Maven present
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: C:\apache-maven-3.6.3\bin\..
Java version: 11.0.12, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-11.0.12
Default locale: en_ID, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"


~ $ mvn clean install
...
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.519 s
[INFO] Finished at: 2021-12-02T17:53:30+07:00
[INFO] ------------------------------------------------------------------------
```

## Usage

You can run the unit test by doing
```
evermos $ mvn test
...
...
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 11.309 s - in com.evermos.onlinestore.OnlinestoreApplicationTests
2020-03-13 23:14:34.903  INFO 92539 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
2020-03-13 23:14:34.904  INFO 92539 --- [extShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2020-03-13 23:14:34.906  INFO 92539 --- [extShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2020-03-13 23:14:34.916  INFO 92539 --- [extShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  15.872 s
[INFO] Finished at: 2021-12-02T18:14:35+07:00
[INFO] ------------------------------------------------------------------------

```

You can run the full program directly by doing
```
evermos $ mvn spring-boot:run

[INFO] Scanning for projects...
[INFO]
[INFO] ----------------------< com.evermos:onlinestore >-----------------------
[INFO] Building onlinestore 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] >>> spring-boot-maven-plugin:2.3.12.RELEASE:run (default-cli) > test-compile @ onlinestore >>>
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ onlinestore ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] Copying 0 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ onlinestore ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ onlinestore ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory C:\Users\Iman Khaeruddin\evermos\onlinestore\src\test\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ onlinestore ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] <<< spring-boot-maven-plugin:2.3.12.RELEASE:run (default-cli) < test-compile @ onlinestore <<<
[INFO]
[INFO]
[INFO] --- spring-boot-maven-plugin:2.3.12.RELEASE:run (default-cli) @ onlinestore ---
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v2.3.12.RELEASE)  

2021-12-02 20:24:04.226  INFO 23752 --- [           main] c.e.onlinestore.OnlinestoreApplication   : Starting OnlinestoreApplication on DESKTOP-J468PI9 with PID 23752 (C:\Users\Iman Khaeruddin\evermos\onlinestore\target\classes started by Iman Khaeruddin in C:\Users\Iman Khaeruddin\evermos\onlinestore)
2021-12-02 20:24:04.226  INFO 23752 --- [           main] c.e.onlinestore.OnlinestoreApplication   : No active profile set, falling back to default profiles: default
2021-12-02 20:24:05.234  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode!
2021-12-02 20:24:05.234  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2021-12-02 20:24:05.434  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data JPA - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.elastic.ElasticCategoriesRepository. If you want this repository to be a JPA repository, consider annotating your entities with one of these annotations: javax.persistence.Entity, javax.persistence.MappedSuperclass (preferred), or consider extending one of the following types with your repository: org.springframework.data.jpa.repository.JpaRepository.
2021-12-02 20:24:05.434  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data JPA - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.elastic.ElasticItemRepository. If you want this repository to be a JPA repository, consider annotating your entities with one of these annotations: javax.persistence.Entity, javax.persistence.MappedSuperclass (preferred), or consider extending one of the following types with your repository: org.springframework.data.jpa.repository.JpaRepository.
2021-12-02 20:24:05.451  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 208ms. Found 5 JPA repository interfaces.
2021-12-02 20:24:05.467  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode!
2021-12-02 20:24:05.467  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Elasticsearch repositories in DEFAULT mode.
2021-12-02 20:24:05.467  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 1ms. Found 0 Elasticsearch repository interfaces.
2021-12-02 20:24:05.698  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode!
2021-12-02 20:24:05.698  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Elasticsearch repositories in DEFAULT mode.
2021-12-02 20:24:05.734  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Elasticsearch - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.BucketItemRepository. If you want this repository to be a Elasticsearch repository, consider annotating your entities with one of these annotations: org.springframework.data.elasticsearch.annotations.Document (preferred), or consider extending one of the following types with your repository: org.springframework.data.elasticsearch.repository.ElasticsearchRepository, org.springframework.data.elasticsearch.repository.ElasticsearchRepository.
2021-12-02 20:24:05.734  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Elasticsearch - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.BucketRepository. If you want this repository to be a Elasticsearch repository, consider annotating your entities with one of these annotations: org.springframework.data.elasticsearch.annotations.Document (preferred), or consider extending one of the following types with your repository: org.springframework.data.elasticsearch.repository.ElasticsearchRepository, org.springframework.data.elasticsearch.repository.ElasticsearchRepository.
2021-12-02 20:24:05.734  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Elasticsearch - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.CategoriesRepository. If you want this repository to be a Elasticsearch repository, consider annotating your entities with one of these annotations: org.springframework.data.elasticsearch.annotations.Document (preferred), or consider extending one of the following types with your repository: org.springframework.data.elasticsearch.repository.ElasticsearchRepository, org.springframework.data.elasticsearch.repository.ElasticsearchRepository.
2021-12-02 20:24:05.734  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Elasticsearch - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.ItemRepository. If you want this repository to be a Elasticsearch repository, consider annotating your entities with one of these annotations: org.springframework.data.elasticsearch.annotations.Document (preferred), or consider extending one of the following types with your repository: org.springframework.data.elasticsearch.repository.ElasticsearchRepository, org.springframework.data.elasticsearch.repository.ElasticsearchRepository.
2021-12-02 20:24:05.734  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Elasticsearch - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.TransactionRepository. If you want this repository to be a Elasticsearch repository, consider annotating your entities with one of these annotations: org.springframework.data.elasticsearch.annotations.Document (preferred), or consider extending one of the following types with your repository: org.springframework.data.elasticsearch.repository.ElasticsearchRepository, org.springframework.data.elasticsearch.repository.ElasticsearchRepository.
2021-12-02 20:24:05.766  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 12ms. Found 0 Reactive Elasticsearch repository interfaces.
2021-12-02 20:24:05.783  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode!
2021-12-02 20:24:05.783  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.BucketItemRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.BucketRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.CategoriesRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.ItemRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.TransactionRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.elastic.ElasticCategoriesRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.evermos.onlinestore.repository.elastic.ElasticItemRepository. If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository.
2021-12-02 20:24:05.815  INFO 23752 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 12ms. Found 0 Redis repository interfaces.
2021-12-02 20:24:06.908  INFO 23752 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2021-12-02 20:24:06.924  INFO 23752 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2021-12-02 20:24:06.924  INFO 23752 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.46]
2021-12-02 20:24:07.058  INFO 23752 --- [           main] o.a.c.c.C.[.[localhost].[/onlinestore]   : Initializing Spring embedded WebApplicationContext
2021-12-02 20:24:07.058  INFO 23752 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2744 ms
2021-12-02 20:24:07.278  INFO 23752 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2021-12-02 20:24:07.357  INFO 23752 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.4.32.Final
2021-12-02 20:24:07.509  INFO 23752 --- [           main] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2021-12-02 20:24:07.710  INFO 23752 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2021-12-02 20:24:07.953  INFO 23752 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2021-12-02 20:24:07.968  INFO 23752 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.MySQLDialect
2021-12-02 20:24:08.627  INFO 23752 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2021-12-02 20:24:08.627  INFO 23752 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2021-12-02 20:24:09.635  WARN 23752 --- [           main] o.s.data.convert.CustomConversions       : Registering converter from class org.springframework.data.geo.Point to interface java.util.Map as writing converter although it doesn't convert to a store-supported type! You might want to check your annotation setup at the converter implementation.
2021-12-02 20:24:09.635  WARN 23752 --- [           main] o.s.data.convert.CustomConversions       : Registering converter from interface java.util.Map to class org.springframework.data.geo.Point as reading converter although it doesn't convert from a store-supported type! You might want to check your annotation setup at the converter implementation.
2021-12-02 20:24:09.635  WARN 23752 --- [           main] o.s.data.convert.CustomConversions       : Registering converter from class org.springframework.data.elasticsearch.core.geo.GeoPoint to interface java.util.Map as writing converter although it doesn't convert to a store-supported type! You might want to check your annotation setup at the converter implementation.
2021-12-02 20:24:09.635  WARN 23752 --- [           main] o.s.data.convert.CustomConversions       : Registering converter from interface java.util.Map to class org.springframework.data.elasticsearch.core.geo.GeoPoint as reading converter although it doesn't convert from a store-supported type! You might want to check your annotation setup at the converter implementation.
2021-12-02 20:24:11.722  INFO 23752 --- [           main] o.s.d.elasticsearch.support.VersionInfo  : Version Spring Data Elasticsearch: 4.0.9.RELEASE
2021-12-02 20:24:11.722  INFO 23752 --- [           main] o.s.d.elasticsearch.support.VersionInfo  : Version Elasticsearch Client in build: 7.6.2
2021-12-02 20:24:11.722  INFO 23752 --- [           main] o.s.d.elasticsearch.support.VersionInfo  : Version Elasticsearch Client used: 7.6.2
2021-12-02 20:24:13.811  WARN 23752 --- [           main] .d.e.r.s.AbstractElasticsearchRepository : Cannot create index: Connection refused: no further information; nested exception is java.lang.RuntimeException: Connection refused: no further information
2021-12-02 20:24:14.492  WARN 23752 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2021-12-02 20:24:14.708  INFO 23752 --- [           main] pertySourcedRequestMappingHandlerMapping : Mapped URL path [/v2/api-docs] onto method [springfox.documentation.swagger2.web.Swagger2Controller#getDocumentation(String, HttpServletRequest)]
2021-12-02 20:24:14.890  INFO 23752 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2021-12-02 20:24:17.141  WARN 23752 --- [           main] .d.e.r.s.AbstractElasticsearchRepository : Cannot create index: Connection refused: no further information; nested exception is java.lang.RuntimeException: Connection refused: no further information
2021-12-02 20:24:17.341  INFO 23752 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path '/onlinestore'

```

## Swagger
Visit http://localhost:8080/onlinestore/swagger-ui.html