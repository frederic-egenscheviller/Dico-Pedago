package fr.univ_amu.iut;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switchTo(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource() ;
        String data = (String) node.getUserData();

        root = FXMLLoader.load(getClass().getResource(data));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setMaximized(true);
        scene = new Scene(root,1280,720);
        stage.setTitle("Dico Pédago");
        stage.setScene(scene);
        stage.show();
    }

    public void switchTo2(Node node, String filename) throws IOException {
        root = FXMLLoader.load(getClass().getResource(filename));
        stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        scene = new Scene(root,1280,720);
        stage.setTitle("Dico Pédago");
        stage.setScene(scene);
        stage.show();
    }
}
