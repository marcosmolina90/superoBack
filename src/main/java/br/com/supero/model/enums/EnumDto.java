package br.com.supero.model.enums;

import java.io.Serializable;

public class EnumDto implements Serializable {

	private static final long serialVersionUID = 989852299520259769L;
	private String value;
	private String descricao;

	public EnumDto() {
	}

	public EnumDto(String value, String descricao) {
		super();
		this.value = value;
		this.descricao = descricao;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}