import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class InterventionView extends BorderPane {

    private final TableView<InterventionRow> table = new TableView<>();
    private final ObservableList<InterventionRow> data = FXCollections.observableArrayList();

    public InterventionView(MainApp app) {
        setPadding(new Insets(15));

        Label titre = new Label("Interventions");
        setTop(titre);
        BorderPane.setMargin(titre, new Insets(0, 0, 10, 0));

        TableColumn<InterventionRow, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(c -> c.getValue().dateProperty());

        TableColumn<InterventionRow, String> colTech = new TableColumn<>("Technicien");
        colTech.setCellValueFactory(c -> c.getValue().technicienProperty());

        TableColumn<InterventionRow, String> colBat = new TableColumn<>("Bâtiment");
        colBat.setCellValueFactory(c -> c.getValue().batimentProperty());

        TableColumn<InterventionRow, String> colResp = new TableColumn<>("Responsable");
        colResp.setCellValueFactory(c -> c.getValue().responsableProperty());

        TableColumn<InterventionRow, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(c -> c.getValue().typeProperty());

        TableColumn<InterventionRow, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(c -> c.getValue().statutProperty());

        table.getColumns().addAll(colDate, colTech, colBat, colResp, colType, colStatut);
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
            SELECT
                i.date_intervention,
                i.type_intervention,
                i.statut,
                CONCAT(t.nom, ' ', t.prenom) AS technicien,
                b.nom AS batiment,
                CONCAT(r.nom, ' ', r.prenom, ' (', r.email, ')') AS responsable
            FROM intervention i
            JOIN technicien t ON t.id = i.technicien_id
            JOIN batiment b ON b.id = i.batiment_id
            JOIN responsable r ON r.id = i.responsable_id
            ORDER BY i.date_intervention DESC, i.id DESC
        """;

        try (var conn = DB.getConnection();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new InterventionRow(
                        rs.getString("date_intervention"),
                        rs.getString("technicien"),
                        rs.getString("batiment"),
                        rs.getString("responsable"),
                        rs.getString("type_intervention"),
                        rs.getString("statut")
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            afficherAlerte("Erreur DB", "Impossible de charger les interventions.\nVérifie la connexion et la table intervention.");
        }
    }

    private void afficherAlerte(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static class InterventionRow {
        private final SimpleStringProperty date;
        private final SimpleStringProperty technicien;
        private final SimpleStringProperty batiment;
        private final SimpleStringProperty responsable;
        private final SimpleStringProperty type;
        private final SimpleStringProperty statut;

        public InterventionRow(String date, String technicien, String batiment, String responsable, String type, String statut) {
            this.date = new SimpleStringProperty(date);
            this.technicien = new SimpleStringProperty(technicien);
            this.batiment = new SimpleStringProperty(batiment);
            this.responsable = new SimpleStringProperty(responsable);
            this.type = new SimpleStringProperty(type);
            this.statut = new SimpleStringProperty(statut);
        }

        public SimpleStringProperty dateProperty() { return date; }
        public SimpleStringProperty technicienProperty() { return technicien; }
        public SimpleStringProperty batimentProperty() { return batiment; }
        public SimpleStringProperty responsableProperty() { return responsable; }
        public SimpleStringProperty typeProperty() { return type; }
        public SimpleStringProperty statutProperty() { return statut; }
    }
}
