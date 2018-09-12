/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbcLtd;

import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SAIFUL
 */
public class Receipt extends javax.swing.JFrame {

    login log = new login();
    PreparedStatement pstm;
    ResultSet rs;
    Statement stm;

    /**
     * Creates new form MReceived
     */
    public Receipt() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void insert() {

        String com_name = "  ";
        double rate = 0.0;
        int qty = 0;
        double comm = 0.0;

        if (jComboBox_status.getSelectedItem().equals("Received")) {
            try {
                String tk_insert = "insert into transactionTable(date,c_id,status,com_name,u_price,quantity,comm,payment,received) values(?,?,?,?,?,?,?,?,?)";
                pstm = log.con.prepareStatement(tk_insert);

                pstm.setString(1, txt_date.getText());
                pstm.setString(2, text_cid.getText());
                pstm.setString(3, jComboBox_status.getSelectedItem().toString());
                pstm.setString(4, com_name);
                pstm.setString(5, String.valueOf(rate));
                pstm.setString(6, String.valueOf(qty));
                pstm.setString(7, String.valueOf(comm));
                int c_payment = 0;
                pstm.setString(8, String.valueOf(c_payment));
                pstm.setString(9, text_tk.getText());
                int up = pstm.executeUpdate();
                if (up > 0) {
                    JOptionPane.showMessageDialog(null, "Successfully Received");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (jComboBox_status.getSelectedItem().equals("Payment")) {

            double tbuy = 0;
            double tsale = 0;
            double bal = 0;
            int depo = 0;
            int wdraw = 0;
            double gain = 0;
            int tk = Integer.parseInt(text_tk.getText());
//        jTextArea1.setText(abc);
            try {
                String select = "select * from c_" + text_cid.getText();
                stm = log.con.createStatement();
                rs = stm.executeQuery(select);
                int a = 0;
                String b = "";
                int c = 0;
                double d = 0;
                double e = 0;
                String s = "";
                double totalCost = 0;
                while (rs.next()) {

                    a = rs.getInt(1);
                    b = rs.getString(2);
                    c = rs.getInt(3);
                    d = rs.getDouble(4);
                    e = d / c;
                    s += a + "\t" + b + "\t\t" + c + "\t\t" + String.format("%.2f", e) + "\t\t" + String.format("%.2f", d) + "\n";
                    totalCost += d;
                }
                try {
                    String totalBuy = "select t_cost from transactiontable where c_id=" + text_cid.getText() + " and status = 'Buy'";
                    stm = log.con.createStatement();
                    rs = stm.executeQuery(totalBuy);
                    while (rs.next()) {
                        double t = Double.parseDouble(rs.getString("t_cost"));
                        tbuy += t;
//                System.out.println("buy: "+tbuy);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                try {
                    String deposit = "select received from test.transactiontable where c_id=" + text_cid.getText();
                    stm = log.con.createStatement();
                    rs = stm.executeQuery(deposit);
                    while (rs.next()) {
                        int dip = rs.getInt("received");
                        depo += dip;
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                try {
                    String withdraw = "select payment from test.transactiontable where c_id=" + text_cid.getText();
                    stm = log.con.createStatement();
                    rs = stm.executeQuery(withdraw);
                    while (rs.next()) {
                        int wid = rs.getInt("payment");
                        wdraw += wid;
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                try {
                    String totalBuy = "select t_cost from transactiontable where c_id=" + text_cid.getText() + " and status = 'Sale'";
                    stm = log.con.createStatement();
                    rs = stm.executeQuery(totalBuy);
                    while (rs.next()) {
                        double t = Double.parseDouble(rs.getString("t_cost"));
                        tsale += t;
//               System.out.println("sale: "+tsale);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                bal = depo - tbuy + tsale - wdraw;
            } catch (Exception ex) {

            }
            if (bal < tk) {
                JOptionPane.showMessageDialog(null, "Not Sufficient Balance", "", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    String tk_insert = "insert into transactionTable(date,c_id,status,com_name,u_price,quantity,comm,payment,received) values(?,?,?,?,?,?,?,?,?)";
                    pstm = log.con.prepareStatement(tk_insert);

                    pstm.setString(1, txt_date.getText());
                    pstm.setString(2, text_cid.getText());
                    pstm.setString(3, jComboBox_status.getSelectedItem().toString());
                    pstm.setString(4, com_name);
                    pstm.setString(5, String.valueOf(rate));
                    pstm.setString(6, String.valueOf(qty));
                    pstm.setString(7, String.valueOf(comm));
                    pstm.setString(8, text_tk.getText());
                    int c_received = 0;
                    pstm.setString(9, String.valueOf(c_received));

                    int up = pstm.executeUpdate();
                    if (up > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Paid");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public void clear() {
        text_cid.setText("");
        text_cname.setText("");
        text_tk.setText("");
        text_tkInword.setText("");
        jComboBox_status.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        text_cid = new javax.swing.JTextField();
        text_cname = new javax.swing.JTextField();
        text_tk = new javax.swing.JTextField();
        text_tkInword = new javax.swing.JTextField();
        Date sd= new Date();
        String stdDate =(1900+sd.getYear())+"-"+(sd.getMonth()+1)
        +"-"+(sd.getDate());
        txt_date = new javax.swing.JTextField(stdDate);
        Submite = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox_status = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Client ID:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Client Name:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Date:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Taka:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("In word:");

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

        text_tk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_tk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_tkKeyPressed(evt);
            }
        });

        text_tkInword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_date.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        Submite.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Submite.setForeground(new java.awt.Color(0, 153, 51));
        Submite.setText("Submit");
        Submite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmiteActionPerformed(evt);
            }
        });

        clear.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        clear.setForeground(new java.awt.Color(0, 0, 153));
        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        exit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        exit.setForeground(new java.awt.Color(255, 0, 0));
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Status:");

        jComboBox_status.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Received", "Payment" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addGap(51, 51, 51)
                        .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3)
                        .addGap(30, 30, 30)
                        .addComponent(text_cname, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(jComboBox_status, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel5)
                        .addGap(73, 73, 73)
                        .addComponent(text_tk, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel6)
                        .addGap(55, 55, 55)
                        .addComponent(text_tkInword, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(Submite)
                        .addGap(73, 73, 73)
                        .addComponent(clear)
                        .addGap(74, 74, 74)
                        .addComponent(exit)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(text_cname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jComboBox_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(text_tk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(text_tkInword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Submite)
                    .addComponent(clear)
                    .addComponent(exit))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("ABC SECURITIES LID.                                          #Receipt");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Motijheel C/A Dhaka-1000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SubmiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmiteActionPerformed
        insert();

    }//GEN-LAST:event_SubmiteActionPerformed

    private void text_cidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cidKeyReleased
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

    private void text_cidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_cidFocusGained
        clear();
    }//GEN-LAST:event_text_cidFocusGained

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        dispose();
    }//GEN-LAST:event_exitActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        clear();
    }//GEN-LAST:event_clearActionPerformed

    private void text_tkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_tkKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            insert();
        }
    }//GEN-LAST:event_text_tkKeyPressed

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
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Submite;
    private javax.swing.JButton clear;
    private javax.swing.JButton exit;
    private javax.swing.JComboBox jComboBox_status;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField text_cid;
    private javax.swing.JTextField text_cname;
    private javax.swing.JTextField text_tk;
    private javax.swing.JTextField text_tkInword;
    private javax.swing.JTextField txt_date;
    // End of variables declaration//GEN-END:variables
}
