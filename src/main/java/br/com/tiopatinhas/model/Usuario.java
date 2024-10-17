package br.com.tiopatinhas.model;

import br.com.tiopatinhas.dao.UsuarioDAO;
import br.com.tiopatinhas.database.OracleConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Usuario {
	private String cpf;
	private String nome;
	private String endereco;
	private String email;

	public static Scanner input = new Scanner(System.in);

	public Usuario() {
	}
	// Construtor para criar um usuário com informações já fornecidas
	public Usuario(String nome, String cpf, String endereco, String email) {
		this.setNome(nome);
		this.setCpf(cpf);
		this.setEndereco(endereco);
		this.setEmail(email);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}