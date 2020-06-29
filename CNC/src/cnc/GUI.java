package cnc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUI extends Application{
	
	private Stage primaryStage;
	private Font font = new Font("Arial",17);
	private Font fontBold = new Font("Arial BOLD" ,17);
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
			primaryStage.setHeight(1050);
			primaryStage.setWidth(1950);
			primaryStage.setTitle("Computerized Numerical Control");
		
		//Das ist die WurzelGruppe
		HBox rootHBox = new HBox();

		//Das ist die Gruppe der InfoAnzeige
		VBox ctrlVBox = new VBox();
			ctrlVBox.setPrefSize(350, 1050);
			ctrlVBox.setSpacing(13);	
				Label statusInfos = new Label("Statusinfos");
					statusInfos.setFont(new Font("Arial BOLD",30));
				ctrlVBox.getChildren().add(statusInfos);
				
				Label aktuellePos = new Label("aktuelle Postion: ");
					aktuellePos.setFont(fontBold);
				ctrlVBox.getChildren().add(aktuellePos);
				
				//Hbox für die Anzeige der X und Y Position
				HBox posHBox = new HBox();
					posHBox.setPrefWidth(ctrlVBox.getWidth());
					posHBox.setSpacing(20);
					
					Label aktuellX_static = new Label("X:");
						aktuellX_static.setFont(font);
					Label aktuellX = new Label("0000");
						aktuellX.setFont(font);
					Label aktuellY_static = new Label("Y:");
						aktuellY_static.setFont(font);
					Label aktuellY = new Label("0000");
						aktuellY.setFont(font);
				
					posHBox.getChildren().addAll(aktuellX_static,aktuellX,aktuellY_static,aktuellY);
					
				ctrlVBox.getChildren().add(posHBox);
			
				//Anzeige des Spindelstatus
				Label spindelStatus_static = new Label("Spindelstatus:");
					spindelStatus_static.setFont(fontBold);
				Label spindelStatus = new Label("aktiv");
					spindelStatus.setFont(font);
				ctrlVBox.getChildren().addAll(spindelStatus_static, spindelStatus);
				
				//Anzeige der DrehRichtung
				Label drehRichtung_static = new Label("DrehRichtung:");
					drehRichtung_static.setFont(fontBold);
				Label drehRichtung = new Label("Rechtsdrehung");
					drehRichtung.setFont(font);
				ctrlVBox.getChildren().addAll(drehRichtung_static, drehRichtung);
				
				//Anzeige Kühlmittelstatus
				Label kuehlmit_static = new Label("Kühlmittelstatus:");
					kuehlmit_static.setFont(fontBold);
				Label kuehlmit= new Label("aktiv");
					kuehlmit.setFont(font);
				ctrlVBox.getChildren().addAll(kuehlmit_static, kuehlmit);
				
				//Anzeige Geschwindigkeit
				Label geschwin_static = new Label("Geschwindigkeit:");
				geschwin_static.setFont(fontBold);
				
					//HBox für Geschwindigkeit plus Einheit;
					HBox geschHBox = new HBox();
						geschHBox.setSpacing(5);
						Label geschwin = new Label("7373");
							geschwin.setFont(font);
						Label geschwinEinheit = new Label("m/min");
							geschwinEinheit.setFont(font);
						geschHBox.getChildren().addAll(geschwin, geschwinEinheit);
					
				ctrlVBox.getChildren().addAll(geschwin_static, geschHBox);
				
				//Anzeige der Console
					TextArea console = new TextArea("Hallooooooooooooooooo\noooooooooooooooooo\nooooooooooooooooooo\noooooooooooooooooo\noooooooooooooooo\noooooooo\nooooooo");
					console.setPrefSize(ctrlVBox.getPrefWidth(), ctrlVBox.getPrefHeight());
					console.setFont(new Font("Arial", 17));
					console.setEditable(false);

				ctrlVBox.getChildren().add(console);
				
				//Eingabefeld
				
				TextField eingabe_TF = new TextField("Geben Sie hier Ihre Codes ein");
					eingabe_TF.setPrefWidth(ctrlVBox.getPrefWidth());
					eingabe_TF.setFont(new Font("Arial", 17));
				ctrlVBox.getChildren().add(eingabe_TF);
				VBox.setMargin(eingabe_TF, new Insets(10,2,15,2));
				
		rootHBox.getChildren().add(ctrlVBox);
		
		//Das ist die Gruppe der Buttons
		VBox btnVBox = new VBox();
			btnVBox.setPrefSize(200, 1050);
			btnVBox.setSpacing(30);
			btnVBox.setAlignment(Pos.TOP_CENTER);
			
			int btn_width = 150;
			int btn_height = 50;
			
				Button startBtn = new Button("Start");
					startBtn.setPrefSize(btn_width, btn_height);
				btnVBox.getChildren().add(startBtn);
				
//				FileInputStream fip = new FileInputStream("/C:/Users/driepl/git/CNC/CNC/files/Notstop.png");	Wäre Nice2have, aber Problem mit Dateipfad
//				Image notStopImage = new Image(fip);
//				ImageView notStopView = new ImageView(notStopImage);
				
				Button notStopBtn = new Button("NotStop" );
					notStopBtn.setPrefSize(btn_width, btn_height);
				btnVBox.getChildren().add(notStopBtn);
				

				Button kuehlmitBtn = new Button("Kühlmittel aktiv");
					kuehlmitBtn.setPrefSize(btn_width, btn_height);
				btnVBox.getChildren().add(kuehlmitBtn);
				
				Button addCodeBtn = new Button("Code hinzufügen");
					addCodeBtn.setPrefSize(btn_width, btn_height);
					btnVBox.getChildren().add(addCodeBtn);
				
				Button clearBtn = new Button("Clear Input");
					clearBtn.setPrefSize(btn_width, btn_height);
				btnVBox.getChildren().add(clearBtn);

		rootHBox.getChildren().add(btnVBox);
		
		//Das ist die Arbeitsfläche
		Pane arbeitsF = new Pane();
			arbeitsF.setMinSize(1400, 1050);
			arbeitsF.setMaxSize(1400, 1050);
			arbeitsF.setStyle("-fx-background-color: grey;");
			Circle HomePos = new Circle(0,1050,7, Color.RED);
			
			arbeitsF.getChildren().add(HomePos);
		rootHBox.getChildren().add(arbeitsF);

		
		
		Scene scene = new Scene(rootHBox);
		primaryStage.setScene(scene);


//		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
