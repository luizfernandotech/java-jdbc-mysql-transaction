package application;

import db.DB;
import db.DbException;

import java.sql.*;


public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        Statement st = null;

        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false);

            st = conn.createStatement();

            int row1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 2");

            int x = 1;
            if (x > 2) {
                throw new SQLException("Fake error");
            }

            int row2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 4");

            conn.commit();

            System.out.println("Row 1: " + row1);
            System.out.println("Row 2: " + row2);

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
            } catch (SQLException e2) {
                throw new DbException("Error trying to rollback! Caused by: " + e2.getMessage());
            }
        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
