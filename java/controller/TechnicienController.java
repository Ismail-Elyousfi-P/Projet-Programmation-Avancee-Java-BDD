package controller;

import app.MainApp;
import dao.TechnicienDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Technicien;

public class TechnicienController {

    @FXML
    private TableView<Technicien> table;

    @FXML
    private TableColumn<Technicien, String> colNom;

    @FXML
    private TableColumn<Technicien, String> colPrenom;

    @FXML
    private TableColumn<Technicien, String> colTel;

    @FXML
    private TableColumn<Technicien, String> colQualif;

    @FXML
    private TableColumn<Technicien, Boolean> colDispo;

    private final TechnicienDAO technicienDAO = new TechnicienDAO();

    @FXML
    public void initialize() {

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colQualif.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        colDispo.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        refreshTable();
    }

    private void refreshTable() {
        ObservableList<Technicien> data =
                FXCollections.observableArrayList(technicienDAO.findAll());
        table.setItems(data);
    }


    @FXML
    private void handleAjouter() {

        Dialog<Technicien> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un technicien");

        ButtonType btnOk = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk, ButtonType.CANCEL);

        TextField nom = new TextField();
        TextField prenom = new TextField();
        TextField tel = new TextField();
        TextField qualif = new TextField();
        CheckBox dispo = new CheckBox("Disponible");

        VBox content = new VBox(10,
                new Label("Nom"), nom,
                new Label("Prénom"), prenom,
                new Label("Téléphone"), tel,
                new Label("Qualification"), qualif,
                dispo
        );

        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == btnOk) {
                Technicien t = new Technicien();
                t.setNom(nom.getText());
                t.setPrenom(prenom.getText());
                t.setTelephone(tel.getText());
                t.setQualification(qualif.getText());
                t.setDisponible(dispo.isSelected());
                return t;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(t -> {
            technicienDAO.save(t);
            refreshTable();
        });
    }

    @FXML
    private void handleModifier() {

        Technicien selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Veuillez sélectionner un technicien.");
            return;
        }

        Dialog<Technicien> dialog = new Dialog<>();
        dialog.setTitle("Modifier le technicien");

        ButtonType btnOk = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk, ButtonType.CANCEL);

        TextField nom = new TextField(selected.getNom());
        TextField prenom = new TextField(selected.getPrenom());
        TextField tel = new TextField(selected.getTelephone());
        TextField qualif = new TextField(selected.getQualification());
        CheckBox dispo = new CheckBox("Disponible");
        dispo.setSelected(selected.isDisponible());

        VBox content = new VBox(10,
                new Label("Nom"), nom,
                new Label("Prénom"), prenom,
                new Label("Téléphone"), tel,
                new Label("Qualification"), qualif,
                dispo
        );

        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == btnOk) {
                selected.setNom(nom.getText());
                selected.setPrenom(prenom.getText());
                selected.setTelephone(tel.getText());
                selected.setQualification(qualif.getText());
                selected.setDisponible(dispo.isSelected());
                return selected;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(t -> {
            technicienDAO.update(t);
            refreshTable();
        });
    }

    @FXML
    private void handleSupprimer() {

        Technicien selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Veuillez sélectionner un technicien.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le technicien ?");
        confirm.setContentText(selected.getNom() + " " + selected.getPrenom());

        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                technicienDAO.delete(selected);
                refreshTable();
            }
        });
    }

    @FXML
    private void handleRetour() {
    	MainApp.afficherAccueil();
    }


    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
