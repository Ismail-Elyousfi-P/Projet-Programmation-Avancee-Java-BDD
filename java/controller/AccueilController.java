package controller;

import app.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccueilController {

    @FXML
    private Label labelUser;

    // Initialisation
    @FXML
    public void initialize() {
        labelUser.setText("Connecté");
    }


    public void setUserEmail(String email) {
        labelUser.setText("Connecté : " + email);
    }

    
    // Actions FXML
    @FXML
    private void handleTechniciens() {
        MainApp.afficherTechniciens();
    }

    @FXML
    private void handleBatiments() {
        MainApp.afficherBatiments();
    }

    @FXML
    private void handleInterventions() {
        MainApp.afficherInterventions();
    }

    // Déconnecter reellement (enlever les placeholder)
    @FXML
    private void handleLogout() {
    	MainApp.setResponsableConnecte(null);
        MainApp.afficherConnexion();
    }
}
