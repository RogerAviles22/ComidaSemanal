/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import static Modelo.Comida.CONNECTION;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Rogencio
 */
public class Fecha_Comida {
    private int id_comida;
    private Date fecha;
    private String tipo;
    private final String agregar_fecha_comida = "{call   agregar_fecha_comida (?,?,?)}";
    public final String get_fecha_comida = "select fa.id_fecha_comida as ID, c.nombre as Comida, fa.fecha as Fecha, fa.tipo as Tipo \n" +
"from fecha_comida fa join comida c on c.id_comida= fa.id_comida\n" +
"where fa.fecha between ? and DATE_ADD(?, INTERVAL 7 DAY);";
    public final String update_fecha_comida="update Fecha_Comida "
            + "set fecha =?, id_comida=(select id_comida from Comida where nombre = ?), tipo = ? "
            + "where id_fecha_comida= ? ;";
    public final String delete_fecha_comida="delete from fecha_comida where id_fecha_comida= ?;";
    
    public Fecha_Comida() {
    }

    
    public int getId_comida() {
        return id_comida;
    }

    public void setId_comida(int id_comida) {
        this.id_comida = id_comida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public boolean agregar_fecha_comida(String comida, String fecha, String tipo){
        try {
            CONNECTION.conectar();
            PreparedStatement sp = CONNECTION.getConnection().prepareStatement(agregar_fecha_comida);
            sp.setString(1, comida);
            sp.setString(2, fecha);
            sp.setString(3, tipo);
            sp.execute();
            sp.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            CONNECTION.desconectar();
        }
        return false;
    }
    
    public boolean modificar_fecha_comida(String comida, String fecha, String tipo, Integer id_fecha_comida){
        try {
            CONNECTION.conectar();
            PreparedStatement sp;
            sp = CONNECTION.getConnection().prepareStatement(update_fecha_comida);
            sp.setString(1, fecha );
            sp.setString(2, comida);
            sp.setString(3, tipo);
            sp.setInt(4, id_fecha_comida);
            sp.execute();
            sp.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            CONNECTION.desconectar();
        }
        return false;
    }
    
    public boolean eliminar_fecha_comida(Integer id_fecha_comida){
        try {
            CONNECTION.conectar();
            PreparedStatement sp = CONNECTION.getConnection().prepareStatement(delete_fecha_comida);
            sp.setInt(1, id_fecha_comida);
            sp.execute();
            sp.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            CONNECTION.desconectar();
        }
        return false;
    }
    
}
