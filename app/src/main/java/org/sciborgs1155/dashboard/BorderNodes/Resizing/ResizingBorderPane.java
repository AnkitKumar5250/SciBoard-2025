package org.sciborgs1155.dashboard.BorderNodes.Resizing;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/** A {@link BorderPane} with {@link Pane 4 corners and 4 sides} that listen for mouse movements and resize the specified {@link Region region}. */
public class ResizingBorderPane extends BorderPane {
    /** The X-Coordinate of the mouse on the screen. */
    private final DoubleProperty mouseXPosition = new SimpleDoubleProperty(this,"Mouse X-Position",0);

    /** The Y-Coordinate of the mouse on the screen. */
    private final DoubleProperty mouseYPosition = new SimpleDoubleProperty(this,"Mouse Y-Position",0);

    /** 
     * The Y-Coordinate of the mouse on when it was first pressed down. 
     * Used for detecting the drag amounts. 
     */
    private final DoubleProperty mouseResizeStartedXPosition = new SimpleDoubleProperty(this,"Mouse Resize Started X-Position",0);

    /** 
     * The Y-Coordinate of the mouse on when it was first pressed down. 
     * Used for detecting the drag amounts.
     */
    private final DoubleProperty mouseResizeStartedYPosition = new SimpleDoubleProperty(this,"Mouse Resize Started Y-Position",0);

    /** The Y-Translation of the mouse since the last resize started. */
    private final DoubleProperty mouseResizeXTranslation = new SimpleDoubleProperty(this, "Mouse Resize X-Translation", 0);

    /** The X-Translation of the mouse since the last resize started. */
    private final DoubleProperty mouseResizeYTranslation = new SimpleDoubleProperty(this, "Mouse Resize Y-Translation", 0);

    /** 
     * The width of the window on when it the mouse was first pressed down. 
     * Used for calculating window resize. {@link Property#bind Bound} in {@link #ResizingBorderPane() the constructor}. 
     */
    private final DoubleProperty windowResizeStartedWidth = new SimpleDoubleProperty(this,"Window Resize Started Width",0);

    /** 
     * The height of the window on when it the mouse was first pressed down. 
     * Used for calculating window resize.
     */
    private final DoubleProperty windowResizeStartedHeight = new SimpleDoubleProperty(this,"Window Resize Started Height",0);

    /** 
     * The X-Position of the window on when it the mouse was first pressed down. 
     * Used for calculating window motion. 
     */
    private final DoubleProperty windowResizeStartedXPosition = new SimpleDoubleProperty(this,"Window Resize Started X-Position",0);

    /** 
     * The Y-Position of the window on when it the mouse was first pressed down. 
     * Used for calculating window motion.  
     */
    private final DoubleProperty windowResizeStartedYPosition = new SimpleDoubleProperty(this,"Window Resize Started Y-Position",0);

    /** The width of the window.  */
    private final DoubleProperty windowWidth = new SimpleDoubleProperty(this,"Window Width",0);

    /** The height of the window. */
    private final DoubleProperty windowHeight = new SimpleDoubleProperty(this,"Window Height",0);

    /** The minimum width of the window. */
    private final DoubleProperty windowMinimumWidth = new SimpleDoubleProperty(this,"Window Minimum Width",0);

    /** The minimum height of the window. */
    private final DoubleProperty windowMinimumHeight = new SimpleDoubleProperty(this,"Window Minimum Height",0);

    /** Length of center panes(controls size of corner panes too). */
    public final DoubleProperty borderLength = new SimpleDoubleProperty(this, "Border Length", 0.9);

    /** Thickness of the panes. */
    public final DoubleProperty paneThickness = new SimpleDoubleProperty(this, "Pane Thickness", 5);

    /** Whether the window width is less than or equal to the minimum width. */
    private final BooleanProperty isAtMinimumWidth = new SimpleBooleanProperty(this, "Window At Minimum Width", false);

    /** Whether the window height is less than or equal to the minimum height. */
    private final BooleanProperty isAtMinimumHeight = new SimpleBooleanProperty(this, "Window At Minimum Height", false);

