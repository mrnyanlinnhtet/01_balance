package com.coder.balance.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.coder.balance.entity.Balance;
import com.coder.balance.entity.Balance.Category;
import com.coder.balance.entity.Balance.Type;
import com.coder.balance.util.MessageHandler;

public class MysqlDB implements Repository {

	private static final String URL = "jdbc:mysql://localhost:3306/balance_fx";
	private static final String USER = "root";
	private static final String PASS = "admin";

	private static Repository REPO = new MysqlDB();

	private MysqlDB() {
	}

//	 For Singleton
	public static Repository getInstance() {
		return REPO;
	}

//	To Get Connection
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

	@Override
	public void add(Balance balance) {
		String insert = "INSERT INTO balances(b_type,b_date,amount,category,remark)VALUES(?,?,?,?,?)";

		try (Connection con = getConnection();
				PreparedStatement prep = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

			prep.setString(1, balance.getType().toString());
			prep.setDate(2, Date.valueOf(balance.getCreation()));
			prep.setInt(3, balance.getAmount());
			prep.setString(4, balance.getCategory().toString());
			prep.setString(5, balance.getRemark());

			prep.executeUpdate();

		} catch (Exception e) {
			MessageHandler.showAlert(e);
		}

	}

	@Override
	public void delete(int id) {

		String delete = "DELETE FROM balances WHERE id = ?";
		try (Connection con = getConnection(); PreparedStatement prep = con.prepareStatement(delete)) {

			prep.setInt(1, id);
			prep.executeUpdate();

		} catch (Exception e) {
			MessageHandler.showAlert(e);
		}

	}

	@Override
	public void update(Balance balance) {
		String update = "UPDATE balances SET b_type = ? , b_date = ? , amount = ? , category = ? "
				+ ", remark = ? WHERE id = ?";
		try (Connection con = getConnection(); PreparedStatement prep = con.prepareStatement(update)) {

			prep.setString(1, balance.getType().toString());
			prep.setDate(2, Date.valueOf(balance.getCreation()));
			prep.setInt(3, balance.getAmount());
			prep.setString(4, balance.getCategory().toString());
			prep.setString(5, balance.getRemark());
			prep.setInt(6, balance.getId());

			prep.executeUpdate();

		} catch (Exception e) {
			MessageHandler.showAlert(e);
		}

	}

	@Override
	public Balance Search(int id) {
		String findById = "SELECT * FROM balances WHERE id =?";
		try (Connection con = getConnection(); PreparedStatement prep = con.prepareStatement(findById)) {

			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				return getBalance(rs);
			}
		} catch (Exception e) {
			MessageHandler.showAlert(e);
		}
		return null;
	}

	@Override
	public List<Balance> search(Type type, Category category, LocalDate startDate, LocalDate endDate) {
		// To store data
		List<Balance> list = new ArrayList<>();

		// Dynamic Query
		StringBuilder query = new StringBuilder("SELECT * FROM balances WHERE 1 = 1 ");

		// Parameter
		List<Object> params = new ArrayList<>();

		if (null != type) {
			query.append("and b_type = ? ");
			params.add(type.toString());
		}

		if (null != category) {
			query.append("and category = ? ");
			params.add(category.toString());
		}

		if (null != startDate) {
			query.append("and b_date <= ? ");
			params.add(Date.valueOf(startDate));
		}

		if (null != endDate) {
			query.append("and b_date >= ? ");
			params.add(Date.valueOf(endDate));
		}

		try (Connection connection = getConnection();
				PreparedStatement prep = connection.prepareStatement(query.toString())) {

			// set parameter

			for (int i = 0; i < params.size(); i++) {
				prep.setObject(i + 1, params.get(i));
			}

			// execute Query in database
			ResultSet rs = prep.executeQuery();

			// add result into list
			while (rs.next()) {
				list.add(getBalance(rs));
			}
		} catch (Exception e) {
			MessageHandler.showAlert(e);
		}
		return list;
	}

	private Balance getBalance(ResultSet rs) throws SQLException {
		Balance b = new Balance();

		b.setId(rs.getInt("id"));
		b.setType(Type.valueOf(rs.getString("b_type")));
		b.setCreation(rs.getDate("b_date").toLocalDate());
		b.setAmount(rs.getInt("amount"));
		b.setCategory(Category.valueOf(rs.getString("category")));
		b.setRemark(rs.getString("remark"));

		return b;
	}

}
