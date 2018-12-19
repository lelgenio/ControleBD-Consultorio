package pkg13.projetoconsultorio;

//    paciente : nome, cpf, telefone, endereco
//    medico   : nome, cpf, especialidade
//    consulta : data, hora, medico, paciente, detalhe


//    DEMO METODOS:
//    

//    adicionar("NomeDaTabela", "campo1, campo2","valor1, valo2");
//    listar("NomeDaTabela", "campo1, campo2", tbExemplo);
//    remover("NomeDaTabela", "campo1", "123");
//    editar("NomeDaTabela", "cpf", 123456, "nome = 'joao', idade = '31' ");
//    pegaValor(tbExemplo, 0 )

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;



public class ControleBD {

    public String arquivoBanco;
    public ControleBD(String nomeBanco) {
        arquivoBanco = nomeBanco;
    }

    public void adicionar(String tabela, String campos, String valores) {

        Connection banco = null;
        Statement comando = null;
        try {
            Class.forName("org.sqlite.JDBC");
            banco = DriverManager.getConnection("jdbc:sqlite:"+arquivoBanco);
            banco.setAutoCommit(false);
            comando = banco.createStatement();
            /*Sintaxe em SQL para inserir um registro na tabela*/

            String sql = "INSERT INTO " + tabela + " (" + campos + ") "
                    + "VALUES ('" + valores + "');";

            comando.executeUpdate(sql);
            comando.close();
            banco.commit();
            banco.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Cadastrado com sucesso");

    }

    public void listar(String tabelaBanco, String campos, JTable tabelaInterface) {
        Connection banco = null;
        /*Variável para conectar com o banco de dados*/
        Statement comando = null;/*Variável para executar os comando de banco de dados*/
        Statement prepara = null;
        try {
            Class.forName("org.sqlite.JDBC");/*Classe que faz a ponte entre o Java e o SQLITE*/
            banco = DriverManager.getConnection("jdbc:sqlite:"+arquivoBanco);/*Classe que conecta com o arquivo de banco de dados*/
            prepara = banco.createStatement();
            ResultSet listaTabela = prepara.executeQuery("SELECT "+campos+" FROM " + tabelaBanco + ";");
            tabelaInterface.setModel(DbUtils.resultSetToTableModel(listaTabela));
            listaTabela.close();
            prepara.close();
            banco.close();
            /*CASO HOUVER ALGUM ERRO NA EXECUÇÃO DO COMANDO SQL*/
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            /*Mostra mensagem de erro*/
            System.exit(0);/*Fecha o programa*/
        }
    }

    public void remover(String tabelaBanco, String campo, String valor) {

        Connection banco = null;
        Statement comando = null;
        try {
            Class.forName("org.sqlite.JDBC");
            banco = DriverManager.getConnection("jdbc:sqlite:"+arquivoBanco);
            banco.setAutoCommit(false);
            comando = banco.createStatement();
            /*Sintaxe em SQL para inserir um registro na tabela*/

            String sql = "DELETE FROM " + tabelaBanco
                    + " WHERE "+campo+" = '" + valor + "';";
            comando.executeUpdate(sql);
            comando.close();
            banco.commit();
            banco.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Removido com sucesso");

    }

    public void editar(String tabelaBanco, String id, String valorId, String novos) {

        Connection banco = null;
        Statement comando = null;
        try {
            Class.forName("org.sqlite.JDBC");
            banco = DriverManager.getConnection("jdbc:sqlite:"+arquivoBanco);
            banco.setAutoCommit(false);
            comando = banco.createStatement();
            /*Sintaxe em SQL para inserir um registro na tabela*/

            String sql = "UPDATE " + tabelaBanco
                    + " SET " + novos + "' "
                    + " WHERE " + id + " = '" + valorId + "';";

            comando.executeUpdate(sql);
            comando.close();
            banco.commit();
            banco.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Editado com sucesso");

    }

    public String pegaValor(JTable tabela, int coluna) {

        return tabela.getModel().getValueAt(
                tabela.getSelectedRow(), coluna
        ).toString();

    }
}
