package ru.borisof.pasteme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PastemeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastemeApplication.class, args);
    }

}
