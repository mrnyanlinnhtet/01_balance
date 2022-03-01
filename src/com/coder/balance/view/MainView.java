package com.coder.balance.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.coder.balance.entity.Balance;
import com.coder.balance.entity.Balance.Category;
import com.coder.balance.entity.Balance.Type;
import com.coder.balance.service.Repository;
import com.coder.balance.util.MessageHandler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class MainView implements Initializable {

	@FXML
	private VBox root;

	@FXML
	private ComboBox<Type> cbxType;

	@FXML
	private ComboBox<Category> cbxCategory;

	@FXML
	private DatePicker dateTo;

	@FXML
	private DatePicker dateFrom;

	@FXML
	private TableView<Balance> tblResult;

	@FXML
	private Label lblMessage;

	private Repository repo;

	@FXML
	void addNew() {
		// Loading View from Balance Edit
		BalanceEdit.viewLoader(balance -> save(balance));
	}

	// To Transfer data
	private void save(Balance b) {

		if (b.getId() == 0)
			// Add to database
			repo.add(b);
		else
			// Update data
			repo.update(b);

		// Reload data
		search();
	}

	@FXML
	void clear() {
		cbxType.setValue(null);
		cbxCategory.setValue(null);
		dateTo.setValue(null);
		dateFrom.setValue(null);
		lblMessage.setText("");

		// Reload Data
		search();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Create Service
		repo = Repository.getInstance();

		// Initialize View Data
		cbxType.getItems().addAll(Type.values());
		cbxCategory.getItems().addAll(Category.values());

		// Set ContexMenu
		createContexMenu();

		// Shortcut Key
		root.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.F4) {
				Platform.exit();
			}
			
			if(e.getCode() == KeyCode.F2) {
				addNew();
			}
		});
		
		

		// Reset View data
		clear();

		// Search Data
		search();

	}

	public void createContexMenu() {

		MenuItem edit = new MenuItem("EDIT");
		MenuItem delete = new MenuItem("DELETE");
		delete.setOnAction(e -> delete());
		edit.setOnAction(e -> edit());
		ContextMenu ctx = new ContextMenu(edit, delete);
		tblResult.setContextMenu(ctx);
	}

	// Edit data
	private void edit() {
		Balance b = tblResult.getSelectionModel().getSelectedItem();

		// Pass balance object into balance edit controller
		BalanceEdit.viewLoader(this::save, b);
	}

	// Delete Data
	private void delete() {
		Balance balance = tblResult.getSelectionModel().getSelectedItem();

		// Delete balance data with id
		repo.delete(balance.getId());

		// Reload data from table view
		search();

	}

	@FXML
	void search() {

		// Validate Date from and Date To
		if (dateFrom.getValue() != null && dateTo.getValue() != null
				&& dateTo.getValue().compareTo(dateFrom.getValue()) > 0) {

			// Show Error Message
			MessageHandler.showAlert("Date To must be greater than Date From !");

			// Reset Data of DateTo
			dateFrom.setValue(null);

		}

		// Clear Table data
		tblResult.getItems().clear();

		// Search Data
		List<Balance> list = repo.search(cbxType.getValue(), cbxCategory.getValue(), dateFrom.getValue(),
				dateTo.getValue());

		// Add data to TableView
		tblResult.getItems().addAll(list);

		// Show table data count in label
		lblMessage.setText("Total : " + list.size());
	}
}
