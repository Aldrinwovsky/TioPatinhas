package br.com.tiopatinhas.dao;

import br.com.tiopatinhas.model.TipoDeInvestimento;
import br.com.tiopatinhas.database.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoDeTransacaoDAO {
    private Connection connection;

    public TipoDeTransacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(TipoDeInvestimento tipo) throws SQLException {
        String sql = "INSERT INTO tipo_investimento (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo.getNome());
            stmt.executeUpdate();
            System.out.println("Tipo de investimento inserido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo de investimento: " + e.getMessage());
            throw e;
        }
    }
    public void excluir(int investimentoId) throws SQLException {
        String sql = "DELETE FROM tipo_investimento WHERE investimento_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, investimentoId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tipo de investimento excluído com sucesso.");
            } else {
                System.out.println("Nenhum tipo de investimento encontrado com o ID: " + investimentoId);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir tipo de investimento: " + e.getMessage());
            throw e;
        }
    }

    public List<TipoDeInvestimento> listarTodos() throws SQLException {
        List<TipoDeInvestimento> tipos = new ArrayList<>();
        String sql = "SELECT * FROM tipo_investimento";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TipoDeInvestimento tipo = new TipoDeInvestimento();
                tipo.setInvestimentoId(rs.getInt("investimento_id"));
                tipo.setNome(rs.getString("nome"));
                tipos.add(tipo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar tipos de investimento: " + e.getMessage());
            throw e; // Lança a exceção para tratamento posterior
        }
        return tipos;
    }

    public void testar() {
        try {
            // Teste da inserção de um novo tipo de investimento
            TipoDeInvestimento novoTipo = new TipoDeInvestimento();
            novoTipo.setNome("Fundos imobiliários");
            this.inserir(novoTipo);

            // Lista todos os tipos de investimento
            List<TipoDeInvestimento> tipos = this.listarTodos();
            System.out.println("Lista de tipos de investimento:");
            for (TipoDeInvestimento tipo : tipos) {
                System.out.println("ID: " + tipo.getInvestimentoId() + ", Nome: " + tipo.getNome());
            }

            // Teste da exclusão
            if (!tipos.isEmpty()) {
                int idParaExcluir = tipos.get(0).getInvestimentoId();
                this.excluir(idParaExcluir);
            }

        } catch (SQLException e) {
            System.out.println("Erro no teste: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection();
            TipoDeTransacaoDAO tipoDAO = new TipoDeTransacaoDAO(connection);
            tipoDAO.testar();
        } catch (Exception e) {
            System.out.println("Erro ao conectar ou executar operações: " + e.getMessage());
        } finally {
            OracleConnection.closeConnection(connection);
        }
    }
}
