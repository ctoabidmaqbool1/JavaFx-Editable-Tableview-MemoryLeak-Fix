module com.maqboolsolutions.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires one.jpro.platform.treeshowing;


    opens com.maqboolsolutions.app to javafx.fxml;
    exports com.maqboolsolutions.app;
}