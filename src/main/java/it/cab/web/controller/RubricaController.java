package it.cab.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.cab.data.model.Contatto;
import it.cab.data.model.Email;
import it.cab.data.model.Telefono;
import it.cab.service.ContattoService;
import it.cab.service.EmailService;
import it.cab.service.TelefonoService;

@Controller
@RequestMapping("/contatti")
public class RubricaController {
	
	@Autowired ContattoService contattoService;
	@Autowired EmailService emailService;
	@Autowired TelefonoService telefonoService;
	
	@GetMapping(path="/{id}")
	public String getContatto(@PathVariable("id") long contattoId, Model model, Principal principal) {
		Contatto contatto = contattoService.getContattoById(contattoId);
		List<Email> emails = contatto.getEmails();
		List<Telefono> telefoni = contatto.getTelefoni();
		
		contatto.setUsername(principal.getName());
		
		model.addAttribute("contatto", contatto);
		model.addAttribute("emails", emails);
		model.addAttribute("telefoni", telefoni);
		
		return "contatto_details";
	}
	
	@PostMapping("/aggiorna")
    public String aggiornaContatto(@ModelAttribute Contatto contatto, Principal principal) {
        contatto.setUsername(principal.getName());
		contattoService.updateContatto(contatto.getId(), contatto);
        return "redirect:/contatti/list?updated"; // Redirect a una pagina di conferma
    }
	
	@GetMapping("/creacontatto")
	public String createContatto(Model model, Principal principal) {
		Contatto contatto = new Contatto();
		contatto.setNome("");
		contatto.setCognome("");
		
		Email email = new Email();
		Telefono telefono = new Telefono();
		email.setMail("");
		telefono.setNumero("");
		telefono.setDescrizione("");
		
		List<Email> emails = new ArrayList<Email>();
		List<Telefono> telefoni = new ArrayList<Telefono>();
		
		emails.add(email);
		telefoni.add(telefono);
		
		contatto.setEmails(emails);
		contatto.setTelefoni(telefoni);
		
		contatto.setUsername(principal.getName());
		
		model.addAttribute("contatto", contatto);
		
		return "crea_contatto";
	}
	
	@PostMapping("/crea")
    public String creaContatto(Principal principal, @ModelAttribute Contatto contatto) 
	{
		contatto.setUsername(principal.getName());

        contattoService.createContatto(contatto);
		
        return "redirect:/contatti/list?created"; // Redirect a una pagina di conferma
    }
	
	@PostMapping("/delete")
    public String eliminaContatto(@RequestParam("id") Long contattoId) {
        contattoService.deleteContatto(contattoId);
        return "redirect:/contatti/list?deleted";
    }
	
	@PostMapping("/deleteemail")
    public String eliminaEmail(@RequestParam("id") Long emailId,
    		@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer
    ) {
        emailService.deleteEmail(emailId);
        return "redirect:" + referrer;
    }
	
	@PostMapping("/deletetelefono")
    public String eliminaTelefono(@RequestParam("id") Long telefonoId,
    		@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer
    ) {
        telefonoService.deleteTelefono(telefonoId);
        return "redirect:" + referrer;
    }
	
	@GetMapping("/list")
    public String listContatti(Model model, 
    		@RequestParam("page") Optional<Integer> page, 
    		@RequestParam("size") Optional<Integer> size,
    		Principal principal,
    		@RequestParam("keyword") Optional<String> keyword
    ) {
        final int currentPage = page.orElse(1);
        final int pageSize = size.orElse(8);
        
        
        Page<Contatto> contattoPage = Page.empty();
        
        if(keyword.isPresent()) {
        	contattoPage = contattoService.findPaginated(PageRequest.of(currentPage - 1, pageSize), principal.getName(), keyword.get());
        } else {
        	contattoPage = contattoService.findPaginated(PageRequest.of(currentPage - 1, pageSize), principal.getName(), null);
        }
        
        model.addAttribute("contattoPage", contattoPage);

        int totalPages = contattoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "list";
    }

}
