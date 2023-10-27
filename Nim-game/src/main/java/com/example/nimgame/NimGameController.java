package com.example.nimgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NimGameController {

    @FXML
    private Button newGameButton;


    @FXML
    private Label one_th;

    @FXML
    private Button pcPlayButton;

    @FXML
    private Button rowFourButton;

    @FXML
    private Button rowOneButton;

    @FXML
    private Button rowThreeButton;

    @FXML
    private Button rowTowButton;

    @FXML
    private Label oud1;

    @FXML
    private Label oud10;

    @FXML
    private Label oud11;

    @FXML
    private Label oud12;

    @FXML
    private Label oud13;

    @FXML
    private Label oud14;

    @FXML
    private Label oud15;

    @FXML
    private Label oud16;

    @FXML
    private Label oud2;

    @FXML
    private Label oud3;

    @FXML
    private Label oud4;

    @FXML
    private Label oud5;

    @FXML
    private Label oud6;

    @FXML
    private Label oud7;

    @FXML
    private Label oud8;

    @FXML
    private Label oud9;

    int row1 = 1;
    int row2 = 3;
    int row3 = 5;
    int row4 = 7;

//    @FXML
//    private  Label [] ={oud1,oud2,oud3,oud4,oud5,oud6,oud7,oud8,oud9,oud10,oud11,oud12,oud13,oud14,oud15,oud16};


    @FXML
    void rowOneFunction(ActionEvent event) {

        if(row1 == 1){

                oud1.setText("");

            row1=row1-1;
            rowTowButton.setDisable(true);
            rowThreeButton.setDisable(true);
            rowFourButton.setDisable(true);

        }

    }

    @FXML
    void rowTowFunction(ActionEvent event) {
        if(row2>0 && row2<=3){
            if (row2 ==3){
                oud2.setText("");
            }
            else if (row2==2){
                oud3.setText("");
            }
            else if (row2==1){
                oud4.setText("");
            }

            rowOneButton.setDisable(true);
            rowThreeButton.setDisable(true);
            rowFourButton.setDisable(true);

            row2=row2-1;
        }


    }

    @FXML
    void rowThreeFunction(ActionEvent event) {
        if (row3 >0 && row3<=5){
            if (row3==5){
                oud5.setText("");
            }
            else if (row3==4){
                oud6.setText("");
            }
            else if (row3==3){
                oud7.setText("");
            }
            else if (row3==2){
                oud8.setText("");
            }
            else if (row3==1){
                oud9.setText("");
            }
            row3=row3-1;


            rowOneButton.setDisable(true);
            rowTowButton.setDisable(true);
            rowFourButton.setDisable(true);

        }
    }

    @FXML
    void rowFourFunction(ActionEvent event) {
        if(row4 >0 && row4<=7){
            if(row4==7){
                oud10.setText("");
            }
            else if(row4==6){
                oud11.setText("");
            }
            else if(row4==5){
                oud12.setText("");
            }
            else if(row4==4){
                oud13.setText("");
            }
            else if(row4==3){
                oud14.setText("");
            }
            else if(row4==2){
                oud15.setText("");
            }
            else if(row4==1){
                oud16.setText("");
            }
            row4=row4-1;


            rowOneButton.setDisable(true);
            rowThreeButton.setDisable(true);
            rowTowButton.setDisable(true);

        }

    }

    @FXML
    void newGameFunction(ActionEvent event) {
        row1=1;
        row2=3;
        row3=5;
        row4=7;
        rowOneButton.setDisable(false);
        rowThreeButton.setDisable(false);
        rowTowButton.setDisable(false);
        rowFourButton.setDisable(false);

//        String showOud="oud";

//            String showOud = "oud"+i;
            oud1.setText("|");
            oud2.setText("|");
            oud3.setText("|");
            oud4.setText("|");
            oud5.setText("|");
            oud6.setText("|");
            oud7.setText("|");
            oud8.setText("|");
            oud9.setText("|");
            oud10.setText("|");
            oud11.setText("|");
            oud12.setText("|");
            oud13.setText("|");
            oud14.setText("|");
            oud15.setText("|");
            oud16.setText("|");





    }








}