    /** The border of the center-panes(usually made opaque for debugging) */
    public final Property<Border> centerPaneBorder = new SimpleObjectProperty<Border>(this, "Center Pane Border Color", Border.stroke(Color.TRANSPARENT));

    /** The border of the corner-panes(usually made opaque for debugging) */
    public final Property<Border> cornerPaneBorder = new SimpleObjectProperty<Border>(this, "Corner Pane Border Color", Border.stroke(Color.TRANSPARENT));

    /** Pane that lines the top. Contains {@link #topLeftCorner the top left listener} and {@link #topRightCorner the top right listener}. */
    private final HBox topPane;

    /** Pane listening for top-of-window height resizing. */
    private final Pane topCenter;

    /** Pane listening for top-left rezising motions. */
    private final Pane topLeftCorner;

    /** Pane listening for top-right rezising motions. */
    private final Pane topRightCorner;

    /** Pane that lines the bottom. Contains {@link #bottomLeftCorner the bottom left listener} and {@link #bottomRightCorner the bottom right listener}. */
    private final HBox bottomPane;

    /** Pane listening for bottom-of-window height resizing. */
    private final Pane bottomCenter;

    /** Pane listening for bottom-left rezising motions. */
    private final Pane bottomLeftCorner;

    /** Pane listening for bottom-right rezising motions. */
    private final Pane bottomRightCorner;

    /** Pane listening for left-of-window width resizing. */
    private final Pane leftCenter;

    /** Pane listening for right-of-window width resizing. */
    private final Pane rightCenter;

    public ResizingBorderPane(Region region) {
        windowWidth.bind(region.widthProperty());
        windowHeight.bind(region.heightProperty());

        windowMinimumWidth.bind(region.minWidthProperty());
        windowMinimumHeight.bind(region.minHeightProperty());

        isAtMinimumWidth.bind(windowWidth.lessThanOrEqualTo(windowMinimumWidth.add(5)));
        isAtMinimumHeight.bind(windowHeight.lessThanOrEqualTo(windowMinimumHeight.add(5)));

        mouseResizeXTranslation.bind(mouseXPosition.subtract(mouseResizeStartedXPosition));
        mouseResizeYTranslation.bind(mouseYPosition.subtract(mouseResizeStartedYPosition));

        this.setOnMouseMoved(mouse -> {
            mouseXPosition.setValue(mouse.getScreenX());
            mouseYPosition.setValue(mouse.getScreenY());
        });

        this.setOnMouseDragged(mouse -> {
            mouseXPosition.setValue(mouse.getScreenX());
            mouseYPosition.setValue(mouse.getScreenY());
        });

        this.setOnMousePressed(mouse -> {
            mouseResizeStartedXPosition.setValue(mouse.getScreenX());
            mouseResizeStartedYPosition.setValue(mouse.getScreenY());

            windowResizeStartedWidth.setValue(region.getWidth());
            windowResizeStartedHeight.setValue(region.getHeight());

            windowResizeStartedXPosition.setValue(region.getLayoutX());
            windowResizeStartedYPosition.setValue(region.getLayoutY());
        });

        topCenter = new Pane();
        topLeftCorner = new Pane();
        topRightCorner = new Pane();
        topPane = new HBox();

        this.configureTopPane(region);

        bottomCenter = new Pane();
        bottomLeftCorner = new Pane();
        bottomRightCorner = new Pane();
        bottomPane = new HBox();

        this.configureBottomPane(region);

        leftCenter = new Pane();
        rightCenter = new Pane();

        this.configureSidePanes(region);
    }

