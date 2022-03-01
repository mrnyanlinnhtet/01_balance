package com.coder.balance.service;

import java.time.LocalDate;
import java.util.List;

import com.coder.balance.entity.Balance;
import com.coder.balance.entity.Balance.Category;
import com.coder.balance.entity.Balance.Type;

public interface Repository {
	
	//Create instance of Service
	public static Repository getInstance() {
		return MysqlDB.getInstance();
	}

	// Add Data
	void add(Balance balance);

	// Delete data with id
	void delete(int id);

	// Update data
	void update(Balance balance);

	// Find by id
	Balance Search(int id);

	// Find All
	List<Balance> search(Type type, Category category, LocalDate startDate, LocalDate endDate);

}
