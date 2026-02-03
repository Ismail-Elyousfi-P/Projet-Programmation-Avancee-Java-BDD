package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Responsable;

import java.net.URL;

public class MainApp extends Application {

    private static Stage primaryStage;
    
    // Une logique de cache utilisateur car une seule connexion par machine :)
    private static Responsable ResponsableConnecte;
    
    public static Responsable getResponsableConnecte() {
		return ResponsableConnecte;
	}

	public static void setResponsableConnecte(Responsable responsableConnecte) {
		ResponsableConnecte = responsableConnecte;
	}

	// fonction de début
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Maintenance App");

        afficherConnexion();
    }
    
    // Mapping des scenes avec fxml associés
    public static void afficherConnexion() {
        loadScene("/views/connexion.fxml", "Connexion");
    }

    public static void afficherAccueil() {
        loadScene("/views/accueil.fxml", "Accueil");
    }

    public static void afficherTechniciens() {
        loadScene("/views/techniciens.fxml", "Gestion des techniciens");
    }

    public static void afficherBatiments() {
        loadScene("/views/batiments.fxml", "Gestion des bâtiments");
    }

    public static void afficherInterventions() {
        loadScene("/views/interventions.fxml", "Gestion des interventions");
    }

    // Fonction de chargement des scenes
    private static void loadScene(String fxmlPath, String title) {
        try {
            URL fxmlUrl = MainApp.class.getResource(fxmlPath);

            if (fxmlUrl == null) {
                throw new RuntimeException("FXML introuvable : " + fxmlPath);
            }

            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root);

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            throw new RuntimeException("Impossible de charger : " + fxmlPath, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
