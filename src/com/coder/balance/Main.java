package com.coder.balance;

import com.coder.balance.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(MainView.class.getResource("MainView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("com/coder/balance/resource/sing.jpg"));
//			primaryStage.setResizable(false);
			primaryStage.setTitle("MY BALANCE APP");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
