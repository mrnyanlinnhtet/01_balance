package com.coder.balance.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.coder.balance.entity.Balance;
import com.coder.balance.entity.Balance.Category;
import com.coder.balance.entity.Balance.Type;

public class MemoryDB implements Repository {

	private static MemoryDB INSTANCE = new MemoryDB();

	private List<Balance> list;

	private MemoryDB() {
		list = new ArrayList<>();
	}

	
	public static MemoryDB getInstance() {
		return INSTANCE;
	}

	@Override
	public void add(Balance balance) {
		/* Generate Id */
		int size = list.size();

		if (size == 0) {
			balance.setId(1);
		} else {
			int id = list.get(size - 1).getId() + 1;
			balance.setId(id);
		}

		/* Insert data to List */
		list.add(balance);
	}

	@Override
	public void delete(int id) {
		/* Search Balance Object to Delete */
		Balance balance = Search(id);

		/* Delete Process Balance Object */
		if (null != balance) {
			list.remove(balance);
		}

	}

	@Override
	public void update(Balance balance) {
		/* Delete existing data to update balance Object */
		delete(balance.getId());

		/* Add data to update balance Object */
		add(balance);
	}

	@Override
	public Balance Search(int id) {
		for (Balance balance : list) {
			if (balance.getId() == id) {
				return balance;
			}
		}
		return null;
	}


	@Override
	public List<Balance> search(Type type, Category category, LocalDate startDate, LocalDate endDate) {
		Predicate<Balance> condition = b -> true;

		if (null != type)
			condition = condition.and(b -> b.getType() == type);

		if (null != category)
			condition = condition.and(b -> b.getCategory() == category);

		if (null != startDate)
			condition = condition.and(b -> b.getCreation().compareTo(startDate) >= 0);
		
		if(null != endDate)
			condition = condition.and(b-> b.getCreation().compareTo(endDate) <= 0);

		return list.stream().filter(condition).collect(Collectors.toList());
	}
	
	 
	 

}
