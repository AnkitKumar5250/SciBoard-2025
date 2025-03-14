package org.sciborgs1155.dashboard.BorderNodes.TitleBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.sciborgs1155.dashboard.Utils.Assets;
import org.sciborgs1155.dashboard.Utils.Styles;

/**
 * Collection of 3 {@link TitleBarButton TitleBarButtons}: Minimize, maximize, and close; at the top
 * of the window.
 */
public class TitleBar extends HBox {
  /** Used for controlling window drag behaviour. Mouse position when window dragging starts. */
  private double mouseXOffset = 0;

  /** Used for controlling window drag behaviour. Mouse position when window dragging starts. */
  private double mouseYOffset = 0;

  /** Used to minimize the window. */
  private final TitleBarButton minimizeButton;

  /** Used to maximize the window. */
  private final TitleBarButton maximizeButton;

  /** Used to close the window. */
  private final TitleBarButton closeButton;

  /** The background color. */
  public final Color backgroundColor = Color.gray(0.1);

  /** The background radii. */
  public final CornerRadii cornerRadii = CornerRadii.EMPTY;

  /**
   * Constructs a new {@link TitleBar} that is binded to a {@link Stage}.
   *
   * @param stage The stage binded to this {@link TitleBar}.
   */
  public TitleBar(Stage stage) {
    this.setBackground(
        new Background(new BackgroundFill(backgroundColor, cornerRadii, Insets.EMPTY)));
    this.setSpacing(5);
    this.setFillHeight(false);
    this.setAlignment(Pos.CENTER_RIGHT);
    this.setViewOrder(-1);

    this.setMinHeight(stage.getHeight() / 10);

    this.setOnMousePressed(
        event -> {
          mouseXOffset = event.getSceneX();
          mouseYOffset = event.getSceneY();
        });

    this.setOnMouseDragged(
        event -> {
          stage.setX(event.getScreenX() - mouseXOffset);
          stage.setY(event.getScreenY() - mouseYOffset);
        });

    this.minimizeButton =
        new TitleBarButton(
            () -> stage.setIconified(!stage.isIconified()),
            Assets.getImage("TitleBarIcons/minimizeButton.png"),
            this);
    this.maximizeButton =
        new TitleBarButton(
            () -> stage.setMaximized(!stage.isMaximized()),
            Assets.getImage("TitleBarIcons/maximizeButton.png"),
            this);
    this.closeButton =
        new TitleBarButton(
            () -> stage.close(), Assets.getImage("TitleBarIcons/closeButton.png"), this);

    this.getChildren()
        .add(
            Styles.applyHoverFillAnimation(
                Color.TRANSPARENT,
                this.backgroundColor.deriveColor(1, 1, 3, 1),
                Duration.millis(100),
                minimizeButton));

    this.getChildren()
        .add(
            Styles.applyHoverFillAnimation(
                Color.TRANSPARENT,
                this.backgroundColor.deriveColor(1, 1, 3, 1),
                Duration.millis(100),
                maximizeButton));

    this.getChildren()
        .add(
            Styles.applyHoverFillAnimation(
                Color.TRANSPARENT,
                Color.RED.deriveColor(1, 1, 3, 1),
                Duration.millis(100),
                closeButton));

    this.minWidthProperty().bind(this.minimizeButton.minWidthProperty().multiply(3));
  }
}
