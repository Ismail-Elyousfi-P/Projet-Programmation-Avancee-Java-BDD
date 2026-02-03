package ui;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BatimentView extends BorderPane {

    private final TableView<BatimentRow> table = new TableView<>();
    private final ObservableList<BatimentRow> data = FXCollections.observableArrayList();

    public BatimentView(MainApp app) {
        setPadding(new Insets(15));

        Label titre = new Label("Bâtiments");
        setTop(titre);
        BorderPane.setMargin(titre, new Insets(0, 0, 10, 0));

        TableColumn<BatimentRow, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> c.getValue().nomProperty());

        TableColumn<BatimentRow, String> colLoc = new TableColumn<>("Localisation");
        colLoc.setCellValueFactory(c -> c.getValue().localisationProperty());

        table.getColumns().addAll(colNom, colLoc);
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
            SELECT nom, localisation
            FROM batiment
            ORDER BY nom
        """;

        try (var conn = DB.getConnection();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new BatimentRow(
                        rs.getString("nom"),
                        rs.getString("localisation")
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            afficherAlerte("Erreur DB", "Impossible de charger les bâtiments.\nVérifie la connexion et la table batiment.");
        }
    }

    private void afficherAlerte(String titre, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static class BatimentRow {
        private final SimpleStringProperty nom;
        private final SimpleStringProperty localisation;

        public BatimentRow(String nom, String localisation) {
            this.nom = new SimpleStringProperty(nom);
            this.localisation = new SimpleStringProperty(localisation);
        }

        public SimpleStringProperty nomProperty() { return nom; }
        public SimpleStringProperty localisationProperty() { return localisation; }
    }
}
