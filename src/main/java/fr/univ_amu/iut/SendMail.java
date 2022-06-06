package fr.univ_amu.iut;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Desktop;

import java.io.IOException;
import java.net.URI;

public class SendMail {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static String data;

    @FXML
    public void switchTo(ActionEvent event) throws IOException {
        SceneController sc = new SceneController();
        Node node = (Node) event.getSource() ;
        data = (String) node.getUserData();
        sc.switchTo2(node,data);

    }
    @FXML
    public void sendMessage() throws IOException {
        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:frederic.egenscheviller@etu.univ-amu.fr?subject=Contact";
        URI uri = URI.create(message);
        desktop.mail(uri);
    }
}
