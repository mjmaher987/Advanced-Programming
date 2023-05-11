module Graphic.Copy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
//    requires gson;
//    requires opencsv;
    
    opens project.View to javafx.fxml;

    exports project.View;
}