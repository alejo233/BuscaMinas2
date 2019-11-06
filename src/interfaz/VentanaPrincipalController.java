package interfaz;

import java.awt.Color;
import java.awt.Font;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import modelo.CasillaDestapadaException;
import modelo.FormatoInvalidoException;
import modelo.FueraDeRangoException;
import modelo.Tablero;

public class VentanaPrincipalController {
	
	public static final int FACIL_TAM=74;
	public static final int MEDIO_TAM=37;
	public static final int DIFICIL_TAM=37;
	//////////////////////////////
	public static final int FACIL=8;
	public static final int MEDIO=16;
	public static final int DIFICIL=30;
	/////////////////////////////
	public static final int EASY_MINES= 10;
	public static final int MIDDLE_MINES= 40;
	public static final int HARD_MINES=99;
	
	
	
	@FXML private GridPane grid;
	@FXML private GridPane gridOpciones;
	@FXML private Label titulo;
	@FXML private SplitMenuButton dificultad;
	
	@FXML private MenuItem easy;
	@FXML private MenuItem middle;
	@FXML private MenuItem hard;
	
//	@FXML private Button numberMines;
	@FXML private Button pista;
	@FXML private Button solucionar;
	
	@FXML private TextField coordenada;
	
	@FXML private BorderPane root;
	
	private Main main;
	
	private int minasRestantes;
	private int filas;
	private int columnas;
	private int totalMinas;
	private int tamanio;
	private int destapados;
	
	private Tablero tablero;
	public void VentanaPrincipalController() {
		
	}
	
