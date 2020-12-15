/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toko.komputer.pengaturan;
import java.sql.*;

/**
 *
 * @author msi
 */
public class koneksi {
    public Connection koneksi = null;
    public Connection koneksiDatabase(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_penjualan","root","");
        }catch (ClassNotFoundException | SQLException e ){
            System.out.println("Connection Error :  "+e.getMessage());
        }
        return koneksi;
    }
    
}