module ru.mrbedrockpy.launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.json;
    requires java.net.http;


    opens ru.mrbedrockpy.launcher to javafx.fxml;
    exports ru.mrbedrockpy.launcher;
}