package app;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Alec Lund Date written: 4/21/20 Description: Allows for the user to
 * manipulate sliders to change the color and opacity of a circle to what they
 * want.
 */
public class Lund_lab7b1 extends Application {
    private Color circleColor = Color.rgb(0, 0, 0, 1.0);
    private Circle circle = new Circle(20);
    // Initializing all the sliders
    private Text redText = new Text("Red: 0");
    private Text greenText = new Text("Green: 0");
    private Text blueText = new Text("Blue: 0");
    private Text alphaText = new Text("Opacity: 100.00%");
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        circle.setLayoutX(350);
        circle.setLayoutY(150);

        AnchorPane ap = new AnchorPane(); // Main Window Pane
        ap.setPrefSize(600,300);

        VBox sliderControls = new VBox(10); // A box to contain all the sliders

        

        Slider[] sliders = makeSliders();
        // All the color boxes
        HBox rBox = makeColorBox(sliders[0], redText);
        HBox bBox = makeColorBox(sliders[1], greenText);
        HBox gBox = makeColorBox(sliders[2], blueText);
        HBox aBox = makeColorBox(sliders[3], alphaText);

        sliderControls.getChildren().addAll(rBox, bBox, gBox, aBox);
        ScrollPane sp = new ScrollPane(); // Scroll pane for the sliders.
        sp.setContent(sliderControls);
        sp.setPrefSize(150, 300);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        sp.setHvalue(sp.getHmin());
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);

        ap.getChildren().addAll(sp, circle);
        primaryStage.setTitle("Play around with sliders to control circle color");
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
        
    }
    public void updateCircleColor(Color newColor) // Updates the cirlces color.
    {
        circleColor = newColor;
        circle.setFill(circleColor);
        circle.setStroke(circleColor);
    }
    public HBox makeColorBox(Slider s, Text t)
    {
        HBox box = new HBox(15, t, s);
        box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        return box;
    }
    public Slider[] makeSliders() // Initializes Sliders and returns a slider array in RGBA format with a size of 4
    {
        Slider red = new Slider();
        Slider green = new Slider();
        Slider blue = new Slider();
        Slider alpha = new Slider();
        alpha.valueProperty().set(100.0); // Sets the opacity to 100 by default
        // Add Event listeners for the slider values then convert them to match 0-255 for RGB and 0-1.0 for alpha.
        red.valueProperty().addListener(e -> { // Controls Red
            double value = ((DoubleProperty)e).get();
            int valueToInt = (int)(value / 100 * 255); // Converts the slider value into a range 0 - 255 for RGB
            redText.setText("Red: " + valueToInt);
            Color newColor = Color.rgb(valueToInt, (int)(circleColor.getGreen() * 255), (int)(circleColor.getBlue() * 255), circleColor.getOpacity());
            updateCircleColor(newColor);
        });
        green.valueProperty().addListener(e -> { // Controls Green
            double value = ((DoubleProperty)e).get();
            int valueToInt = (int)(value / 100 * 255); // Converts the slider value into a range 0 - 255 for RGB
            greenText.setText("Green: " + valueToInt);
            Color newColor = Color.rgb((int)(circleColor.getRed() * 255), valueToInt, (int)(circleColor.getBlue() * 255), circleColor.getOpacity());
            updateCircleColor(newColor);
        });
        blue.valueProperty().addListener(e -> { // Controls Blue
            double value = ((DoubleProperty)e).get();
            int valueToInt = (int)(value / 100 * 255); // Converts the slider value into a range 0 - 255 for RGB
            blueText.setText("Blue: " + valueToInt);
            Color newColor = Color.rgb((int)(circleColor.getRed() * 255), (int)(circleColor.getGreen() * 255), valueToInt, circleColor.getOpacity());
            updateCircleColor(newColor);
        });
        alpha.valueProperty().addListener(e -> { // Controls Opacity
            double value = ((DoubleProperty)e).get();
            alphaText.setText(String.format("Opacity: %.2f%%", value));
            value = value / 100.0;
            Color newColor = Color.rgb((int)(circleColor.getRed() * 255), (int)(circleColor.getGreen() * 255), (int)(circleColor.getBlue() * 255), value);
            updateCircleColor(newColor);
        });
        return new Slider[] {red,green,blue,alpha};
    }
}



