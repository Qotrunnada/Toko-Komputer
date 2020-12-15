package toko.komputer.barang;

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

public class CariBarangController {
    Connection conn = new Koneksi().getKoneksi();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabModel; 
    
public void tampil_data(CariBarangView bv){
  try{
      String[] judul = {"Kode Barang","Nama Barang","Kategori","Harga"};
      tabModel = new DefaultTableModel(null, judul);
      bv.tabelBarang.setModel(tabModel);
      String sql;
      if (bv.textCari.getText().isEmpty()) {
         sql = "select * from tb_barang";
      }else{
         sql = "select * from tb_barang where nama_barang like '%"+bv.textCari.getText()+"%'";
      }
      pst = conn.prepareStatement(sql);
      rs = pst.executeQuery();
      while (rs.next()){
        String[] data = {rs.getString("kode_barang"),rs.getString("nama_barang"),rs.getString(3),rs.getString(4)};
        tabModel.addRow(data);
      }
  }catch(SQLException ex){
    JOptionPane.showMessageDialog(null,"Terjadi kesalahan pada tampil data barang. Details : "+ex.toString());
    Logger.getLogger(BarangView.class.getName()).log(Level.SEVERE, null, ex);
  }
}

public void pilih_barang(CariBarangView bv){
  int row = bv.tabelBarang.getSelectedRow();
  TransaksiView.textKodeBarang.setText(bv.tabelBarang.getValueAt(row, 0).toString());
  TransaksiView.textNamaBarang.setText(bv.tabelBarang.getValueAt(row, 1).toString());
  TransaksiView.textKategori.setText(bv.tabelBarang.getValueAt(row, 2).toString());
  TransaksiView.textHarga.setText(bv.tabelBarang.getValueAt(row, 3).toString());
  bv.dispose();
}
}
