package com.example.demo.controller;

import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller principale pour affichage des clients / factures sur la page d'acceuil.
 */
@Controller
public class HomeController {

    //permet d'aller chercher l'instance
    @Autowired
    private ClientService clientService;

   // @Autowired
//    private FactureService factureService;

    @GetMapping("/")
    public ModelAndView home() {
        //nom du template
        ModelAndView modelAndView = new ModelAndView("home");

        List<Client> clients = clientService.findAllClients();
        modelAndView.addObject("clients", clients);
        //on appel la liste des des clients pour afficher leurs données

        return modelAndView;
    }
}
