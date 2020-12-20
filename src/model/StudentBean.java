package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.ClassDAO;
import dao.StudentDAO;
import dto.ClassDTO;
import dto.StudentDTO;
@ManagedBean
@RequestScoped
public class StudentBean {
	private String id;
	private String name;
	private String className;
	private Date registerDate;
	private String status;

	private StudentDAO dao = new StudentDAO();
	private ClassDAO cdao=new ClassDAO();

	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date string) {
		this.registerDate = string;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ClassDTO> classlist(){
		ClassDTO dto = new ClassDTO();
		dto.setId("");
		dto.setName("");
		List<ClassDTO> list=cdao.select(dto);
		return list;
	}
	public String save() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		StudentDTO dto = new StudentDTO();
		dto.setStudentId(id);
		dto.setStudentName(name);
		dto.setClassName(className);
		dto.setRegisterDate(dateFormat.format(registerDate));
		dto.setStatus(status);
		if (dao.selectOne(id).getId()!=null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Student already exit!"));
			return "";

		}else {
			int res = dao.insert(dto);
			if (res > 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully added"));
				return "";
			}
		}


		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Input Error"));
		return "";
	}

	public List<StudentBean> student;

	public void Select1() {
		List<StudentBean> list=dao.selectAll();
		student=list;
	}

	public List<StudentBean> Select() {
		List<StudentBean> list=dao.selectAll();
		return list;

	}

	public String edit(String id) {
		StudentBean stu=new StudentBean();
		stu=dao.selectOne(id);
		StudentBean bean = new StudentBean();
		bean.setId(stu.getId());
		bean.setName(stu.getName());
		bean.setClassName(stu.getClassName());
		bean.setRegisterDate(stu.getRegisterDate());
		bean.setStatus(stu.getStatus());
		sessionMap.put("editStudent", bean);
		return "/BUD002-01.xhtml?faces-redirect=true";


	}

	public String update(StudentBean stu) {
		StudentDTO dto = new StudentDTO();
		dto.setStudentId(stu.getId());
		dto.setStudentName(stu.getName());
		dto.setClassName(stu.getClassName());
		dto.setRegisterDate(dateFormat.format(stu.getRegisterDate()));
		dto.setStatus(stu.getStatus());
		int result = dao.update(dto);
		System.out.println(result);
		if (result != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data Updated"));
			return "";
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Cannot update"));
			return "";
		}
	}

	public String delete(StudentBean stu) {
		StudentDTO dto = new StudentDTO();
		dto.setStudentId(stu.getId());
		int result = dao.deleteData(dto);
		if (result != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cannot"));
			return "BUD001.xhtml?faces-redirect=true";
			//return "index.xhtml?faces-redirect=true";
		}
		else
			return "";

	}
}
