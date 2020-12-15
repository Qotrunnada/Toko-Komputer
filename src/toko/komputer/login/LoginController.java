package toko.komputer.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import toko.komputer.home.HomeView;
import toko.komputer.pengaturan.Koneksi;


public class LoginController {
 public void batal(LoginView lv){
   lv.textUser.setText("");
   lv.textPass.setText("");
   lv.textUser.requestFocus();
 }
 
public void loginAplikasi(LoginView lv){
  if (lv.textUser.getText().isEmpty() || lv.textPass.getText().isEmpty()){
      JOptionPane.showMessageDialog(null, "Username dan Password belum diisi");
  }else{
      try {
          Connection conn = new Koneksi().getKoneksi();
          String sql = "select * from tb_pengguna where username=? and password=?";
          PreparedStatement pst = conn.prepareStatement(sql);
          pst.setString(1, lv.textUser.getText());
          pst.setString(2, lv.textPass.getText());
          ResultSet rs = pst.executeQuery();
          if (rs.next()){
              JOptionPane.showMessageDialog(null, "Login Berhasil!");
              HomeView hv = new HomeView(rs.getString("status"));
              hv.setVisible(true);
              lv.hide();
          }else{
              JOptionPane.showMessageDialog(null, "Login Gagal!");
          }
      } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, ex.toString());
          Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
      }   
      } 
  }
}
