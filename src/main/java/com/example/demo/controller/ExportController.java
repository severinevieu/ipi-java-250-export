package com.example.demo.controller;

import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import org.apache.poi.ss.formula.functions.Na;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

/**
 * Controlleur pour réaliser les exports.
 */
@Controller
@RequestMapping("/")
public class ExportController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
        PrintWriter writer = response.getWriter();
        // On récupère les données clients de la liste
        List<Client> clients = clientService.findAllClients();
        //pour avoir la date en cours
        LocalDate now = LocalDate.now();
        //On crée un Iterator dans la list client pour pouvoir la parcourir
        ListIterator<Client> iterator = clients.listIterator();
        while (iterator.hasNext()) {
            Client c = iterator.next();

            writer.println(c.getId()+";"+ c.getNom()+";"+c.getPrenom()+";"+
                                          c.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yy"))+";"+
                                          (now.getYear() - c.getDateNaissance().getYear()));
        }
    }
        @GetMapping("/clients/xlsx")
        public void clientsXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"clients.xlsx\""); // là je lui dis qu'il va faire un fichier à télécharger et qu'il s'appelle clients.xlsx
            List<Client> clients = clientService.findAllClients();
            ListIterator<Client> it = clients.listIterator();
            LocalDate now = LocalDate.now();

            Workbook workbook = new XSSFWorkbook(); // crée un fichier excel
            Sheet sheet = workbook.createSheet("Clients"); // crée un onglet
            Row headerRow = sheet.createRow(0); // crée une ligne

            Cell cellHeaderId = headerRow.createCell(0); // crée une cellule
            cellHeaderId.setCellValue("Id");

            Cell cellHeaderNom = headerRow.createCell(1); // crée une cellule
            cellHeaderNom.setCellValue("Nom");

            Cell cellHeaderPrenom = headerRow.createCell(2); // crée une cellule
            cellHeaderPrenom.setCellValue("Prénom"); // ajoute une valeur dans la cellule


            Cell cellHeaderDateNaissance = headerRow.createCell(3); // crée une cellule
            cellHeaderDateNaissance.setCellValue("Date Naissance");

            Cell cellHeaderAge = headerRow.createCell(4); // crée une cellule
            cellHeaderAge.setCellValue("Age");

            int i = 1;

           for (Client client : clients){

               Row row = sheet.createRow(i);

               Cell cellId = row.createCell(0);
               cellId.setCellValue(client.getId());

               Cell cellName = row.createCell(1);
               cellName.setCellValue(client.getNom());

               Cell cellPrenom = row.createCell(2);
               cellPrenom.setCellValue(client.getPrenom());

               Cell cellDateNaissance = row.createCell(3);
              // Date dateNaissance = Date.from()
               cellDateNaissance.setCellValue(client.getDateNaissance().toEpochDay());

               Cell cellAge = row.createCell(34);
               cellAge.setCellValue(client.getAge());





           }









            workbook.write(response.getOutputStream()); // ici on dit qu'on écrit dans un objet de type Output
            workbook.close();


        }
    }


