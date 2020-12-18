package model;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletRequest;

import dto.ClassDTO;

import dao.ClassDAO;

@ManagedBean
@RequestScoped
public class ClassBean {

	private String id;
	private String name;

	private ClassDAO dao = new ClassDAO();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String save(HttpServletRequest request) {

		ClassDTO dto = new ClassDTO();
		dto.setId(id);
		dto.setName(name);

		List<ClassDTO> list = dao.select(dto);
		if (list.size() != 0) {
			request.setAttribute("err", "Class already exit!");
			return "classRegistion.xhtml?faces-redirect=true";

		} else {
			int res = dao.insert(dto);
			if (res > 0) {
				request.setAttribute("msg", "Register Successfull!");
				return "classRegistion.xhtml?faces-redirect=true";
			}

		}

		return "classRegistion.xhtml?faces-redirect=true";
	}

}