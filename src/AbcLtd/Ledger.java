/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbcLtd;

import java.awt.event.KeyEvent;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SAIFUL
 */
public class Ledger extends javax.swing.JFrame {

    login log = new login();
    PreparedStatement pstm;
    ResultSet rs;
    Statement stm;
    DefaultTableModel dtm = new DefaultTableModel();

    /**
     * Creates new form Ledger
     */
    public Ledger() {
        initComponents();
         this.setLocationRelativeTo(null);
    }

    public void create_view() {
        try {
            DatabaseMetaData dmd = log.con.getMetaData();

            String create_table = "CREATE or Replace VIEW ledger as select * from transactionTable"
                    + " where c_id=" + text_cid.getText();

            pstm = log.con.prepareStatement(create_table);
            pstm.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Ledger view Created");

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Show() {
//        this.setVisible(true);
         double balance =0;
        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery("select * from ledger");

            Vector<String> header = new Vector<String>();
            header.add("Date");
            header.add("Status");
            header.add("Company Name");
            header.add("Unit Price");
            header.add("Quantity");
            header.add("Debit");
            header.add("Credit");
            header.add("Commission");
            header.add("Balance");
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("date"));
                String status = rs.getString("status");
                row.add(status);
                row.add(rs.getString("com_name"));
                double rate = Double.parseDouble(rs.getString("u_price"));
                row.add(rate);
                int quantity = Integer.parseInt(rs.getString("quantity"));
                row.add(quantity);
                double debit =Double.parseDouble(rs.getString("payment"));
                
                if (status.equals("Buy")) {
                    debit = rate * quantity;
                }
                double credit = Double.parseDouble(rs.getString("received"));
                if (status.equals("Sale")) {
                    credit = rate * quantity;
                }
                row.add(debit);
                row.add(credit);

                double comm = Double.parseDouble(rs.getString("comm"));
                row.add(String.format("%.2f", comm));
                
                balance += credit - debit - comm;
                 row.add(String.format("%.2f", balance));
                data.add(row);
            }
            dtm.setDataVector(data, header);
        } catch (SQLException ex) {
            System.out.println("Error in table: " + ex);
        }

    }

    public void from_to() {
         double balance =0;
        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery("select * from ledger where date between '" + text_from.getText()
                    + "' and '" + text_to.getText() + "'");
             Vector<String> header = new Vector<String>();
            header.add("Date");
            header.add("Status");
            header.add("Company Name");
            header.add("Unit Price");
            header.add("Quantity");
            header.add("Debit");
            header.add("Credit");
            header.add("Commission");
            header.add("Balance");
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("date"));
                String status = rs.getString("status");
                row.add(status);
                row.add(rs.getString("com_name"));
                double rate = Double.parseDouble(rs.getString("u_price"));
                row.add(rate);
                int quantity = Integer.parseInt(rs.getString("quantity"));
                row.add(quantity);
                double debit =Double.parseDouble(rs.getString("payment"));
                
                if (status.equals("Buy")) {
                    debit = rate * quantity;
                }
                double credit = Double.parseDouble(rs.getString("received"));
                if (status.equals("Sale")) {
                    credit = rate * quantity;
                }
                row.add(debit);
                row.add(credit);

                double comm = Double.parseDouble(rs.getString("comm"));
                row.add(String.format("%.2f", comm));
                
                balance += credit - debit - comm;
                 row.add(String.format("%.2f", balance));
                data.add(row);
            }
            dtm.setDataVector(data, header);
        } catch (SQLException ex) {
            System.out.println("Error in table: " + ex);
        }
    }

//    public void table() {
//
//        try {
//            Vector<String> header = new Vector<String>();
//            header.add("Transaction ID");
//            header.add("Date");
//            header.add("Client ID");
//            header.add("Status");
//            header.add("Company Name");
//            header.add("Unit Price");
//            header.add("Quantity");
//            header.add("Commission");
//            header.add("Total");
//
//            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
//            while (rs.next()) {
//                Vector<Object> row = new Vector<Object>();
//                row.add(rs.getString("t_id"));
//                row.add(rs.getString("date"));
//                row.add(rs.getString("c_id"));
//                row.add(rs.getString("status"));
//                row.add(rs.getString("com_name"));
//                row.add(rs.getString("u_price"));
//                row.add(rs.getString("quantity"));
//                row.add(rs.getString("comm"));
//                row.add(rs.getString("t_cost"));
//
//                data.add(row);
//                dtm.setDataVector(data, header);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(AllTransaction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        text_cid = new javax.swing.JTextField();
        text_cname = new javax.swing.JTextField();
        text_from = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        text_to = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        Date sd= new Date();
        String stdDate =(1900+sd.getYear())+"-"+(sd.getMonth()+1)
        +"-"+(sd.getDate());
        jTextField3 = new javax.swing.JTextField(stdDate);
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ledger");

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText(" ABC Securities ltd.                                                  #Ledger");

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Client ID:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Client Name:");

        text_cid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_cid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                text_cidFocusGained(evt);
            }
        });
        text_cid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cidKeyReleased(evt);
            }
        });

        text_cname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        text_from.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("From");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("to");

        text_to.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_to.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_toKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 51));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_search.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_search.setForeground(new java.awt.Color(0, 153, 0));
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(text_cname, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(39, 39, 39)
                        .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(text_from, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btn_search)
                                    .addGap(44, 44, 44)
                                    .addComponent(jButton1))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(text_to, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(text_cname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btn_search))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(dtm);
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Date:");

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Motijheel C/A Dhaka-1000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_cidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cidKeyReleased
        create_view();
        Show();
        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery("select c_name from clienttable where c_id= " + text_cid.getText());
            while (rs.next()) {
                text_cname.setText(rs.getString("c_name"));
            }

        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex);
        }


    }//GEN-LAST:event_text_cidKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void text_cidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_cidFocusGained
              text_cid.setText("");
        text_cname.setText("");
    }//GEN-LAST:event_text_cidFocusGained

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        from_to();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void text_toKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_toKeyPressed
      if(evt.getKeyCode()== KeyEvent.VK_ENTER){
         from_to();
        }
    }//GEN-LAST:event_text_toKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ledger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ledger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ledger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ledger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ledger().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_search;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField text_cid;
    private javax.swing.JTextField text_cname;
    private javax.swing.JTextField text_from;
    private javax.swing.JTextField text_to;
    // End of variables declaration//GEN-END:variables
}
