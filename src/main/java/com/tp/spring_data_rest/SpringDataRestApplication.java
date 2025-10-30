package com.tp.spring_data_rest;

import com.tp.spring_data_rest.entities.Client;
import com.tp.spring_data_rest.entities.Compte;
import com.tp.spring_data_rest.entities.TypeCompte;
import com.tp.spring_data_rest.repositories.ClientRepository;
import com.tp.spring_data_rest.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class SpringDataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository, RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Compte.class);

            // Créer les clients sans passer la liste de comptes
            Client c1 = new Client();
            c1.setNom("Amal");
            clientRepository.save(c1);

            Client c2 = new Client();
            c2.setNom("Ali");
            clientRepository.save(c2);

            // Créer les comptes
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));

            compteRepository.findAll().forEach(c -> {
                System.out.println(c.toString());
            });
        };
    }
}