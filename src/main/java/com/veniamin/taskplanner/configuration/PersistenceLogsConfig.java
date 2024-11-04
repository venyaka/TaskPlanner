//package com.veniamin.taskplanner.configuration;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = {"com.veniamin.taskplanner.repositoryLogs"},
//        entityManagerFactoryRef = "logsEntityManager",
//        transactionManagerRef = "logsTransactionManager")
//public class PersistenceLogsConfig {
//
////    @Autowired
////    private Environment env;
////
////    @Bean(name = "logsEntityManager")
////    public LocalContainerEntityManagerFactoryBean logsEntityManager(
////            EntityManagerFactoryBuilder builder,
////            @Qualifier("dataSourceLogs") DataSource dataSource) {
////        HashMap<String, Object> properties = new HashMap<>();
////        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
////        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
////
////        return builder
////                .dataSource(dataSource)
////                .properties(properties)
////                .packages("com.veniamin.taskplanner.modelLogs")
////                .build();
////    }
//
////    @Bean("dataSourceLogs")
////    @ConfigurationProperties(prefix = "spring.secondary-datasource")
////    public DataSource dataSourceLogs() {
////        return DataSourceBuilder.create().build();
////    }
//
////    @Bean
////    public PlatformTransactionManager logsTransactionManager(
////            @Qualifier("logsEntityManager") EntityManagerFactory logsEntityManager
////    ) {
////        return new JpaTransactionManager(logsEntityManager);
////    }
//}
