package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.StudentDTO;
import dto.UserDTO;
import model.StudentBean;

public class StudentDAO {
	static Connection con = null;
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
	
	public List<StudentBean> selectAll() {
		List<StudentBean> list = new ArrayList<StudentBean>();
		try {
			PreparedStatement ps = con.prepareStatement("select * from student");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentBean res = new StudentBean();
				res.setId(rs.getString("student_id"));
				res.setName(rs.getString("student_name"));
				Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(rs.getString("register_date"));
				res.setRegisterDate(date1);
				res.setClassName(rs.getString("class_name"));
				res.setStatus(rs.getString("status"));
				list.add(res);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			System.out.println("parse error");
			return null;
		}
		return list;
	}
	/*public int updateData(StudentDTO dto) {
		int result = 0;
		//String sql = "update student set student_name=?,class_name=?,register_date=?,status=? where student_id=?";
		String sql = "update student set student_name=?,class_name=?,register_date=?,status=? where student_id=?";
		try {
			System.out.println(dto.getStudentName());
			System.out.println(dto.getClassName());
			System.out.println(dto.getRegisterDate());
			System.out.println(dto.getStatus());
			System.out.println(dto.getStudentId());
			
			PreparedStatement ps = con.prepareStatement(sql);
			System.out.println("1");
			ps.setString(1, dto.getStudentName());
			ps.setString(2, dto.getClassName());
			ps.setString(3, dto.getRegisterDate());
			ps.setString(4, dto.getStatus());
			ps.setString(5, dto.getStudentId());
			System.out.println(2);
			
			result = ps.executeUpdate();
			System.out.println(3);
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}*/
	
		public int update(StudentDTO dto) {
		int result = 0;
		String sql = "update student set student_name=?,class_name=?,register_date=?,status=? where student_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getStudentName());
			ps.setString(2, dto.getClassName());
			ps.setString(3, dto.getRegisterDate());
			ps.setString(4, dto.getStatus());
			ps.setString(5, dto.getStudentId());
			System.out.println( dto.getStudentName());
			System.out.println(dto.getClassName());
			System.out.println(dto.getRegisterDate());
			System.out.println(dto.getStatus());
			System.out.println(dto.getStudentId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}
	
	public int deleteData(StudentDTO dto) {
		int result = 0;
		System.out.println("delete");
		String sql = "delete from student where student_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getStudentId());
			result = ps.executeUpdate();
			result=1;
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}
}
