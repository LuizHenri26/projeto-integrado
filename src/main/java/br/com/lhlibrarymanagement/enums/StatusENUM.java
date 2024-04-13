package br.com.lhlibrarymanagement.enums;

import lombok.Getter;

@Getter
public enum StatusENUM {

	DISPONIVEL("Disponivel"), EMPRESTADO("Emprestado"), DEVOLVIDO("Devolvido");

	private String descricao;

	private StatusENUM(String descricao) {
		this.descricao = descricao;
	}
}
