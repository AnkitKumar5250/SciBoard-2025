package org.sciborgs1155.dashboard.MainBoard;

import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.sciborgs1155.dashboard.BorderNodes.TitleBar.TitleBar;

/** Contains all of the elements within the {@link MainScene main scene}. */
public class MainRootPane extends BorderPane {
  /** Further details in{@link #titleBarProperty() the getter method}. */
  private final Property<TitleBar> titleBar = new SimpleObjectProperty<TitleBar>(this, "Title Bar");

  /** Lines the top of the pane, allows for window drag and manipulative functionality. */
  public final Property<TitleBar> titleBarProperty() {
    return this.titleBar;
  }

  /** Further details in {@link #backgroundColorProperty() the getter method} */
  private final Property<Color> backgroundColor =
      new SimpleObjectProperty<Color>(this, "Background Color", Color.BLUE);

  /**
   * The {@link Color background color}. A sub-property of {@link #backgroundProperty() the
   * background property}.
   */
  public final Property<Color> backgroundColorProperty() {
    return this.backgroundColor;
  }

  /** Further details in {@link #cornerRadiiProperty() the getter method} */
  private final Property<CornerRadii> cornerRadii =
      new SimpleObjectProperty<CornerRadii>(this, "Corner Radii", new CornerRadii(18, false));

  /**
   * The {@link CornerRadii radii of the corners}. A sub-property of {@link #backgroundProperty()
   * the background property}.
   */
  public final Property<CornerRadii> cornerRadiiProperty() {
    return this.cornerRadii;
  }

  /**
   * Contains all of the elements within the {@link MainScene main scene}.
   *
   * @param stage The {@link Stage stage} of the {@link MainScene main scene}.
   */
  public MainRootPane(Stage stage) {
    this.titleBarProperty().setValue(new TitleBar(stage));

    this.titleBarProperty()
        .getValue()
        .backgroundColorProperty()
        .bind(
            this.backgroundColorProperty()
                .map(
                    new Function<Color, Color>() {
                      @Override
                      public Color apply(Color thisBackgroundColor) {
                        if (thisBackgroundColor.getBrightness() >= 0.5) {
                          return thisBackgroundColor.deriveColor(0, 1, 0.83, 1);
                        } else {
                          return thisBackgroundColor.deriveColor(0, 1, 1.2, 1);
                        }
                      }
                    }));

    this.titleBarProperty()
        .getValue()
        .cornerRadiiProperty()
        .bind(
            this.cornerRadiiProperty()
                .map(
                    new Function<CornerRadii, CornerRadii>() {
                      @Override
                      public CornerRadii apply(CornerRadii thisCornerRadii) {
                        return new CornerRadii(
                            thisCornerRadii.getTopLeftVerticalRadius(),
                            thisCornerRadii.getTopRightVerticalRadius(),
                            0,
                            0,
                            false);
                      }
                    }));

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

    stage
        .maximizedProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observable,
                  Boolean oldValue,
                  Boolean newValue) {
                if (newValue == true) {
                  cornerRadiiProperty().setValue(new CornerRadii(0));
                } else {
                  cornerRadiiProperty().setValue(new CornerRadii(18, false));
                }
              }
            });

    this.topProperty().bind(this.titleBarProperty());
    this.backgroundColorProperty().setValue(Color.BLACK);

    this.minWidthProperty().bind(this.titleBarProperty().getValue().minWidthProperty());
    this.minHeightProperty().bind(this.titleBarProperty().getValue().minHeightProperty().add(30));
  }
}
