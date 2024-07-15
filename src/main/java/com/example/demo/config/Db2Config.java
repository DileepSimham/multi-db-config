package com.example.demo.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "db2EntityManagerFactory", transactionManagerRef = "db2TransactionManager", basePackages = {
		"com.example.demo.db2.repositories" })
public class Db2Config {

	@Autowired
	private Environment environment;

	@Bean(name = "dataSource2")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.ds2")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getProperty("spring.datasource.ds2.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.ds2.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.ds2.password"));

		return dataSource;
	}

	@Primary
	@Bean(name = "db2EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource2") DataSource dataSource) {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");

		return builder.dataSource(dataSource).properties(properties).packages("com.example.demo.db2.entities")
				.persistenceUnit("db2").build();
	}

	@Primary
	@Bean(name = "db2TransactionManager")
	public PlatformTransactionManager db2TransactionManager(
			@Qualifier("db2EntityManagerFactory") EntityManagerFactory db2EntityManagerFactory) {
		return new JpaTransactionManager(db2EntityManagerFactory);
	}

}
