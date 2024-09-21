package ru.box.tornadosbet.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.box.tornadosbet.entity.postgresql.Boxer;
import ru.box.tornadosbet.repository.core.BoxerRepository;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
//@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackageClasses = BoxerRepository.class
)
public class SecondaryDatabaseConfig {

    @Value("${spring.secondary.datasource.hibernate.dialect}")
    private String hibernateDialect;


    @Bean
    @ConfigurationProperties(prefix = "spring.secondary.datasource")
    public DataSourceProperties secondaryDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean
    public DataSource secondaryDataSource(){
        return secondaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
                .dataSource(secondaryDataSource())
                .packages(Boxer.class.getPackageName())
                .persistenceUnit("secondary")
                .build();
        entityManagerFactoryBean.getJpaPropertyMap().put("hibernate.dialect", hibernateDialect);
        return entityManagerFactoryBean;

    }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager(
            EntityManagerFactoryBuilder builder){
        var jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(secondaryEntityManagerFactory(builder).getObject());
        return jpaTransactionManager;
    }
}
