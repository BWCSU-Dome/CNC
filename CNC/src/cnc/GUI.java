package cnc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class GUI extends Application{

	private Stage primaryStage;
	private Font font = new Font("Arial",17);
	private Font fontBold = new Font("Arial BOLD" ,17);
	public static Pane arbeitsF;
	private static int hight = 1050;
	public static Circle Kopf;
	private static Button kuehlmitBtn;
	private static Label  kuehlmit, spindelStatus,geschwin,aktuellX,aktuellY;
	private static TextArea console;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
			primaryStage.setHeight(1050);
			primaryStage.setWidth(1950);
			primaryStage.setTitle("Computerized Numerical Control");
		
		//Das ist die WurzelGruppe
		HBox rootHBox = new HBox();
		
		//Das ist die gesamte Gruppe der "Menu" Elemente
		VBox steuerungsVBox = new VBox();
		
			//Das ist die Gruppe der Statusinfos
			HBox statsHBox = new HBox();
				statsHBox.setPrefSize(550, 300);
				statsHBox.setAlignment(Pos.TOP_LEFT);
			
				//linke Hälfte der stats H-Box
				VBox statsLeftVBox = new VBox();
					statsLeftVBox.setSpacing(20);
					
					//Das ist die Überschrift
					Label statusInfos = new Label("Statusinfos");
						statusInfos.setFont(new Font("Arial BOLD",30));
						statusInfos.setUnderline(true);
					statsLeftVBox.getChildren().add(statusInfos);
				
					
					Label aktuellePos = new Label("aktuelle Postion: ");
						aktuellePos.setFont(fontBold);
					statsLeftVBox.getChildren().add(aktuellePos);
					
					//Anzeige des Spindelstatus
					Label spindelStatus_static = new Label("Spindelstatus:");
						spindelStatus_static.setFont(fontBold);
					statsLeftVBox.getChildren().add(spindelStatus_static);
					
					//Anzeige der DrehRichtung
					Label drehRichtung_static = new Label("DrehRichtung:");
						drehRichtung_static.setFont(fontBold);
					statsLeftVBox.getChildren().add(drehRichtung_static);

					//Anzeige Kühlmittelstatus
					Label kuehlmit_static = new Label("Kühlmittelstatus:");
						kuehlmit_static.setFont(fontBold);
					statsLeftVBox.getChildren().add(kuehlmit_static);

					//Anzeige Geschwindigkeit
					Label geschwin_static = new Label("Geschwindigkeit:");
					geschwin_static.setFont(fontBold);
					statsLeftVBox.getChildren().add(geschwin_static);
					
				statsHBox.getChildren().add(statsLeftVBox);
					
				VBox statsRightVBox = new VBox();
					statsRightVBox.setSpacing(20);
					
					//Hbox für die Anzeige der X und Y Position
					HBox posHBox = new HBox();
						posHBox.setSpacing(20);
						
						Label aktuellX_static = new Label("X:");
							aktuellX_static.setFont(font);
						aktuellX = new Label("0000");
							aktuellX.setFont(font);
						Label aktuellY_static = new Label("Y:");
							aktuellY_static.setFont(font);
						aktuellY = new Label("0000");
							aktuellY.setFont(font);

						posHBox.getChildren().addAll(aktuellX_static,aktuellX,aktuellY_static,aktuellY);
						VBox.setMargin(posHBox, new Insets(58,0,0,0));
						
					statsRightVBox.getChildren().add(posHBox);
						
						
					spindelStatus = new Label("aktiv");
						spindelStatus.setFont(font);
					statsRightVBox.getChildren().add( spindelStatus);
					
					Label drehRichtung = new Label("Rechtsdrehung");
						drehRichtung.setFont(font);
					statsRightVBox.getChildren().addAll(drehRichtung);

					kuehlmit= new Label(Main.getKuehlungAktiv());
						kuehlmit.setFont(font);
					statsRightVBox.getChildren().addAll(kuehlmit);
					
					//HBox für Geschwindigkeit plus Einheit;
					HBox geschHBox = new HBox();
						geschHBox.setSpacing(5);
						geschwin = new Label("");
						setGeschwindigkeit();
							geschwin.setFont(font);
						Label geschwinEinheit = new Label("m/min");
							geschwinEinheit.setFont(font);
						geschHBox.getChildren().addAll(geschwin, geschwinEinheit);
						
					statsRightVBox.getChildren().add(geschHBox);
						
				statsHBox.getChildren().add(statsRightVBox);
				
			steuerungsVBox.getChildren().add(statsHBox);
			
			//Das ist die Gruppe der Buttons und Console
			HBox ctrlBtnHBox = new HBox();
				ctrlBtnHBox.setPrefSize(550, 750);
				
				//Das ist die Gruppe der Console
				VBox ctrlVBox = new VBox();
					ctrlVBox.setPrefSize(350, ctrlBtnHBox.getPrefHeight());
					ctrlVBox.setSpacing(13);	
						
						//Anzeige der Console
							console = new TextArea("N10 M03\r\n" + 
															"N20 M08\r\n" + 
															"N30 G01 X0 Y0\r\n" + 
															"N40 G02 X0 Y10 I0 J5\r\n" + 
															"N50 G01 X10 Y10\r\n" + 
															"N60 G02 X10 Y0 I0 J-5\r\n" + 
															"N70 G28\r\n" + 
															"N80 M00");
							console.setPrefSize(ctrlVBox.getPrefWidth(), ctrlVBox.getPrefHeight());
							console.setFont(new Font("Arial", 17));
							console.setEditable(false);
		
						ctrlVBox.getChildren().add(console);
						
						//Eingabefeld
						
						TextField eingabe_TF = new TextField("Geben Sie hier Ihre Codes ein");
							eingabe_TF.setPrefWidth(ctrlVBox.getPrefWidth());
							
							//Diese Methode dient dazu, dass das EingabeFeld beim ersten aktivieren geleert wird. 
							eingabe_TF.setOnMousePressed((EventHandler<Event>) new EventHandler<>() {
								@Override
								public void handle(Event arg0) {
									if(eingabe_TF.getText().equals("Geben Sie hier Ihre Codes ein") ) {
										eingabe_TF.setText("");
									}
								}
							});
							eingabe_TF.setFont(new Font("Arial", 17));
		
						ctrlVBox.getChildren().add(eingabe_TF);
						VBox.setMargin(eingabe_TF, new Insets(10,2,15,2));
						
				ctrlBtnHBox.getChildren().add(ctrlVBox);
			
				//Das ist die Gruppe der Buttons
				VBox btnVBox = new VBox();
					btnVBox.setPrefSize(200, ctrlBtnHBox.getPrefWidth());
					btnVBox.setSpacing(30);
					btnVBox.setAlignment(Pos.CENTER);
					
					int btn_width = 170;
					int btn_height = Integer.MAX_VALUE;
					
						Button startBtn = new Button("Start");
							startBtn.setPrefSize(btn_width, btn_height);
							startBtn.setFont(fontBold);
							
							//Dieser Button startet/pausiert die bereits eingebenen Codes
							startBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
								
								@Override
								public void handle(ActionEvent arg0) {
									switch(startBtn.getText()) {
									case "Start":
										startBtn.setText("Pause");
										LineAnimation.line(200, 500);
										break;
									case "Pause":
										startBtn.setText("Start");
										CircleAnimation.kreis(0,200,0,100);
										break;
									}						
								}
							});
							
						btnVBox.getChildren().add(startBtn);

						Button notStopBtn = new Button("Not - Stop");
							notStopBtn.setStyle("-fx-background-color: black; -fx-text-fill: red");
							notStopBtn.setFont(new Font("Arial BOLD" ,22));
							notStopBtn.setPrefSize(btn_width, btn_height);
							
							//Diese Aktionen dienen dazu, dass der Button seine Farbe beim Betreten/Verlassen der Maus ändert
							notStopBtn.setOnMouseEntered((EventHandler<Event>) new EventHandler<>() {
								
								@Override
								public void handle(Event arg0) {
									notStopBtn.setStyle("-fx-background-color: red; -fx-text-fill: black");
								}
							});
							notStopBtn.setOnMouseExited((EventHandler<Event>) new EventHandler<>() {
								@Override
								public void handle(Event arg0) {
									notStopBtn.setStyle("-fx-background-color: black; -fx-text-fill: red");
								}
							});
							
						btnVBox.getChildren().add(notStopBtn);
						
						kuehlmitBtn = new Button("Kühlmittel\naktivieren");
							kuehlmitBtn.setPrefSize(btn_width, btn_height);
							kuehlmitBtn.setFont(fontBold);
							
							//Das dient dazu, dass das Kühlmittel de/aktivert werden kann
							kuehlmitBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									switch(kuehlmitBtn.getText()) {
									case "Kühlmittel\naktivieren":
										Main.setKuehlungAktiv(true);						
										break;
									case "Kühlmittel\ndeaktivieren":
										Main.setKuehlungAktiv(false);
										break;
									}
								}
							});
							
						btnVBox.getChildren().add(kuehlmitBtn);
						
						
						
						// Dieser Button dient dazu, dass der eingegebne String in einen Code zerlegt wird.
						Button addCodeBtn = new Button("Code\nhinzufügen");
							addCodeBtn.setPrefSize(btn_width, btn_height);
							addCodeBtn.setFont(fontBold);
							
							addCodeBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent arg0) {
									System.out.println(eingabe_TF.getText());
									eingabe_TF.setText("");	
								}
							});
							
							
						btnVBox.getChildren().add(addCodeBtn);
						
						Button clearBtn = new Button("Anwendung\nbeenden"); /// Im Moment Fenter schließen
							clearBtn.setPrefSize(btn_width, btn_height);
							clearBtn.setFont(fontBold);
							
							clearBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	
								@Override
								public void handle(ActionEvent arg0) {
									primaryStage.close();
								}
							});
							
						btnVBox.getChildren().add(clearBtn);
						VBox.setMargin(clearBtn, new Insets(0,0,15,0));
						
						
				ctrlBtnHBox.getChildren().add(btnVBox);
			
			steuerungsVBox.getChildren().add(ctrlBtnHBox);
			
		rootHBox.getChildren().add(steuerungsVBox);
		
		//Das ist die Arbeitsfläche
		arbeitsF = new Pane();
			arbeitsF.setMinSize(1400, 1050);
			arbeitsF.setMaxSize(1400, 1050);
			arbeitsF.setStyle("-fx-background-color: grey;");
			Circle HomePos = new Circle(0,1050 - 0,7, Color.GREEN);
			HomePos.toFront();
			Kopf = new Circle(HomePos.getCenterX(),HomePos.getCenterY(),HomePos.getRadius(),Color.RED);
			
			arbeitsF.getChildren().add(HomePos);
		rootHBox.getChildren().add(arbeitsF);

		
		
		Scene scene = new Scene(rootHBox);
		primaryStage.setScene(scene);


		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void addToAF(Node node) {
		arbeitsF.getChildren().add(node);
	}
	
	public static int getHight() {
		return hight;
	}
	
	/**Eine Methode um die Textfelder und Buttons in der GUI zu steuern.
	 * 
	 * @param wert Boolean Angabe, ob die Kühlung aktiv sein soll
	 */
	public static void setKuehlung(boolean wert) {
		if(wert) {
			kuehlmitBtn.setText("Kühlmittel\ndeaktivieren");
			kuehlmit.setText("aktiviert");
		}else {
			kuehlmitBtn.setText("Kühlmittel\naktivieren");
			kuehlmit.setText("deaktiviert");
		}
	}
	/** Diese Methode nimmt die aktuelle Geschwindigkeit 
	 * 	und aktualisert die GUI 
	 *  Es wird auch dirket in Meter / min umgerechnet
	 */
	public static void setGeschwindigkeit() {
		geschwin.setText(String.valueOf(Main.getAktGeschw()*60/1000));
	}
	
	public static void writeConsole(String arg) {
		console.setText(arg);
	}
	public static void setYLabel(double y) {
		aktuellY.setText(String.valueOf(y));
	}
	public static void setXLabel(double x) {
		aktuellX.setText(String.valueOf(x));
	}
}
