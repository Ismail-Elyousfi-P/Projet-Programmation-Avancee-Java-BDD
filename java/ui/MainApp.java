package ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;

    
    private Integer responsableId = null;
    private String responsableEmail = null;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Maintenance - Responsable");

        afficherConnexion();

        this.primaryStage.show();
    }

    //Navigation 

    public void afficherConnexion() {
        Scene scene = new Scene(new ConnexionView(this), 420, 180);
        primaryStage.setScene(scene);
    }

    public void afficherAccueil() {
        Scene scene = new Scene(new AccueilView(this), 800, 500);
        primaryStage.setScene(scene);
    }

    public void afficherTechniciens() {
        Scene scene = new Scene(new TechnicienView(this), 900, 520);
        primaryStage.setScene(scene);
    }

    public void afficherBatiments() {
        Scene scene = new Scene(new BatimentView(this), 900, 520);
        primaryStage.setScene(scene);
    }

    public void afficherInterventions() {
        Scene scene = new Scene(new InterventionView(this), 1100, 520);
        primaryStage.setScene(scene);
    }

    

    public void setResponsableConnecte(int id, String email) {
        this.responsableId = id;
        this.responsableEmail = email;
    }

    public boolean isResponsableConnecte() {
        return responsableId != null;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public String getResponsableEmail() {
        return responsableEmail;
    }

    public void deconnexion() {
        this.responsableId = null;
        this.responsableEmail = null;
        afficherConnexion();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
