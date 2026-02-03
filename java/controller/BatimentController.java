package controller;

import app.MainApp;
import dao.BatimentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Batiment;

public class BatimentController {

    @FXML
    private TableView<Batiment> table;

    @FXML
    private TableColumn<Batiment, String> colNom;

    @FXML
    private TableColumn<Batiment, String> colLocalisation;

    private final BatimentDAO batimentDAO = new BatimentDAO();

    /* ======================
       INITIALISATION
       ====================== */
    @FXML
    public void initialize() {

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));

        refreshTable();
    }

    private void refreshTable() {
        ObservableList<Batiment> data =
                FXCollections.observableArrayList(batimentDAO.findAll());
        table.setItems(data);
    }

    /* ======================
       ACTIONS BOUTONS
       ====================== */

    @FXML
    private void handleAjouter() {

        Dialog<Batiment> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un bâtiment");

        ButtonType btnOk = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk, ButtonType.CANCEL);

        TextField nom = new TextField();
        TextField localisation = new TextField();

        VBox content = new VBox(10,
                new Label("Nom"), nom,
                new Label("Localisation"), localisation
        );

        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == btnOk) {
                Batiment b = new Batiment();
                b.setNom(nom.getText());
                b.setLocalisation(localisation.getText());
                return b;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(b -> {
            batimentDAO.save(b);
            refreshTable();
        });
    }

    @FXML
    private void handleModifier() {

        Batiment selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Veuillez sélectionner un bâtiment.");
            return;
        }

        Dialog<Batiment> dialog = new Dialog<>();
        dialog.setTitle("Modifier le bâtiment");

        ButtonType btnOk = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk, ButtonType.CANCEL);

        TextField nom = new TextField(selected.getNom());
        TextField localisation = new TextField(selected.getLocalisation());

        VBox content = new VBox(10,
                new Label("Nom"), nom,
                new Label("Localisation"), localisation
        );

        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(btn -> {
            if (btn == btnOk) {
                selected.setNom(nom.getText());
                selected.setLocalisation(localisation.getText());
                return selected;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(b -> {
            batimentDAO.update(b);
            refreshTable();
        });
    }

    @FXML
    private void handleSupprimer() {

        Batiment selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Veuillez sélectionner un bâtiment.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le bâtiment ?");
        confirm.setContentText(selected.getNom());

        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                batimentDAO.delete(selected);
                refreshTable();
            }
        });
    }

    @FXML
    private void handleRetour() {
    	MainApp.afficherAccueil();
    }

    /* ======================
       UTILITAIRE UI
       ====================== */

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
