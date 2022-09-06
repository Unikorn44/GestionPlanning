package fr.m2i.servlets;

import java.io.IOException;

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

import fr.m2i.models.Planning;
import fr.m2i.models.User;

/**
 * Servlet implementation class PageAdmin
 */
@WebServlet("/pageadmin")
public class PageAdmin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String PAGE="/WEB-INF/pages/pageAdmin.jsp";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageAdmin() {
    	super();
    	// TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory factory2 = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em2 = factory2.createEntityManager();
		
		em2.getTransaction().begin();
		
		String requete = request.getParameter("req");
		
		//création nouvel utilisateur
		if (requete.equalsIgnoreCase("new")) {
			
			//Récupération des parametres
			String last_name = request.getParameter("last_name");
			String first_name = request.getParameter("first_name");
			String city = request.getParameter("city");
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);			

			//Date birthday_date = new Date();
			Date birthday_date = null;
			try {
				birthday_date = (Date) formatter.parse(request.getParameter("birthday_date"));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			String phone_number = request.getParameter("phone_number");
			String email = request.getParameter("email");

			//Création nouvel utilisateur
			User user2Create = new User();
			
			user2Create.setFirst_name(first_name);
			user2Create.setLast_name(last_name);
			user2Create.setCity(city);
			user2Create.setBirthday_date(birthday_date);
			user2Create.setPhone_number(phone_number);
			user2Create.setEmail(email);
			
			user2Create.setCompte_actif(false);
			user2Create.setAdmin(false);
			
			Planning planning2Create = new Planning();
			planning2Create.setExport(false);
			planning2Create.setAcces(false);
			planning2Create.setModification(false);
			
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
			user2bModified.setCompte_actif(compteActif);
			user2bModified.setAdmin(adminStatus);
			user2bModified.getPlanning().setExport(autorExportPlanningStatus);
			user2bModified.getPlanning().setAcces(autorAccesPlanningParExt);
			user2bModified.getPlanning().setModification(autorModifPlanningParCollab);
		}
		
		//suppression utilisateur
		if (requete.equalsIgnoreCase("out")) {
			
			//récupération user concerné (parametre hidden)
			Integer userId = Integer.parseInt(request.getParameter("userId"));			
			Query q = em2.createNamedQuery("selectUserById", User.class);
			q.setParameter("id", userId);		
			User user2bSuppressed = (User) q.getSingleResult();			
			
			Integer planningId =  user2bSuppressed.getPlanning().getId();
			//Query qp =  em2.createNamedQuery("selectPlanningById", Planning.class);
			//qp.setParameter("id", planningId);	
			//Planning planning2Bdeleted = (Planning) qp.getSingleResult();
					
			em2.remove(user2bSuppressed);
			//em2.remove(planning2Bdeleted);
			em2.createNamedQuery("deleteNatifTest").setParameter("id", planningId).executeUpdate();					
		}
		
		//Commit des opérations
		em2.getTransaction().commit();		
		em2.close();
		
		//redirection vers pageadmin
		response.sendRedirect("/Jpa/pageadmin");
	}

}
