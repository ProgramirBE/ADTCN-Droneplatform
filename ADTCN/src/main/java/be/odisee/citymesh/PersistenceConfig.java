package be.odisee.citymesh;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuratieklasse voor de persistencelaag van de Citymesh-applicatie.
 * <p>
 * Deze klasse configureert:
 * <ul>
 *     <li>De {@link DataSource} voor een H2-database</li>
 *     <li>De {@link EntityManagerFactory} voor JPA-implementatie met Hibernate</li>
 *     <li>De {@link PlatformTransactionManager} voor transacties</li>
 * </ul>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "be.odisee.citymesh")
public class PersistenceConfig {

    /**
     * Configureert de H2-database datasource.
     *
     * @return een geconfigureerde {@link DataSource}
     */
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:file:~/test;INIT=CREATE SCHEMA IF NOT EXISTS CITYMESH")
                .username("citymeshuser")
                .password("citymeshpwd")
                .build();
    }

    /**
     * Configureert de EntityManagerFactory met Hibernate als JPA-provider.
     *
     * @return een {@link LocalContainerEntityManagerFactoryBean} met de nodige Hibernate-properties
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("be.odisee.citymesh.domain");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", "update"); // maakt tabellen automatisch aan op basis van entities
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.format_sql", "true");

        em.setJpaProperties(props);
        return em;
    }

    /**
     * Configureert het transaction management voor JPA.
     *
     * @param emf de EntityManagerFactory die gebruikt wordt voor transacties
     * @return een {@link JpaTransactionManager}
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
