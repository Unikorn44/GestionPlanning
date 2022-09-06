package fr.m2i.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		
		//récupération état des checkbox
		boolean compteActif = request.getParameter("compteActif") != null;
		boolean adminStatus = request.getParameter("adminStatus") != null;
		boolean autorExportPlanningStatus = request.getParameter("autorExportPlanningStatus") != null;
		boolean autorAccesPlanningParExt = request.getParameter("autorAccesPlanningParExt") != null;
		boolean autorModifPlanningParCollab = request.getParameter("autorModifPlanningParCollab") != null;		
		
		//récupération user concerné (parametre hidden
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		
		Query q = em2.createNamedQuery("selectUserById", User.class);
		q.setParameter("id", userId);		
		User user2bModified = (User) q.getSingleResult();
		 
		//modif° des paramêtres
		user2bModified.setCompte_actif(compteActif);
		user2bModified.setAdmin(adminStatus);
		user2bModified.getPlanning().setExport(autorExportPlanningStatus);
		user2bModified.getPlanning().setAcces(autorAccesPlanningParExt);
		user2bModified.getPlanning().setModification(autorModifPlanningParCollab);
		
		//em2.merge(user2bModified);
		
		em2.getTransaction().commit();
		
		em2.close();
		
		//response.sendRedirect(request.getContextPath() + PAGE);
		response.sendRedirect("/Jpa/pageadmin");
	}

}
