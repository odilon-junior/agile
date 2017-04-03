package br.com.agile.gateway.config;

import br.com.agile.gateway.web.authentication.Usuario;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by odilon on 02/04/17.
 */
@Configuration
public class HibernateConfig {

    @Bean(name = "customerEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("br.com.agile.gateway.web.authentication");
        entityManagerFactoryBean.setJpaProperties(additionalJpaProperties());
        return  entityManagerFactoryBean;

    }

    private Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/agile")
                .driverClassName("com.mysql.jdbc.Driver")
                .username("root")
                .password("batida1355")
                .build();
    }

    @Bean(name = "customerTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }
}
