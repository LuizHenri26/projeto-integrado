package br.com.lhlibrarymanagement.enums;

import lombok.Getter;

@Getter
public enum StatusENUM {

	DISPONIVEL("Disponível"), EMPRESTADO("Emprestado"), DEVOLVIDO("Devolvido");

	private String descricao;

	private StatusENUM(String descricao) {
		this.descricao = descricao;
	}
}
