package controller;

import app.MainApp;
import dao.BatimentDAO;
import dao.InterventionDAO;
import dao.TechnicienDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Batiment;
import model.Intervention;
import model.Technicien;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class InterventionController implements Initializable {

    // Tables
    @FXML private TableView<Intervention> interventionTable;
    @FXML private TableColumn<Intervention, Date> colDate;
    @FXML private TableColumn<Intervention, String> colTechnicien;
    @FXML private TableColumn<Intervention, String> colBatiment;
    @FXML private TableColumn<Intervention, String> colType;
    @FXML private TableColumn<Intervention, String> colStatut;

    // Composantse des filtres
    @FXML private ComboBox<Technicien> filtreTechnicien;
    @FXML private ComboBox<Batiment> filtreBatiment;
    @FXML private ComboBox<String> filtreStatut;
    @FXML private DatePicker filtreDate;

    // Composantes du formulaire d'ajout
    @FXML private ComboBox<Technicien> technicienCombo;
    @FXML private ComboBox<Batiment> batimentCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField typeField;
    @FXML private TextArea descriptionArea;

    // Appel au DAO
    private final InterventionDAO interventionDAO = new InterventionDAO();
    private final TechnicienDAO technicienDAO = new TechnicienDAO();
    private final BatimentDAO batimentDAO = new BatimentDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Récuperer exactement olonnes TableView 
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateIntervention"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeIntervention"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // getTechnicienNom() / getBatimentNom()
        colTechnicien.setCellValueFactory(new PropertyValueFactory<>("technicienNom"));
        colBatiment.setCellValueFactory(new PropertyValueFactory<>("batimentNom"));

        // préparation des listes (pour filtres)
        List<Technicien> techniciens = technicienDAO.findAll();
        List<Batiment> batiments = batimentDAO.findAll();

        filtreTechnicien.setItems(FXCollections.observableArrayList(techniciens));
        filtreBatiment.setItems(FXCollections.observableArrayList(batiments));

        technicienCombo.setItems(FXCollections.observableArrayList(techniciens));
        batimentCombo.setItems(FXCollections.observableArrayList(batiments));

        filtreStatut.setItems(FXCollections.observableArrayList(
                "Planifiée", "En cours", "Terminée"
        ));

        // chargement initial
        this.chargerInterventions();
    }

    // chargement ou rechargement de données
    private void chargerInterventions() {
        List<Intervention> list = interventionDAO.findAll();
        interventionTable.setItems(FXCollections.observableArrayList(list));
    }

    // filtres
    @FXML
    private void handleFiltrer() {

        Technicien technicien = filtreTechnicien.getValue();
        Batiment batiment = filtreBatiment.getValue();
        String statut = filtreStatut.getValue();

        Date date = null;
        if (filtreDate.getValue() != null) {
            date = java.sql.Date.valueOf(filtreDate.getValue());
        }

        List<Intervention> result = interventionDAO.findByCriteria(technicien, batiment, statut, date);

        interventionTable.setItems(FXCollections.observableArrayList(result));
    }

    @FXML
    private void handleReset() {

        filtreTechnicien.setValue(null);
        filtreBatiment.setValue(null);
        filtreStatut.setValue(null);
        filtreDate.setValue(null);

        this.chargerInterventions();
    }

    // Operations crud sur interventions
    @FXML
    private void handleAjouter() {

        Technicien technicien = technicienCombo.getValue();
        Batiment batiment = batimentCombo.getValue();
        LocalDate localDate = datePicker.getValue();

        if (technicien == null || batiment == null || localDate == null) {
            return;
        }

        Intervention i = new Intervention();
        i.setTechnicien(technicien);
        i.setBatiment(batiment);
        i.setDateIntervention(java.sql.Date.valueOf(localDate));
        i.setTypeIntervention(typeField.getText());
        i.setDescription(descriptionArea.getText());
        i.setStatut("Planifiée");
        i.setResponsable(MainApp.getResponsableConnecte());

        interventionDAO.save(i);
        this.chargerInterventions();
    }

    @FXML
    private void handleModifier() {

        Intervention selected = interventionTable.getSelectionModel().getSelectedItem();
        String nextStatut = null;
        if (selected == null) {
            return;
        }
        
        switch (selected.getStatut()) {
        case "En cours":
        	nextStatut = "Terminée";
            break;
        case "Planifiée":
        	nextStatut = "En cours";
            break;
        case "Terminée":
        	nextStatut = "Planifiée";
            break;
        }
        
        selected.setStatut(nextStatut);
        interventionDAO.update(selected);
        this.chargerInterventions();
    }

    @FXML
    private void handleSupprimer() {

        Intervention selected =
                interventionTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        interventionDAO.delete(selected);
        this.chargerInterventions();
    }
    
    @FXML
    private void handleRetour() {
        MainApp.afficherAccueil();
    }
}
