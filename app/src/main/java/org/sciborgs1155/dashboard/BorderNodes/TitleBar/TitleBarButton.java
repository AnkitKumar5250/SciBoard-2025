package org.sciborgs1155.dashboard.BorderNodes.TitleBar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.util.Duration;
import org.sciborgs1155.dashboard.Utils.Styles;

/** {@link Button Stylized Buttons} used for {@link TitleBar}. */
public class TitleBarButton extends Button {
  /** The amount that the button is scaled when it's clicked relative to original size. */
  public static final double clickScaleFactor = 0.9;

  /** The opacity of the button when it isn't being hovered over relative to original. */
  public static final double hoverOpacityFactor = 0.7;

  /**
   * The Constructor.
   *
   * @param action The action to do on the click of this button.
   * @param icon The icon of the button.
   * @param titleBar The TitleBar that this button is a part of.
   */
  public TitleBarButton(Runnable action, Image icon, TitleBar titleBar) {
    this.setOnAction(
        event -> {
          action.run();
          event.consume();
        });

    this.setAlignment(Pos.CENTER);

    this.prefHeightProperty().bind(titleBar.minHeightProperty());
    this.prefWidthProperty().bind(this.prefHeightProperty());

    this.minWidthProperty().bind(this.prefHeightProperty());
    this.minHeightProperty().bind(this.prefHeightProperty());

    Styles.applyHoverOpacityAnimation(hoverOpacityFactor, 1, Duration.millis(100), this);
    Styles.applyClickScaleAnimation(1, clickScaleFactor, Duration.millis(100), this);

    // Need to create a seperate variable as inside the 'ChangeListener', the 'this' keyword is
    // overriden.
    TitleBarButton thisButton = this;
    titleBar
        .widthProperty()
        .addListener(
            new ChangeListener<Number>() {
              @Override
              public void changed(
                  ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                thisButton.setBackground(
                    new Background(
                        new BackgroundImage(
                            icon,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            new BackgroundSize(
                                thisButton.getMinWidth() / 2,
                                thisButton.getMinHeight() / 2,
                                false,
                                false,
                                false,
                                false))));
              }
            });
  }
}
