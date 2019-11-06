package interfaz;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private Scene scene;
	private Stage pS;
	@Override
	public void start(Stage primaryStage) {
		try {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaPrincipal.fxml"));
			BorderPane root = (BorderPane) loader.load();
			
			//Scene scene = new Scene(root, 1250, 800);
			scene = new Scene(root, Paint.valueOf("blue"));
			VentanaPrincipalController controller=loader.getController();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			controller.relacionMain(this);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Buscaminas");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("boom.png")));
			setStage(primaryStage);
			primaryStage.centerOnScreen();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setStage(Stage pS) {
		this.pS=pS;
	}
	
	public Stage getStage() {
		return pS;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
