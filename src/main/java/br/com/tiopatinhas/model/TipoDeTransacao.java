package br.com.tiopatinhas.model;

public class TipoDeTransacao {
	private int investimentoId; // ID do tipo de investimento
	private String nome; // Nome do tipo de investimento

	// Construtor padrão
	public TipoDeTransacao() {
	}

	// Métodos getter e setter
	public int getInvestimentoId() {
		return investimentoId;
	}

	public void setInvestimentoId(int investimentoId) {
		this.investimentoId = investimentoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