    /**
     * Configures the {@link #topPane}, {@link #topCenter}, {@link #topLeftCorner}, and {@link #topRightCorner} panes.
     * @param region The region to use for property bindings.
     */
    @SuppressWarnings("unused")
    private void configureTopPane(Region region) {
        this.topPane.getChildren().addAll(this.topLeftCorner,this.topCenter,this.topRightCorner);
        this.topPane.setSpacing(0);

        this.topPane.prefWidthProperty().bind(region.widthProperty());
        this.topPane.prefHeightProperty().bind(paneThickness);
        
        this.configureHorizontalCenterPane(mouse -> {
            if (windowResizeStartedHeight.subtract(mouseResizeYTranslation).greaterThan(windowMinimumHeight).get()) {
                region.setLayoutY(windowResizeStartedYPosition.add(mouseResizeYTranslation).get());
                region.setPrefHeight(windowResizeStartedHeight.subtract(mouseResizeYTranslation).get());
            }
        },this.topCenter, this.topPane, Cursor.S_RESIZE);

        this.configureCornerPane(mouse -> {
            if (windowResizeStartedWidth.subtract(mouseResizeXTranslation).greaterThan(windowMinimumWidth).get()) {
                region.setLayoutX(windowResizeStartedXPosition.add(mouseResizeXTranslation).get());
                region.setPrefWidth(windowResizeStartedWidth.subtract(mouseResizeXTranslation).get());
            }

            if (windowResizeStartedHeight.subtract(mouseResizeYTranslation).greaterThan(windowMinimumHeight).get()) {
                region.setLayoutY(windowResizeStartedYPosition.add(mouseResizeYTranslation).get());
                region.setPrefHeight(windowResizeStartedHeight.subtract(mouseResizeYTranslation).get());
            }
        },this.topLeftCorner, this.topPane, Cursor.SE_RESIZE);

        this.configureCornerPane(mouse -> {
            if (windowResizeStartedWidth.add(mouseResizeXTranslation).greaterThan(windowMinimumWidth).get()) {
                region.setPrefWidth(windowResizeStartedWidth.add(mouseResizeXTranslation).get());
            }

            if (windowResizeStartedHeight.subtract(mouseResizeYTranslation).greaterThan(windowMinimumHeight).get()) {
                region.setLayoutY(windowResizeStartedYPosition.add(mouseResizeYTranslation).get());
                region.setPrefHeight(windowResizeStartedHeight.subtract(mouseResizeYTranslation).get());
            }
        },this.topRightCorner, this.topPane, Cursor.SW_RESIZE);

        this.setTop(this.topPane);
    }

    /**
     * Configures the {@link #bottomPane}, {@link #bottomCenter}, {@link #bottomLeftCorner}, and {@link #bottomRightCorner} panes.
     * @param region The region to use for property bindings.
     */
    @SuppressWarnings("unused")
    private void configureBottomPane(Region region) {
        this.bottomPane.getChildren().addAll(this.bottomLeftCorner,this.bottomCenter,this.bottomRightCorner);
        this.bottomPane.setSpacing(0);

        this.bottomPane.prefWidthProperty().bind(region.widthProperty());
        this.bottomPane.prefHeightProperty().bind(paneThickness);
        
        this.configureHorizontalCenterPane(mouse -> {
            if (windowResizeStartedHeight.add(mouseResizeYTranslation).greaterThan(windowMinimumHeight).get()) {
                region.setPrefHeight(windowResizeStartedHeight.add(mouseResizeYTranslation).get());
            }
        },this.bottomCenter, this.bottomPane, Cursor.N_RESIZE);

        this.configureCornerPane(mouse -> {
            if (windowResizeStartedWidth.subtract(mouseResizeXTranslation).greaterThan(windowMinimumWidth).get()) {
                region.setLayoutX(windowResizeStartedXPosition.add(mouseResizeXTranslation).get());
                region.setPrefWidth(windowResizeStartedWidth.subtract(mouseResizeXTranslation).get());
            }

            if (windowResizeStartedHeight.add(mouseResizeYTranslation).greaterThan(windowMinimumHeight).get()) {
                region.setPrefHeight(windowResizeStartedHeight.add(mouseResizeYTranslation).get());
            }
        },this.bottomLeftCorner, this.bottomPane, Cursor.NE_RESIZE);

        this.configureCornerPane(mouse -> {
            if (windowResizeStartedWidth.add(mouseResizeXTranslation).greaterThan(windowMinimumWidth).get()) {
                region.setPrefWidth(windowResizeStartedWidth.add(mouseResizeXTranslation).get());
            }

            if (windowResizeStartedHeight.add(mouseResizeYTranslation).greaterThan(windowMinimumHeight).get()) {
                region.setPrefHeight(windowResizeStartedHeight.add(mouseResizeYTranslation).get());
            }
        },this.bottomRightCorner, this.bottomPane, Cursor.NW_RESIZE);

        this.setBottom(bottomPane);
    }

