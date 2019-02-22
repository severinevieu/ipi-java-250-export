package com.example.demo.service;

import com.example.demo.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private EntityManager em;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }


    private void insertTestData() {
        Client client1 = newClient("PETRILLO", "Alexandre", LocalDate.of( 2000, 12, 22),"34");
        em.persist(client1);


        Client client2 = newClient("LICORNE", "Violette", LocalDate.of(1988, 03, 07),"33");
        em.persist(client2);

        Client client3 = newClient("Dupont", "Jérome", LocalDate.of(1985, 04, 25),"45");
        em.persist(client3);
    }

    private Client newClient(String nom, String prenom, LocalDate dateNaissance, String age) {
        Client client = new Client();

        client.setNom(nom);
        client.setPrenom(prenom);
        client.setDateNaissance(dateNaissance);
        client.setAge(age);
        return client;
    }

}
