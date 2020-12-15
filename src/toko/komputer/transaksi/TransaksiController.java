package toko.komputer.transaksi;

import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import toko.komputer.home.HomeView;
import toko.komputer.pengaturan.Koneksi;

public class TransaksiController {
    Connection conn = new Koneksi().getKoneksi();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabModel;
    
private void clear_item_belanja(TransaksiView tv){
    try{
       for(int a=tv.tabelItemBelanja.getRowCount()-1; a >= 0;  a--){
         tabModel.removeRow(a);             
         tv.tabelItemBelanja.setModel(tabModel);
       }                           
    }catch(Exception ex){
       JOptionPane.showMessageDialog(null,"Kesalahan pada method clear item belanjar. \nDetails:   "+ex.toString());
    }
}

public void kode_otomatis(TransaksiView tv){
    try {
        int kodeLama;            
        pst = conn.prepareStatement("select no_nota from tb_penjualan order by no_nota desc limit 1");
        rs = pst.executeQuery();
        if(rs.next()){
           kodeLama = Integer.parseInt(rs.getString(1).substring(4))+1;
           tv.textNota.setText("NTA-"+kodeLama);
        }else{
           tv.textNota.setText("NTA-100000");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada kode otomatis. Details: "+ex.toString());
    }        
}
    
public boolean validasi(TransaksiView tv) {
  boolean cek = false;
  Date tgl =  tv.textTanggal.getDate();
  if (tgl==null){
    JOptionPane.showMessageDialog(null,"Tanggal Transaksi belum diisi!",null,JOptionPane.ERROR_MESSAGE);        
    tv.textTanggal.requestFocus();
  }else if (TransaksiView.textKodePelanggan.getText().equals("")){
    JOptionPane.showMessageDialog(null,"Data pelanggan belum diisi!",null,JOptionPane.ERROR_MESSAGE);
    tv.buttonCariPelanggan.requestFocus();
  }else if(tv.tabelItemBelanja.getRowCount() <= 0){
    JOptionPane.showMessageDialog(null,"Data barang belanja masih kosong!",null,JOptionPane.ERROR_MESSAGE);
    tv.buttonCariBarang.requestFocus();
  }else if(tv.textBayar.getText().equals("0")){
    JOptionPane.showMessageDialog(null,"Textbox bayar belum diisi!",null,JOptionPane.ERROR_MESSAGE);         
    tv.textBayar.requestFocus();
  }else{
    cek = true;
  }
  return cek;
}
    
public void refresh(TransaksiView tv){
  kode_otomatis(tv);
  TransaksiView.textKodePelanggan.setText("");
  TransaksiView.textNamaPelanggan.setText("");
  TransaksiView.textKodeBarang.setText("");
  TransaksiView.textNamaBarang.setText("");
  TransaksiView.textKategori.setText("");
  TransaksiView.textHarga.setText("0");            
  tv.textJumlahBeli.setText("0");
  tv.textSubtotal.setText("0");
  tv.textDiskon.setText("0");
  tv.textGrandTotal.setText("0");
  tv.textBayar.setText("0");
  tv.textKembali.setText("0");
  clear_item_belanja(tv);
  //menonaktifkan textfield dan button
  tv.textNota.setEnabled(false);
  TransaksiView.textKodePelanggan.setEnabled(false);
  TransaksiView.textNamaPelanggan.setEnabled(false);
  TransaksiView.textKodeBarang.setEnabled(false);
  TransaksiView.textNamaBarang.setEnabled(false);
  TransaksiView.textKategori.setEnabled(false);
  TransaksiView.textHarga.setEnabled(false);
  TransaksiView.textHarga.setEnabled(false);
  tv.textJumlahBeli.setEnabled(true);
  tv.textSubtotal.setEnabled(false);
  tv.textDiskon.setEnabled(true);
  tv.textGrandTotal.setEnabled(false);
  tv.textBayar.setEnabled(true);
  tv.textKembali.setEnabled(false);
  tv.buttonBatal.setEnabled(true);
  tv.buttonCariBarang.setEnabled(true);
  tv.buttonCariPelanggan.setEnabled(true);
  tv.buttonSimpan.setEnabled(true);
  tv.buttonTambah.setEnabled(true);
  tv.buttonHapus.setEnabled(true);
}
      
public void tambah_item_belanja(TransaksiView tv){
  try{
     if(TransaksiView.textKodeBarang.getText().equals("")){
        JOptionPane.showMessageDialog(tv,"Pilih dulu barang yang ingin ditambahkan!");
     }else if(tv.textJumlahBeli.getText().equals("0")){
        JOptionPane.showMessageDialog(tv,"Jumlah beli barang belum diisi!!");            
        tv.textJumlahBeli.requestFocus();
     }
     else{
          tabModel = (DefaultTableModel) tv.tabelItemBelanja.getModel();
          ArrayList data = new ArrayList();
          data.add(TransaksiView.textKodeBarang.getText());
          data.add(TransaksiView.textNamaBarang.getText());
          data.add(TransaksiView.textHarga.getText());
          data.add(tv.textJumlahBeli.getText());
          BigDecimal harga = new BigDecimal(TransaksiView.textHarga.getText());
          BigDecimal jumlahBeli = new BigDecimal(tv.textJumlahBeli.getText());
          BigDecimal totalHarga = harga.multiply(jumlahBeli);
          data.add(totalHarga);                
          tabModel.addRow(data.toArray()); 
          hitung_subtotal(tv);
          TransaksiView.textKodeBarang.setText("");
          TransaksiView.textNamaBarang.setText("");
          TransaksiView.textKategori.setText("");
          TransaksiView.textHarga.setText("0");                
          tv.textJumlahBeli.setText("0");                                                
         }            
  }catch(HeadlessException | NumberFormatException ex){
        JOptionPane.showMessageDialog(tv,"Gagal menambahkan item belanja! \nDetails:"+ex.toString(),
        "Not Found",JOptionPane.ERROR_MESSAGE);
  }
}     

public void hapus_item_belanja(TransaksiView tv){
  try{
     int row = tv.tabelItemBelanja.getSelectedRow();
     if (row < 0){
         JOptionPane.showMessageDialog(null, "Pilih dulu item yang ingin dihapus!");
     }else{                   
         tabModel.removeRow(row);
         tv.tabelItemBelanja.setModel(tabModel);
         hitung_subtotal(tv);
     }
  }catch(Exception e){
     JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada proses hapus item belanja.Details\n"+e.toString());
  }
}
    
public void hitung_subtotal(TransaksiView tv){
  BigDecimal total = new BigDecimal(0);        
  for(int a=0; a<tv.tabelItemBelanja.getRowCount(); a++){
    total= total.add(new BigDecimal( tv.tabelItemBelanja.getValueAt(a, 4).toString()));
  }
  tv.textSubtotal.setText(total.toString());
}
    
public void hitung_grandtotal(TransaksiView tv){
  BigDecimal diskon = new BigDecimal(0);
  if (!tv.textDiskon.getText().equals("")){
     diskon = new BigDecimal(tv.textDiskon.getText());
  }
  BigDecimal subTotal = new BigDecimal(tv.textSubtotal.getText());
  BigDecimal grandTotal = subTotal.subtract(diskon);
  tv.textGrandTotal.setText(grandTotal.toString());
}
    
public void hitung_kembali(TransaksiView tv){
  BigDecimal bayar = new BigDecimal(0);
  if(!tv.textBayar.getText().equals("")){
     bayar = new BigDecimal(tv.textBayar.getText());
  }
  BigDecimal grandTotal = new BigDecimal(tv.textGrandTotal.getText());
  BigDecimal kembali = bayar.subtract(grandTotal);
  tv.textKembali.setText(kembali.toString());
}
    
public void simpan_penjualan(TransaksiView tv){
  if(validasi(tv)){
    try {
        int isSucces;
        Date d = tv.textTanggal.getDate();
        java.sql.Date tgl = new java.sql.Date(d.getTime());
        pst = conn.prepareStatement("insert into tb_penjualan values (?,?,?,?,?,?,?)");
        pst.setString(1, tv.textNota.getText());
        pst.setString(2, tgl.toString());
        pst.setString(3, TransaksiView.textKodePelanggan.getText());                
        pst.setBigDecimal(4, new BigDecimal(tv.textGrandTotal.getText()));
        pst.setBigDecimal(5, new BigDecimal(tv.textBayar.getText()));
        pst.setBigDecimal(6, new BigDecimal(tv.textKembali.getText()));
        pst.setString(7, HomeView.labelStatus.getText());
        isSucces = pst.executeUpdate();
        if (isSucces == 1){
            simpan_item_belanja(tv);                    
        }
         JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");                
         refresh(tv);
    } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada Simpan Penjualan: Details \n"+ex.toString());
    }            
  }    
}
    
public void simpan_item_belanja(TransaksiView tv){
  for(int a = 0; a <= tv.tabelItemBelanja.getRowCount()-1; a++){
    try {
        pst = conn.prepareStatement("insert into tb_detail_penjualan (no_nota, kode_barang, qty, subtotal) values(?,?,?,?)");
        pst.setString(1, tv.textNota.getText());
        pst.setString(2, tv.tabelItemBelanja.getValueAt(a, 0).toString());
        pst.setInt(3, Integer.parseInt(tv.tabelItemBelanja.getValueAt(a, 3).toString()));
        pst.setBigDecimal(4,new BigDecimal(tv.tabelItemBelanja.getValueAt(a, 4).toString()));
        pst.executeUpdate();                                                
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada simpan item belanja: Details\n"+ex.toString(),"",JOptionPane.ERROR_MESSAGE);
    }
  }
}

}