package toko.komputer.pelanggan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import toko.komputer.pengaturan.Koneksi;
import toko.komputer.transaksi.TransaksiView;

public class CariPelangganController {
  Connection conn = new Koneksi().getKoneksi();
  PreparedStatement pst;
  ResultSet rs;
  DefaultTableModel tabModel;

public void tampil_data(CariPelangganView pv){
  try{
      String[] judul = {"Id Pelanggan","Nama Pelanggan","Alamat"};
      tabModel = new DefaultTableModel(null, judul);
      pv.tabelPelanggan.setModel(tabModel);
      String sql;
      if (pv.textCariNamaPelanggan.getText().isEmpty()) {
         sql = "select * from tb_pelanggan";
      }else{
         sql = "select * from tb_pelanggan where nama_pelanggan like '%"+pv.textCariNamaPelanggan.getText()+"%'";
      }
      pst = conn.prepareStatement(sql);
      rs = pst.executeQuery();
      while (rs.next()){
        String[] data = {rs.getString("id_pelanggan"),rs.getString("nama_pelanggan"),rs.getString(3)};
        tabModel.addRow(data);
      }
  }catch(SQLException ex){
    JOptionPane.showMessageDialog(null,"Terjadi kesalahan pada tampil data pelanggan. Details : "+ex.toString());
    Logger.getLogger(PelangganView.class.getName()).log(Level.SEVERE, null, ex);
  }
}

public void pilih_pelanggan(CariPelangganView pv){
  int row = pv.tabelPelanggan.getSelectedRow();
  TransaksiView.textKodePelanggan.setText(pv.tabelPelanggan.getValueAt(row, 0).toString());
  TransaksiView.textNamaPelanggan.setText(pv.tabelPelanggan.getValueAt(row, 1).toString());
  pv.dispose();
}

}
