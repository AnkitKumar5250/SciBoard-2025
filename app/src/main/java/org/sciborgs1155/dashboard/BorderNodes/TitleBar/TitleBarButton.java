package org.sciborgs1155.dashboard.BorderNodes.TitleBar;

import java.util.function.Function;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/** {@link Button Stylized Buttons} used for {@link TitleBar}. */
public class TitleBarButton extends Button {
  /** The amount that the button is scaled when it's clicked relative to original size. */
  private final DoubleProperty clickScale = new SimpleDoubleProperty(0.9);

  /** The opacity of the button when it isn't being hovered over relative to original. */
  private final DoubleProperty hoverOpacity = new SimpleDoubleProperty(0.7);

  /** Further details in {@link #hoverColorProperty() the getter method}. */
  private final Property<Color> hoverColor = new SimpleObjectProperty<Color>(Color.TRANSPARENT);

  /** The color of the background when it is being hovered over. */
  public final Property<Color> hoverColorProperty() {
    return hoverColor;
  }

  /**
   * The time the hover animations take to execute. The click animations will always be half of this
   * value.
   */
  private final Property<Duration> animationTime =
      new SimpleObjectProperty<Duration>(Duration.millis(100));

  /**
   * An animation that makes {@link #backgroundFill the background} opaque on hover in order to
   * simulate color change.
   */
  private final FadeTransition hoverColorAnimation;

  /** An animation that scales the button down when pressed on. */
  private final ScaleTransition clickScaleAnimation;

  /** An animation that makes the button icon more opaque on hover. */
  private final FadeTransition hoverOpacityAnimation;

  /** The solid background(used for hover-fill animations). */
  private final Rectangle backgroundFill;

  /** The background Image(used as graphic). */
  private final ImageView backgroundIcon;

  /**
   * The Constructor.
   *
   * @param action The action to do on the click of this button.
   * @param icon The icon of the button.
   * @param titleBar The {@link } that this button is a part of.
   */
  public TitleBarButton(Runnable action, Image icon, TitleBar titleBar) {
    this.backgroundIcon = new ImageView(icon);

    this.backgroundIcon.fitWidthProperty().bind(this.widthProperty().divide(2));
    this.backgroundIcon.fitHeightProperty().bind(this.heightProperty().divide(2));

    this.backgroundIcon.xProperty().bind(this.layoutXProperty());
    this.backgroundIcon.yProperty().bind(this.layoutYProperty());

    this.backgroundIcon.setEffect(new ColorAdjust(0, 0, 1, 1));

    this.backgroundFill = new Rectangle();
    this.backgroundFill.setOpacity(0);

    this.backgroundFill.widthProperty().bind(this.widthProperty());
    this.backgroundFill.heightProperty().bind(this.heightProperty());
    this.backgroundFill.xProperty().bind(this.layoutXProperty());
    this.backgroundFill.yProperty().bind(this.layoutYProperty());

    this.backgroundFill.clipProperty().bind(this.clipProperty());

    this.setGraphic(new StackPane(this.backgroundFill, this.backgroundIcon));

    this.hoverColorAnimation = new FadeTransition();
    this.hoverColorAnimation.setNode(backgroundFill);

    this.hoverColorAnimation.setFromValue(0);
    this.hoverColorAnimation.setToValue(1);

    this.hoverColorAnimation.durationProperty().bind(animationTime);

    this.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          hoverColorAnimation.setRate(1);
          hoverColorAnimation.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    this.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          hoverColorAnimation.setRate(-1);
          hoverColorAnimation.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    this.hoverColor.bind(
        titleBar
            .backgroundColorProperty()
            .map(
                new Function<Color, Color>() {
                  @Override
                  public Color apply(Color backgroundColor) {
                    return backgroundColor.deriveColor(0, 1, 0.7, 1);
                  }
                }));

    this.hoverColor.addListener(
        new ChangeListener<Color>() {
          @Override
          public void changed(
              ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
            backgroundFill.setFill(newValue);
            backgroundIcon.setEffect(new ColorAdjust((newValue.getHue() + 180) % 360, 1, 1, 1));
            hoverColorAnimation.setToValue(newValue.getOpacity());
          }
        });

    titleBar
        .cornerRadiiProperty()
        .addListener(
            new ChangeListener<CornerRadii>() {
              @Override
              public void changed(
                  ObservableValue<? extends CornerRadii> observable,
                  CornerRadii oldValue,
                  CornerRadii newValue) {
                setBackground(
                    new Background(new BackgroundFill(Color.TRANSPARENT, newValue, Insets.EMPTY)));
              }
            });

    this.clickScaleAnimation = new ScaleTransition();
    this.clickScaleAnimation.setNode(this);

    this.clickScaleAnimation.setFromX(1);
    this.clickScaleAnimation.setFromY(1);

    this.clickScaleAnimation.toXProperty().bind(clickScale);
    this.clickScaleAnimation.toYProperty().bind(clickScale);

    this.clickScaleAnimation.durationProperty().bind(animationTime);

    this.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          clickScaleAnimation.setRate(1);
          clickScaleAnimation.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    this.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          hoverColorAnimation.setRate(-1);
          clickScaleAnimation.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    this.hoverOpacityAnimation = new FadeTransition();
    this.hoverOpacityAnimation.setNode(this);

    this.hoverOpacityAnimation.fromValueProperty().bind(hoverOpacity);
    this.hoverOpacityAnimation.setToValue(1);

    this.hoverOpacityAnimation.durationProperty().bind(animationTime);

    this.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          hoverOpacityAnimation.setRate(1);
          hoverOpacityAnimation.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    this.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          hoverOpacityAnimation.setRate(-1);
          hoverOpacityAnimation.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    this.setOnAction(
        event -> {
          action.run();
          event.consume();
        });

    this.setAlignment(Pos.CENTER);
    this.setBackground(Background.fill(Color.TRANSPARENT));

    this.prefWidthProperty().bind(titleBar.minHeightProperty());
    this.prefHeightProperty().bind(titleBar.minHeightProperty());

    this.minWidthProperty().bind(this.prefHeightProperty());
    this.minHeightProperty().bind(this.prefHeightProperty());
  }
}
