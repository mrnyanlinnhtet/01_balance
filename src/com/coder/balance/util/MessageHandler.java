package com.coder.balance.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageHandler {

	public static Alert alert = new Alert(null);

	// For Exception
	public static void showAlert(Exception e) {
		AlertType type = e instanceof BalanceExpection ? AlertType.WARNING : AlertType.ERROR;
		String title = e instanceof BalanceExpection ? "Error In Application !" : "Internal Error !";
		alert.setAlertType(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(e.getMessage());
		alert.show();
	}

	// For Information(Custom)
	public static void showAlert(String message) {
		alert.setAlertType(AlertType.INFORMATION);
		alert.setTitle("Information From Application !");
		alert.setResizable(true);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
		
	}

}
