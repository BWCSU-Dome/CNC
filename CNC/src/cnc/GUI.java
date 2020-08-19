package cnc;

import java.io.FileInputStream;

import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class GUI extends Application{

	private Stage primaryStage;
	private Font font = new Font("Arial",17);
	private Font fontBold = new Font("Arial BOLD" ,17);
	public static Pane arbeitsF;
	private static int height = 1050;
	private static int width = 1950;
	public static Circle Kopf;
	private static Button kuehlmitBtn;
	private static Label  kuehlmit, spindelStatus,geschwin;
	public static Label aktuellX,aktuellY;
	private static TextArea InputConsole, OutputConsole;
	private static Timeline timelineGrafik, timelineKoordinaten;	
	private static Boolean CodeVerarbeitungStarten = true;
	public static Button startBtn;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
			primaryStage.setHeight(height);
			primaryStage.setWidth(width);
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
				
				VBox txtVBox = new VBox();
					 txtVBox.setPrefSize(350, ctrlBtnHBox.getPrefHeight());
					 txtVBox.setSpacing(15);
					 		//Anzeige der Ausgabe Console
					 
					 		OutputConsole = new TextArea("Hier steht der Output");
					 		OutputConsole.setFont(new Font("Arial", 17));
					 		OutputConsole.setBackground(new Background( new BackgroundFill( Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY ) ));					 	
					 		OutputConsole.setEditable(false);
					 		OutputConsole.setMouseTransparent(true);
					 		OutputConsole.setPrefSize(txtVBox.getPrefWidth(), 300);
					 		txtVBox.getChildren().add(OutputConsole);
					 		
							//Anzeige der EingabeConsole
							InputConsole = new TextArea( 
	//												"M03\r\n" + 
	//												"M08\r\n" + 
													"G01 X100 Y200\r\n"  +
	//												"G02 X700 Y500 I500 J0\r\n" + 
													"G01 X300 Y10\r\n" +
													"G01 X500 Y100\r\n" +
													"G01 X50 Y50\r\n" + 	
													"G28\r\n" 
													);
							InputConsole.setFont(new Font("Arial", 17));
							InputConsole.setPrefSize(txtVBox.getPrefWidth(), ctrlBtnHBox.getPrefHeight()-OutputConsole.getPrefHeight()-10);
							VBox.setMargin(InputConsole, new Insets(0,0,13,0));
					txtVBox.getChildren().add(InputConsole);
					
				ctrlBtnHBox.getChildren().add(txtVBox);
			
				//Das ist die Gruppe der Buttons
				VBox btnVBox = new VBox();
					btnVBox.setPrefSize(200, ctrlBtnHBox.getPrefWidth());
					btnVBox.setSpacing(30);
					btnVBox.setAlignment(Pos.CENTER);
					
					int btn_width = 170;
					int btn_height = Integer.MAX_VALUE;
					
						startBtn = new Button("Start");
							startBtn.setPrefSize(btn_width, btn_height);
							startBtn.setFont(fontBold);
							
							//Dieser Button startet/pausiert die bereits eingebenen Codes
							startBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent arg0) {
									switch(startBtn.getText()) {
									case "Start":
										startBtn.setText("Pause");
										if(CodeVerarbeitungStarten) {
										Main.launchCodeRun(); 
										}
										CodeVerarbeitungStarten = false;
										
										if(timelineGrafik != null && timelineKoordinaten != null) {
											if(timelineGrafik.getCurrentRate() == 0) {
												timelineGrafik.play();
												timelineKoordinaten.play();
											}
										}
										break;
									case "Pause":
										startBtn.setText("Start");
										if(timelineGrafik != null && timelineKoordinaten != null) {
											timelineGrafik.pause();
											timelineKoordinaten.pause();
										}
										Main.codeRun.interrupt();
//										CircleAnimation.kreis(320,401,20,100);
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
						
						kuehlmitBtn = new Button("Dummy Button");
							kuehlmitBtn.setPrefSize(btn_width, btn_height);
							kuehlmitBtn.setFont(fontBold);
							kuehlmitBtn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent arg0) {
								
									clearAF();
															
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
									
									try {
									Codes.neubildenQueue(InputConsole.getText().split("\n"));
									
									}catch(Exception e) {

									}
									
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
			
			Circle HomePos = new Circle(Main.getHomePosX(),1050 - Main.getHomePosY(),7.5, Color.GREEN);
			HomePos.toFront();
			Kopf = new Circle(HomePos.getCenterX(),HomePos.getCenterY(),HomePos.getRadius(),Color.RED);
			
			
			arbeitsF.getChildren().add(HomePos);
		rootHBox.getChildren().add(arbeitsF);
		
		//Dieser Teil dient dazu, dass ein kleines Icon angezeigt wird.
		FileInputStream fip = new FileInputStream("files/ressources/CnC_klein.png");
		Image zahnrad= new Image(fip);
		primaryStage.getIcons().add(zahnrad);
		
		Scene scene = new Scene(rootHBox);
		primaryStage.setScene(scene);


//		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/**Eine Methode um die Textfelder und Buttons in der GUI zu steuern.
	 * 
	 * @param wert Boolean Angabe, ob die Kühlung aktiv sein soll
	 */
	public static void setKuehlung(boolean wert) {
		Platform.runLater(()->{
		if(wert) {
			kuehlmitBtn.setText("Kühlmittel\ndeaktivieren");
			kuehlmit.setText("aktiviert");
		}else {
			kuehlmitBtn.setText("Kühlmittel\naktivieren");
			kuehlmit.setText("deaktiviert");
		}
		});
		
	}
	/** Diese Methode nimmt die aktuelle Geschwindigkeit 
	 * 	und aktualisert die GUI 
	 *  Es wird auch dirket in Meter / min umgerechnet
	 */
	public static void setGeschwindigkeit() {
		Platform.runLater(()->{
			geschwin.setText(String.valueOf(Main.getAktGeschw()*60/1000));
		});
	}
	
	/**Fügt ein Node der Arbeitsfläche hinzu
	 * 
	 * @param node Node
	 */
	public static void addToAF(Node node) {
		Platform.runLater(()->{
		arbeitsF.getChildren().add(node);
		});
	}
	
	//Ab hier folgen nur noch Standard getter und setter
	
	public static int getHeight() {
		return height;
	}
	public static int getWidth() {
		return width;
	}
	
	public static void writeConsole(String arg) {
		InputConsole.setText(arg);
	}
	public static void setYLabel(double y) {
		aktuellY.setText(String.valueOf(y));
	}
	public static void setXLabel(double x) {
		aktuellX.setText(String.valueOf(x));
	}
	public static void setKopfX(double newX) {
		Kopf.setCenterX(newX);
	}
	public static void setKopfY(double newY) {
		Kopf.setCenterY(newY);
	}
	public static double getKopfX() {
		return Kopf.getCenterX();
	}
	public static double getKopfY() {
		return Kopf.getCenterY();
	}
	public static void KopfsetVisible(boolean wert) {
		Kopf.setVisible(wert);
	}
	public static double getKopfRadius() {
		return Kopf.getRadius();
	}
	public static Paint getKopfFill() {
		return Kopf.getFill();
	}
	public static String getOutputConsole() {
		return OutputConsole.getText();
	}
	public static void setTXTOutputConsole(String outputConsole) {
		OutputConsole.setText(outputConsole);
	}
	public static String getInputConsole() {
		return InputConsole.getText();
	}
	public static void setInputConsole(String inputConsole) {
		InputConsole.setText(inputConsole);
	}
	public static void clearAF() {
		arbeitsF.getChildren().clear();
	}
	
	public static void setAndPlayTimeline(Timeline tGrafik, Timeline tKoordinaten) {
		timelineGrafik = tGrafik;
		timelineKoordinaten = tKoordinaten;
		timelineGrafik.play();
		timelineKoordinaten.play();
	}
	public static void setCodeVerarbeitungStartenTrue() {
		CodeVerarbeitungStarten = true;
	}
	public static void clearTimelines() {
		timelineGrafik = null;
		timelineKoordinaten = null;
	}
	
}
