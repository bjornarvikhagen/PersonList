module com.personlist.personlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;

    opens com.personlist.personlist to javafx.fxml;
    exports com.personlist.personlist;
}