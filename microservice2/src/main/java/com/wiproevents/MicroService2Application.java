package com.wiproevents;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main application.
 */
@SpringBootApplication(exclude = {SocialWebAutoConfiguration.class, DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.wiproevents"})
public class MicroService2Application extends BaseMicoServiceApplication {
    /**
     * The main entry point of the application.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MicroService2Application.class, args);
    }
}
