module ru.mrbedrockpy.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens ru.mrbedrockpy.launcher to javafx.fxml;
    exports ru.mrbedrockpy.launcher;
}