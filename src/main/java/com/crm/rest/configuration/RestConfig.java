package com.crm.rest.configuration;

import com.crm.rest.util.StringConverter;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.crm.rest")
@PropertySource("classpath:persistence-mysql.properties")
public class RestConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource securityDataSource(){
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        try{
            setJDBCConnection(pooledDataSource);
            setConnectionPool(pooledDataSource);
        }catch (PropertyVetoException exception){
            throw new RuntimeException(exception);
        }

        return pooledDataSource;
    }

    private void setJDBCConnection(ComboPooledDataSource source) throws PropertyVetoException{
        source.setDriverClass(environment.getProperty("jdbc.driver"));
        source.setJdbcUrl(environment.getProperty("jdbc.url"));
        source.setUser(environment.getProperty("jdbc.user"));
        source.setPassword(environment.getProperty("jdbc.password"));
    }

    private void setConnectionPool(ComboPooledDataSource dataSource) throws PropertyVetoException {
        dataSource.setInitialPoolSize(StringConverter.convertToInt(environment.getProperty("connection.pool.initialPoolSize")));
        dataSource.setMinPoolSize(StringConverter.convertToInt(environment.getProperty("connection.pool.minPoolSize")));
        dataSource.setMaxPoolSize(StringConverter.convertToInt(environment.getProperty("connection.pool.maxPoolSize")));
        dataSource.setMaxIdleTime(StringConverter.convertToInt(environment.getProperty("connection.pool.maxIdleTime")));
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(securityDataSource());
        sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(setHibernateProperties());

        return sessionFactory;
    }

    private Properties setHibernateProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);

        return hibernateTransactionManager;
    }
}
