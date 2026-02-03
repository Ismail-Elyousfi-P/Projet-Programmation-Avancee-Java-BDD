package ui;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ConnexionView extends GridPane {

    public ConnexionView(MainApp app) {
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        Label messageLabel = new Label();
        Button boutonConnexion = new Button("Se connecter");

        add(new Label("Email :"), 0, 0);
        add(emailField, 1, 0);

        add(new Label("Mot de passe :"), 0, 1);
        add(passwordField, 1, 1);

        add(boutonConnexion, 1, 2);
        add(messageLabel, 1, 3);

        boutonConnexion.setOnAction(e -> {
            String email = emailField.getText().trim();
            String mdp = passwordField.getText();

            if (email.isEmpty() || mdp.isEmpty()) {
                messageLabel.setText("Email et mot de passe obligatoires");
                return;
            }

            String sql = "SELECT id FROM responsable WHERE email = ? AND mdp_hash = MD5(?)";

            try (var conn = DB.getConnection();
                 var ps = conn.prepareStatement(sql)) {

                ps.setString(1, email);
                ps.setString(2, mdp);

                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        app.afficherAccueil();
                    } else {
                        messageLabel.setText("Identifiants incorrects");
                    }
                }

            } catch (Exception ex) {
                messageLabel.setText("Erreur connexion base");
                ex.printStackTrace();
            }
        });
    }
}
