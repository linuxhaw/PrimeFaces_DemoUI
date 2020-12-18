package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dto.ClassDTO;
import dto.StudentDTO;
import model.StudentBean;

public class StudentDAO {
	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}
	
	public int insert(StudentDTO dto) {
		int result = 0;
		String sql = "insert into student(student_id,student_name,class_name,register_date,status) values(?,?,?,?,?)";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getStudentId());
			ps.setString(2, dto.getStudentName());
			ps.setString(3, dto.getClassName());
			ps.setString(4, dto.getRegisterDate());
			ps.setString(5, dto.getStatus());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}
	
	public StudentBean selectOne(String id) {
		try {
			PreparedStatement ps = con.prepareStatement("select * from student where student_id=?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			StudentBean res = new StudentBean();
			while (rs.next()) {
				res.setId(rs.getString("student_id"));
				res.setName(rs.getString("student_name"));
				res.setClassName(rs.getString("class_name"));
				Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(rs.getString("register_date"));
				res.setRegisterDate(date1);
				res.setStatus(rs.getString("status"));
			}
			return res;
		} catch (SQLException e) {
			return null;
		} catch (ParseException e) {
			System.out.println("parse error");
			return null;
		}

	}
}
