package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/**
 * Alec Lund
 * Date Written: 4/19/20
 * Desc: A blank canvas that a user can use to draw lines using the arrows keys on a keyboard.
 */
public class Lund_lab7a2 extends Application
{
    public void start(Stage primaryStage)
    {
        Point currentPosition = new Point(); // The Current position of the line
        currentPosition.setX(150);
        currentPosition.setY(150);

        
        
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(300, 300);
        Scene mainScene = new Scene(ap);
        mainScene.setOnKeyPressed(e -> {
            KeyCode kc = e.getCode();
            ExtendLine(kc, ap, currentPosition);
        });


        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Line Fun! Use arrow keys to draw and extend a line!");
        primaryStage.show();
    }

    public void ExtendLine(KeyCode kc, Pane edge, Point p) // Extends the line on an arrow key press
    {
        double x = p.getX();
        double y = p.getY();
        if(x > 300) { p.setX(300); } // The point has gone off the screen if any of these conditions are met
        if(x < 0) { p.setX(0); } //
        if(y > 300) { p.setY(300); } //
        if(y < 0) { p.setY(0); } //


        Circle c = new Circle(5); // Uses a cirlce in small increments in any direction to simulate lines being drawn
        c.setFill(Color.BLACK);
        c.setStroke(Color.BLACK);
        switch(kc)
        {
            case LEFT:
                p.setX(p.getX() - 2.5);
                SetCirclePosToPoint(c, p);
                edge.getChildren().add(c);
            break;
            case RIGHT:
                p.setX(p.getX() + 2.5);
                SetCirclePosToPoint(c, p);
                edge.getChildren().add(c);
            break;
            case UP:
                p.setY(p.getY() - 2.5);
                SetCirclePosToPoint(c, p);
                edge.getChildren().add(c);
            break;
            case DOWN:
                p.setY(p.getY() + 2.5);
                SetCirclePosToPoint(c, p);
                edge.getChildren().add(c);
            break;
            default:
            break;
        }
    }
    public void SetCirclePosToPoint(Circle c, Point p) // Sets a given circle to a given point
    {
        c.setCenterX(p.getX());
        c.setCenterY(p.getY());
        return;
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}