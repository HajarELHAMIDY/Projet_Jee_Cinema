package org.glsid.cinema.dao;

import org.glsid.cinema.entitie.Categorie;
import org.glsid.cinema.entitie.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface SalleRepository extends JpaRepository<Salle,Long> {
	public Salle findByName(String name);
}
