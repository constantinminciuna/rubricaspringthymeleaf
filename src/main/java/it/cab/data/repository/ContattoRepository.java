package it.cab.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cab.data.model.Contatto;


@Repository
public interface ContattoRepository extends JpaRepository<Contatto, Long> {
	List<Contatto> findByUsername(String username);
}
