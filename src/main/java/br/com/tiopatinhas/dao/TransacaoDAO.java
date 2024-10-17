package br.com.tiopatinhas.dao;

import br.com.tiopatinhas.model.Transacao;
import br.com.tiopatinhas.database.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TransacaoDAO {
    private Connection connection;

    public TransacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO transacoes (tipo, data, ci_numero_conta, ci_cpf, ti_investimento_id, montante) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transacao.getTipo());
            stmt.setDate(2, Date.valueOf(transacao.getData()));
            stmt.setInt(3, transacao.getCiNumeroConta());
            stmt.setString(4, transacao.getCiCpf());
            stmt.setInt(5, transacao.getTiInvestimentoId());
            stmt.setDouble(6, transacao.getMontante());
            stmt.executeUpdate();
            System.out.println("Transação inserida com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir transação: " + e.getMessage());
            throw e;
        }
    }

    public List<Transacao> listarTodos() throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT transacao_id, tipo, data, ci_numero_conta, ci_cpf, ti_investimento_id, montante FROM transacoes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Transacao transacao = new Transacao();
                transacao.setTransacaoId(rs.getInt("transacao_id"));
                transacao.setTipo(rs.getString("tipo"));
                transacao.setData(rs.getDate("data").toLocalDate());
                transacao.setCiNumeroConta(rs.getInt("ci_numero_conta"));
                transacao.setCiCpf(rs.getString("ci_cpf"));
                transacao.setTiInvestimentoId(rs.getInt("ti_investimento_id"));
                transacao.setMontante(rs.getDouble("montante"));
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar transações: " + e.getMessage());
            throw e;
        }
        return transacoes;
    }

    public void testar() {
        try {
            // Testa a inserção de uma nova transação
            Transacao novaTransacao = new Transacao();
            novaTransacao.setTipo("Deposito");
            novaTransacao.setData(LocalDate.now()); // Data atual
            novaTransacao.setCiNumeroConta(3); // Substitua pelo número da conta existente
            novaTransacao.setCiCpf("48536459859"); // Substitua pelo CPF existente
            novaTransacao.setTiInvestimentoId(1); // Substitua pelo ID de investimento existente
            novaTransacao.setMontante(500.00); // Montante da transação
            this.inserir(novaTransacao); // Método que lança SQLException

            // Lista todas as transações
            List<Transacao> transacoes = this.listarTodos(); // Método que lança SQLException
            System.out.println("Lista de transações:");
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
            System.out.println("Erro no teste: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = OracleConnection.getConnection(); // Obtém a conexão
            TransacaoDAO transacaoDAO = new TransacaoDAO(connection);
            transacaoDAO.testar(); // Testa a inserção e listagem
        } catch (Exception e) {
            System.out.println("Erro ao conectar ou executar operações: " + e.getMessage());
        } finally {
            OracleConnection.closeConnection(connection); // Fecha a conexão
        }
    }
}
