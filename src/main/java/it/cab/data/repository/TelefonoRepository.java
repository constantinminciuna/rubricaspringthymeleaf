package it.cab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cab.data.model.Telefono;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Long> {

}
