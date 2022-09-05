package fr.m2i.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
		
		System.out.println("sup?");
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
		
		String requete = request.getParameter("req");
		System.out.println("La requête POST est" + requete);
	
	}

}
