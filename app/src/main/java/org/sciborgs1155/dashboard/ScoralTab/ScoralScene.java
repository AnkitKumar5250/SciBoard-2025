package org.sciborgs1155.dashboard.ScoralTab;

import org.sciborgs1155.dashboard.BorderNodes.Resizing.StageResizer;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The {@link Scene main scene} of the app, contains the {@link ScoralRootPane}. Is used for scoring
 * coral and algay into the reef.
 */
public class ScoralScene extends Scene {
  /**
   * The main {@link Scene scene} of the app, contains the {@link ScoralRootPane}.
   *
   * @param stage The {@link Stage stage} to copy the {@link #widthProperty() width} and {@link
   *     #heightProperty() height} of.
   */
  public ScoralScene(Stage stage) {
    super(new StageResizer(stage, new ScoralRootPane(stage)), stage.getWidth(), stage.getHeight(), Color.TRANSPARENT);
  }

  
}
