/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbcLtd;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SAIFUL
 */
public class portfolio extends javax.swing.JFrame {

    login log = new login();
    ResultSet rs;
    Statement stm;
    DefaultTableModel dtm = new DefaultTableModel();

    /**
     * Creates new form portfolio
     */
    public portfolio() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void table() {

        try {
            Vector<String> header = new Vector<String>();
            header.add("SL#");
            header.add("Compamy Name");
            header.add("Quantity");
            header.add(" Avg. Rate");
            header.add("Total Cost");

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();

                row.add(rs.getString("com_id"));
                row.add(rs.getString("com_name"));
                int qty = Integer.parseInt(rs.getString("quantity"));
                row.add(qty);
                double cost = Double.parseDouble(rs.getString("t_cost"));
                double avg = cost / qty;
                row.add(String.format("%.2f", avg));
                row.add(String.format("%.2f", cost));

                data.add(row);

                dtm.setDataVector(data, header);

                double total = 0;

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void Show() {

        try {
            String select = "select * from c_" + text_cid.getText();
            stm = log.con.createStatement();
            rs = stm.executeQuery(select);
            table();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void ShowInTextArea() {
        String name = "ABC SECURITIES LTD.";
        String add = "Motijheel C/A Dhaka-1000";
        String portfolio = "#PORTFOLIO";
        String clname = text_cname.getText();
        String cid = text_cid.getText();
        double tbuy= 0;
        double tsale= 0;
        double bal= 0;
        int depo=0;
        int wdraw=0;
        double gain= 0;
//        jTextArea1.setText(abc);
        try {
            String select = "select * from c_" + text_cid.getText();
            stm = log.con.createStatement();
            rs = stm.executeQuery(select);
            int a = 0;
            String b = "";
            int c = 0;
            double d = 0;
            double e=0;
            String s = "";
            double totalCost=0;
            while (rs.next()) {

                a = rs.getInt(1);
                b = rs.getString(2);
                c = rs.getInt(3);
                d = rs.getDouble(4);
                e=d/c;
                s+=a+"\t"+b+"\t\t"+c+"\t\t"+String.format("%.2f", e)+"\t\t"+String.format("%.2f", d)+"\n";
                totalCost += d;
            }
            try {
                String totalBuy = "select t_cost from transactiontable where c_id="+ text_cid.getText()+" and status = 'Buy'";
            stm = log.con.createStatement();
            rs = stm.executeQuery(totalBuy);
            while(rs.next()){
               double t= Double.parseDouble(rs.getString("t_cost"));
               tbuy +=t; 
//                System.out.println("buy: "+tbuy);
            }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            try {
                String deposit="select received from test.transactiontable where c_id="+ text_cid.getText();
                stm = log.con.createStatement();
                rs= stm.executeQuery(deposit);
                while(rs.next()){
                int dip= rs.getInt("received");
                depo +=dip;
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
             try {
                String withdraw="select payment from test.transactiontable where c_id="+ text_cid.getText();
                stm = log.con.createStatement();
                rs= stm.executeQuery(withdraw);
                while(rs.next()){
                int wid= rs.getInt("payment");
                wdraw +=wid;
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            
            try {
                String totalBuy = "select t_cost from transactiontable where c_id="+ text_cid.getText()+" and status = 'Sale'";
            stm = log.con.createStatement();
            rs = stm.executeQuery(totalBuy);
            while(rs.next()){
               double t= Double.parseDouble(rs.getString("t_cost"));
               tsale +=t; 
//               System.out.println("sale: "+tsale);
            }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            bal = depo-tbuy+ tsale-wdraw;
            
//            gain = depo- (bal+totalCost);

             String A = "\nDate: "+date.getText()+"\n\n"+name+"\t\t\t\t\t"+portfolio+"\n"+add
                     +"\n\nClient Name: "+clname+"\t\tClient ID: "+cid+"\n\nSL"
                     + "\tCompany Name\tQuantity\t\tAvg. Rate\t\tTotal Cost\n"+s+"\n\nGrand-Total : \t\t\t\t\t\t\t "+String.format("%.2f", totalCost)
                     +"\n\n\n\tDeposit:\t "+depo+"\t\tLedger Balance:\t "+String.format("%.2f", bal)+"\n\n\tWithdrawn:\t "+wdraw; //+"\t\t Gain/Loss:\t\t "+String.format("%.2f", gain);
            
            jTextArea1.setText(String.valueOf(A));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Client not Exist","",JOptionPane.ERROR_MESSAGE);
            jTextArea1.setText("");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        text_cid = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        text_cname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        Date sd= new Date();
        String stdDate =(1900+sd.getYear())+"-"+(sd.getMonth()+1)
        +"-"+(sd.getDate());
        date = new javax.swing.JTextField(stdDate);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Portfolio");

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ABC Securities ltd.                                              #Portfoloi");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Motijheel C/A Dhaka-1000");

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204), 2));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Client Id:");

        text_cid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_cid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                text_cidFocusGained(evt);
            }
        });
        text_cid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_cidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_cidKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Client Name:");

        text_cname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 0));
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("Print");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton1)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_cname, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_cname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(263, Short.MAX_VALUE))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Date:");

        date.setEditable(false);
        date.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_cidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_cidFocusGained
        text_cid.setText("");
        text_cname.setText("");
    }//GEN-LAST:event_text_cidFocusGained

    private void text_cidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cidKeyReleased
        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery("select c_name from clienttable where c_id= " + text_cid.getText());
            while (rs.next()) {
                text_cname.setText(rs.getString("c_name"));
            }

        } catch (SQLException ex) {
//                       JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_text_cidKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ShowInTextArea();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void text_cidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cidKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
         ShowInTextArea();
        }
    }//GEN-LAST:event_text_cidKeyPressed

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
            java.util.logging.Logger.getLogger(portfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(portfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(portfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(portfolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new portfolio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField text_cid;
    private javax.swing.JTextField text_cname;
    // End of variables declaration//GEN-END:variables
}
