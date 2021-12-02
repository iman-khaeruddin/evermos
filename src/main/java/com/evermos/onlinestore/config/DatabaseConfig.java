package com.evermos.onlinestore.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.evermos.onlinestore.repository")
@EntityScan(basePackages = "com.evermos.onlinestore.model.entity")
@EnableTransactionManagement
public class DatabaseConfig {

  @Autowired
  private Environment environment;

  @Value("${datasource.onlinestore.maxPoolSize:3}")
  private int maxPoolSize;

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "datasource.onlinestore")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource dataSource() {
    DataSourceProperties dataSourceProperties = dataSourceProperties();
    HikariDataSource dataSource = DataSourceBuilder
        .create(dataSourceProperties.getClassLoader())
        .driverClassName(dataSourceProperties.getDriverClassName())
        .url(dataSourceProperties.getUrl())
        .username(dataSourceProperties.getUsername())
        .password(dataSourceProperties.getPassword())
        .type(HikariDataSource.class)
        .build();
        dataSource.setMaximumPoolSize(maxPoolSize);
    dataSource.setConnectionInitSql("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(dataSource());
    factoryBean.setPackagesToScan(environment.getRequiredProperty("datasource.onlinestore.scanPackage"));
    factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
    factoryBean.setJpaProperties(jpaProperties());
    return factoryBean;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  @Autowired
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(emf);
    return txManager;
  }

  private Properties jpaProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.onlinestore.hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.onlinestore.hibernate.show_sql"));
    properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.onlinestore.hibernate.format_sql"));
    properties.put("hibernate.enable_lazy_load_no_trans", environment.getRequiredProperty("datasource.onlinestore.hibernate.enable_lazy_load_no_trans"));
    properties.put("hibernate.connection.CharSet", environment.getRequiredProperty("datasource.onlinestore.hibernate.charset"));
    properties.put("hibernate.connection.characterEncoding", environment.getRequiredProperty("datasource.onlinestore.hibernate.charset_encoding"));
    properties.put("hibernate.connection.useUnicode", environment.getRequiredProperty("datasource.onlinestore.hibernate.use_unicode"));
    properties.put("hibernate.jdbc.batch_size", environment.getRequiredProperty("datasource.onlinestore.hibernate.batch_size"));
    return properties;
  }
}
