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

public class BarangController {
    Connection conn = new Koneksi().getKoneksi();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabModel;
    
public void refresh(BarangView bv){
  bv.buttonSimpan.setEnabled(false);
  bv.buttonHapus.setEnabled(false);
  bv.buttonUbah.setEnabled(false);
  bv.buttonTambah.setEnabled(true);
  bv.textKode.setEnabled(false);
  bv.textNama.setEnabled(false);
  bv.textHarga.setEnabled(false);
  bv.comboKategori.setEnabled(false);
  bv.labelStatus.setText("");
  bv.textKode.setText("");
  bv.textNama.setText("");
  bv.textHarga.setText("");
  bv.textCari.setText("");
  bv.comboKategori.setSelectedIndex(0);
}

public void tampil_data(BarangView bv){
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

public void kode_otomatis(BarangView bv){
  try {
      int kodeLama;
      String sql= "";
      pst = conn.prepareStatement("select kode_barang from tb_barang order by kode_barang desc limit 1");
      rs = pst.executeQuery();
      if (rs.next()){
          kodeLama = Integer.parseInt(rs.getString(1).substring(4))+1;
          bv.textKode.setText("BRG-"+kodeLama);
      }else{
          bv.textKode.setText("BRG-100000");
      }
  }catch(SQLException ex) {
    JOptionPane.showMessageDialog(null,"Terjadi kesalahan pada kode otomatis. Details : "+ex.toString());
    Logger.getLogger(BarangView.class.getName()).log(Level.SEVERE, null, ex);
  }
  }

public Boolean validasi(BarangView bv){
  boolean cek = false;
  if(bv.textNama.getText().isEmpty()){
     JOptionPane.showMessageDialog(null, "Nama barang belum diisi!");
     bv.textNama.requestFocus();
  }else if(bv.comboKategori.getSelectedIndex()==0){
     JOptionPane.showMessageDialog(null, "Kategori barang belum dipilih!");
     bv.comboKategori.requestFocus();
  }else if(bv.textHarga.getText().isEmpty()){
     JOptionPane.showMessageDialog(null, "Harga barang belum diisi!");
     bv.textHarga.requestFocus();
  }else{
     cek = true;
  }
  return cek;   
}

public void tambah_barang(BarangView bv){
  bv.labelStatus.setText("tambah");
  kode_otomatis(bv);
  bv.buttonTambah.setEnabled(false);
  bv.buttonSimpan.setEnabled(true);
  bv.textNama.setEnabled(true);
  bv.textHarga.setEnabled(true);
  bv.textNama.requestFocus();
  bv.comboKategori.setEnabled(true);
}

public void simpan_barang(BarangView bv){
  if(validasi(bv)==true){
      try{
          String sql = "";
          if(bv.labelStatus.getText().equals("tambah")){
              sql = "insert into tb_barang values ('"+bv.textKode.getText()+"','"+bv.textNama.getText()+"',"
                      + "'"+bv.comboKategori.getSelectedItem()+"','"+bv.textHarga.getText()+"')";
          }else if(bv.labelStatus.getText().equals("ubah")){
              sql = "update tb_barang set nama_barang='"+bv.textNama.getText()+"',"
                      + "kategori='"+bv.comboKategori.getSelectedItem()+"',"
                      + "harga='"+bv.textHarga.getText()+"' where kode_barang='"+bv.textKode.getText()+"'";
          }
          pst = conn.prepareStatement(sql);
          pst.executeUpdate();
          JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
          refresh(bv);
          tampil_data(bv);
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada simpan data. Details : "+ex.toString());
          Logger.getLogger(BarangView.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}

public void ubah_barang(BarangView bv){
  bv.labelStatus.setText("ubah");
  bv.buttonUbah.setEnabled(false);
  bv.buttonSimpan.setEnabled(true);
  bv.textNama.setEnabled(true);
  bv.textHarga.setEnabled(true);
  bv.textNama.requestFocus();
  bv.comboKategori.setEnabled(true);
}

public void hapus_barang(BarangView bv){
  int hapus = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus?",
              "Hapus Data?",JOptionPane.YES_NO_OPTION);
  if(hapus==JOptionPane.YES_OPTION){
     try{
         pst = conn.prepareStatement("delete from tb_barang where kode_barang='"+bv.textKode.getText()+"'");
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Data barang berhasil dihapus!");
         refresh(bv);
         tampil_data(bv);
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada hapus barang. Details : "+ex.toString());
         Logger.getLogger(BarangView.class.getName()).log(Level.SEVERE, null, ex);
     }
  }
}

public void pilih_barang(BarangView bv){
  int row = bv.tabelBarang.getSelectedRow();
  bv.textKode.setText(bv.tabelBarang.getValueAt(row, 0).toString());
  bv.textNama.setText(bv.tabelBarang.getValueAt(row, 1).toString());
  bv.comboKategori.setSelectedItem(bv.tabelBarang.getValueAt(row, 2).toString());
  bv.textHarga.setText(bv.tabelBarang.getValueAt(row, 3).toString());
  bv.buttonTambah.setEnabled(false);
  bv.buttonSimpan.setEnabled(false);
  bv.buttonUbah.setEnabled(true);
  bv.buttonHapus.setEnabled(true);
}

}