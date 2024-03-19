package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Membro implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 private String nome;
	 private LocalDate dataNascimento;
	 private String cpf;
	 private String sexo;
	 @OneToOne(cascade={CascadeType.ALL})
	 @JoinColumn(name="id_carteirinha")
	 private Carteirinha carteirinha;
	 @OneToOne(cascade={CascadeType.ALL})
	 @JoinColumn(name="id_contato")
	 private Contato contato;
	 @OneToOne(cascade={CascadeType.ALL})
	 @JoinColumn(name="id_endereco")
	 private Endereco endereco;

}
