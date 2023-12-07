package it.cab.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "contatti")
public class Contatto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="nome")
	private String nome;
	@Column(name="cognome")
	private String cognome;
	
	@OneToMany(
			mappedBy = "contatto",
			cascade = CascadeType.ALL)
	private List<Email> emails  = new ArrayList<>();
	@OneToMany(
			mappedBy = "contatto",
			cascade = CascadeType.ALL)
	private List<Telefono> telefoni = new ArrayList<>();
	
	private String username;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public List<Telefono> getTelefoni() {
		return telefoni;
	}
	public void setTelefoni(List<Telefono> telefoni) {
		this.telefoni = telefoni;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}