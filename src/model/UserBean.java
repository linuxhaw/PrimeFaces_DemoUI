package model;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.UserDAO;
import dto.UserDTO;

@ManagedBean(name = "user")
@RequestScoped
public class UserBean {
	private String id;
	private String name;
	private String password;
	private String confirm;
	
	private UserDAO dao = new UserDAO();
	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public List<UserDTO> bookList() {
		UserDTO dto = new UserDTO();
		List list = dao.select(dto);
		return list;
	}
	
	public String save() {
		UserDTO dto = new UserDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPassword(password);
		int result = dao.insert(dto);
		if (result != 0)
			
			return "USR002.xhtml?faces-redirect=true";
		else
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info","User registered successfully!")); 
			return "USR002.xhtml?faces-redirect=true";
	}
	
}
