package it.cab.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.cab.data.model.Contatto;
import it.cab.data.model.Email;
import it.cab.data.model.Telefono;
import it.cab.data.repository.ContattoRepository;
import it.cab.data.repository.EmailRepository;
import it.cab.data.repository.TelefonoRepository;
import it.cab.expection.ResourceNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ContattoService {
	
	private final static Logger log = LoggerFactory.getLogger(ContattoService.class);
	
	@Autowired ContattoRepository contattoRepository;
	@Autowired EmailRepository emailRepository;
	@Autowired TelefonoRepository telefonoRepository;
	
	public List<Contatto> getContatti() {
		return contattoRepository.findAll();
	}

	public Contatto getContattoById(Long contattoId) {
		return contattoRepository.findById(contattoId).orElseThrow(() -> new ResourceNotFoundException("Contatto", "id", contattoId));
	}

	@Transactional
	public Contatto createContatto(Contatto contatto) {
		
		Contatto tempContatto = contattoRepository.save(contatto);

		for(Email tempEmail : tempContatto.getEmails()) {
			if (tempEmail.getContatto() == null) {
				tempEmail.setContatto(tempContatto);
				log.info(tempEmail + " ");
				emailRepository.save(tempEmail);
				
			}
		}
		
		for(Telefono tempTelefono : tempContatto.getTelefoni()) {
			if (tempTelefono.getContatto() == null) {
				tempTelefono.setContatto(tempContatto);
				log.info(tempTelefono + " ");
				telefonoRepository.save(tempTelefono);
			}
		}
		
		return tempContatto;
	}

	public Contatto updateContatto(Long contattoId, Contatto contattoDetails) {
		Contatto contatto = contattoRepository.findById(contattoId)
				.orElseThrow(() -> new ResourceNotFoundException("Contatto", "id", contattoId));
		contatto.setNome(contattoDetails.getNome());
		contatto.setCognome(contattoDetails.getCognome());
		
		List<Email> emails = new ArrayList<Email>();
		for(Email tempEmail : contattoDetails.getEmails()) {
			if (tempEmail.getContatto() == null) {
				tempEmail.setContatto(contattoDetails);
				emails.add(tempEmail);
			}
		}
		
		List<Telefono> telefoni = new ArrayList<Telefono>();
		for(Telefono tempTelefono : contattoDetails.getTelefoni()) {
			if (tempTelefono.getContatto() == null) {
				tempTelefono.setContatto(contattoDetails);
				telefoni.add(tempTelefono);
			}
		}
		
		contatto.setEmails(emails);
		contatto.setTelefoni(telefoni);
		Contatto updatedContatto = contattoRepository.save(contatto);
		return updatedContatto;
	}

	public boolean deleteContatto(Long contattoId) {
		Contatto contatto = contattoRepository.findById(contattoId)
				.orElseThrow(() -> new ResourceNotFoundException("Contatto", "id", contattoId));
		contattoRepository.delete(contatto);
		return true;
	}
	
	public Page<Contatto> findPaginated(Pageable pageable, String username, String keyword) {
		List<Contatto> contatti = new ArrayList<Contatto>();
		
		if(keyword != null) {
        	contatti = contattoRepository.findByKeyword(keyword, username);
        } else {
        	contatti = contattoRepository.findByUsername(username);
        }
        
		int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Contatto> list;

        if (contatti.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, contatti.size());
            list = contatti.subList(startItem, toIndex);
        }

        Page<Contatto> contattoPage
          = new PageImpl<Contatto>(list, PageRequest.of(currentPage, pageSize), contatti.size());

        return contattoPage;
    }

}
