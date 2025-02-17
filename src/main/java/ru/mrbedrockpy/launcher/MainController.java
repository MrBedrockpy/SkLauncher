package ru.mrbedrockpy.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import lombok.Getter;
import ru.mrbedrockpy.downloadlibs.DownloadLibrariesManager;

import java.util.List;

public class MainController {

    @FXML
    private ImageView settingsButton;

    @Getter
    @FXML
    private ChoiceBox<String> versionChange;

    @FXML
    private CheckBox showSnapshots;

    public MainController() {}

    @FXML
    void initialize() {
        versionChange.getItems().addAll(new DownloadLibrariesManager().getVersionsStrings());
    }

    @FXML
    protected void onHelloButtonClick() {
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

    @FXML
    protected void onShowSnapshots() {
        List<String> versions = new DownloadLibrariesManager().getVersionsStrings();
        if (showSnapshots.isSelected()) versions.removeIf(version -> version.startsWith("snapshot"));
        versionChange.getItems().addAll(versions);
    }

}