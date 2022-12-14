package fr.m2i.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.DigestUtils;

import fr.m2i.models.Login;
import fr.m2i.models.Planning;
import fr.m2i.models.User;

@WebServlet("/pageadmin")
public class PageAdmin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE="/WEB-INF/pages/pageAdmin.jsp";
			
    public PageAdmin() {
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory factory2 = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em2 = factory2.createEntityManager();
		
		//Récupération liste users form userTable
		List<User> userListRecup = em2.createNamedQuery("selectAllUsers", User.class).getResultList();

		//Préparation élement pour renvoi via request
		request.setAttribute("userListRecup", userListRecup);		
				
		em2.close();
		
		this.getServletContext().getRequestDispatcher(PAGE).forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory factory2 = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em2 = factory2.createEntityManager();
		
		em2.getTransaction().begin();
		
		String requete = request.getParameter("req");
		
		//création nouvel utilisateur
		if (requete.equalsIgnoreCase("new")) {
			
			//Recuperation des parametres
			String last_name = request.getParameter("last_name");
			String first_name = request.getParameter("first_name");
			String city = request.getParameter("city");
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);			

			//Date birthday_date = new Date();
			Date birthday_date = null;
			try {
				birthday_date = (Date) formatter.parse(request.getParameter("birthday_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			String phone_number = request.getParameter("phone_number");
			String email = request.getParameter("email");

			//création new User
			User user2Create = createUser(first_name, last_name, city, birthday_date, phone_number, email); 
			
			//Création login
			Login login2create = createLogin(user2Create);
			
			//Creation planning user
			Planning planning2Create = createPlanning();
						
			em2.persist(login2create);
			em2.persist(planning2Create);			
			user2Create.setPlanning(planning2Create);
			em2.persist(user2Create);	
		}
		
		//modif° des paramêtres
		if (requete.equalsIgnoreCase("mod")) {
			
			//récupération état des checkbox
			boolean compteActif = request.getParameter("compteActif") != null;
			boolean adminStatus = request.getParameter("adminStatus") != null;
			boolean autorExportPlanningStatus = request.getParameter("autorExportPlanningStatus") != null;
			boolean autorAccesPlanningParExt = request.getParameter("autorAccesPlanningParExt") != null;
			boolean autorModifPlanningParCollab = request.getParameter("autorModifPlanningParCollab") != null;		
			
			//récupération user concerné (parametre hidden)
			Integer userId = Integer.parseInt(request.getParameter("userId"));			
			Query q = em2.createNamedQuery("selectUserById", User.class);
			q.setParameter("id", userId);		
			User user2bModified = (User) q.getSingleResult();
			
			//Modification des parametres utilisateur
			modifParamUser (user2bModified, compteActif, adminStatus, autorExportPlanningStatus,
					autorAccesPlanningParExt, autorModifPlanningParCollab);				
		}
		
		//suppression utilisateur
		if (requete.equalsIgnoreCase("out")) {
			
			//récupération user concerné (parametre hidden)
			Integer userId = Integer.parseInt(request.getParameter("userId"));			
			Query q = em2.createNamedQuery("selectUserById", User.class);
			q.setParameter("id", userId);		
			

			User user2bSuppressed = (User) q.getSingleResult();			
			//Integer planningId =  user2bSuppressed.getPlanning().getId();
			
			//Query qp =  em2.createNamedQuery("selectPlanningById", Planning.class);
			//qp.setParameter("id", planningId);	
			//Planning planning2Bdeleted = (Planning) qp.getSingleResult();
					

			//récupération planning de user
			Integer planningId =  user2bSuppressed.getPlanning().getId();
			Query qp =  em2.createNamedQuery("selectPlanningById", Planning.class);
			qp.setParameter("id", planningId);	
			Planning planning2Bdeleted = (Planning) qp.getSingleResult();
			
			//récupération login de user
			Query qp2 =  em2.createNamedQuery("findLoginByUserId", Login.class);
			qp2.setParameter("id", userId);	
			Login login2Bdeleted = (Login) qp2.getSingleResult();		
			
			em2.remove(login2Bdeleted);
			em2.remove(planning2Bdeleted);			
			em2.remove(user2bSuppressed);
		}
		
		//Commit des operations
		em2.getTransaction().commit();		
		em2.close();
		
		//redirection vers pageadmin
		response.sendRedirect("/Jpa/pageadmin");
	}
	
	//Password creation
	public String createPassword(User user) {
		
		int chiffre1 = (int) (Math.random() * 10);
		int chiffre2 = (int) (Math.random() * 10);
		
		String lettersOfName = user.getFirst_name();
		int length = lettersOfName.length();
		if(length > 2) {
			lettersOfName = lettersOfName.substring(length - 2);
		} else {
			lettersOfName = lettersOfName.substring(length - 1);
		}
		
		System.out.println("userPassword: " + "user" + chiffre1 + chiffre2 + lettersOfName);
				
		return "user" + chiffre1 + chiffre2 + lettersOfName;
	}
	
	private String create(String password) {
		byte[] data = password.getBytes(StandardCharsets.UTF_8);
		String result = DigestUtils.md5DigestAsHex(data);		
		return result;
	}
	
	//Creation new user
	public User createUser(String first_name, String last_name, String city, Date birthday_date, 
			String phone_number, String email) {
		
		User user2Create = new User(first_name, last_name, city, birthday_date, phone_number,
				email, false, false);
		
		return user2Create;
	}
	
	// Creation login
	public Login createLogin(User user2Create){
		
		Login login2create = new Login();
		login2create.setLogin(user2Create.getFirst_name());
		String password = createPassword(user2Create);
		String sha = create(password);
		login2create.setPassword(sha);
		login2create.setUser(user2Create);
		
		return login2create;
	}
	
	// Creation planning user
	public Planning createPlanning() {
		
		Planning planning2Create = new Planning(false, false, false);		
		return planning2Create;
	}
	
	//Modification des parametres utilisateur
	public void modifParamUser (User user2bModified, boolean compteActif,
			boolean adminStatus, boolean autorExportPlanningStatus, 
			boolean autorAccesPlanningParExt, boolean autorModifPlanningParCollab) 
	{
		user2bModified.setCompte_actif(compteActif);
		user2bModified.setAdmin(adminStatus);
		user2bModified.getPlanning().setExport(autorExportPlanningStatus);
		user2bModified.getPlanning().setAcces(autorAccesPlanningParExt);
		user2bModified.getPlanning().setModification(autorModifPlanningParCollab);
	}
	
	
	
}
