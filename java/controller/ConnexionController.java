package controller;

import app.MainApp;
import dao.ResponsableDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Responsable;
import util.PasswordHash;

public class ConnexionController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final ResponsableDAO responsableDAO = new ResponsableDAO();

    // Action FXML
    @FXML
    private void handleConnexion() {

        String email = emailField.getText();
        String password = passwordField.getText();

        if (email == null || email.isBlank() ||
            password == null || password.isBlank()) {

            showError("Veuillez renseigner l’email et le mot de passe.");
            return;
        }
        
        String hashedPassword = PasswordHash.md5(password);

        Responsable responsable = responsableDAO.findByEmailAndPassword(email, hashedPassword);

        if (responsable != null) {
            // Enregistrer l'utilisateur dans le cache puis paginer vers L'acceuil
            MainApp.setResponsableConnecte(responsable);
            MainApp.afficherAccueil();
        } else {
            showError("Identifiants incorrects.");
        }
    }

    // Affichage d'erruer
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
