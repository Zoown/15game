module com.example.objektorientering_inlamning3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.objektorientering_inlamning3 to javafx.fxml;
    exports com.example.objektorientering_inlamning3;
}