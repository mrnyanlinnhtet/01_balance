package com.coder.balance.entity;

import java.time.LocalDate;

public class Balance {

	public enum Type {
		Incomes, Expenses
	};

	public enum Category {
		Salary, Transporation, Cloths, Work, Foods_Drinks, Accessories, Gatget, Others
	};

	private int id;
	private Type type;
	private Category category;
	private LocalDate creation;
	private int amount;
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LocalDate getCreation() {
		return creation;
	}

	public void setCreation(LocalDate creation) {
		this.creation = creation;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
