package ui;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TechnicienView extends BorderPane {

    private final TableView<TechnicienRow> table = new TableView<>();
    private final ObservableList<TechnicienRow> data = FXCollections.observableArrayList();

    public TechnicienView(MainApp app) {
        setPadding(new Insets(15));

        Label titre = new Label("Techniciens");
        setTop(titre);
        BorderPane.setMargin(titre, new Insets(0, 0, 10, 0));

        TableColumn<TechnicienRow, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> c.getValue().nomProperty());

        TableColumn<TechnicienRow, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(c -> c.getValue().prenomProperty());

        TableColumn<TechnicienRow, String> colTel = new TableColumn<>("Téléphone");
        colTel.setCellValueFactory(c -> c.getValue().telephoneProperty());

        TableColumn<TechnicienRow, String> colQualif = new TableColumn<>("Qualification");
        colQualif.setCellValueFactory(c -> c.getValue().qualificationProperty());

        TableColumn<TechnicienRow, String> colDispo = new TableColumn<>("Disponible");
        colDispo.setCellValueFactory(c -> c.getValue().disponibleProperty());

        table.getColumns().addAll(colNom, colPrenom, colTel, colQualif, colDispo);
        table.setItems(data);

        setCenter(table);

        Button refresh = new Button("Rafraîchir");
        refresh.setOnAction(e -> chargerDepuisDB());

        Button retour = new Button("Retour");
        retour.setOnAction(e -> app.afficherAccueil());

        HBox actions = new HBox(10, refresh, retour);
        actions.setPadding(new Insets(10, 0, 0, 0));
        setBottom(actions);

        chargerDepuisDB();
    }

    private void chargerDepuisDB() {
        data.clear();

        String sql = """
            SELECT nom, prenom, telephone, qualification, disponible
            FROM technicien
            ORDER BY nom, prenom
        """;

        try (var conn = DB.getConnection();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String tel = rs.getString("telephone");
                String qualif = rs.getString("qualification");
                boolean dispo = rs.getBoolean("disponible");

                data.add(new TechnicienRow(
                        nom, prenom, tel, qualif, dispo ? "Oui" : "Non"
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            afficherAlerte("Erreur DB", "Impossible de charger les techniciens.\nVérifie la connexion et la table technicien.");
        }
    }

    private void afficherAlerte(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static class TechnicienRow {
        private final SimpleStringProperty nom;
        private final SimpleStringProperty prenom;
        private final SimpleStringProperty telephone;
        private final SimpleStringProperty qualification;
        private final SimpleStringProperty disponible;

        public TechnicienRow(String nom, String prenom, String telephone, String qualification, String disponible) {
            this.nom = new SimpleStringProperty(nom);
            this.prenom = new SimpleStringProperty(prenom);
            this.telephone = new SimpleStringProperty(telephone);
            this.qualification = new SimpleStringProperty(qualification);
            this.disponible = new SimpleStringProperty(disponible);
        }

        public SimpleStringProperty nomProperty() { return nom; }
        public SimpleStringProperty prenomProperty() { return prenom; }
        public SimpleStringProperty telephoneProperty() { return telephone; }
        public SimpleStringProperty qualificationProperty() { return qualification; }
        public SimpleStringProperty disponibleProperty() { return disponible; }
    }
}
