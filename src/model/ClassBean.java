package model;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
		if(id.equals("")||name.equals("")){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Input Requier"));
			return "classRegistion.xhtml?faces-redirect=true";
		}
		ClassDTO dto = new ClassDTO();
		dto.setId(id);
		dto.setName(name);
		if (dao.selectOne(id).getId()!=null) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Class already exit!"));
			return "classRegistion.xhtml?faces-redirect=true";

		} else {
			int res = dao.insert(dto);
			if (res > 0) {
				request.setAttribute("msg", "Register Successfull!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully added"));
				List<ClassDTO> list1 = dao.selectAll();
				request.getSession().setAttribute("classlist", list1);
				return "classRegistion.xhtml?faces-redirect=true";
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Input Error"));
			return "classRegistion.xhtml?faces-redirect=true";

		}
	}
	public List<ClassDTO> classList(){
		
		List<ClassDTO> list=dao.selectAll();
		return list;
	}
	
	/*public void delete(String cid) {
		ClassDTO dto=new ClassDTO();
		dto.setId(cid);
		dao.delete(dto);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Save!", "Successfully Deleted!."));
	}
	*/

}