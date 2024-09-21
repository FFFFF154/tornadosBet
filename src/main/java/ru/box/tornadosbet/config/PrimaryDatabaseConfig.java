package ru.box.tornadosbet.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.box.tornadosbet.entity.mysql.User;
import ru.box.tornadosbet.repository.security.UserRepository;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
//@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        basePackageClasses = UserRepository.class
)
public class PrimaryDatabaseConfig {

    @Value("${spring.primary.datasource.hibernate.dialect}")
    private String hibernateDialect;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.primary.datasource")
    public DataSourceProperties primaryDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource dataSource(){
        return primaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
                .dataSource(dataSource())
                .packages(User.class.getPackageName())
                .persistenceUnit("primary")
                .build();
        entityManagerFactoryBean.getJpaPropertyMap().put("hibernate.dialect", hibernateDialect);
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager(
            EntityManagerFactoryBuilder builder){
        var jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(primaryEntityManagerFactory(builder).getObject());
        return jpaTransactionManager;
    }
}
