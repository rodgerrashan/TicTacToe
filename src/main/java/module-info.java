module com.aurora.ttt {
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.media;

    requires org.kordamp.bootstrapfx.core;

    opens com.aurora.ttt to javafx.fxml;
    exports com.aurora.ttt;
}