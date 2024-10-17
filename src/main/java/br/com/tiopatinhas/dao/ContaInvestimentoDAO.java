package br.com.tiopatinhas.dao;

import br.com.tiopatinhas.model.ContaInvestimento;
import br.com.tiopatinhas.database.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaInvestimentoDAO {
    private Connection connection;

    public ContaInvestimentoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(ContaInvestimento conta) throws SQLException {
        if (!usuarioExists(conta.getCpfUsuario())) {
            throw new SQLException("Usuário não encontrado para o CPF: " + conta.getCpfUsuario());
        }

        String sql = "INSERT INTO conta_investimento (saldo, tipo_moeda, usuarios_cpf) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, conta.getSaldo());
            stmt.setString(2, conta.getTipoMoeda());
            stmt.setString(3, conta.getCpfUsuario());
            stmt.executeUpdate();
            System.out.println("Conta de investimento inserida com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir conta de investimento: " + e.getMessage());
            throw e;
        }
    }

    public List<ContaInvestimento> listarTodos() throws SQLException {
        List<ContaInvestimento> contas = new ArrayList<>();
        String sql = "SELECT numero_conta, saldo, tipo_moeda, usuarios_cpf FROM conta_investimento";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ContaInvestimento conta = new ContaInvestimento(
                        rs.getDouble("saldo"),
                        rs.getString("tipo_moeda"),
                        rs.getString("usuarios_cpf")
                );
                conta.setId(rs.getInt("numero_conta"));
                contas.add(conta);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar contas de investimento: " + e.getMessage());
            throw e;
        }
        return contas;
    }

    // Método para buscar conta pelo CPF
    public ContaInvestimento buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT numero_conta, saldo, tipo_moeda FROM conta_investimento WHERE usuarios_cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ContaInvestimento conta = new ContaInvestimento(
                            rs.getDouble("saldo"),
                            rs.getString("tipo_moeda"),
                            cpf
                    );
                    conta.setId(rs.getInt("numero_conta")); // Supondo que numero_conta é o ID
                    return conta;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar conta pelo CPF: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public void excluirPorCpf(String cpf) throws SQLException {
        String sql = "DELETE FROM conta_investimento WHERE usuarios_cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Conta de investimento excluída com sucesso.");
            } else {
                System.out.println("Nenhuma conta encontrada para o CPF: " + cpf);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir conta: " + e.getMessage());
            throw e;
        }
    }

    private boolean usuarioExists(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void testar() {
        try {
            ContaInvestimento novaConta = new ContaInvestimento(1000.00, "BRL", "48536459859");
            this.inserir(novaConta); // Método que lança SQLException

            List<ContaInvestimento> contas = this.listarTodos(); // Método que lança SQLException
            System.out.println("Lista de contas:");
            for (ContaInvestimento conta : contas) {
                System.out.println("ID: " + conta.getId() +
                        ", Saldo: " + conta.getSaldo() +
                        ", Tipo de moeda: " + conta.getTipoMoeda() +
                        ", CPF do usuário: " + conta.getCpfUsuario());
            }

            System.out.println("Buscando conta para o CPF: " + novaConta.getCpfUsuario());
            ContaInvestimento contaBuscada = this.buscarPorCpf(novaConta.getCpfUsuario());
            if (contaBuscada != null) {
                System.out.println("Conta encontrada: ID: " + contaBuscada.getId() +
                        ", Saldo: " + contaBuscada.getSaldo() +
                        ", Tipo de moeda: " + contaBuscada.getTipoMoeda());
            } else {
                System.out.println("Nenhuma conta encontrada para o CPF: " + novaConta.getCpfUsuario());
            }

            System.out.println("Excluindo conta para o CPF: " + novaConta.getCpfUsuario());
            this.excluirPorCpf(novaConta.getCpfUsuario());

        } catch (SQLException e) {
            System.out.println("Erro no teste: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection();
            ContaInvestimentoDAO contaDAO = new ContaInvestimentoDAO(connection);
            contaDAO.testar();
        } catch (Exception e) {
            System.out.println("Erro ao conectar ou executar operações: " + e.getMessage());
        } finally {
            OracleConnection.closeConnection(connection);
        }
    }
}
