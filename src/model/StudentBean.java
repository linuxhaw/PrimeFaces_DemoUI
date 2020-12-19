package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
	
	private ClassDAO daoc = new ClassDAO();
	

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
		List<ClassDTO> list=daoc.select(dto);
		return list;
	}
	
	public String save() {
		if (id==null) {
			System.out.println(id);
			return"";
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		
		StudentDTO dto = new StudentDTO();
		dto.setStudentId(id);
		dto.setStudentName(name);
		dto.setClassName(className);
		dto.setRegisterDate(dateFormat.format(registerDate));
		dto.setStatus(status);
		if (dao.selectOne(id).getId()!=null) {			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Class already exit!"));
			return "classRegistion.xhtml?faces-redirect=true";

		}else {
			int res = dao.insert(dto);
			if (res > 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully added"));
				return "classRegistion.xhtml?faces-redirect=true";
			}
		}
		
		
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Input Error"));
		return "";
	}
	
	

	/*
	 * public List<Car> createCars(int size) { List<Car> list = new
	 * ArrayList<Car>(); for(int i = 0 ; i < size ; i++) { list.add(new
	 * Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(),
	 * getRandomPrice(), getRandomSoldState())); }
	 * 
	 * return list; }
	 */
	
	public List<StudentBean> Select() {
		List<StudentBean> list=dao.selectAll();
		System.out.println(list.size());
		//ClassDTO dto = new ClassDTO();
		return list;
		
		
		
	}
	
}
