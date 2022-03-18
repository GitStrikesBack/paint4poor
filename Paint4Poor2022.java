import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;


public class Paint4Poor2022 extends Application {
  // Anfang Attribute
  private Pane root;                // global definiert für späteren Zugriff
  private Pixel[][] leinwand;       // Ein Array aus erweiterten Buttons
  private Color[][] bild;           // Ein reines Farb-Array
  private String grundStyle = "-fx-border-width: 0;-fx-background-radius: 0;-fx-border-color:LIGHTGRAY;-fx-border-insets: 0;-fx-border-radius: 0;"; // für alle Pixel
  private String geloeschterStyle = grundStyle + "-fx-background-color: #FFFFFF";
  // private String checkBoxStyle = ".check-box .box {-fx-background-color: white;-fx-border-color:grey; -fx-border-radius:3px;} .check-box:selected .mark {-fx-background-color: white;} .check-box:selected .box {-fx-background-color: #28a3f4;}"; 
  private Color aktuelleFarbe = Color.BLACK;
  private ColorPicker colorPicker1 = new ColorPicker();
  private Button bLoeschen = new Button();
  private Button bFuellen = new Button();
  private CheckBox checkBox1 = new CheckBox();
  // Ende Attribute
  
  
  // Belegt die beiden Arrays leinwand und bild mit Startwerten
  public void generiereLeinwand(int spalten, int zeilen, int pixelbreite) {
    int linkerRand = 10;
    int obererRand = 10;
    String pixelStyle;
    leinwand = new Pixel[spalten][zeilen];
    bild = new Color[spalten][zeilen];
    for (int y = 0; y < zeilen; y++) {
      for (int x = 0; x < spalten; x++) {
        bild[x][y] = Color.WHITE;
        leinwand[x][y] = new Pixel(x, y);
        leinwand[x][y].setLayoutX(linkerRand + x * pixelbreite);
        leinwand[x][y].setLayoutY(obererRand + y * pixelbreite);
        leinwand[x][y].setPrefHeight(pixelbreite);
        leinwand[x][y].setPrefWidth(pixelbreite);
        pixelStyle = grundStyle + "-fx-background-color: #" + leinwand[x][y].getFarbe().toString().substring(2)+";";
        leinwand[x][y].setStyle(pixelStyle);                      
        leinwand[x][y].setOnAction(
        (event) -> {leinwand_Action(event, zeilen, spalten);} 
        );
        root.getChildren().add(leinwand[x][y]);
      }
    }
  }
  
  
  // Das wird alles einmal beim Starten ausgeführt
  public void start(Stage primaryStage) { 
    root = new Pane();
    Scene scene = new Scene(root, 640, 512);
    // Anfang Komponenten
    bLoeschen.setOnAction((event) -> {bLoeschen_Action(event);} );
    bLoeschen.setLayoutX(500);
    bLoeschen.setLayoutY(70);
    bLoeschen.setPrefHeight(25);
    bLoeschen.setPrefWidth(100);                                                  
    bLoeschen.setText("Alles löschen");
    root.getChildren().add(bLoeschen);
    
    bFuellen.setLayoutX(500);
    bFuellen.setLayoutY(100);
    bFuellen.setPrefHeight(25);
    bFuellen.setPrefWidth(100);
    bFuellen.setOnAction((event) -> {bFuellen_Action(event);} );
    bFuellen.setText("Alles füllen");
    root.getChildren().add(bFuellen);
    
    checkBox1.setLayoutX(500);
    checkBox1.setLayoutY(130);
    checkBox1.setPrefHeight(17);
    checkBox1.setPrefWidth(100);
    checkBox1.setText("Radierer");
    // checkBox1.setStyle(checkBoxStyle); noch nicht fertig
    root.getChildren().add(checkBox1);
    // Ende Komponenten
    
    colorPicker1.setLayoutX(500);
    colorPicker1.setLayoutY(40);
    colorPicker1.setPrefHeight(25);
    colorPicker1.setPrefWidth(100);
    root.getChildren().add(colorPicker1);
   
    // erzeugen des "Array of Button" sowie initialiseren von bild[][]
    generiereLeinwand(30,30,15);
                                                              
    primaryStage.setOnCloseRequest(e -> System.exit(0));
    primaryStage.setTitle("Paint4Poor2022");
    primaryStage.setScene(scene);
    primaryStage.show();
  } 
  // Anfang Methoden
  
  
  // wenn irgendein Button der Leinwand gedrückt wird
  public void leinwand_Action(Event evt, int zeilen, int spalten) {
    int x = ((Pixel) evt.getSource()).getX();
    int y = ((Pixel) evt.getSource()).getY();
    if (checkBox1.isSelected()) {
      aktuelleFarbe = Color.WHITE;
    } else {
      aktuelleFarbe = colorPicker1.getValue();  
    }
    leinwand[x][y].setFarbe(aktuelleFarbe);
    leinwand[x][y].setStyle(grundStyle + "-fx-background-color: #" + leinwand[x][y].getFarbe().toString().substring(2)+";"); 
    bild[x][y] = aktuelleFarbe;
  }
  
  
  // "Hauptprogramm"
  public static void main(String[] args) {
    launch(args);
  } 
  
  
  // Button zum Löschen des gesamten Bildes
  public void bLoeschen_Action(Event evt) {
    for(int i = 0; i < leinwand.length; i++){
      for(int y = 0; y < leinwand[i].length; y++){
        leinwand[i][y].setStyle(geloeschterStyle);
      }
    }
  }
  
  
  // Button zum Füllen der gesamten leinwand[][] mit einer ausgewählten Farbe
  public void bFuellen_Action(Event evt) {
    for(int i = 0; i < leinwand.length; i++){
      for(int y = 0; y < leinwand[i].length; y++){
        leinwand[i][y].setStyle(grundStyle + "-fx-background-color: #" + colorPicker1.getValue().toString().substring(2) + ";");
      }
    }
  }
  
  // Ende Methoden
} 

