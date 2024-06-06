package com.aurora.ttt;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URISyntaxException;
import java.net.URL;

public class AudioPlayer {

    private final String gameStartaudio = "/audio/gameStart.mp3";
    private final String makeTapaudio = "/audio/tap.mp3";
    private final String gameSemiWinaudio = "/audio/semiWin.mp3";
    private final String buttonClickaudio = "/audio/button.mp3";
    private final String champaudio = "/audio/champ.mp3";

    public String getGameStartaudio() {
        return gameStartaudio;
    }

    public String getMakeTapaudio() {
        return makeTapaudio;
    }

    public String getGameSemiWinaudio() {
        return gameSemiWinaudio;
    }

    public String getButtonClickaudio() {
        return buttonClickaudio;
    }

    public String getChampaudio() {
        return champaudio;
    }


    @FXML
    protected void playAudio(MediaView mymedia,String url) {

        try {
            URL resourceUrl = getClass().getResource(url);
            if (resourceUrl == null) {
                throw new RuntimeException("Resource not found: " + url);
            }
            String filename = resourceUrl.toURI().toString();
            Media media = new Media(filename);
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            if (mymedia.getMediaPlayer() != null){
                // clear prev media player
                mymedia.getMediaPlayer().stop();
                mymedia.getMediaPlayer().dispose();
            }
            mymedia.setMediaPlayer(mediaPlayer);

            mymedia.getMediaPlayer().play();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
