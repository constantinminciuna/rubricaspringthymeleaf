package it.cab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.cab.data.model.Telefono;
import it.cab.data.repository.TelefonoRepository;
import it.cab.expection.ResourceNotFoundException;

@Service
public class TelefonoService {
	@Autowired TelefonoRepository telefonoRepository;
	
	public List<Telefono> getTelefoni() {
		return telefonoRepository.findAll();
	}

	public Telefono getTelefonoById(Long telefonoId) {
		return telefonoRepository.findById(telefonoId).orElseThrow(() -> new ResourceNotFoundException("Telefono", "id", telefonoId));
	}

	public Telefono createTelefono(Telefono telefono) {
		return telefonoRepository.save(telefono);
	}

	public Telefono updateTelefono(Long telefonoId, Telefono telefonoDetails) {
		Telefono telefono = telefonoRepository.findById(telefonoId)
				.orElseThrow(() -> new ResourceNotFoundException("Telefono", "id", telefonoId));
		telefono.setId(telefonoDetails.getId());
		telefono.setContatto(telefonoDetails.getContatto());
		telefono.setNumero(telefonoDetails.getNumero());
		telefono.setDescrizione(telefonoDetails.getDescrizione());
		Telefono updatedTelefono = telefonoRepository.save(telefono);
		return updatedTelefono;
	}

	public boolean deleteTelefono(Long telefonoId) {
		Telefono telefono = telefonoRepository.findById(telefonoId)
				.orElseThrow(() -> new ResourceNotFoundException("Telefono", "id", telefonoId));
		telefonoRepository.delete(telefono);
		return true;
	}
}
