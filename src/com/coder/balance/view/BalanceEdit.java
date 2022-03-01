package com.coder.balance.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.coder.balance.entity.Balance;
import com.coder.balance.entity.Balance.Category;
import com.coder.balance.entity.Balance.Type;
import com.coder.balance.util.BalanceExpection;
import com.coder.balance.util.MessageHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BalanceEdit implements Initializable {

	@FXML
	private VBox root;

	@FXML
	private ComboBox<Type> type;

	@FXML
	private ComboBox<Category> category;

	@FXML
	private TextField amount;

	@FXML
	private DatePicker date;

	@FXML
	private TextArea remark;

	@FXML
	private Label header;

	private Balance balance;
	private Consumer<Balance> saveHandaler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		type.getItems().addAll(Type.values());
		category.getItems().addAll(Category.values());
		date.setValue(LocalDate.now());
		
		//Short cut Key
		root.setOnKeyPressed(e->{
			 if(e.getCode() == KeyCode.ENTER) {
				 addNew();
			 }
		});

	}

	@FXML
	void addNew() {

		try {

			// Check view data
			validation();

			// Get view data
			if (null == balance)
				balance = new Balance();

			// Get data from UI
			balance.setType(type.getValue());
			balance.setCategory(category.getValue());
			balance.setAmount(Integer.parseInt(amount.getText()));
			balance.setCreation(date.getValue());
			balance.setRemark(remark.getText());

			// Reload table view
			saveHandaler.accept(balance);

			// Close window
			type.getScene().getWindow().hide();

		} catch (Exception e) {
			MessageHandler.showAlert(e);
		}

	}

	private void validation() {

		if (null == type.getValue()) {
			throw new BalanceExpection("Please Select Type !");
		}

		if (null == category.getValue()) {
			throw new BalanceExpection("Please Select Category !");
		}

		if (amount.getText().isEmpty()) {
			throw new BalanceExpection("Please Select Amount !");
		}

		if (null == date.getValue()) {
			throw new BalanceExpection("Please Select Date !");
		}

	}

	@FXML
	void clear() {
		type.setValue(null);
		category.setValue(null);
		date.setValue(null);
		remark.clear();
		amount.clear();

	}

	private void setBalance(Balance b) {
		// Set instance state
		this.balance = b;

		this.type.setValue(b.getType());
		this.category.setValue(b.getCategory());
		this.date.setValue(b.getCreation());
		this.remark.setText(b.getRemark());
		this.amount.setText(String.valueOf(b.getAmount()));

	}

	// For View Loading from Main View
	public static void viewLoader(Consumer<Balance> saveHandaler) {

		try {

			// Loading View From FXML
			FXMLLoader loader = new FXMLLoader(BalanceEdit.class.getResource("BalanceEdit.fxml"));

			// Load View
			Parent root = loader.load();

			// Get Controller
			BalanceEdit controller = loader.getController();
			controller.saveHandaler = saveHandaler;
			controller.header.setText("Add Balance");

			// Stage
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);

			// Set Scene into Stage Object
			stage.setScene(new Scene(root));
			stage.setTitle("Add New Balance !");
			stage.getIcons().add(new Image("com/coder/balance/resource/balanceList.png"));
			stage.show();

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void viewLoader(Consumer<Balance> saveHandaler, Balance b) {

		try {

			// Loading View From FXML
			FXMLLoader loader = new FXMLLoader(BalanceEdit.class.getResource("BalanceEdit.fxml"));

			// Load View
			Parent root = loader.load();

			// Get Controller
			BalanceEdit controller = loader.getController();
			controller.saveHandaler = saveHandaler;
			controller.setBalance(b);
			controller.header.setText("Update Balance");

			// Stage
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);

			// Set Scene into Stage Object
			stage.setScene(new Scene(root));
			stage.setTitle("Update Existing Balance !");
			stage.getIcons().add(new Image("com/coder/balance/resource/balanceList.png"));
			stage.show();

		} catch (Exception e) {
			e.getMessage();
		}
	}

}
