package toko.komputer.home;

import javax.swing.JOptionPane;
import toko.komputer.barang.BarangView;
import toko.komputer.pelanggan.PelangganView;
import toko.komputer.transaksi.DataTransaksiView;
import toko.komputer.transaksi.TransaksiView;

public class HomeView extends javax.swing.JFrame {

    public HomeView() {
        initComponents();
    }
    
    public HomeView(String status){
      initComponents();
      if (status.equals("kasir")){
        menuData.setEnabled(false);
      }
      labelStatus.setText(status);
      this.setExtendedState(MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        labelStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuAplikasi = new javax.swing.JMenu();
        menuKeluar = new javax.swing.JMenuItem();
        menuData = new javax.swing.JMenu();
        menuBarang = new javax.swing.JMenuItem();
        menuPelanggan = new javax.swing.JMenuItem();
        menuTransaksi = new javax.swing.JMenu();
        menuPenjualan = new javax.swing.JMenuItem();
        menuDataTransaksi = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU UTAMA");

        panelMain.setBackground(new java.awt.Color(204, 255, 204));

        labelStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelStatus.setForeground(new java.awt.Color(255, 255, 255));
        labelStatus.setText("jLabel1");

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelStatus)
                .addContainerGap(468, Short.MAX_VALUE))
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                .addContainerGap(331, Short.MAX_VALUE)
                .addComponent(labelStatus)
                .addContainerGap())
        );

        getContentPane().add(panelMain, java.awt.BorderLayout.CENTER);

        menuAplikasi.setText("Aplikasi");

        menuKeluar.setText("Keluar");
        menuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKeluarActionPerformed(evt);
            }
        });
        menuAplikasi.add(menuKeluar);

        jMenuBar1.add(menuAplikasi);

        menuData.setText("Data");

        menuBarang.setText("Data Barang");
        menuBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBarangActionPerformed(evt);
            }
        });
        menuData.add(menuBarang);

        menuPelanggan.setText("Data Pelanggan");
        menuPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPelangganActionPerformed(evt);
            }
        });
        menuData.add(menuPelanggan);

        jMenuBar1.add(menuData);

        menuTransaksi.setText("Transaksi");

        menuPenjualan.setText("Penjualan");
        menuPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPenjualanActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuPenjualan);

        menuDataTransaksi.setText("Data Transaksi");
        menuDataTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDataTransaksiActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuDataTransaksi);

        jMenuBar1.add(menuTransaksi);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBarangActionPerformed
      BarangView bv = new BarangView();
      panelMain.add(bv);
      bv.setVisible(true);
    }//GEN-LAST:event_menuBarangActionPerformed

    private void menuKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKeluarActionPerformed
    int k = JOptionPane.showConfirmDialog(null, "Yakin ingin keluar?","KONFIRMASI KELUAR",
              JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
      if (k == JOptionPane.YES_OPTION){
          System.exit(0);
      }
    }//GEN-LAST:event_menuKeluarActionPerformed

    private void menuPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPenjualanActionPerformed
     TransaksiView tv = new TransaksiView();
     panelMain.add(tv);
     tv.setVisible(true);
    }//GEN-LAST:event_menuPenjualanActionPerformed

    private void menuPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPelangganActionPerformed
     PelangganView pv = new PelangganView();
     panelMain.add(pv);
     pv.setVisible(true);
    }//GEN-LAST:event_menuPelangganActionPerformed

    private void menuDataTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDataTransaksiActionPerformed
      DataTransaksiView dtv = new DataTransaksiView();
      panelMain.add(dtv);
      dtv.setVisible(true);
    }//GEN-LAST:event_menuDataTransaksiActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    public static javax.swing.JLabel labelStatus;
    private javax.swing.JMenu menuAplikasi;
    private javax.swing.JMenuItem menuBarang;
    private javax.swing.JMenu menuData;
    private javax.swing.JMenuItem menuDataTransaksi;
    private javax.swing.JMenuItem menuKeluar;
    private javax.swing.JMenuItem menuPelanggan;
    private javax.swing.JMenuItem menuPenjualan;
    private javax.swing.JMenu menuTransaksi;
    private javax.swing.JPanel panelMain;
    // End of variables declaration//GEN-END:variables
}
