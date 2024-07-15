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
@EnableJpaRepositories(entityManagerFactoryRef = "db3EntityManagerFactory", transactionManagerRef = "db3TransactionManager", basePackages = {
		"com.example.demo.db3.repositories" })
public class Db3Config {

	@Autowired
	private Environment environment;

	@Bean(name = "dataSource3")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.ds3")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getProperty("spring.datasource.ds3.url"));
		dataSource.setUsername(environment.getProperty("spring.datasource.ds3.username"));
		dataSource.setPassword(environment.getProperty("spring.datasource.ds3.password"));

		return dataSource;
	}

	@Primary
	@Bean(name = "db3EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean db3EntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource3") DataSource dataSource) {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");

		return builder.dataSource(dataSource).properties(properties).packages("com.example.demo.db3.entities")
				.persistenceUnit("db3").build();
	}

	@Primary
	@Bean(name = "db3TransactionManager")
	public PlatformTransactionManager db3TransactionManager(
			@Qualifier("db3EntityManagerFactory") EntityManagerFactory db3EntityManagerFactory) {
		return new JpaTransactionManager(db3EntityManagerFactory);
	}

}
