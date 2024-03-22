package br.com.lhlibrarymanagement.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Perfil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@ManyToMany(mappedBy = "perfis", fetch = FetchType.EAGER)
	private List<Usuario> usuarios;

}
