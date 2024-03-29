package gr.aueb.mscis.sample.resource;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.TelephoneNumber;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.service.EpoptisCategoryService;

@XmlRootElement
public class EpoptisInfo {
	
	private Integer id;
	
	private String name;
	
	private String surname;
	
	private String telephone;
	
	private String email;
	
	private String password;
	
	private String category;
	
	public EpoptisInfo() {
	}
	
	public EpoptisInfo(int id, String name, String surname, String telephone, String email, String password , String category) {
		this(name, surname, telephone, email,password, category);
		this.id = id;
	}
	
	
	public EpoptisInfo(String name, String surname, String telephone, String email, String password , String category) {
		super();
		this.name = name;
		this.surname = surname;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
		this.category = category;
	}
	
	public EpoptisInfo(Epoptis e) {
		this.id = e.getId();
		this.name = e.getName();
		this.surname = e.getSurName();
		this.telephone = e.getTelephone().getTelephoneNumber();
		this.email = e.getEmail().getAddress();
		this.password = e.getPassword();
		this.category = e.getCategory().getDescription();
		
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSurname()
	{
		return surname;
	}
	
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	
    public String getTelephone() {
        return telephone;
    }
	
	public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }
	
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
    
    public String getCategory()
    {
    	return category;
    }
	
    public void setCategory(String category)
    {
    	this.category = category;
    }
    
	public static EpoptisInfo wrap(Epoptis e) {
		return new EpoptisInfo(e);
	}
	
	public static List<EpoptisInfo> wrap(List<Epoptis> epoptes) {

		List<EpoptisInfo> epoptisInfoList = new ArrayList<>();

		for (Epoptis e : epoptes) {
			epoptisInfoList.add(new EpoptisInfo(e));
		}

		return epoptisInfoList;

	}
	
	public Epoptis getEpoptis(EntityManager em) {

		Epoptis epoptis = null;

		if (id != null) {
			epoptis = em.find(Epoptis.class, id);
		} else {
			epoptis = new Epoptis();
		}

		epoptis.setName(name);
		epoptis.setSurName(surname);
		epoptis.setTelephone(new TelephoneNumber(telephone));
		epoptis.setEmail(new EmailAddress(email));
		epoptis.setPassword(password);

		EpoptisCategoryService ecs = new EpoptisCategoryService(em);
		List<EpoptisCategory> lec = ecs.findAllEpoptisCategories();
		
		if(category.equals("ΠΠ")) {
			epoptis.setCategory(lec.get(0));
		}
		if(category.equals("ΥΔ")) {
			epoptis.setCategory(lec.get(1));
		}
		
		return epoptis;
	}
}
