package diginamic.fr.app_covoiturage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppCovoiturageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppCovoiturageApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("(ツ)");
            System.out.println(" HELLO SPRING BOOT APP !!");
            System.out.println("(ツ)");

        };
    }
}
