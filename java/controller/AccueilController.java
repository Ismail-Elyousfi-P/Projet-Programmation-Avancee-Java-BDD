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
        System.out.println("Navigation vers Techniciens");
        MainApp.afficherTechniciens();
    }

    @FXML
    private void handleBatiments() {
        System.out.println("Navigation vers Bâtiments");
        MainApp.afficherBatiments();
    }

    @FXML
    private void handleInterventions() {
        System.out.println("Navigation vers Interventions");
        MainApp.afficherInterventions();
    }

    @FXML
    private void handleLogout() {
        System.out.println("Déconnexion");
    }
}
