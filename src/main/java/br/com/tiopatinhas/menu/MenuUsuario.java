package br.com.tiopatinhas.menu;

import br.com.tiopatinhas.dao.UsuarioDAO;
import br.com.tiopatinhas.database.OracleConnection;
import br.com.tiopatinhas.model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {
    private UsuarioDAO usuarioDAO;
    private Scanner input;

    public MenuUsuario(Connection connection) {
        this.usuarioDAO = new UsuarioDAO(connection);
        this.input = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean sairUsuario = false;
        while (!sairUsuario) {
            System.out.println("\n---------- Menu Usuário ----------");
            System.out.println("1. Criar Usuário");
            System.out.println("2. Buscar Usuário");
            System.out.println("3. Atualizar Usuário");
            System.out.println("4. Apagar Usuário");
            System.out.println("5. Listar Todos os Usuários");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            String opcaoUsuario = input.nextLine();

            switch (opcaoUsuario) {
                case "1":
                    criarUsuario();
                    break;

                case "2":
                    buscarUsuario();
                    break;

                case "3":
                    atualizarUsuario();
                    break;

                case "4":
                    apagarUsuario();
                    break;

                case "5":
                    listarUsuarios();
                    break;

                case "6":
                    sairUsuario = true; // Voltar ao Menu Principal
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void criarUsuario() {
        System.out.println("---------- Criação de Usuário ----------");
        System.out.print("Qual o seu nome? ");
        String nome = input.nextLine();
        System.out.print("Qual o seu CPF? ");
        String cpf = input.nextLine();
        System.out.print("Qual o seu endereço? ");
        String endereco = input.nextLine();
        System.out.print("Qual o seu email? ");
        String email = input.nextLine();

        Usuario novoUsuario = new Usuario(nome, cpf, endereco, email);
        try {
            usuarioDAO.inserir(novoUsuario);
            System.out.println("Usuário inserido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    private void buscarUsuario() {
        System.out.println("---------- Buscar Usuário ----------");
        System.out.print("Informe o CPF para busca: ");
        String cpfBusca = input.nextLine();
        try {
            Usuario usuarioBuscado = usuarioDAO.buscarPorCpf(cpfBusca);
            if (usuarioBuscado != null) {
                System.out.println("Usuário encontrado: " + usuarioBuscado.getNome());
            } else {
                System.out.println("Nenhum usuário encontrado para o CPF informado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    private void atualizarUsuario() {
        System.out.println("---------- Atualizar Usuário ----------");
        System.out.print("Informe o CPF do usuário a ser atualizado: ");
        String cpfAtualizar = input.nextLine();
        try {
            Usuario usuarioParaAtualizar = usuarioDAO.buscarPorCpf(cpfAtualizar);
            if (usuarioParaAtualizar != null) {
                System.out.print("Novo Nome: ");
                usuarioParaAtualizar.setNome(input.nextLine());
                System.out.print("Novo Endereço: ");
                usuarioParaAtualizar.setEndereco(input.nextLine());
                System.out.print("Novo Email: ");
                usuarioParaAtualizar.setEmail(input.nextLine());
                usuarioDAO.atualizar(usuarioParaAtualizar);
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    private void apagarUsuario() {
        System.out.println("---------- Apagar Usuário ----------");
        System.out.print("Informe o CPF do usuário a ser apagado: ");
        String cpfDeletar = input.nextLine();
        try {
            usuarioDAO.deletar(cpfDeletar);
            System.out.println("Usuário apagado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao apagar usuário: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        System.out.println("---------- Lista de Usuários ----------");
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário encontrado.");
            } else {
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario.getNome() + " - " + usuario.getCpf());
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection(); // Obtendo a conexão
            MenuUsuario menu = new MenuUsuario(connection);
            menu.exibirMenu(); // Chama o método para exibir o menu
        } catch (Exception e) {
            System.out.println("Erro ao conectar ou executar operações: " + e.getMessage());
        } finally {
            OracleConnection.closeConnection(connection); // Fecha a conexão
        }
    }
}

