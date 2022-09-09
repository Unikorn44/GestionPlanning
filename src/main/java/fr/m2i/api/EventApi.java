package fr.m2i.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.m2i.models.Event;
import fr.m2i.models.Planning;
import fr.m2i.models.Planning_event;
import fr.m2i.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		
	// Ajout d'un nouvel event
	@POST
	@Path("/create/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createEvent(@PathParam("userId") int id, Event event)
	{
		System.out.println("je suis dans le create Event");
		
		factory = Persistence.createEntityManagerFactory("UnityPersist");
		em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		Query q = em.createNamedQuery("selectPlanningByUserId", Planning.class);
		q.setParameter("id", id);
		Planning userPlanning = (Planning) q.getSingleResult();
		ArrayList<Planning> arrayPlanning = new ArrayList<Planning>();
		
		arrayPlanning.add(userPlanning);
		
		Event newEvent = new Event(
			event.getTitle(),
			event.getDate_event(),
			event.getStart_time(),
			event.getEnd_time(),
			event.getDescription(),
			arrayPlanning
		);
		
		em.persist(newEvent);
				
		em.getTransaction().commit();
		em.close();
		factory.close();
	}
}
