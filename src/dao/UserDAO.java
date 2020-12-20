package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UserDTO;


public class UserDAO {
	public static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public int insert(UserDTO dto) {
		int result = 0;
		String sql = "insert into user (id,name,password) values(?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getPassword());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}

	public int update(UserDTO dto) {
		int result = 0;
		String sql = "update user set name=?,password=? where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(3, dto.getId());
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getPassword());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database error");
		}
		return result;
	}

	public int delete(UserDTO dto) {
		int result = 0;
		String sql = "delete from user where id=?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public UserDTO select(UserDTO dto) {

		try {
			PreparedStatement p=con.prepareStatement("select * from user where id=? or name=?");
			p.setString(1,dto.getId());
		p.setString(2, dto.getName());
		ResultSet rs=p.executeQuery();
		while(rs.next()) {
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setPassword(rs.getString("password"));
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}
	public List<UserDTO> selectAll(){
		List<UserDTO> dto=new ArrayList<UserDTO>();
		try {
			PreparedStatement p=con.prepareStatement("select * from user ");
			ResultSet rs=p.executeQuery();
			while(rs.next()) {
				UserDTO res=new UserDTO();
			res.setId(rs.getString("id"));
			res.setName(rs.getString("name"));
	res.setPassword(rs.getString("password"));

		dto.add(res);
			}
		}catch(SQLException e) {
			System.out.println("DataBase ERROR!");
		}
		return dto;
	}

}
