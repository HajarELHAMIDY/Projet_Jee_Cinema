package org.glsid.cinema.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.glsid.cinema.dao.CategorieRepository;
import org.glsid.cinema.dao.CinemaRepository;
import org.glsid.cinema.dao.FilmRepository;
import org.glsid.cinema.dao.PlaceRepository;
import org.glsid.cinema.dao.ProjectionRepository;
import org.glsid.cinema.dao.SalleRepository;
import org.glsid.cinema.dao.TicketRepository;
import org.glsid.cinema.dao.VilleRepository;
import org.glsid.cinema.entitie.Categorie;
import org.glsid.cinema.entitie.Cinema;
import org.glsid.cinema.entitie.Film;
import org.glsid.cinema.entitie.Place;
import org.glsid.cinema.entitie.Projection;
import org.glsid.cinema.entitie.Salle;
import org.glsid.cinema.entitie.Ticket;
import org.glsid.cinema.entitie.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private  FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CategorieRepository cateRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    
    @GetMapping(value = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable (name ="id")Long id)throws Exception{
        Film film=filmRepository.findById(id).get();
        String photoName=film.getPhoto();
        File file=new File(System.getProperty("user.home")+"/cinema/images/"+photoName);
        Path path= Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
    @PostMapping(path="/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file,@PathVariable Long id) throws Exception {
		Film p = filmRepository.findById(id).get();
		p.setPhoto(id+".jpg");
		Files.write(Paths.get(System.getProperty("user.home")+"/cinema/images/"+p.getPhoto()), file.getBytes());
		filmRepository.save(p);
		
		
	}
    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket> tickets=new ArrayList<>();
        ticketForm.getTickets().forEach(id->{
            Ticket ticket=ticketRepository.findById(id).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReserve(true);
            ticket.setCodePayement(ticketForm.getCodePayment());
            ticketRepository.save(ticket);
            tickets.add(ticket);
        });
        return tickets;
    }
    @DeleteMapping("/villes/cancel/{name}")
	public List<Ville> deleteEmployee(@PathVariable String name)
			throws ResourceNotFoundException {
		Ville ville = villeRepository.findByName(name);

		villeRepository.delete(ville);
		
		return villeRepository.findAll();
	}

    @PostMapping("/villes/register")
    @Transactional
    public String register(@RequestBody Ville ville) {
    	villeRepository.save(ville);
    	return "La ville "+ville.getName()+" est bien ajouter !!!";
    }
    @DeleteMapping("/categories/cancel/{name}")
  	public List<Ville> deleteCategory(@PathVariable String name)
  			throws ResourceNotFoundException {
  		Categorie cat = cateRepository.findByName(name);

  		cateRepository.delete(cat);
  		
  		return villeRepository.findAll();
  	}

      @PostMapping("/categories/register")
      @Transactional
      public String registerCat(@RequestBody Categorie category) {
      	cateRepository.save(category);
      	return "La categorie "+category.getName()+" est bien ajouter !!!";
      }
      @PutMapping("/films/update")
      public Film updateFilm(@RequestBody  Film film) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return filmRepository.save(film);
      }
      @DeleteMapping("/films/cancel/{id}")
  	public List<Film> deleteFilm(@PathVariable Long  id)
  			throws ResourceNotFoundException {
  		Film film = filmRepository.findById(id).get();

  		filmRepository.delete(film);
  		
  		return filmRepository.findAll();
  	}

      @PostMapping("/films/register")
      @Transactional
      public String registerFilm(@RequestBody Film film) {
      	filmRepository.save(film);
      	return "Le film "+film.getTitre()+" est bien ajouter !!!";
      }
    
      @PutMapping("/villes/update")
      public Ville updateVille(@RequestBody  Ville ville) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return villeRepository.save(ville);
      }
      
      
      @PutMapping("/categories/update")
      public Categorie updateCategory(@RequestBody  Categorie category) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return cateRepository.save(category);
      }
      @PutMapping("/places/update")
      public Place updatePlace(@RequestBody  Place place) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return placeRepository.save(place);
      }
      @DeleteMapping("/places/cancel/{id}")
  	public List<Place> deletePlace(@PathVariable Long  id)
  			throws ResourceNotFoundException {
  		Place place = placeRepository.findById(id).get();

  		placeRepository.delete(place);
  		
  		return placeRepository.findAll();
  	}

      @PostMapping("/places/register")
      @Transactional
      public String registePlace(@RequestBody Place place) {
      	placeRepository.save(place);
      	return "La place  est bien ajouter !!!";
      }
    
      @PutMapping("/salles/update")
      public Salle updateSalle(@RequestBody  Salle salle) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return salleRepository.save(salle);
      }
      @DeleteMapping("/salles/cancel/{name}")
  	public List<Salle> deleteSalle(@PathVariable String  name)
  			throws ResourceNotFoundException {
  		Salle salle = salleRepository.findByName(name);

  		salleRepository.delete(salle);
  		
  		return salleRepository.findAll();
  	}

      @PostMapping("/salles/register")
      @Transactional
      public String registeSalle(@RequestBody Salle salle) {
      	salleRepository.save(salle);
      	return "La salle  est bien ajouter !!!";
      }
      @PutMapping("/projections/update")
      public Projection updateProjection(@RequestBody  Projection pr) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return projectionRepository.save(pr);
      }
      @DeleteMapping("/projections/cancel/{id}")
  	public List<Projection> deleteProjection(@PathVariable Long  id)
  			throws ResourceNotFoundException {
  		Projection pr = projectionRepository.findById(id).get();

  		projectionRepository.delete(pr);
  		
  		return projectionRepository.findAll();
  	}

      @PostMapping("/projections/register")
      @Transactional
      public String registeProjection(@RequestBody Projection pr) {
    	  projectionRepository.save(pr);
      	return "La projection  est bien ajouter !!!";
      }
      
      @PutMapping("/tickets/update")
      public Ticket updateTicket(@RequestBody  Ticket ticket) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return ticketRepository.save(ticket);
      }
      @DeleteMapping("/tickets/cancel/{id}")
  	public List<Ticket> deleteTicket(@PathVariable Long  id)
  			throws ResourceNotFoundException {
  		Ticket ticket = ticketRepository.findById(id).get();

  		ticketRepository.delete(ticket);
  		
  		return ticketRepository.findAll();
  	}

      @PostMapping("/tickets/register")
      @Transactional
      public String registeTickets(@RequestBody Ticket ticket) {
    	  ticketRepository.save(ticket);
      	return "Le ticket  est bien ajouter !!!";
      }
      
      @PutMapping("/cinemas/update")
      public Cinema updateCinema(@RequestBody  Cinema salle) {
    	 // Ville v = villeRepository.findByName(ville.getName());
    	  
    	  return cinemaRepository.save(salle);
      }
      @DeleteMapping("/salles/cancel/{id}")
  	public List<Cinema> deleteCinema(@PathVariable Long  id)
  			throws ResourceNotFoundException {
    	  Cinema salle = cinemaRepository.findById(id).get();

    	  cinemaRepository.delete(salle);
  		
  		return cinemaRepository.findAll();
  	}

      @PostMapping("/cinemas/register")
      @Transactional
      public String registeCinema(@RequestBody Cinema salle) {
      	cinemaRepository.save(salle);
      	return "Le cinema  est bien ajouter !!!";
      }
      
      
  
}

@Data
class TicketForm{
    private String nomClient;
    private int codePayment;
    private List<Long> tickets=new ArrayList<>();
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public int getCodePayment() {
		return codePayment;
	}
	public void setCodePayment(int codePayment) {
		this.codePayment = codePayment;
	}
	public List<Long> getTickets() {
		return tickets;
	}
	public void setTickets(List<Long> tickets) {
		this.tickets = tickets;
	}
    
}
