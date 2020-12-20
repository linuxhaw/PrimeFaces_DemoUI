package model;


import java.io.Serializable;
import java.util.List;
import java.util.Map;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;




import dao.UserDAO;
import dto.UserDTO;

@ManagedBean
@RequestScoped

public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String password;
	private String confirm;

	private List<UserDTO> userFilterList;

	public List<UserDTO> getUserFilterList() {
		return userFilterList;
	}

	public void setUserFilterList(List<UserDTO> userFilterList) {
		this.userFilterList = userFilterList;
	}


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

		if (!FacesContext.getCurrentInstance().isPostback()) {
			List list = dao.selectAll();
			return list;
		} else {
			List list = dao.selectAll();
			return list;
		}

	}

	public String save() {
		UserDTO dto = new UserDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPassword(password);

		
		if (dao.select(dto).getId()!=null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Message!", "User Already Exist!"));
			return "USR002.xhtml?faces-redirect=true?";
		} else {
			int result = dao.insert(dto);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Message!", "Successfully  Saved!"));

			return "USR002.xhtml?faces-redirect=true";
		}

	}

	public String edit(String code) {


		UserDTO dto = new UserDTO();
		dto.setId(code);
		System.out.println(dto.getId());

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


			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Message!", "Successfully Updated!."));

			return "USR002-01.xhtml?faces-redirect=true";
		} else {

			return "USR002-01.xhtml?faces-redirect=true";
		}
	}



	public void delete(String code) {
		UserDTO dto = new UserDTO();
		dto.setId(code);
		dao.delete(dto);
		FacesContext.getCurrentInstance().addMessage(null,

				new FacesMessage(FacesMessage.SEVERITY_INFO, "Message!", "Successfully Deleted!."));

	}

	public void test() {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			List<UserDTO> dto = dao.selectAll();
		}

	}

}
