import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccueilView extends BorderPane {

    public AccueilView(MainApp app) {
        setPadding(new Insets(15));

        Label titre = new Label("Accueil - Gestion de Maintenance");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label infoConnexion;
        if (app.isResponsableConnecte()) {
            infoConnexion = new Label("Connecté : " + app.getResponsableEmail());
        } else {
            infoConnexion = new Label("Non connecté");
        }

        VBox topBox = new VBox(5, titre, infoConnexion);
        setTop(topBox);

        // Boutons principaux
        Button btnTech = new Button("Techniciens");
        Button btnBat = new Button("Bâtiments");
        Button btnInter = new Button("Interventions");

        btnTech.setPrefWidth(200);
        btnBat.setPrefWidth(200);
        btnInter.setPrefWidth(200);

        // LIAISONS (c'est ça qui manquait)
        btnTech.setOnAction(e -> app.afficherTechniciens());
        btnBat.setOnAction(e -> app.afficherBatiments());
        btnInter.setOnAction(e -> app.afficherInterventions());

        VBox center = new VBox(15, btnTech, btnBat, btnInter);
        center.setPadding(new Insets(30, 0, 0, 0));
        setCenter(center);

        // Bas : déconnexion
        Button btnLogout = new Button("Déconnexion");
        btnLogout.setOnAction(e -> app.deconnexion());

        HBox bottom = new HBox(btnLogout);
        bottom.setPadding(new Insets(20, 0, 0, 0));
        setBottom(bottom);
    }
}
