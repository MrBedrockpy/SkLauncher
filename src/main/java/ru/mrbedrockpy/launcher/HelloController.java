package ru.mrbedrockpy.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HelloController {

    @FXML
    private ImageView settingsButton;

    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        new Thread(() -> {
            settingsButton.setScaleX(settingsButton.getScaleX() - 0.1);
            settingsButton.setScaleY(settingsButton.getScaleY() - 0.1);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            settingsButton.setScaleX(settingsButton.getScaleX() + 0.1);
            settingsButton.setScaleY(settingsButton.getScaleY() + 0.1);
        }).start();
    }
}