package ma.enset.reclamation;

import ma.enset.reclamation.entities.Reclamation;
import ma.enset.reclamation.repositories.ReclamationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ReclamationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReclamationApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(ReclamationRepository reclamationRepository){
        return args -> {
          reclamationRepository.save(new Reclamation(null,new Date(),"Article manquant dans la commande","recl1@gmail.com",true));
          reclamationRepository.save(new Reclamation(null,new Date(),"Service client insatisfaisant","reclamation2@gmail.com",true));
          reclamationRepository.save(new Reclamation(null,new Date(),"Livraison non respectée","recl30@gmail.com",true));
          reclamationRepository.findAll().forEach(r->{
              System.out.println(r.getType());
          });
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
