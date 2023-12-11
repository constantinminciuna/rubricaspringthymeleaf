package it.cab.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.cab.data.model.Contatto;


@Repository
public interface ContattoRepository extends JpaRepository<Contatto, Long> {
	List<Contatto> findByUsername(String username);
	
	@Query(value = "select * from contatti c where c.nome like %:keyword% or c.cognome like %:keyword% and c.username = :username", 
			nativeQuery = true)
	List<Contatto> findByKeyword(@Param("keyword") String keyword, @Param("username") String username);
	
}
