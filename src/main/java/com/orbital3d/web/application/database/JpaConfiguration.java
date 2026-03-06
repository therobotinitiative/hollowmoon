package com.orbital3d.web.application.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories
@EntityScan
@EnableTransactionManagement
public class JpaConfiguration {

	@Value("${datasource.url:jdbc:h2:mem:testdb}")
	private String dataSourceURL;

	@Value("${datasource.driver:org.h2.Driver}")
	private String dataSourceDriver;

	@Value("${datasource.username:sa}")
	private String dataSourceUsername;

	@Value("${datasource.password:password}")
	private String dataSourcePassword;

	@Bean
	DataSource dataSource() {
		return DataSourceBuilder.create().url(dataSourceURL).driverClassName(dataSourceDriver)
				.username(dataSourceUsername).password(dataSourcePassword).build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory((EntityManagerFactory) entityManagerFactory().getObject());

		return transactionManager;
	}

	private LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource());
		entityManager.setPackagesToScan("com.orbital3d.web.application.database.entity");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		entityManager.setJpaProperties(additionalProperties());
		return entityManager;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
//		properties.setProperty("hbm2ddl.auto", "create");
//		properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
//		properties.setProperty("hibernate.generate-ddl", "true");
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
		return properties;
	}
}
