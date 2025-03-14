package org.sciborgs1155.dashboard.Utils;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/** Contains methods for styling certain {@link Node} objects. */
public class Styles {
  /**
   * Applies a basic on-hover {@link ScaleTransition Scale Transition} to the {@link Node node}.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER NODE}
   *
   * @param notHoveredScale The scaling of the node when it isn't being hovered over.
   * @param isHoveredScale The scaling of the node when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param node The node to apply the transition to.
   */
  public static Node applyHoverScaleAnimation(
      double notHoveredScale, double isHoveredScale, Duration duration, Node node) {
    final ScaleTransition scaleTransition = new ScaleTransition(duration);
    scaleTransition.setNode(node);
    scaleTransition.setToX(isHoveredScale);
    scaleTransition.setToY(isHoveredScale);
    scaleTransition.setFromX(notHoveredScale);
    scaleTransition.setFromY(notHoveredScale);

    node.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          scaleTransition.setRate(1);
          node.setViewOrder(-1);
          node.setEffect(new DropShadow(5, Color.BLACK));
          scaleTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    node.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          scaleTransition.setRate(-1);
          node.setViewOrder(0);
          scaleTransition.play();
          node.setEffect(new DropShadow(0, Color.TRANSPARENT));
          event.isConsumed(); // For VSCode higlighting.
        });

    return node;
  }

  /**
   * Applies a basic on-hover {@link ScaleTransition Scale Transition} to the {@link Node node}. In
   * addition, adds a {@link DropShadow} to the node when it is scaled up, removed afterwards.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER NODE}
   *
   * @param notHoveredScale The scaling of the node when it isn't being hovered over.
   * @param isHoveredScale The scaling of the node when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param shadow The shadow to apply on hover.
   * @param node The node to apply the transition to.
   */
  public static Node applyHoverScaleAnimationWithShadow(
      double notHoveredScale,
      double isHoveredScale,
      Duration duration,
      DropShadow shadow,
      Node node) {
    final ScaleTransition scaleTransition = new ScaleTransition(duration);
    scaleTransition.setNode(node);
    scaleTransition.setToX(isHoveredScale);
    scaleTransition.setToY(isHoveredScale);
    scaleTransition.setFromX(notHoveredScale);
    scaleTransition.setFromY(notHoveredScale);

    node.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          scaleTransition.setRate(1);
          node.setViewOrder(-1);
          node.setEffect(shadow);
          scaleTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    node.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          scaleTransition.setRate(-1);
          node.setViewOrder(0);
          scaleTransition.play();
          node.setEffect(new DropShadow(0, Color.TRANSPARENT));
          event.isConsumed(); // For VSCode higlighting.
        });

    return node;
  }

  /**
   * Applies a basic on-hover {@link ScaleTransition Scale Transition} to the {@link Node node}. In
   * addition, adds a {@link DropShadow} to the node when it is scaled up, removed afterwards.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER NODE}
   *
   * @param notHoveredScale The scaling of the node when it isn't being hovered over.
   * @param isHoveredScale The scaling of the node when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param shadow The shadow to apply on hover.
   * @param eventListener The node to act as the reciever for the hovering.
   * @param node The node to apply the transition to.
   */
  public static Node applyHoverScaleAnimationWithShadow(
      double notHoveredScale,
      double isHoveredScale,
      Duration duration,
      DropShadow shadow,
      Node eventListener,
      Node node) {
    final ScaleTransition scaleTransition = new ScaleTransition(duration);
    scaleTransition.setNode(node);
    scaleTransition.setToX(isHoveredScale);
    scaleTransition.setToY(isHoveredScale);
    scaleTransition.setFromX(notHoveredScale);
    scaleTransition.setFromY(notHoveredScale);

    node.setScaleX(notHoveredScale);
    node.setScaleY(notHoveredScale);

    eventListener.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          scaleTransition.setRate(1);
          node.setViewOrder(-1);
          node.setEffect(shadow);
          scaleTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    eventListener.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          scaleTransition.setRate(-1);
          node.setViewOrder(0);
          scaleTransition.play();
          node.setEffect(new DropShadow(0, Color.TRANSPARENT));
          event.isConsumed(); // For VSCode higlighting.
        });

    return node;
  }

  // TODO Styling for background rectangles for bttons
  // TODO sidebar + resizing

  /**
   * Applies a basic on-click {@link ScaleTransition Scale Transition} to the {@link Button button}.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER NODE}
   *
   * @param beforeClickedScale The scaling of the node when it isn't being hovered over.
   * @param afterClickedScale The scaling of the node when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param node The node to apply the transition to.
   */
  public static Node applyClickScaleAnimation(
      double beforeClickedScale, double afterClickedScale, Duration duration, Node node) {
    final ScaleTransition scaleTransition = new ScaleTransition(duration);
    scaleTransition.setNode(node);
    scaleTransition.setToX(afterClickedScale);
    scaleTransition.setToY(afterClickedScale);
    scaleTransition.setFromX(beforeClickedScale);
    scaleTransition.setFromY(beforeClickedScale);

    node.setScaleX(beforeClickedScale);
    node.setScaleY(beforeClickedScale);

    node.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          scaleTransition.setRate(1);
          node.setViewOrder(-1);
          scaleTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    node.addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          scaleTransition.setRate(-2);
          node.setViewOrder(0);
          scaleTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    return node;
  }

  /**
   * Applies a basic on-hover {@link FadeTransition Scale Transition} to the {@link Node node}.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER NODE}
   *
   * @param notHoveredOpacity The opacity of the node when it isn't being hov`ered over.
   * @param isHoveredOpacity The opacity of the node when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param node The node to apply the transition to.
   */
  public static Node applyHoverOpacityAnimation(
      double notHoveredOpacity, double isHoveredOpacity, Duration duration, Node node) {
    final FadeTransition fadeTransition = new FadeTransition(duration);
    fadeTransition.setNode(node);
    fadeTransition.setFromValue(notHoveredOpacity);
    fadeTransition.setToValue(isHoveredOpacity);

    node.setOpacity(notHoveredOpacity);

    node.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          fadeTransition.setRate(1);
          fadeTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    node.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          fadeTransition.setRate(-1);
          fadeTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    return node;
  }

  /**
   * Applies a basic on-hover background swap to the {@link Region region}. Use {@link
   * #applyHoverFillAnimation(Color, Color, Duration ,Shape)} for smoother animations.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER REGION}
   *
   * @param notHoveredFill The fill color of the region when it isn't being hovered over.
   * @param isHoveredFill The fill color of the region when it is being hovered over.
   * @param region The region to apply the transition to.
   */
  public static Region applyHoverFillAnimation(
      Color notHoveredFill, Color isHoveredFill, Region region) {
    final BackgroundFill regionBackgroundFill = region.getBackground().getFills().get(0);

    final Background notHoveredBackground =
        new Background(
            new BackgroundFill(
                notHoveredFill, regionBackgroundFill.getRadii(), regionBackgroundFill.getInsets()));
    final Background isHoveredBackground =
        new Background(
            new BackgroundFill(
                isHoveredFill, regionBackgroundFill.getRadii(), regionBackgroundFill.getInsets()));

    region.setBackground(notHoveredBackground);

    region.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          region.setBackground(isHoveredBackground);
          event.isConsumed(); // For VSCode higlighting.
        });
    region.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          region.setBackground(notHoveredBackground);
          event.isConsumed(); // For VSCode higlighting.
        });

    return region;
  }

  /**
   * Applies a basic on-hover background color change animation to the {@link Shape shape}.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER SHAPE}
   *
   * @param notHoveredFill The fill color of the shape when it isn't being hovered over.
   * @param isHoveredFill The fill color of the shape when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param shape The shape to apply the transition to.
   */
  public static Shape applyHoverFillAnimation(
      Color notHoveredFill, Color isHoveredFill, Duration duration, Shape shape) {
    final FillTransition fillTransition = new FillTransition(duration);
    fillTransition.setShape(shape);
    fillTransition.setFromValue(notHoveredFill);
    fillTransition.setToValue(isHoveredFill);

    shape.setFill(notHoveredFill);

    shape.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          fillTransition.setRate(1);
          fillTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    shape.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          fillTransition.setRate(-1);
          fillTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    return shape;
  }

  /**
   * Applies a basic on-hover background color change animation to the {@link Shape shape}.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER SHAPE}
   *
   * @param notHoveredFill The fill color of the shape when it isn't being hovered over.
   * @param isHoveredFill The fill color of the shape when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param eventListener The node to act as the reciever for the hovering.
   * @param shape The shape to apply the transition to.
   */
  public static Shape applyHoverFillAnimation(
      Color notHoveredFill,
      Color isHoveredFill,
      Duration duration,
      Node eventListener,
      Shape shape) {
    final FillTransition fillTransition = new FillTransition(duration);
    fillTransition.setShape(shape);
    fillTransition.setFromValue(notHoveredFill);
    fillTransition.setToValue(isHoveredFill);

    shape.setFill(notHoveredFill);

    eventListener.addEventHandler(
        MouseEvent.MOUSE_ENTERED,
        event -> {
          fillTransition.setRate(1);
          fillTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });
    eventListener.addEventHandler(
        MouseEvent.MOUSE_EXITED,
        event -> {
          fillTransition.setRate(-1);
          fillTransition.play();
          event.isConsumed(); // For VSCode higlighting.
        });

    return shape;
  }

  /**
   * Applies a basic on-hover background color change animation to the {@link Region shape}.
   *
   * <p>{@code THIS SHOULD ONLY BE CALLED ONCE PER NODE}
   *
   * <p>This method creates a shape object right behind the node with the same
   * width/height/position. Then, it calls {@link Styles#applyHoverFillAnimation(Color, Color,
   * Duration, Node, Shape)} with the shape.
   *
   * @param notHoveredFill The fill color of the background when it isn't being hovered over.
   * @param isHoveredFill The fill color of the background when it is being hovered over.
   * @param duration The amount of time the transition takes(After the mouse hovers over it).
   * @param region The region to apply the transition to.
   * @return A stackpane containing the region and the background shape.
   */
  public static StackPane applyHoverFillAnimation(
      Color notHoveredFill, Color isHoveredFill, Duration duration, Region region) {

    final Rectangle background = new Rectangle(region.getWidth(), region.getHeight());
    final StackPane stackPane = new StackPane();

    stackPane.getChildren().addAll(background, region);

    stackPane.viewOrderProperty().bind(region.viewOrderProperty());
    background.viewOrderProperty().bind(region.viewOrderProperty().add(1));

    stackPane.visibleProperty().bind(region.visibleProperty());

    stackPane.prefWidthProperty().bind(region.prefWidthProperty());
    stackPane.prefHeightProperty().bind(region.prefHeightProperty());

    background.widthProperty().bind(region.prefWidthProperty());
    background.heightProperty().bind(region.prefHeightProperty());

    background.xProperty().bind(region.layoutXProperty());
    background.yProperty().bind(region.layoutYProperty());

    background.opacityProperty().bind(region.opacityProperty());

    stackPane.clipProperty().bind(region.clipProperty());
    background.clipProperty().bind(region.clipProperty());

    background.idProperty().bind(region.idProperty().concat(" Background"));
    stackPane.idProperty().bind(region.idProperty().concat(" StackPane"));

    background.setBlendMode(BlendMode.SRC_ATOP);
    stackPane.setAlignment(Pos.CENTER);

    Styles.applyHoverFillAnimation(notHoveredFill, isHoveredFill, duration, region, background);

    return stackPane;
  }
}
