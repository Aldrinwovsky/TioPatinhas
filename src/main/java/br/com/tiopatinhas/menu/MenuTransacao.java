package br.com.tiopatinhas.menu;

import br.com.tiopatinhas.dao.TransacaoDAO;
import br.com.tiopatinhas.database.OracleConnection;
import br.com.tiopatinhas.model.Transacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class MenuTransacao {
    private TransacaoDAO transacaoDAO;
    private final Scanner scanner;

    public MenuTransacao() {
        this.scanner = new Scanner(System.in);
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection(); // Obtém a conexão
            this.transacaoDAO = new TransacaoDAO(connection);
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("===== Menu de Transações =====");
            System.out.println("1. Inserir Transação");
            System.out.println("2. Listar Transações");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    inserirTransacao();
                    break;
                case 2:
                    listarTransacoes();
                    break;
                case 3:
                    System.out.println("Saindo do menu de transações...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);
    }

    private void inserirTransacao() {
        try {
            Transacao transacao = new Transacao();

            System.out.print("Informe o tipo da transação (Ex: Depósito, Saque): ");
            transacao.setTipo(scanner.nextLine());

            transacao.setData(LocalDate.now()); // Usa a data atual

            System.out.print("Informe o número da conta (Ex: 12345): ");
            transacao.setCiNumeroConta(scanner.nextInt());
            scanner.nextLine(); // Limpa o buffer após nextInt()

            // CPF do usuário
            String cpf;
            while (true) {
                System.out.print("Informe o CPF do usuário (Ex: 48536459859): ");
                cpf = scanner.nextLine();

                // Validação simples para verificar se o CPF não está vazio e contém apenas dígitos
                if (cpf.matches("\\d{11}")) {
                    transacao.setCiCpf(cpf);
                    break; // Sai do loop se o CPF for válido
                } else {
                    System.out.println("Entrada inválida! O CPF deve conter 11 dígitos.");
                }
            }
            System.out.print("Informe o ID do tipo de investimento (Ex: 1 para Ações): ");
            transacao.setTiInvestimentoId(scanner.nextInt());
            scanner.nextLine(); // Limpa o buffer após nextInt()

            System.out.print("Informe o montante (Ex: 100,0): ");
            transacao.setMontante(scanner.nextDouble());
            System.out.println(scanner.nextDouble());


            // Chama o DAO para inserir a transação
            transacaoDAO.inserir(transacao);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número." + e);
            scanner.nextLine(); // Limpa o buffer para evitar loop infinito
        } catch (SQLException e) {
            System.out.println("Erro ao inserir transação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTransacoes() {
        try {
            List<Transacao> transacoes = transacaoDAO.listarTodos();
            System.out.println("Lista de Transações:");
            for (Transacao transacao : transacoes) {
                System.out.println("ID: " + transacao.getTransacaoId() +
                        ", Tipo: " + transacao.getTipo() +
                        ", Data: " + transacao.getData() +
                        ", Número da Conta: " + transacao.getCiNumeroConta() +
                        ", CPF do Usuário: " + transacao.getCiCpf() +
                        ", ID do Tipo de Investimento: " + transacao.getTiInvestimentoId() +
                        ", Montante: " + transacao.getMontante());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar transações: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MenuTransacao menu = new MenuTransacao();
        menu.exibirMenu();
    }
}