    /**
     * Configures the {@link #leftCenter} and {@link #rightCenter}.
     * @param region The region to use for property bindings.
     */
    @SuppressWarnings("unused")
    private void configureSidePanes(Region region) {
        this.configureSidePane(mouse -> {
            if (windowResizeStartedWidth.subtract(mouseResizeXTranslation).greaterThan(windowMinimumWidth).get()) {
                region.setLayoutX(windowResizeStartedXPosition.add(mouseResizeXTranslation).get());
                region.setPrefWidth(windowResizeStartedWidth.subtract(mouseResizeXTranslation).get());
            }
        }, leftCenter, region, Cursor.W_RESIZE);

        this.configureSidePane(mouse -> {
            if (windowResizeStartedWidth.add(mouseResizeXTranslation).greaterThan(windowMinimumWidth).get()) {
                region.setPrefWidth(windowResizeStartedWidth.add(mouseResizeXTranslation).get());
            }
        }, rightCenter, region, Cursor.E_RESIZE);

        this.setLeft(leftCenter);
        this.setRight(rightCenter);
    }

    /**
     * Generalized method for configuring all of the center panes.
     * 
     * @param onMouseDragged The resize function.
     * @param centerPane The pane to configure.
     * @param parentPane The parent pane(top, bottom, left, right).
     * @param cursor The cursor to display on hover.
     * @return The configured pane.
     */
    private Pane configureHorizontalCenterPane(EventHandler<? super MouseEvent> onMouseDragged, Pane centerPane, Pane parentPane, Cursor cursor) {
        centerPane.prefWidthProperty().bind(parentPane.widthProperty().multiply(this.borderLength));
        centerPane.prefHeightProperty().bind(parentPane.prefHeightProperty());

        centerPane.setCursor(cursor);
        centerPane.setBorder(this.centerPaneBorder.getValue());

        BorderPane.setAlignment(centerPane, Pos.CENTER);

        centerPane.setOnMouseDragged(onMouseDragged);

        return centerPane;
    }

    /**
     * Generalized method for configuring all of the side panes.
     * 
     * @param onMouseDragged The resize function.
     * @param centerPane The pane to configure.
     * @param region The region to bind properties to.
     * @param cursor The cursor to display on hover.
     * @return The configured pane.
     */
    private Pane configureSidePane(EventHandler<? super MouseEvent> onMouseDragged, Pane centerPane, Region region, Cursor cursor) {
        centerPane.prefWidthProperty().bind(this.paneThickness);
        centerPane.prefHeightProperty().bind(region.heightProperty().multiply(this.borderLength));

        centerPane.setCursor(cursor);
        centerPane.borderProperty().bind(this.centerPaneBorder);

        BorderPane.setAlignment(centerPane, Pos.CENTER);

        centerPane.setOnMouseDragged(onMouseDragged);

        return centerPane;
    }

    /**
     * Generalized method for configuring all of the corner panes.
     * 
     * @param onMouseDragged The resize function.
     * @param cornerPane The pane to configure.
     * @param parentPane The parent pane(top, bottom, left, right).
     * @param cursor The cursor to display on hover.
     * @return The configured pane.
     */
    private Pane configureCornerPane(EventHandler<? super MouseEvent> onMouseDragged, Pane cornerPane, Pane parentPane, Cursor cursor) {
        cornerPane.prefWidthProperty().bind(topPane.widthProperty().multiply(borderLength.subtract(1).negate().divide(2)));
        cornerPane.prefHeightProperty().bind(parentPane.prefHeightProperty());

        cornerPane.borderProperty().bind(this.cornerPaneBorder);
        cornerPane.setCursor(cursor);

        BorderPane.setAlignment(cornerPane, Pos.CENTER_LEFT);

        cornerPane.setOnMouseDragged(onMouseDragged);

        return cornerPane;
    }
}
