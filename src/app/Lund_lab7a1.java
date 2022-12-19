package app;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/**
 * Alec Lund
 * Date Written: 4/19/20 *I know its incredibly late and I'm sorry. I just didnt have any time whatsoever to get to it yet.
 * Desc: A white canvas in which left clicks will add circles and right clicking on a circle will remove it.
 */

public class Lund_lab7a1 extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage)
  {
    AnchorPane canvas = new AnchorPane(); // Anchor pane for flexibility
    canvas.setPrefSize(500, 500);
    canvas.setOnMouseClicked(e -> { // When the canvas/background is clicked CreateCircle is called.
      if(e.getButton() == MouseButton.PRIMARY)
      {
        CreateCircle(e.getX(), e.getY(), canvas);
      }
    });
    Scene mainScene = new Scene(canvas);
    primaryStage.setScene(mainScene);
    primaryStage.setTitle("Circle Fun");
    primaryStage.show();
  }
  
  public void CreateCircle(double x, double y, Pane pane) // Implements removal method in here.
  {
      Circle c = new Circle(5);
      c.setOnMouseClicked(e -> {
        if(e.getButton() == MouseButton.SECONDARY) // Removes the circle if it is right clicked
        {
          pane.getChildren().remove(c);
        }
      });
      c.setCenterX(x);
      c.setCenterY(y);
      c.setStroke(Color.WHITE);
      c.setFill(Color.BLACK);
      pane.getChildren().add(c);
  }









  /**
  //  * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) 
  { 
    launch(args);
  }
}
