package org.sciborgs1155.dashboard.Utils;

import javafx.scene.image.Image;

/** Shortens and error-proofs asset retreiving. */
public class Assets {

  /**
   * Returns the image with the specified path (relative to the resources folder).
   *
   * @param path The path of the image.
   * @return The image.
   */
  public static Image getImage(String path) {
    try {
      return new Image(ClassLoader.getSystemResourceAsStream(path));
    } catch (Exception e) {
      System.err.println("No Image Found!");
      return new Image(ClassLoader.getSystemResourceAsStream("SciborgIcons/sciborg.png"));
    }
  }
}
