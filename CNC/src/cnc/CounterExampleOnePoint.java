package cnc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CounterExampleOnePoint extends Application {

    private static final int MAX_COUNT = 100;
    private Map<Label, Integer> counters;
//    private Map<Circle, Boolean> circles;
    private ArrayList<Circle> circles;
    private Pane cirPane;
    private double xtemp, ytemp;
    @Override public void start(Stage stage) throws Exception {
    	stage.setHeight(700);
    	stage.setWidth(1000);
//        counters = new HashMap<>();
//        circles = new HashMap<>();
    	circles = new ArrayList<Circle>();
        cirPane = new Pane();
        cirPane.setLayoutX(100);
        cirPane.setPrefSize(600, 600);
        Button btnCir = new Button("ZeichneKreis");
        btnCir.setOnAction(e-> {new Circle(300,300,7.5);for(int w = 0; w < 360; w += 1) { System.out.println(w);;addCir(w);}update();});
        
        Pane root = new Pane(cirPane,btnCir);
        
//        stage.setScene(new Scene(new ScrollPane(root),250,200));
        stage.setScene(new Scene(root));
        stage.show();
        
        
    }
	public static void main(String[] args) {
		launch();
	}
    private void addCir(int winkel) {
  
    	xtemp = (300 + 200*Math.cos(Math.toRadians(winkel)));
		ytemp = (300 + 200*Math.sin(Math.toRadians(winkel)));
    	Circle cir = new Circle(xtemp,ytemp,7.5);     
        cir.setFill(Color.BLACK);
        cir.setVisible(false);
        circles.add(cir);
        cirPane.getChildren().add(cir);
    }
	
	private static int stueck = 0 ;
	private static int intervalle = 360;
	
	private void updateCirs() {
			
		if((stueck-1)> 0 && (intervalle - stueck)>1) {
			Circle temp2 = circles.get(stueck-1);
			temp2.setFill(Color.BLUE);
			temp2.setStroke(Color.BLUE);
			circles.set(stueck-1, temp2);
			temp2.setVisible(true);
			System.out.println(temp2 + "Blau");
		}
		Circle temp = circles.get(stueck);
		temp.setVisible(true);
		
		
		
		circles.set(stueck, temp);
		System.out.println(temp);
		stueck++;
	}
	
	private void update() {

        PauseTransition pause = new PauseTransition(Duration.seconds(0.05));
        pause.setOnFinished(event ->{
        	if(stueck < intervalle) {
        		stueck++;
        		updateCirs();
                pause.play();
        	}
            
        });
        pause.play();
    }
}
