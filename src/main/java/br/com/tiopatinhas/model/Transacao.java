package br.com.tiopatinhas.model;

import java.time.LocalDate;

public class Transacao {
	private int transacaoId; // ID da transação (auto incremental)
	private String tipo; // Tipo de transação
	private LocalDate data; // Data da transação
	private int ciNumeroConta; // Número da conta de investimento
	private String ciCpf; // CPF do usuário da conta
	private int tiInvestimentoId; // ID do tipo de investimento
	private double montante; // Montante da transação

	// Construtor padrão
	public Transacao() {
		// Inicializa os campos se necessário
	}

	// Método para exibir o recibo
	public void mostraRecibo() {
		System.out.println("-----Recibo-----");
		System.out.println("ID: " + this.getTransacaoId());
		System.out.println("Tipo: " + this.getTipo());
		System.out.println("Data: " + this.getData());
		System.out.println("Montante: " + this.getMontante());
		System.out.println("Número da Conta: " + this.getCiNumeroConta());
		System.out.println("CPF do Usuário: " + this.getCiCpf());
		System.out.println("ID do Tipo de Investimento: " + this.getTiInvestimentoId());
	}

	// Getters e Setters
	public int getTransacaoId() {
		return transacaoId;
	}

	public void setTransacaoId(int transacaoId) {
		this.transacaoId = transacaoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getCiNumeroConta() {
		return ciNumeroConta;
	}

	public void setCiNumeroConta(int ciNumeroConta) {
		this.ciNumeroConta = ciNumeroConta;
	}

	public String getCiCpf() {
		return ciCpf;
	}

	public void setCiCpf(String ciCpf) {
		this.ciCpf = ciCpf;
	}

	public int getTiInvestimentoId() {
		return tiInvestimentoId;
	}

	public void setTiInvestimentoId(int tiInvestimentoId) {
		this.tiInvestimentoId = tiInvestimentoId;
	}

	public double getMontante() {
		return montante;
	}

	public void setMontante(double montante) {
		this.montante = montante;
	}
}
