package org.sciborgs1155.dashboard;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.sciborgs1155.dashboard.Network.Server;
import org.sciborgs1155.dashboard.ScoralTab.ScoralScene;
import org.sciborgs1155.dashboard.Utils.Assets;

/** Main class for starting the app. */
public class App extends Application {
  /** Starts the window(given as an {@link Stage}). */
  @Override
  public void start(Stage stage) {
    /** Removes the default title bar */
    stage.initStyle(StageStyle.UNDECORATED);

    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);

    stage.setTitle("SciBoard 2025");
    stage.getIcons().add(Assets.getImage("SciborgIcons/sciborgDisconnected.png"));

    stage.setScene(new ScoralScene(stage));

    stage.setResizable(true);
    stage.setMaximized(true);
    stage.show();

    Network.load();
    Network.connect(Server.SIMULATION);
    Network.startNetworkThread(stage);
  }

  /**
   * Runs the program.
   *
   * @param args Arguments to run the program with.
   */
  public static void main(String[] args) {
    launch();
  }
}
