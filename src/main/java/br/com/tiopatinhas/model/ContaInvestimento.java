package br.com.tiopatinhas.model;

public class ContaInvestimento {
    private double saldo = 0.0;
    private String tipoMoeda;
    private String cpfUsuario;
    private int id;

    // Construtor padrão para entrada de dados
    public ContaInvestimento(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    // Construtor para uso no DAO
    public ContaInvestimento(double saldo, String tipoMoeda, String cpfUsuario) {
        this.saldo = saldo;
        this.tipoMoeda = tipoMoeda;
        this.cpfUsuario = cpfUsuario;
    }

    // Construtor padrão
    public ContaInvestimento() {
    }

    public void mostraConta() {
        System.out.println("Número da Conta: " + this.id);
        System.out.println("CPF: " + this.cpfUsuario);
        System.out.println("Tipo Moeda: " + this.tipoMoeda);
        System.out.println("Saldo: " + this.saldo);
    }

    public void setId(int id) {
        this.id = id; // Método para definir o ID
    }

    public int getId() { // Corrigido para ser um getter
        return this.id;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTipoMoeda() {
        return tipoMoeda;
    }

    public void setTipoMoeda(String tipoMoeda) {
        this.tipoMoeda = tipoMoeda;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }
}
