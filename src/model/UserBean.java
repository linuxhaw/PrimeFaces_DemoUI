package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.MessageInterpolator.Context;

import dao.UserDAO;
import dto.UserDTO;

@ManagedBean
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

	public List<UserDTO> userList() {
		List list = dao.selectAll();
		return list;
	}

	public String save() {
		UserDTO dto = new UserDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPassword(password);
		String s = " ";
		List<UserDTO> f = dao.selectAll();
		for (UserDTO d : f) {
			s = d.getId();
		}
		if (s.equals(dto.getId())) {
			String message = "Error";
			return "USR002.xhtml?faces-redirect=true?message=" + message;
		} else {
			int result = dao.insert(dto);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Message!", "Successfully Saved!"));
			return "USR002.xhtml?faces-redirect=true";
		}

	}

	public String edit(String code) {
		UserDTO dto = new UserDTO();
		dto.setId(code);
		UserDTO one = dao.select(dto);
		UserBean bean = new UserBean();
		bean.setId(one.getId());
		bean.setName(one.getName());
		bean.setPassword(one.getPassword());
		bean.setConfirm(one.getPassword());
		sessionMap.put("editUser", bean);
		return "USR002-01.xhtml?faces-redirect=true";

	}

	public String update(UserBean user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setPassword(user.getPassword());
		int result = dao.update(dto);
		if (result != 0) {

			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Message!", "Successfully Updated!."));
			return "USR002-01.xhtml?faces-redirect=true";
		} else {

			return "USR002-01.xhtml?faces-redirect=true";
		}
	}

	public String select() {
		UserDTO d = new UserDTO();
		d.setId(id);
		d.setName(name);
		UserDTO dto = dao.select(d);
		return "USR001.xhtml?faces-redirect=true";
	}

	public void delete(String code) {
		UserDTO dto = new UserDTO();
		dto.setId(code);
		dao.delete(dto);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Save!", "Successfully Deleted!."));
	}
	//,HttpServletRequest request
	public String login(UserBean user,HttpServletRequest request) {
		UserDTO u1= new UserDTO();
		u1=dao.selectOne(user.getId());
		if (u1.getId()==null) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Message!", "User Not found"));
			return "";
		}
		if (u1.getPassword().equals(user.getPassword())) {
			request.getSession().setAttribute("sesUser", user);
			return "index.xhtml?faces-redirect=true";
		}else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Message!", "Password not correct"));
			return "";
		}
		
	}

}
