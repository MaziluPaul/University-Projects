module com.example.a4mazilupaul {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;
    requires org.junit.jupiter.api;

    opens com.example.a4mazilupaul to javafx.fxml;
    exports com.example.a4mazilupaul;
}