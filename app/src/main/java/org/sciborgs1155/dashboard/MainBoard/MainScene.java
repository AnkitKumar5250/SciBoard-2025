package org.sciborgs1155.dashboard.MainBoard;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/** The home page of the app, contains a {@link MainRootPane root} and a {@link Resizer resizer}. */
public class MainScene extends Scene {
  /** Further details in {@link #mainRootPaneProperty() the getter method}. */
  private final Property<MainRootPane> mainRootPane =
      new SimpleObjectProperty<MainRootPane>(this, "Main Root Pane");

  /** Contains the main content of the scene. */
  public Property<MainRootPane> mainRootPaneProperty() {
    return this.mainRootPane;
  }

  /** Further details in {@link #stageProperty() the getter method}. */
  private final Property<Stage> stage = new SimpleObjectProperty<Stage>(this, "Stage");

  /** Contains the main content of the scene. */
  public Property<Stage> stageProperty() {
    return this.stage;
  }

  /**
   * The home page of the app, contains a {@link MainRootPane root}.
   *
   * @param stage The {@link Stage stage} to bind the {@link MainRootPane root} to.
   */
  public MainScene(Stage stage) {
    super(new MainRootPane(stage), Color.TRANSPARENT);

    this.stageProperty().setValue(stage);
    this.mainRootPaneProperty().setValue((MainRootPane) this.getRoot());

    stage.minHeightProperty().bind(this.mainRootPaneProperty().getValue().minHeightProperty());
    stage.minWidthProperty().bind(this.mainRootPaneProperty().getValue().minWidthProperty());
  }
}
