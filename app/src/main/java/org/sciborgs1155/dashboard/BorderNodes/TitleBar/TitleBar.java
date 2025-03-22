package org.sciborgs1155.dashboard.BorderNodes.TitleBar;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.sciborgs1155.dashboard.Utils.Assets;

/**
 * Collection of 3 {@link TitleBarButton TitleBarButtons}: Minimize, maximize, and close; at the top
 * of the window.
 */
public class TitleBar extends HBox {
  /** Used for controlling window drag behaviour. Mouse X-Position when window dragging starts. */
  private final DoubleProperty mouseXOnPress = new SimpleDoubleProperty(this, "Mouse X-Offset", 0);

  /** Used for controlling window drag behaviour. Mouse Y-Position when window dragging starts. */
  private final DoubleProperty mouseYOnPress = new SimpleDoubleProperty(this, "Mouse Y-Offset", 0);

  /** Further details in {@link #backgroundColorProperty() the getter method}. */
  private final Property<Color> backgroundColor =
      new SimpleObjectProperty<Color>(this, "Background Color", Color.gray(0.1));

  /**
   * The background color. Used as opposed to {@link #backgroundProperty() the background property}
   * due to versatility in bindings.
   */
  public Property<Color> backgroundColorProperty() {
    return backgroundColor;
  }

  /** Further details in {@link #cornerRadiiProperty() the getter method}. */
  private final Property<CornerRadii> cornerRadii =
      new SimpleObjectProperty<CornerRadii>(this, "Corner Radii", CornerRadii.EMPTY);

  /**
   * The corner radii. Used as opposed to {@link #backgroundProperty() the background property} due
   * to versatility in bindings.
   */
  public Property<CornerRadii> cornerRadiiProperty() {
    return cornerRadii;
  }

  /** Further details in {@link #minimizeButtonProperty() the getter method}. */
  private final Property<TitleBarButton> minimizeButton;

  /** Used to minimize the window. */
  public Property<TitleBarButton> minimizeButtonProperty() {
    return minimizeButton;
  }

  /** Further details in {@link #maximizeButtonProperty() the getter method}. */
  private final Property<TitleBarButton> maximizeButton;

  /** Used to maximize the window. */
  public Property<TitleBarButton> maximizeButtonProperty() {
    return maximizeButton;
  }

  /** Further details in {@link #closeButtonProperty() the getter method}. */
  private final Property<TitleBarButton> closeButton;

  /** Used to close the window. */
  public Property<TitleBarButton> closeButtonProperty() {
    return closeButton;
  }

  /**
   * Constructs a new {@link TitleBar} that is binded to a {@link Stage}.
   *
   * @param stage The stage binded to this {@link TitleBar}.
   */
  public TitleBar(Stage stage) {
    this.setSpacing(5);
    this.setFillHeight(false);
    this.setAlignment(Pos.CENTER_RIGHT);
    this.setViewOrder(-1);
    this.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() / 30);

    this.setOnMousePressed(
        event -> {
          mouseXOnPress.setValue(event.getSceneX());
          mouseYOnPress.setValue(event.getSceneY());
        });

    this.setOnMouseDragged(
        event -> {
          stage.setX(event.getScreenX() - mouseXOnPress.get());
          stage.setY(event.getScreenY() - mouseYOnPress.get());
        });

    this.minimizeButton =
        new SimpleObjectProperty<TitleBarButton>(
            this,
            "Minimize Button",
            new TitleBarButton(
                () -> stage.setIconified(!stage.isIconified()),
                Assets.getImage("TitleBarIcons/minimizeButton.png"),
                this));

    this.getChildren().add(minimizeButton.getValue());

    this.maximizeButton =
        new SimpleObjectProperty<TitleBarButton>(
            this,
            "Maximize Button",
            new TitleBarButton(
                () -> stage.setMaximized(!stage.isMaximized()),
                Assets.getImage("TitleBarIcons/maximizeButton.png"),
                this));

    this.getChildren().add(maximizeButton.getValue());

    this.closeButton =
        new SimpleObjectProperty<TitleBarButton>(
            this,
            "Close Button",
            new TitleBarButton(
                () -> stage.close(), Assets.getImage("TitleBarIcons/closeButton.png"), this));

    this.closeButtonProperty().getValue().hoverColorProperty().unbind();
    this.closeButtonProperty().getValue().hoverColorProperty().setValue(Color.RED);

    this.getChildren().add(closeButton.getValue());

    this.minWidthProperty()
        .bind(
            this.minimizeButton
                .getValue()
                .prefWidthProperty()
                .add(this.maximizeButton.getValue().prefWidthProperty())
                .add(this.closeButton.getValue().prefWidthProperty())
                .add(this.spacingProperty().multiply(2)));

    this.cornerRadiiProperty()
        .addListener(
            new ChangeListener<CornerRadii>() {
              @Override
              public void changed(
                  ObservableValue<? extends CornerRadii> observable,
                  CornerRadii oldValue,
                  CornerRadii newValue) {
                setBackground(
                    new Background(
                        new BackgroundFill(backgroundColor.getValue(), newValue, Insets.EMPTY)));
              }
            });

    this.backgroundColorProperty()
        .addListener(
            new ChangeListener<Color>() {
              @Override
              public void changed(
                  ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                setBackground(
                    new Background(
                        new BackgroundFill(newValue, cornerRadii.getValue(), Insets.EMPTY)));
              }
            });
  }
}
