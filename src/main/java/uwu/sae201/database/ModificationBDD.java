package uwu.sae201.database;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uwu.sae201.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModificationBDD {

    public static int numero;

    @FXML
    private static TextField thematique_usage;
    @FXML
    private static TextField discipline;
    @FXML
    private static TextField degre;
    @FXML
    private static TextField academie;
    @FXML
    private static TextField region_academique;
    @FXML
    private static TextField type_acteur;
    @FXML
    private static TextField identite_acteur;
    @FXML
    private static TextField url_ressource;
    @FXML
    private static TextField nom_ressource;
    @FXML
    private static TextField type_source;
    @FXML
    private static TextField commentaires;

    private static List<TextField> textFieldList = new ArrayList<>();
    private static List<String> stringUsageList = new ArrayList<>();

    private static Connection connection = Database.getDBConnection();
    private PreparedStatement preparedStatement;
    private static Statement statement;
    private String req;


    @FXML
    public void initialize() throws SQLException {
        initializeTextFieldList();
        initializeStringUsageList();
    }

    public static List<TextField> initializeTextFieldList() {
        textFieldList.add(thematique_usage);
        textFieldList.add(discipline);
        textFieldList.add(degre);
        textFieldList.add(academie);
        textFieldList.add(region_academique);
        textFieldList.add(type_acteur);
        textFieldList.add(identite_acteur);
        textFieldList.add(url_ressource);
        textFieldList.add(nom_ressource);
        textFieldList.add(type_source);
        textFieldList.add(commentaires);
        return textFieldList;

    }

    public static List<String> initializeStringUsageList() {
        stringUsageList.add("THEMATIQUE_USAGE");
        stringUsageList.add("DISCIPLINE");
        stringUsageList.add("DEGRE");
        stringUsageList.add("ACADEMIE");
        stringUsageList.add("REGION_ACADEMIQUE");
        stringUsageList.add("TYPE_ACTEUR");
        stringUsageList.add("IDENTITE_ACTEUR");
        stringUsageList.add("URL_RESSOURCE");
        stringUsageList.add("NOM_RESSOURCE");
        stringUsageList.add("TYPE_SOURCE");
        stringUsageList.add("COMMENTAIRES");
        return stringUsageList;
    }

    public static void setNumeroByCount() throws SQLException {
        statement = connection.createStatement();

        String reqNumero = "SELECT COUNT(*) FROM typologie;";

        System.out.println("Execution de la requête : " + reqNumero );
        ResultSet rset = statement.executeQuery(reqNumero);

        while(rset.next()){
            numero = rset.getInt(1) + 1;
        }
    }
    @FXML
    private void verificationDesLabels(){
        for (TextField l : textFieldList){
            if (l.getText().isEmpty()){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Exécution de la requête SQL Impossible");
                errorAlert.setContentText("Tous les champs doivent être remplis");
                errorAlert.showAndWait();
                return;
            }
        }
        ajout();
    }

    private void ajout(){
        System.out.println("action de la requête en cours");

        try {
            //Alerte
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Exécution de la requête SQL en cours");
            alert.setContentText("Voulez-vous vraiment ajouter ces tuples dans la base de données ?");

            Optional<ButtonType> result = alert.showAndWait();  //Creer option OK | CANCEL
            if (result.get() == ButtonType.OK){
                //Requete
                req = "INSERT INTO typologie (NUMERO, THEMATIQUE_USAGE, DISCIPLINE, DEGRE, ACADEMIE, REGION_ACADEMIQUE, TYPE_ACTEUR, IDENTITE_ACTEUR, URL_RESSOURCE, NOM_RESSOURCE, TYPE_SOURCE, COMMENTAIRES) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);"
                ;
                preparedStatement = connection.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);

                //Remplir le preparedStatement
                setNumeroByCount();
                preparedStatement.setInt(1, numero);

                for (int i = 0; i < textFieldList.size(); ++i) {
                    preparedStatement.setString(i+2, String.valueOf(textFieldList.get(i).getText()));
                }

                //Execution
                preparedStatement.executeUpdate();
                System.out.println("requête envoyée");
            } else {
                System.out.println("requête annulée");
            }
        } catch (SQLException e) {
            System.out.println("erreur");
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void suppression(){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Exécution de la requête SQL en cours");
            alert.setContentText("Voulez-vous vraiment supprimer ces tuples de la base de données ?");

            Optional<ButtonType> result = alert.showAndWait();  //Creer option OK | CANCEL
            if(result.get() == ButtonType.OK) {

                //Requete
                req = "DELETE FROM typologie WHERE ";

                for (int i = 0; i < textFieldList.size(); ++i) {
                    if (!(textFieldList.get(i).getText().isEmpty())) {
                        req += stringUsageList.get(i) + " = '" + textFieldList.get(i).getText() + "'";
                        if (i != textFieldList.size() - 1) {
                            req += " and ";
                        }
                    }
                }
                req += ";";

                //Execution
                PreparedStatement pstmt = connection.prepareStatement(req);
                pstmt.executeUpdate();

                System.out.println("requête envoyée");
            }
        } catch (SQLException e) {
            System.out.println("erreur");
            throw new RuntimeException(e);
        }
    }
}
