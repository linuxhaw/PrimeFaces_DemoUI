package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClassDTO;


public class ClassDAO {
	static Connection con = null;
	static {
		con = MyConnection.getConnection();
	}

	public int insert(ClassDTO dto) {
		int result = 0;
		String sql = "insert into class (id,name) values(?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			result = ps.executeUpdate();
			result =1;
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return result;
	}

	public List<ClassDTO> select(ClassDTO dto) {
		List<ClassDTO> list = new ArrayList<ClassDTO>();

		try {
			PreparedStatement ps = con.prepareStatement("select * from class");
			if (!dto.getId().equals("") || !dto.getName().equals("")) {
				ps = con.prepareStatement("select * from class where id=? or name=?");
				ps.setString(1, dto.getId());
				ps.setString(2, dto.getName());
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassDTO res = new ClassDTO();
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				list.add(res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ClassDTO> selectAll() {
		List<ClassDTO> list = new ArrayList<ClassDTO>();
		try {
			PreparedStatement ps = con.prepareStatement("select * from class");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClassDTO res = new ClassDTO();
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
				list.add(res);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ClassDTO selectOne(String id) {
		try {
			PreparedStatement ps = con.prepareStatement("select * from class where id=?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			ClassDTO res = new ClassDTO();
			while (rs.next()) {
				res.setId(rs.getString("id"));
				res.setName(rs.getString("name"));
			}
			return res;
		} catch (SQLException e) {
			return null;
		}

	}

	
}
