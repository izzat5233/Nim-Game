package com.example.nimgame.fxml;

//import com.example.nimgame.object.PileSelectionListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PlayerNameController implements Initializable {


    @FXML
    public TextField playerOneName=new TextField();
    @FXML
    public TextField playerTowName=new TextField();
    @FXML
    private Label warningLabel=new Label();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }




    @FXML
    void OkayButton(ActionEvent event) throws IOException {


//        System.out.println(playerOneName.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Multiplayer.fxml"));
            Parent parent = loader.load();
            MultiplayerController multiplayerController = loader.getController();
            // Send data to MultiplayerController
            String p1=playerOneName.getText();
            String p2 = playerTowName.getText();

            multiplayerController.setNameplay(p1);
            multiplayerController.setNames(p1,p2);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

    }

}
