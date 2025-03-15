package org.sciborgs1155.dashboard.ScoralTab;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.sciborgs1155.dashboard.BorderNodes.TitleBar.TitleBar;

/** Contains all of the elements within the {@link ScoralScene scoral scene}. */
public class ScoralRootPane extends BorderPane {
  /** The {@link TitleBar} lining the top the {@link ScoralScene scoral scene}. */
  private final TitleBar titleBar;

  /**
   * Contains all of the elements within the {@link ScoralScene scoral scene}
   *
   * @param stage The {@link Stage stage} of the {@link ScoralScene scoral scene}.
   */
  public ScoralRootPane(Stage stage) {
    this.titleBar = new TitleBar(stage);

    this.setBackground(Background.fill(Color.BLACK));

    this.setTop(titleBar);
    this.minWidthProperty().bind(titleBar.minWidthProperty());
    this.minHeightProperty().bind(titleBar.minHeightProperty().add(30));

    stage.minHeightProperty().bind(this.minHeightProperty());
    stage.minWidthProperty().bind(this.minWidthProperty());
  }
}