	public void initialize() {
		destapados=0;
		easy.setStyle("-fx-font: 30px Barbarian;-fx-text-fill: darkslategrey");
		middle.setStyle("-fx-font: 30px Barbarian;-fx-text-fill: darkslategrey");
		hard.setStyle("-fx-font: 30px Barbarian;-fx-text-fill: darkslategrey");
		filas=FACIL;
		columnas=FACIL;
		tamanio=FACIL_TAM;
		llenarElGrid(FACIL, FACIL, FACIL_TAM);
		darFuncionOpciones();
		totalMinas=EASY_MINES;
		minasRestantes=EASY_MINES;
		
			coordenada.setStyle("-fx-font-size:15px; -fx-text-fill: blue; -fx-font-weight:bold;"); 
			coordenada.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					procesarCoordenada();
					coordenada.clear();
				}
			}
			});
	}
	
	public void procesarCoordenada() {
		try{
			boolean valido=tablero.revisarCoordenada(coordenada.getText());
			if (valido){
				String[] nums=coordenada.getText().split("-");
			int	pos= ((columnas*(Integer.parseInt(nums[0])-1))+Integer.parseInt(nums[1]));
				destaparCasilla((Button)grid.getChildren().get(pos));
			}
		}catch(FormatoInvalidoException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("COORDENADA");
			alert.setHeaderText("Ocurrio un problema con la coordenada");
			alert.setContentText(e.getMessage());
			alert.initOwner(main.getStage());
			alert.showAndWait();
			
		}catch(FueraDeRangoException s) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("COORDENADA");
			alert.setHeaderText("Ocurrio un problema con la coordenada");
			alert.setContentText(s.getMessage());
			alert.initOwner(main.getStage());
			alert.showAndWait();
			
		}catch(CasillaDestapadaException d) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("COORDENADA");
			alert.setHeaderText("Ocurrio un problema con la coordenada");
			alert.setContentText(d.getMessage());
			alert.initOwner(main.getStage());
			alert.showAndWait();
			
		}catch(NumberFormatException a
				) {
			
		}
	}
	
	public void darPista() {
		boolean stop=false;
		for (int i = 1; i <grid.getChildren().size()&&!stop; i++) {
			if (grid.getChildren().get(i) instanceof Button) {
				Button boton= (Button)(grid.getChildren().get(i));
				int[] datos=tablero.convertirRevisar(boton.getId());
				String numero= datos[0]+"";
				if (datos[0]!=0&&datos[0]!=(-1)) {
					Label numeroMinas= new Label(numero);
					numeroMinas.setPrefSize(tamanio,tamanio);
					numeroMinas.setStyle(darColorNumeros(datos[0]));
					
					
					grid.add(numeroMinas, datos[2], datos[1]);
					grid.getChildren().remove(boton);
					destapados++;
					isGanador();
					stop=true;
				}
			}
			
		}
		
	}
	
	public void perdio() throws CasillaDestapadaException {
		solucionar();
		titulo.setText("Perdiste)");
	}
	
	public void relacionMain(Main main) {
		this.main=main;
	}
	
	public void darFuncionOpciones() {
		dificultad.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent a) {
				dificultad.show();
			}
		});
		pista.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				darPista();
			}
		});
		
		
		solucionar.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					solucionar();
				} catch (CasillaDestapadaException e1) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("COORDENADA");
					alert.setHeaderText("Ocurrio un problema con la coordenada");
					alert.setContentText(e1.getMessage());
					alert.initOwner(main.getStage());
					alert.showAndWait();
					
					
				}
			}
		});
		
		easy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				main.getStage().setMinHeight(810);
				main.getStage().setMinWidth(611);
				
				main.getStage().setWidth(611);
				main.getStage().setHeight(810);
								
				
				main.getStage().setMaxWidth(611);
				main.getStage().setMaxHeight(810);

				
				grid.getRowConstraints().removeAll(grid.getRowConstraints());
				grid.getColumnConstraints().removeAll(grid.getColumnConstraints());
				for (int i = grid.getChildren().size()-1; i > 0; i--) {
					Node child= grid.getChildren().get(i);
					grid.getChildren().remove(child);
				}
				llenarElGrid(FACIL, FACIL, FACIL_TAM);
				minasRestantes=EASY_MINES;
				totalMinas=EASY_MINES;
				
			}
		});
		
		middle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				main.getStage().setMinHeight(810);
				main.getStage().setMinWidth(611);
				
				main.getStage().setWidth(611);
				main.getStage().setHeight(810);
								
				
				main.getStage().setMaxWidth(611);
				main.getStage().setMaxHeight(810);

				
				grid.getRowConstraints().removeAll(grid.getRowConstraints());
				grid.getColumnConstraints().removeAll(grid.getColumnConstraints());
				for (int i = grid.getChildren().size()-1; i > 0; i--) {
					Node child= grid.getChildren().get(i);
					grid.getChildren().remove(child);
				}
				
				llenarElGrid(MEDIO, MEDIO, MEDIO_TAM);
				minasRestantes=MIDDLE_MINES;
				totalMinas=MIDDLE_MINES;
				
			}
		});
		
		hard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				main.getStage().setMinHeight(810);
				main.getStage().setMinWidth(1127);
				
				main.getStage().setMaxHeight(810);
				main.getStage().setMaxWidth(1127);
				
				main.getStage().setHeight(810);
				main.getStage().setWidth(1127);
				
				grid.getRowConstraints().removeAll(grid.getRowConstraints());
				grid.getColumnConstraints().removeAll(grid.getColumnConstraints());
				for (int i = grid.getChildren().size()-1; i > 0; i--) {
					Node child= grid.getChildren().get(i);
					grid.getChildren().remove(child);
				}
				minasRestantes=HARD_MINES;
				totalMinas=HARD_MINES;
				llenarElGrid(MEDIO, DIFICIL, DIFICIL_TAM);
				
			}
		});
	}
	public void llenarElGrid(int dificultadF,int dificultadC, int tam) {
		filas=dificultadF;
		columnas=dificultadC;
		tamanio=tam;
		titulo.setText("Buscaminas)");
		grid.setGridLinesVisible(true);
		tablero=new Tablero(dificultadC/8);
		
		for (int i = 0; i < (dificultadF-1); i++) {
			RowConstraints fila = new RowConstraints(tam);
			grid.getRowConstraints().add(fila);
		}
		for (int i = 0; i <( dificultadC-1); i++) {
			ColumnConstraints columna = new ColumnConstraints(tam);
			grid.getColumnConstraints().add(columna);
		}
		
		int m=0;
		
		for (int i = 0; i < dificultadF; i++) {
			for (int j = 0; j < dificultadC; j++) {
				String mm=m+"";
				
				Button boton= new Button();
				boton.setId(mm);
				m++;
				boton.setPrefHeight(tam);
				boton.setPrefWidth(tam);
				
				boton.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent t) {
						if(t.getButton()==MouseButton.PRIMARY) {
						try {
							destaparCasilla(boton);
						} catch (CasillaDestapadaException e) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("COORDENADA");
							alert.setHeaderText("Ocurrio un problema con la coordenada");
							alert.setContentText(e.getMessage());
							alert.initOwner(main.getStage());
							alert.showAndWait();
							
						}
						}else if (t.getButton()==MouseButton.SECONDARY) {
							Image bomba = new Image(getClass().getResourceAsStream("flag.png"));
							ImageView imagen= new ImageView(bomba);
							imagen.setFitHeight(tamanio-10);
							imagen.setFitWidth(tamanio-20);
							boton.setGraphic(imagen);
							minasRestantes--;
							
						}
						
					}
				
				});
				grid.add(boton, j, i);	
			}
		}
	}
	public void solucionar() throws CasillaDestapadaException {
		int contar=0;
		for (int i = 0; i < tablero.getTablero().length; i++) {
			for (int j = 0; j < tablero.getTablero()[0].length; j++) {
				if(tablero.getTablero()[i][j].getDestapada()) {
					contar++;
				}
			}
		}
		if (contar==columnas*filas-totalMinas) {
			throw new CasillaDestapadaException();
		}
		
		for (int i = grid.getChildren().size()-1; i >0; i--) {
			if (grid.getChildren().get(i) instanceof Button) {
				Button boton= (Button)(grid.getChildren().get(i));
				int[] datos=tablero.convertirRevisar(boton.getId());
				String numero= datos[0]+"";
				if (datos[0]!=0&&datos[0]!=(-1)) {
					Label numeroMinas= new Label(numero);
					tablero.getTablero()[datos[2]][datos[1]].setDestapada(true);
					numeroMinas.setPrefSize(tamanio,tamanio);
					numeroMinas.setStyle(darColorNumeros(datos[0]));
					
					
					grid.add(numeroMinas, datos[2], datos[1]);
				}else if (datos[0]==(-1)) {
					Label mina= new Label();
					Image bomba = new Image(getClass().getResourceAsStream("minaa.png"));
					ImageView imagen= new ImageView(bomba);
					tablero.getTablero()[datos[2]][datos[1]].setDestapada(true);
					imagen.setFitHeight(tamanio-5);
					imagen.setFitWidth(tamanio-5);
					mina.setPrefSize(tamanio,tamanio);
					mina.setStyle("-fx-alignment: center;");
					mina.setGraphic(imagen);
					
					grid.add(mina, datos[2], datos[1]);
				}
				grid.getChildren().remove(boton);
			}
			
		}
		
	}
	public void destaparCasilla(Button boton) throws CasillaDestapadaException {
		
		int[] datos=tablero.convertirRevisar(boton.getId());
		String numero= datos[0]+"";
		if (datos[0]!=0&&datos[0]!=(-1)) {

			Label numeroMinas= new Label(numero);
			numeroMinas.setPrefSize(tamanio,tamanio);
			numeroMinas.setStyle(darColorNumeros(datos[0]));
			destapados++;
			isGanador();
			grid.add(numeroMinas, datos[2], datos[1]);
		}else if (datos[0]==(-1)) {
			Label mina= new Label();
			Image bomba = new Image(getClass().getResourceAsStream("minaLose.png"));
			ImageView imagen= new ImageView(bomba);
			imagen.setFitHeight(tamanio);
			imagen.setFitWidth(tamanio);
			mina.setPrefSize(tamanio,tamanio);
			mina.setStyle("-fx-alignment: center;");
			mina.setGraphic(imagen);
			grid.add(mina, datos[2], datos[1]);
			perdio();
		
		}else if (datos[0]==0) {
			clickEnVacia(boton);
		}
		grid.getChildren().remove(boton);
	}
	
	public void clickEnVacia(Button boton) {
		
		int pos=Integer.parseInt(boton.getId());
		int vecesI=0;
		int vecesJ=0;
		int[] datosPrime = tablero.convertirRevisar(pos + "");
		int i=pos-(columnas+1);
		while (vecesI < 3) {
			if (datosPrime[2]==0) {
				i++;
				vecesJ++;
			}
			
			while (vecesJ < 3) {
				if (i >= 0&&(i+1)<=grid.getChildren().size()) {
					
					int[] datos = tablero.convertirRevisar(i + "");
					
						String numero = datos[0] + "";
					if (datos[0] != 0) {
						Label numeroMinas = new Label(numero);
						numeroMinas.setPrefSize(tamanio, tamanio);
						numeroMinas.setStyle(darColorNumeros(datos[0]));
						tablero.getTablero()[datos[2]][datos[1]].setDestapada(true);
						destapados++;
						isGanador();
						grid.add(numeroMinas, datos[2], datos[1]);
					} else {
						Label numeroMinas = new Label();
						numeroMinas.setPrefSize(tamanio, tamanio);
						grid.add(numeroMinas, datos[2], datos[1]);
						tablero.getTablero()[datos[2]][datos[1]].setDestapada(true);
						destapados++;
						isGanador();
					}///////////////////aqui voy!!!!!!!!!!!!!
					
					boolean stop = false;
					for (int j = 1; j < ((columnas*filas)+1) && !stop; j++) {
						Node botin = grid.getChildren().get(j);
						if (!(botin instanceof Label)) {
							if (botin.getId().equals(i + "")) {
								grid.getChildren().remove(j);
								stop = true;
							}
						}
					}
				}
				
				if (datosPrime[2] == (columnas-1)&&vecesJ==1) {
					vecesJ++;
					i++;
				}
				i++;
				vecesJ++;
			}
			vecesI++;
			vecesJ = 0;
			i = i + (columnas-3);
		}
		}
		
	public void isGanador() {
		if (destapados==((filas*columnas)-totalMinas)) {
			titulo.setText("Ganaste)");
		}
	}
	
	public String darColorNumeros(int numeroMinas) {
		String color=" ";
		switch (numeroMinas) {
		case 1:
			color="-fx-font-size:"+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: blue; -fx-font-weight:bold; ";
			break;

		case 2:
			color="-fx-font-size:"+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: green; -fx-font-weight:bold;";
			break;
		case 3:
			color="-fx-font-size:"+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: red; -fx-font-weight:bold;";
			break;
		case 4:
			color="-fx-font-size: "+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: darkblue; -fx-font-weight:bold;";
			break;
		case 5:
			color="-fx-font-size:"+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: brown; -fx-font-weight:bold;";
			break;
		case 6:
			color="-fx-font-size:"+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: pink; -fx-font-weight:bold;";
			break;
		case 7:
			color="-fx-font-size:"+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: yellow; -fx-font-weight:bold;";
			break;
		case 8:
			color="-fx-font-size: "+(tamanio==FACIL_TAM? "50":"25")+"px; -fx-alignment:center; -fx-text-fill: purple; -fx-font-weight:bold;";
			break;
		}
		
		
		return color;
	}	
	
}
