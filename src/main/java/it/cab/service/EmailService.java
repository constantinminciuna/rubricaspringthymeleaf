package it.cab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.cab.data.model.Email;
import it.cab.data.repository.EmailRepository;
import it.cab.expection.ResourceNotFoundException;

@Service
public class EmailService {
	@Autowired EmailRepository emailRepository;
	
	public List<Email> getEmails() {
		return emailRepository.findAll();
	}

	public Email getEmailById(Long emailId) {
		return emailRepository.findById(emailId).orElseThrow(() -> new ResourceNotFoundException("Email", "id", emailId));
	}

	public Email createEmail(Email email) {
		return emailRepository.save(email);
	}

	public Email updateEmail(Long emailId, Email emailDetails) {
		Email email = emailRepository.findById(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("Email", "id", emailId));
		email.setId(emailDetails.getId());
		email.setContatto(emailDetails.getContatto());
		email.setMail(emailDetails.getMail());
		Email updatedEmail = emailRepository.save(email);
		return updatedEmail;
	}

	public boolean deleteEmail(Long emailId) {
		Email email = emailRepository.findById(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("Email", "id", emailId));
		emailRepository.delete(email);
		return true;
	}
}
