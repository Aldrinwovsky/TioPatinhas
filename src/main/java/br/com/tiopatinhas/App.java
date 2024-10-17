package br.com.tiopatinhas;

import java.sql.Connection;
import java.util.Scanner;

import br.com.tiopatinhas.database.OracleConnection;
import br.com.tiopatinhas.dao.UsuarioDAO;
import br.com.tiopatinhas.dao.ContaInvestimentoDAO;
import br.com.tiopatinhas.dao.TransacaoDAO;
import br.com.tiopatinhas.menu.MenuUsuario;
import br.com.tiopatinhas.menu.MenuConta;
import br.com.tiopatinhas.menu.MenuTransacao;
import br.com.tiopatinhas.menu.MenuTipoTransacao;


public class App
{
    public static void main( String[] args )
    {
        Connection connection = null;
        Scanner input = new Scanner(System.in);

        try {
            connection = OracleConnection.getConnection();
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            ContaInvestimentoDAO contaDAO = new ContaInvestimentoDAO(connection);
            TransacaoDAO transacaoDAO = new TransacaoDAO(connection);

            boolean sair = false;
            while (!sair) {
                System.out.println("\n---------- Menu Principal ----------");
                System.out.println("1. Usuário");
                System.out.println("2. Conta");
                System.out.println("3. Transações");
                System.out.println("4. Tipo de Transação");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");
                String opcao = input.nextLine();

                switch (opcao) {
                    case "1":
                        MenuUsuario menuUsuario = new MenuUsuario(connection);
                        menuUsuario.exibirMenu();
                        break;

                    case "2":
                        MenuConta menuConta = new MenuConta(connection);
                        menuConta.exibirMenu();
                        break;

                    case "3":
                        MenuTransacao menuTransacao = new MenuTransacao();
                        menuTransacao.exibirMenu();
                        break;

                    case "4":
                        MenuTipoTransacao menuTipoTransacao = new MenuTipoTransacao(connection);
                        menuTipoTransacao.exibirMenu();
                        break;

                    case "5":
                        sair = true;
                        System.out.println("Saindo do sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao executar operações: " + e.getMessage());
        } finally {
            if (connection != null) {
                OracleConnection.closeConnection(connection);
            }
            input.close();
        }
    }
}
