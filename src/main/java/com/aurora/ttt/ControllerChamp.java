package com.aurora.ttt;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerChamp implements Initializable {
    @FXML
    private Button cancel_button,action_button;
    @FXML
    private BorderPane rootPane;
    private final AudioPlayer audioPlayer = new AudioPlayer();
    @FXML
    private MediaView mymedia2;
    @FXML
    private Label win_msg;
    private Model model;


    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancel_button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootPane.setOpacity(0);
        makeFadeInTransition();
        System.out.println("$ "+ Model.getStatus());
        switch (Model.getStatus()) {
            case Status.USER01_CHAMP:
                win_msg.setText("Player #1, You did it! ");
                break;
                case Status.USER02_CHAMP:
                    win_msg.setText("Player #2, You did it! ");
                    break;
            case Status.TIE_CHAMP:
                win_msg.setText("Tied Masters!");
        }
    }
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(5));
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    public void actionButtonOnAction(ActionEvent event) {
        audioPlayer.playAudio(mymedia2,audioPlayer.getButtonClickaudio());
        changeStageStart();

    }
    @FXML
    private void changeStageStart(){
        Stage stage = (Stage) cancel_button.getScene().getWindow();
        Model.setStatus(Status.START);

        try{
            Config config = new Config();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
            Scene sceneChamp = new Scene(fxmlLoader.load(), config.getDisplayWidth(), config.getDisplayHeight());
            Stage   stageStart = new Stage();
            stageStart.initStyle(StageStyle.UNDECORATED);
            stageStart.setTitle("Tic-Tac-Toe");
            stageStart.setScene(sceneChamp);
            stageStart.setResizable(false);
            fadeOutTransition(stage,stageStart);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void fadeOutTransition(Stage prevStage,Stage stage){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(5));
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        fadeTransition.setOnFinished(e ->{
            prevStage.close();
            stage.show();
        });
    }

}
