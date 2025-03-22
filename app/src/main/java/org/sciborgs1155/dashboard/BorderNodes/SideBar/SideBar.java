package org.sciborgs1155.dashboard.BorderNodes.SideBar;

import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SideBar extends VBox {
    public SideBar(Stage stage) {
        this.prefHeightProperty().bind(stage.heightProperty());
        this.prefWidthProperty().bind(stage.widthProperty().divide(10));

        this.setBackground(Background.fill(Color.RED));
    }
}
