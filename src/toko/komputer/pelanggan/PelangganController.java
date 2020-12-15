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


public class PelangganController {
    Connection conn = new Koneksi().getKoneksi();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabModel;
    
public void refresh(PelangganView pv){
  pv.btnSimpan.setEnabled(false);
  pv.btnHapus.setEnabled(false);
  pv.btnUbah.setEnabled(false);
  pv.btnTambah.setEnabled(true);
  pv.textIdPel.setEnabled(false);
  pv.textNamaPel.setEnabled(false);
  pv.textAlamat.setEnabled(false);
  pv.labelStatus.setText("");
  pv.textIdPel.setText("");
  pv.textNamaPel.setText("");
  pv.textAlamat.setText("");
  pv.textCariNamaPel.setText("");
}

public void tampil_data(PelangganView pv){
  try{
      String[] judul = {"Id Pelanggan","Nama Pelanggan","Alamat"};
      tabModel = new DefaultTableModel(null, judul);
      pv.tabelPelanggan.setModel(tabModel);
      String sql;
      if (pv.textCariNamaPel.getText().isEmpty()) {
         sql = "select * from tb_pelanggan";
      }else{
         sql = "select * from tb_pelanggan where nama_pelanggan like '%"+pv.textCariNamaPel.getText()+"%'";
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

public void kode_otomatis(PelangganView pv){
  try {
      int kodeLama;
      String sql= "";
      pst = conn.prepareStatement("select id_pelanggan from tb_pelanggan order by id_pelanggan desc limit 1");
      rs = pst.executeQuery();
      if (rs.next()){
          kodeLama = Integer.parseInt(rs.getString(1).substring(4))+1;
          pv.textIdPel.setText("PLG-"+kodeLama);
      }else{
          pv.textIdPel.setText("PLG-100");
      }
  }catch(SQLException ex) {
    JOptionPane.showMessageDialog(null,"Terjadi kesalahan pada kode otomatis. Details : "+ex.toString());
    Logger.getLogger(PelangganView.class.getName()).log(Level.SEVERE, null, ex);
  }
}

public Boolean validasi(PelangganView pv){
  boolean cek = false;
  if(pv.textNamaPel.getText().isEmpty()){
     JOptionPane.showMessageDialog(null, "Nama pelanggan belum diisi!");
     pv.textNamaPel.requestFocus();
  }else if(pv.textAlamat.getText().isEmpty()){
     JOptionPane.showMessageDialog(null, "Alamat belum diisi!");
     pv.textAlamat.requestFocus();
  }else{
     cek = true;
  }
  return cek;   
}

public void tambah_pelanggan(PelangganView pv){
  pv.labelStatus.setText("tambah");
  kode_otomatis(pv);
  pv.btnTambah.setEnabled(false);
  pv.btnSimpan.setEnabled(true);
  pv.textNamaPel.setEnabled(true);
  pv.textAlamat.setEnabled(true);
  pv.textNamaPel.requestFocus();
}

public void simpan_pelanggan(PelangganView pv){
  if(validasi(pv)==true){
      try{
          String sql = "";
          if(pv.labelStatus.getText().equals("tambah")){
              sql = "insert into tb_pelanggan values ('"+pv.textIdPel.getText()+"','"+pv.textNamaPel.getText()+"','"+pv.textAlamat.getText()+"')";
          }else if(pv.labelStatus.getText().equals("ubah")){
              sql = "update tb_pelanggan set nama_pelanggan='"+pv.textNamaPel.getText()+"',"
                      + "alamat='"+pv.textAlamat.getText()+"' where id_pelanggan='"+pv.textIdPel.getText()+"'"; 
          }
          pst = conn.prepareStatement(sql);
          pst.executeUpdate();
          JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
          refresh(pv);
          tampil_data(pv);
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada simpan data pelanggan. Details : "+ex.toString());
          Logger.getLogger(PelangganView.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}

public void ubah_pelanggan(PelangganView pv){
  pv.labelStatus.setText("ubah");
  pv.btnUbah.setEnabled(false);
  pv.btnSimpan.setEnabled(true);
  pv.textNamaPel.setEnabled(true);
  pv.textAlamat.setEnabled(true);
  pv.textNamaPel.requestFocus();
}

public void hapus_pelanggan(PelangganView pv){
  int hapus = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus?",
              "Hapus Data?",JOptionPane.YES_NO_OPTION);
  if(hapus==JOptionPane.YES_OPTION){
     try{
         pst = conn.prepareStatement("delete from tb_pelanggan where id_pelanggan='"+pv.textIdPel.getText()+"'");
         pst.executeUpdate();
         JOptionPane.showMessageDialog(null, "Data pelanggan berhasil dihapus!");
         refresh(pv);
         tampil_data(pv);
     }catch(SQLException ex){
         JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada hapus pelanggan. Details : "+ex.toString());
         Logger.getLogger(PelangganView.class.getName()).log(Level.SEVERE, null, ex);
     }
  }
}

public void pilih_pelanggan(PelangganView pv){
  int row = pv.tabelPelanggan.getSelectedRow();
  pv.textIdPel.setText(pv.tabelPelanggan.getValueAt(row, 0).toString());
  pv.textNamaPel.setText(pv.tabelPelanggan.getValueAt(row, 1).toString());
  pv.textAlamat.setText(pv.tabelPelanggan.getValueAt(row, 2).toString());
  pv.btnTambah.setEnabled(false);
  pv.btnSimpan.setEnabled(false);
  pv.btnUbah.setEnabled(true);
  pv.btnHapus.setEnabled(true);
}
    
}
