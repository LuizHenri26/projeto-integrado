package br.com.lhlibrarymanagement.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "O campo nome é obrigatório!")
	@Size(min = 2, max = 65, message = "O nome deve conter entre 2 a 65 caracteres")
	private String nome;
	@NotBlank(message = "O campo login do usuário é obrigatório!")
	private String login;
	@NotEmpty(message = "O campo senha é obrigatório!")
	@Size(min = 6, message = "A senha deve conter no minímo 8 digitos.")
	private String senha;
	private boolean ativo;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_perfil", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_perfil"))
	private List<Perfil> perfis;

}
