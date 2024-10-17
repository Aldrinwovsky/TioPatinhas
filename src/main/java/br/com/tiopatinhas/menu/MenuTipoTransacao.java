package br.com.tiopatinhas.menu;

import br.com.tiopatinhas.dao.TipoDeTransacaoDAO;
import br.com.tiopatinhas.database.OracleConnection;
import br.com.tiopatinhas.model.TipoDeInvestimento;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuTipoTransacao {
    private TipoDeTransacaoDAO tipoDAO;
    private Scanner scanner;

    public MenuTipoTransacao(Connection connection) {
        this.tipoDAO = new TipoDeTransacaoDAO(connection);
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("=== Menu de Tipos de Transação ===");
            System.out.println("1. Listar tipos de transação");
            System.out.println("2. Inserir tipo de transação");
            System.out.println("3. Excluir tipo de transação");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    listarTipos();
                    break;
                case 2:
                    inserirTipo();
                    break;
                case 3:
                    excluirTipo();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void listarTipos() {
        try {
            List<TipoDeInvestimento> tipos = tipoDAO.listarTodos();
            System.out.println("=== Lista de Tipos de Transação ===");
            for (TipoDeInvestimento tipo : tipos) {
                System.out.println("ID: " + tipo.getInvestimentoId() + ", Nome: " + tipo.getNome());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tipos de transação: " + e.getMessage());
        }
    }

    private void inserirTipo() {
        System.out.print("Informe o nome do tipo de transação: ");
        String nome = scanner.nextLine();
        TipoDeInvestimento novoTipo = new TipoDeInvestimento();
        novoTipo.setNome(nome);

        try {
            tipoDAO.inserir(novoTipo);
        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo de transação: " + e.getMessage());
        }
    }

    private void excluirTipo() {
        System.out.print("Informe o ID do tipo de transação a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        try {
            tipoDAO.excluir(id);
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tipo de transação: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection(); // Obtendo a conexão
            MenuTipoTransacao menu = new MenuTipoTransacao(connection);
            menu.exibirMenu(); // Chama o método para exibir o menu
        } catch (Exception e) {
            System.out.println("Erro ao conectar ou executar operações: " + e.getMessage());
        } finally {
            OracleConnection.closeConnection(connection); // Fecha a conexão
        }
    }
}
