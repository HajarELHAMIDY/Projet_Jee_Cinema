package org.glsid.cinema.dao;

import org.glsid.cinema.entitie.Categorie;
import org.glsid.cinema.entitie.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
	public Categorie findByName(String name);
}
