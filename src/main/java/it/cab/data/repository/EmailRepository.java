package it.cab.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cab.data.model.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}
