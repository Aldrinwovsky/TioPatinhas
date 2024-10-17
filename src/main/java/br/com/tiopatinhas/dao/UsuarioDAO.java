package br.com.tiopatinhas.dao;

import br.com.tiopatinhas.model.Usuario;
import br.com.tiopatinhas.database.OracleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Métodos existentes (inserir, listarTodos, buscarPorCpf, atualizar, deletar)

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (cpf, nome, endereco, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEndereco());
            stmt.setString(4, usuario.getEmail());
            stmt.executeUpdate();
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT cpf, nome, endereco, email FROM usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setCpf(rs.getString("cpf"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEndereco(rs.getString("endereco"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEndereco(rs.getString("endereco"));
                    usuario.setEmail(rs.getString("email"));
                    return usuario;
                }
            }
        }
        return null; // Retorna null se o usuário não for encontrado
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, endereco = ?, email = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getCpf());
            stmt.executeUpdate();
        }
    }

    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    // Método para testar inserção e listagem
    public void testar() {
        try {
            // Criar um novo usuário para inserir
            Usuario novoUsuario = new Usuario("João Silva", "48536459859", "Rua A, 123", "joao@exemplo.com");
            this.inserir(novoUsuario);
            System.out.println("Usuário inserido com sucesso.");

            // Listar todos os usuários
            List<Usuario> usuarios = this.listarTodos();
            System.out.println("Lista de usuários:");
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getNome() + " - " + usuario.getCpf());
            }

            // Atualizar um usuário
            novoUsuario.setNome("João Silva Atualizado");
            this.atualizar(novoUsuario);
            System.out.println("Usuário atualizado com sucesso.");

            // Buscar um usuário por CPF
            Usuario usuarioBuscado = this.buscarPorCpf("12345678901");
            if (usuarioBuscado != null) {
                System.out.println("Usuário encontrado: " + usuarioBuscado.getNome());
            } else {
                System.out.println("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public static void main(String[] args) {
    Connection connection = null;

        connection = OracleConnection.getConnection();
        UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
        usuarioDAO.testar();
        OracleConnection.closeConnection(connection);
    }

}
