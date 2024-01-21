package be.heh.gourmet.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackEndApplication {
    //TODO: Faire test pour chaque class
    //TODO: Faire arborescence correct

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
