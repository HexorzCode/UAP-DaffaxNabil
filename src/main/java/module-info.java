module org.main.UAP {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.main.UAP to javafx.fxml;
    exports org.main.UAP;
}