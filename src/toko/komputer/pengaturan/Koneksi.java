package toko.komputer.pengaturan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Koneksi {
  private String url = "jdbc:mysql://localhost/db_penjualan";
  
  public Connection getKoneksi(){
      Connection conn = null;
      try {
          conn = DriverManager.getConnection(url,"root","");
      } catch (SQLException ex) {
          Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
      }
      return conn;
  }
    
}
