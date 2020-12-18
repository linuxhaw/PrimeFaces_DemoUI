package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.StudentDTO;

public class StudentDAO {
	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}
	
	public int insertData(StudentDTO dto) {
		int result = 0;
		String sql = "insert into book(book_code,book_title,book_author,book_price)" + "values(?,?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			/*
			 * ps.setString(1, dto.getBookCode()); ps.setString(2, dto.getBookTitle());
			 * ps.setString(3, dto.getBookAuthor()); ps.setDouble(4, dto.getBookPrice());
			 */
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}
}
