package br.com.tiopatinhas.menu;

import br.com.tiopatinhas.dao.ContaInvestimentoDAO;
import br.com.tiopatinhas.database.OracleConnection;
import br.com.tiopatinhas.model.ContaInvestimento;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuConta {
    private ContaInvestimentoDAO contaDAO;
    private Scanner scanner;

    public MenuConta(Connection connection) {
        this.contaDAO = new ContaInvestimentoDAO(connection);
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n----- Menu de Contas de Investimento -----");
            System.out.println("1. Criar Conta");
            System.out.println("2. Listar Contas");
            System.out.println("3. Buscar Conta");
            System.out.println("4. Excluir Conta");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    listarContas();
                    break;
                case 3:
                    buscarConta();
                    break;
                case 4:
                    excluirConta();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);
    }

    private void criarConta() {
        try {
            System.out.print("Digite o CPF do usuário: ");
            String cpf = scanner.nextLine();
            System.out.print("Digite o saldo inicial: ");
            double saldo = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer
            System.out.print("Digite o tipo de moeda: ");
            String tipoMoeda = scanner.nextLine();

            ContaInvestimento novaConta = new ContaInvestimento(saldo, tipoMoeda, cpf);
            contaDAO.inserir(novaConta);
        } catch (SQLException e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    private void listarContas() {
        try {
            System.out.println("Listando contas...");
            for (ContaInvestimento conta : contaDAO.listarTodos()) {
                System.out.println("ID: " + conta.getId() +
                        ", Saldo: " + conta.getSaldo() +
                        ", Tipo de moeda: " + conta.getTipoMoeda() +
                        ", CPF do usuário: " + conta.getCpfUsuario());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar contas: " + e.getMessage());
        }
    }

    private void buscarConta() {
        try {
            System.out.print("Digite o CPF do usuário: ");
            String cpf = scanner.nextLine();
            ContaInvestimento conta = contaDAO.buscarPorCpf(cpf);
            if (conta != null) {
                System.out.println("Conta encontrada: ID: " + conta.getId() +
                        ", Saldo: " + conta.getSaldo() +
                        ", Tipo de moeda: " + conta.getTipoMoeda());
            } else {
                System.out.println("Nenhuma conta encontrada para o CPF: " + cpf);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar conta: " + e.getMessage());
        }
    }

    private void excluirConta() {
        try {
            System.out.print("Digite o CPF do usuário: ");
            String cpf = scanner.nextLine();
            contaDAO.excluirPorCpf(cpf);
        } catch (SQLException e) {
            System.out.println("Erro ao excluir conta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection();
            MenuConta menu = new MenuConta(connection);
            menu.exibirMenu();
        } catch (Exception e) {
            System.out.println("Erro ao conectar ou executar operações: " + e.getMessage());
        } finally {
            OracleConnection.closeConnection(connection); // Fecha a conexão
        }
    }
}
