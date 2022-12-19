package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/**
 * Alec Lund
 * Date Written: 4/21/20
 * Description: Allows for a user to draw to rectangles and will specify if the rectangles intersect anywhere.
 * Drawing more than two rectangles resets the rectangles already drawn and will draw the new one.
 */
public class Lund_lab7b2 extends Application
{
    private Pane canvas = new Pane(); // Canvas where the rectangles will be drawn
    private Label output = new Label("Output: ");
    public void start(Stage primaryStage)
    {
        Rectangle background = new Rectangle(300, 700); // Background for the canvas
        background.setFill(Color.BLACK);
        canvas.getChildren().add(background);

        VBox input = new VBox(); // Input fields and output i.e. rectangle inputs, and draw btn
        TextField xInput = new TextField("Rectangle X");
        xInput.setPrefSize(200, 100);
        xInput.alignmentProperty().set(Pos.CENTER);
        TextField yInput = new TextField("Rectangle Y");
        yInput.setPrefSize(200, 100);
        yInput.alignmentProperty().set(Pos.CENTER);
        TextField widthInput = new TextField("Rectangle Width");
        widthInput.setPrefSize(200, 100);
        widthInput.alignmentProperty().set(Pos.CENTER);
        TextField heightInput = new TextField("Rectangle Height");
        heightInput.setPrefSize(200, 100);
        heightInput.alignmentProperty().set(Pos.CENTER);
        // Output field
        output.setPrefSize(200, 200);
        output.alignmentProperty().set(Pos.TOP_LEFT);
        output.setDisable(true);
        output.setWrapText(true);

        Button drawBtn = new Button("Draw!");
        drawBtn.setPrefSize(200, 100);
        drawBtn.setOnAction(e -> {
            output.setText("Output: ");
            if(canvas.getChildren().size() == 3) // Two Rectangles are drawn
            {
                canvas.getChildren().remove(1); // remove the two rectangles
                canvas.getChildren().remove(1);
                output.setText("Output: Rectanlges reset.");
            }
            if(xInput.getText().contains("Rectangle") || yInput.getText().contains("Rectangle"))
            {
                output.setText("Output: Error. One of the fields does not have a value. Enter a correct value and try again.");
                return;
            }
            int x = 0;
            int y = 0;
            if(Integer.parseInt(xInput.getText()) > 300) { x = 300; } // if out of bounds of the canvas.
            else { x = Integer.parseInt(xInput.getText()); }
            if(Integer.parseInt(yInput.getText()) > 700) { y = 700; }
            else { y = Integer.parseInt(yInput.getText()); }
            
            int width = 0;
            int height = 0;
            if(Integer.parseInt(widthInput.getText()) > 300) { width = 300; } // If the rectangle is off the canvas
            else { width = Integer.parseInt(widthInput.getText()); }
            if(Integer.parseInt(heightInput.getText()) > 700) { height = 700; } 
            else { height = Integer.parseInt(heightInput.getText()); }

            drawRectangleFromPoints(x, y, width, height);
            if(canvas.getChildren().size() == 2) // 1st rectangle drawn
            {
                output.setText(output.getText() + "\nOutput: Rectangle 1 drawn");
            }
            else if(canvas.getChildren().size() == 3) // 2nd rectangle drawn
            {
                output.setText(output.getText() + "\nOutput: Rectangle 2 drawn");
                Rectangle rect1 = (Rectangle)canvas.getChildren().get(1); // Grab the first and second rects drawn
                Rectangle rect2 = (Rectangle)canvas.getChildren().get(2);
                if(RectangleOverlapping(rect1, rect2))
                {
                    output.setText(output.getText() + "\nRectangles Overlap!");
                }
                else
                {
                    output.setText(output.getText() + "\nRectangles are not overlapping.");
                }
            }
        });
        

        RestrictInputs(xInput, yInput, widthInput, heightInput); // Restricts the text inputs from being anything else than numbers and to certain sizes
        input.getChildren().addAll(xInput, yInput, drawBtn, widthInput, heightInput, output);

        HBox canvasLayout = new HBox(); // Layout for the canvas and other things like buttons
        canvasLayout.getChildren().addAll(input, canvas);

        primaryStage.setTitle("Rectangle drawing");
        primaryStage.setScene(new Scene(canvasLayout));
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }

    public void drawRectangleFromPoints(int x, int y, int width, int height)
    {
        if(x + width > 300)
        {
            output.setText("The X Position for the rectangle will be out of bounds please try again with a different input");
            return;
        }
        if(y + height > 700)
        {
            output.setText("The Y Position for the rectangle will be out of bounds please try again with a different input");
            return;
        }
        Rectangle rect = new Rectangle(width, height);
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setFill(Color.WHITE);
        canvas.getChildren().add(rect);
    }
    static class Point
    {
        Point(int x, int y) { this.x = x; this.y = y; }
        public int x, y;
        @Override
        public String toString()
        {
            return "" + x + " " + y + "\n";
        }
    }
    public boolean RectangleOverlapping(Rectangle rect1, Rectangle rect2)
    {
        Point rect1TR = new Point((int)rect1.getLayoutX() + (int)rect1.getWidth(), (int)rect1.getLayoutY());
        Point rect1BL = new Point((int)rect1.getLayoutX(), (int)rect1.getLayoutY() + (int)rect1.getHeight());
        Point rect2TR = new Point((int)rect2.getLayoutX() + (int)rect2.getWidth(), (int)rect2.getLayoutY());
        Point rect2BL = new Point((int)rect2.getLayoutX(), (int)rect2.getLayoutY() + (int)rect2.getHeight());
        if(rect2BL.x > rect1TR.x
        || rect2BL.y < rect2TR.y
        || rect1BL.x > rect2TR.x
        || rect1BL.y < rect2TR.y)
        { return false; }

        return true; // Rectangle overlaps
    }
    public void RestrictInputs(TextField... textfield) // Max X and Max Y inclusive
    {
        for(TextField tf : textfield)
        {
            tf.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr)
                {
                    if(!newStr.matches("\\d*"))
                    {
                        String replaced = newStr.replaceAll("[^\\d]", "");
                        Platform.runLater(() -> { // Had to implement this because the listener doesnt support changing the textfield within the listener itself. :(
                            tf.clear();         // I literally tried for about two hours trying to find why this was happening...
                            tf.setText(replaced);
                        });
                    }
                    else
                    {
                    }
                }
            });
        }
    }
}