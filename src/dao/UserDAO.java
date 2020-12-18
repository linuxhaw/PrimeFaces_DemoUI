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
	
	
	public List<UserDTO> select(UserDTO dto) {
		List<UserDTO> list = new ArrayList<UserDTO>();

		try {
			PreparedStatement ps = con.prepareStatement("select * from user");
			if (!dto.getId().equals("")) {
				ps = con.prepareStatement("select * from user where id=?");
				ps.setString(1, dto.getId());
			} else if (!dto.getName().equals("")) {
				ps = con.prepareStatement("select * from user where name=?");
				ps.setString(1, dto.getName());
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserDTO res = new UserDTO();
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				res.setPassword(rs.getString("password"));
				list.add(res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
