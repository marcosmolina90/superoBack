package br.com.supero.model.enums;

public enum EnumStatusTask
{
	PENDENTE("Pendente"), AUTORIZADO("Autorizado"), FECHADO("Fechado");

	private String descricao;

	private EnumStatusTask(String descricao)
	{
		this.descricao = descricao;
	}

	public String getDescricao()
	{
		return descricao;
	}

}
