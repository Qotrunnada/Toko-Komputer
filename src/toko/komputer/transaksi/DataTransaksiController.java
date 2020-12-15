package toko.komputer.transaksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import toko.komputer.pengaturan.Koneksi;

public class DataTransaksiController {
    Connection conn = new Koneksi().getKoneksi();
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel tabModel;
    
public void tampil_data(DataTransaksiView dtv){
  Object [] Baris = {"No Nota","Tanggal","Kode Pelanggan","Nama Pelanggan","Total"};
  tabModel = new DefaultTableModel(null, Baris);
  dtv.tabelTransaksi.setModel(tabModel);
  try{
     if(dtv.textCari.getText().isEmpty()){
        pst=conn.prepareStatement("select a.*, b.nama_pelanggan from tb_penjualan as a, tb_pelanggan as b where a.id_pelanggan=b.id_pelanggan");   
     }else{
        pst=conn.prepareStatement("select a.*, b.nama_pelanggan from tb_penjualan as a, tb_pelanggan as b where a.id_pelanggan=b.id_pelanggan and a.no_nota like'%"+dtv.textCari.getText()+"%'");
     }
     rs=pst.executeQuery();
     while (rs.next()){
       String[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString("nama_pelanggan"),rs.getString(4)};
       tabModel.addRow(data);
     }
  }catch (SQLException e){
      System.out.println("Terjadi kesalahan pada tampil data transaksi.\nDetails: "+e.toString());
  }
}

public void tampil_detail_data (DataTransaksiView dtv){
  Object[]Baris = {"Kode Barang","Nama Barang","Harga","Qty","Subtotal"};
  tabModel = new DefaultTableModel(null, Baris);
  dtv.tabelItemBelanja.setModel(tabModel);
  try{
      int row = dtv.tabelTransaksi.getSelectedRow();
      String no = dtv.tabelTransaksi.getValueAt(row, 0).toString();
      pst = conn.prepareStatement("select a.*, b.qty, b.subtotal from tb_barang as a, tb_detail_penjualan as b where a.kode_barang=b.kode_barang and b.no_nota='"+ no +"'");
      rs = pst.executeQuery();
      while (rs.next()){
         String[] data = {rs.getString(1),rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(6)};
         tabModel.addRow(data);
      }
  }catch (SQLException e){
      System.out.println("Terjadi kesalahan pada tampil data detail transaksi.\nDetails: "+e.toString());
  }
}



}
