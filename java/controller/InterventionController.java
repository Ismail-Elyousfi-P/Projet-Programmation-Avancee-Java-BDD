package controller;

import dao.BatimentDAO;
import dao.InterventionDAO;
import dao.TechnicienDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Batiment;
import model.Intervention;
import model.Responsable;
import model.Technicien;
import app.MainApp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class InterventionController {

    // ========================
    // Filtres
    // ========================
    @FXML
    private ComboBox<Technicien> filtreTechnicien;

    @FXML
    private ComboBox<Batiment> filtreBatiment;

    @FXML
    private ComboBox<String> filtreStatut;

    // ========================
    // Formulaire
    // ========================
    @FXML
    private ComboBox<Technicien> technicienCombo;

    @FXML
    private ComboBox<Batiment> batimentCombo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField typeField;

    @FXML
    private ComboBox<String> statutCombo;

    @FXML
    private TextArea descriptionArea;

    // ========================
    // Table
    // ========================
    @FXML
    private TableView<Intervention> tableInterventions;

    // ========================
    // DAO
    // ========================
    private final InterventionDAO interventionDAO = new InterventionDAO();
    private final TechnicienDAO technicienDAO = new TechnicienDAO();
    private final BatimentDAO batimentDAO = new BatimentDAO();

    // ========================
    // INITIALISATION
    // ========================
    @FXML
    public void initialize() {

        List<String> statuts = List.of("Planifiée", "En cours", "Terminée");

        filtreStatut.setItems(FXCollections.observableArrayList(statuts));
        statutCombo.setItems(FXCollections.observableArrayList(statuts));

        filtreTechnicien.setItems(
                FXCollections.observableArrayList(technicienDAO.findAll())
        );

        filtreBatiment.setItems(
                FXCollections.observableArrayList(batimentDAO.findAll())
        );

        technicienCombo.setItems(
                FXCollections.observableArrayList(technicienDAO.findAll())
        );

        batimentCombo.setItems(
                FXCollections.observableArrayList(batimentDAO.findAll())
        );

        refreshTable();
    }

    // ========================
    // ACTIONS
    // ========================

    @FXML
    private void handleAjouter() {

        Responsable responsable = MainApp.getResponsableConnecte();
        if (responsable == null) {
            showError("Aucun responsable connecté.");
            return;
        }

        if (technicienCombo.getValue() == null ||
            batimentCombo.getValue() == null ||
            datePicker.getValue() == null ||
            typeField.getText().isBlank()) {

            showError("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        Intervention i = new Intervention();
        i.setTechnicien(technicienCombo.getValue());
        i.setBatiment(batimentCombo.getValue());
        i.setResponsable(responsable);
        i.setTypeIntervention(typeField.getText());
        i.setStatut(statutCombo.getValue());

        LocalDate localDate = datePicker.getValue();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        i.setDateIntervention(date);

        i.setDescription(descriptionArea.getText());

        interventionDAO.save(i);
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleModifier() {

        Intervention selected = tableInterventions.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Veuillez sélectionner une intervention.");
            return;
        }

        selected.setTechnicien(technicienCombo.getValue());
        selected.setBatiment(batimentCombo.getValue());
        selected.setTypeIntervention(typeField.getText());
        selected.setStatut(statutCombo.getValue());
        selected.setDescription(descriptionArea.getText());

        if (datePicker.getValue() != null) {
            Date date = Date.from(
                    datePicker.getValue()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
            );
            selected.setDateIntervention(date);
        }

        interventionDAO.update(selected);
        refreshTable();
    }

    @FXML
    private void handleCloturer() {

        Intervention selected = tableInterventions.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Sélectionnez une intervention.");
            return;
        }

        selected.setStatut("Terminée");
        interventionDAO.update(selected);
        refreshTable();
    }

    @FXML
    private void handleSupprimer() {

        Intervention selected = tableInterventions.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Sélectionnez une intervention.");
            return;
        }

        interventionDAO.delete(selected);
        refreshTable();
    }

    @FXML
    private void handleRetour() {
        MainApp.afficherAccueil();
    }

    // ========================
    // OUTILS
    // ========================
    private void refreshTable() {
        tableInterventions.setItems(
                FXCollections.observableArrayList(
                        interventionDAO.findAll()
                )
        );
    }

    private void clearForm() {
        technicienCombo.setValue(null);
        batimentCombo.setValue(null);
        datePicker.setValue(null);
        typeField.clear();
        statutCombo.setValue(null);
        descriptionArea.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}