package fr.m2i.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.m2i.models.Event;
import fr.m2i.models.Planning_event;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Path("/event")
public class EventApi {
	EntityManagerFactory factory;
	EntityManager em;
	
	// Récupération de tous les events
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> findAllEvents() {
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		Query q = em.createNamedQuery("selectAllEvents", Event.class);

		@SuppressWarnings("unchecked")
		List<Event> events = q.getResultList();
			
		factory.close();
			
		return events;
	}
	
	// Récupération de tous les events d'un utilisateur
		@GET
		@Path("/user/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public List<Event> findAllEventsByUser(@PathParam("id") int id) {
			
			factory = Persistence.createEntityManagerFactory("UnityPersist");
			em = factory.createEntityManager();
			
			Query q = em.createNamedQuery("selectEventsByUserId", Event.class);
			q.setParameter("id", id);
			
			System.out.println("===== ID ===== " + id);
			
			@SuppressWarnings("unchecked")
			List<Event> events = q.getResultList();
				
			factory.close();
				
			return events;
		}
		// Récupération de tous les events d'un utilisateur
				@GET
				@Path("/pe")
				@Produces(MediaType.APPLICATION_JSON)
				public List<Planning_event> findAllPlanningEvents() {
					
					factory = Persistence.createEntityManagerFactory("UnityPersist");
					em = factory.createEntityManager();
					
					Query q = em.createNamedQuery("selectAllPlanningEvents", Planning_event.class);
					
					@SuppressWarnings("unchecked")
					List<Planning_event> pevents = q.getResultList();
						
					factory.close();
						
					return pevents;
				}
}
