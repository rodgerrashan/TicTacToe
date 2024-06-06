package com.aurora.ttt;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final AudioPlayer audioPlayer = new AudioPlayer();

    private Status status;
    private Model model;

    private Player player01;
    private Player player02;

    @FXML
    private MediaView mymedia;
    @FXML
    private Button cancel_button,action_button;
    @FXML
    private Label notes,notes1,player01score,player02score,warningmsg,player1name,player2name,scoreLabel,title,rounds;
    @FXML
    private Button GG01,GG02,GG03,GG04,GG05,GG06,GG07,GG08,GG09;
    private Button [] gridButtonList;
    @FXML
    private BorderPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeFadeInTransition();
        model = new Model();
        gridButtonList = new Button[]{GG01, GG02, GG03, GG04, GG05, GG06, GG07, GG08, GG09};
        player01 = new Player("Player01");
        player02 = new Player("Player02");
        Model.setStatus(Status.START);
        status = Model.getStatus();
        audioPlayer.playAudio(mymedia,audioPlayer.getGameStartaudio());
        rounds.setText(String.valueOf(Model.getRounds()));
        notes1.setText("Good luck, and may the best player take all the bragging rights!");
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancel_button.getScene().getWindow();
        stage.close();

    }

    public void actionButtonOnAction(ActionEvent event) {
        audioPlayer.playAudio(mymedia,audioPlayer.getButtonClickaudio());
        if (Model.getStatus() == Status.START) {

            // when press starts
            scoreLabel.setDisable(false);
            player1name.setDisable(false);
            player2name.setDisable(false);
            player01score.setDisable(false);
            player02score.setDisable(false);

            title.setText("Game On");
            notes.setText("Player 1: ❌ vs. Player 2: ⭕");
            notes1.setText("Your Turn, Player 1! ");
            action_button.setText("RESET");
            Model.addRounds();
            rounds.setText(String.valueOf(Model.getRounds()));
            Model.setStatus(Status.USER01_ONBOARD);

            warningmsg.setText("");
        }else {
            // reset
            resetAppGrid();
            Model.setStatus(Status.START);
            scoreLabel.setDisable(true);
            player1name.setDisable(true);
            player1name.setDisable(true);
            player2name.setDisable(true);
            player01score.setDisable(true);
            player02score.setDisable(true);

            title.setText("Welcome");
            notes.setText("Get ready to outsmart, outplay, and out-tic-tac-toe each other! First to three in a row wins!");
            notes1.setText("It's your move! Who will claim victory in this battle of wits and strategy?");
            action_button.setText("START");

        }

    }

    public void makeaMove(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int clickedButtonId = Integer.parseInt(clickedButton.getId().substring(2)) - 1;
        System.out.println("$ grid button "+clickedButtonId +" pressed");

        if (model.isValidMove(clickedButtonId) && Model.getStatus() != Status.START) {
            audioPlayer.playAudio(mymedia,audioPlayer.getMakeTapaudio());
            System.out.println("$ valid move");
            warningmsg.setText("");
            String existingStyle = clickedButton.getStyle();


            if (Model.getStatus() == Status.USER01_ONBOARD) {
                clickedButton.setText(PlayerSymbol.X.name());
                clickedButton.setStyle(existingStyle+"-fx-text-fill: #002d63;");
                int USER01MODELSYMBOL = 1;
                model.makeMove(clickedButtonId, USER01MODELSYMBOL);
                model.showModelArray();
                notes1.setText("Your Turn, Player 2! ");
                if (!model.checkWinner()){
                    Model.setStatus(Status.USER02_ONBOARD);
                }else{
                    audioPlayer.playAudio(mymedia,audioPlayer.getGameSemiWinaudio());
                    updateScores();
                    Model.addRounds();
                    rounds.setText(String.valueOf(Model.getRounds()));
                    PauseTransition pause = new PauseTransition(Duration.seconds(2)); // 2-second delay
                    pause.setOnFinished(e -> resetAppGrid());
                    pause.play();
                    checkChamp();
                    Model.setStatus(Status.USER02_ONBOARD);

                }
                return;
            }
            if (Model.getStatus() == Status.USER02_ONBOARD) {
                clickedButton.setText(PlayerSymbol.O.name());
                clickedButton.setStyle(existingStyle+"-fx-text-fill:#400250 ;");
                int USER02MODELSYMBOL = -1;
                model.makeMove(clickedButtonId, USER02MODELSYMBOL);
                model.showModelArray();
                notes1.setText("Your Turn, Player 1! ");
                if (!model.checkWinner()){
                    Model.setStatus(Status.USER01_ONBOARD);
                }else{
                    audioPlayer.playAudio(mymedia,audioPlayer.getGameSemiWinaudio());
                    updateScores();
                    Model.addRounds();
                    rounds.setText(String.valueOf(Model.getRounds()));
                    PauseTransition pause = new PauseTransition(Duration.seconds(2)); // 2-second delay
                    pause.setOnFinished(e -> resetAppGrid());
                    pause.play();
                    checkChamp();
                    Model.setStatus(Status.USER01_ONBOARD);
                }
                return;
            }

        }else if(Model.getStatus() == Status.START){
            warningmsg.setText("Click START!");

        }else if(!model.isValidMove(clickedButtonId) ){
            warningmsg.setText("Already filled!");

        }else{
            warningmsg.setText("Invalid move!");
        }
    }
    public void updateScores(){
        try{

            if(Model.getStatus() == Status.USER01_WIN){
                player01.setScore(player01.getScore()+1);
                player01score.setText(String.valueOf(player01.getScore()));
            }else if (Model.getStatus() == Status.USER02_WIN) {
                player02.setScore(player02.getScore()+1);
                player02score.setText(String.valueOf(player02.getScore()));
            }else if (Model.getStatus() == Status.TIE){
                player01.setScore(player01.getScore()+1);
                player01score.setText(String.valueOf(player01.getScore()));
                player02.setScore(player02.getScore()+1);
                player02score.setText(String.valueOf(player02.getScore()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        model.resetGameGrid();

    }
    public void resetAppGrid(){
        for(Button button : gridButtonList){
            button.setDisable(false);
            button.setText("");
        }
    }

    public void deactivateGrid(){
        for (Button button : gridButtonList) {
            button.setDisable(true);
        }
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        for (Button button : gridButtonList) {
            button.setDisable(false);
        }
    }

    public void checkChamp(){
        int roundsPerChamp = 3;
        if(player01.getScore() == roundsPerChamp || player02.getScore() == roundsPerChamp){
            Model.resetRounds();
            status = Model.getStatus();
            audioPlayer.playAudio(mymedia,audioPlayer.getChampaudio());
            if(player01.getScore() == roundsPerChamp && player02.getScore() == roundsPerChamp){
                System.out.println("$ tie champs");
                Model.setStatus(Status.TIE_CHAMP);
                changeStageChamp();

            } else if(player01.getScore() == roundsPerChamp){
                System.out.println("$ player 01 is the champ");
                Model.setStatus(Status.USER01_CHAMP);
                changeStageChamp();

            }else if(player02.getScore() == roundsPerChamp){
                System.out.println("$ player 02 is the champ");
                Model.setStatus(Status.USER02_CHAMP);
                changeStageChamp();

            }

        }
    }

    @FXML
    private void changeStageChamp(){
        Stage stage = (Stage) cancel_button.getScene().getWindow();

        try{
            Config config = new Config();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("champ-view.fxml"));
            Scene sceneChamp = new Scene(fxmlLoader.load(), config.getDisplayWidth(), config.getDisplayHeight());
            Stage   stageChamp = new Stage();
            stageChamp.initStyle(StageStyle.UNDECORATED);
            stageChamp.setTitle("Tic-Tac-Toe");
            stageChamp.setScene(sceneChamp);
            stageChamp.setResizable(false);
            fadeOutTransition(stage,stageChamp);
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
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(5));
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

}