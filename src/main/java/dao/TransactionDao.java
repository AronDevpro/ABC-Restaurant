package dao;

import dbConnection.CrudUtil;
import model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    // Method to create a new transaction
    public static void createTransaction(Transaction transaction) throws ClassNotFoundException {
        try {
            String sql = "INSERT INTO transactions (orderId, paymentMethod, total) " +
                    "VALUES (?, ?, ?)";

            CrudUtil.execute(sql,
                    transaction.getOrderId(),
                    transaction.getPaymentMethod(),
                    transaction.getTotal()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to get the transaction filtered
    public static List<Transaction> getTransactionsByFilter() throws ClassNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM transactions ORDER BY createdAt DESC LIMIT 10";

            ResultSet rs = CrudUtil.execute(sql);
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setOrderId(rs.getInt("orderId"));
                transaction.setPaymentMethod(rs.getString("paymentMethod"));
                transaction.setTotal(rs.getDouble("total"));
                transaction.setStatus(rs.getString("status"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

}
