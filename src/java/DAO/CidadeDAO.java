/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.Cidade;
import model.Estado;

/**
 *
 * @author juann
 */
public class CidadeDAO {

    static Connection c;

    //Talevz nao seja necessario inserir cidades
    public static boolean insereCidade(Cidade cidade) {
        c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO cidade(nome, fkidestado) "
                + "VALUES (?,?);";
        try {
            PreparedStatement ppstt = c.prepareStatement(sql);
            ppstt.setString(1, cidade.getNome());
            ppstt.setInt(2, cidade.getEstado().getIdEstado());
            ppstt.execute();
            ppstt.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    //Recebe cidade atraves do nome
    public static Cidade getCidade(Cidade cidade) {
        String sql = "SELECT cidade.pkidcidade, cidade.nome AS cidadenome, cidade.fkidestado, estado.nome AS estadonome, estado.sigla\n"
                + "FROM cidade INNER JOIN estado ON (cidade.fkidestado = estado.pkidestado) AND cidade.nome = ? ";
        c = ConnectionFactory.getConnection();
        try {
            PreparedStatement ppstt = c.prepareStatement(sql);
            ppstt.setString(1, cidade.getNome());
            ResultSet rs = ppstt.executeQuery();
            if (rs.next()) {
                Cidade cidadeObj = new Cidade();
                Estado estado = new Estado();
                //objeto cidade
                cidadeObj.setIdCidade(rs.getInt("pkidcidade"));
                cidadeObj.setNome(rs.getString("cidadenome"));

                //objeto estado
                estado.setIdEstado(rs.getInt("fkidestado"));
                estado.setNome("estadonome");
                estado.setSigla("sigla");
                cidadeObj.setEstado(estado);
                return cidadeObj;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    

    
    //Recebe todas as cidade
    public static List<Cidade> getCidade() {
        List<Cidade> cidades = new ArrayList<Cidade>();
        String sql = "SELECT cidade.pkidcidade, cidade.nome AS cidadenome, cidade.fkidestado, estado.nome AS estadonome, estado.sigla\n"
                + "FROM cidade INNER JOIN estado ON (cidade.fkidestado = estado.pkidestado)";
        c = ConnectionFactory.getConnection();
        try {
            PreparedStatement ppstt = c.prepareStatement(sql);
            ResultSet rs = ppstt.executeQuery();
            while (rs.next()) {
                Cidade cidade = new Cidade();
                //objeto cidade
                cidade.setIdCidade(rs.getInt("pkidcidade"));
                cidade.setNome(rs.getString("cidadenome"));

                Estado estado = new Estado();
                //objeto estado
                estado.setIdEstado(rs.getInt("fkidestado"));
                estado.setNome(rs.getString("estadonome"));
                estado.setSigla(rs.getString("sigla"));

                cidade.setEstado(estado);
                cidades.add(cidade);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cidades;
    }

    //recebe cidades por estado
    public static List<Cidade> getCidade(int idEstado) {
        List<Cidade> cidades = new ArrayList<Cidade>();
        String sql = "SELECT cid.pkidcidade ,cid.nome AS cidadenome, cid.fkidestado, est.nome AS estadonome, est.sigla AS sigla\n"
                + "FROM cidade cid\n"
                + "INNER JOIN estado AS est ON (cid.fkidestado = est.pkidestado) AND est.pkidestado = ?";
        c = ConnectionFactory.getConnection();
        try {
            PreparedStatement ppstt = c.prepareStatement(sql);
            ppstt.setInt(1, idEstado);
            ResultSet rs = ppstt.executeQuery();
            while (rs.next()) {
                Cidade cidade = new Cidade();
                //objeto cidade
                cidade.setIdCidade(rs.getInt("pkidcidade"));
                cidade.setNome(rs.getString("cidadenome"));

                Estado estadoObj = new Estado();
                //objeto estado
                estadoObj.setIdEstado(rs.getInt("fkidestado"));
                estadoObj.setNome(rs.getString("estadonome"));
                estadoObj.setSigla(rs.getString("sigla"));

                cidade.setEstado(estadoObj);
                cidades.add(cidade);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cidades;
    }

}
