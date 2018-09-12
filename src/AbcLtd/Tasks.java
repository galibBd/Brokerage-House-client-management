/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbcLtd;

import java.awt.event.KeyEvent;
import java.util.Date;
import java.awt.Component;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author SAIFUL
 */
public class Tasks extends javax.swing.JFrame {

    login log = new login();
    PreparedStatement pstm;
    ResultSet rs;
    Statement stm;

    /**
     * Creates new form Tasks
     */
    public Tasks() {
        initComponents();
        this.setLocationRelativeTo(null);
        createTable();
    }

    public void createTable() {
        try {
            DatabaseMetaData dmd = log.con.getMetaData();
            rs = dmd.getTables(null, null, "transactionTable", null);
            if (rs.next()) {
                //do nothing
            } else {
                String create_table = "CREATE TABLE transactionTable ("
                        + "t_id INTEGER NOT NULL auto_increment primary key,"
                        + "date DATE NOT NULL,"
                        + "c_id INTEGER NOT NULL,"
                        + "status VARCHAR(45) NOT NULL,"
                        + "com_name VARCHAR(45),"
                        + "u_price double,"
                        + "quantity INTEGER ,"
                        + "comm double ,"
                        + "t_cost double ,"
                        + "received double ,"
                        + "payment double ,"
                        + "CONSTRAINT FK_transactionTable_cid FOREIGN KEY FK_transactionTable_cid (c_id)"
                        + " REFERENCES clienttable (c_id)"
                        + ")";
                pstm = log.con.prepareStatement(create_table);
                pstm.executeUpdate();
//                JOptionPane.showMessageDialog(null, "Transaction Table Created");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clearField() {
        text_cid.setText("");
        comb_comName1.removeAllItems();
        text_quantity.setText("");
        text_uPrice.setText("");
        text_stock.setText("");
        text_avgCost.setText("");
        text_comm.setText("");
        text_totalCost.setText("");
        jComboBox_status.setSelectedIndex(0);
    }

    public void updateQuantitybuy() {
//        String name = "";
        int qty = 0;
        double tcost = 0;
        String c_name = comb_comName1.getSelectedItem().toString();

        try {
            String UpdateQuantity = "Select quantity,t_cost,com_name from c_" + text_cid.getText()
                    + " where com_name= '" + c_name + "'";
            stm = log.con.createStatement();
            rs = stm.executeQuery(UpdateQuantity);
            while (rs.next()) {
                qty = rs.getInt("quantity");
                tcost = rs.getDouble("t_cost");
                rs.getString("com_name");
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
        int qty2 = Integer.parseInt(text_quantity.getText());
        double tcost2 = Double.parseDouble(text_totalCost.getText());
        int nqty = qty + qty2;
        double ntcost = tcost + tcost2;

        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery("Select com_name from c_" + text_cid.getText());
            String n = "";
            while (rs.next()) {
                n = rs.getString("com_name");
            }
            if (c_name.equals(n)) {
//                JOptionPane.showMessageDialog(null, "true");
                //////////////////////////////////////--------update--------////////////////////////////////////
                try {
                    String updateQuantity = "update c_" + text_cid.getText() + " set Quantity=?,t_cost=? "
                            + " where com_name ='" + c_name + "'";
                    pstm = log.con.prepareStatement(updateQuantity);
                    pstm.setInt(1, nqty);
                    pstm.setDouble(2, ntcost);
                    int up = pstm.executeUpdate();
                    if (up > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Buy");
                    }
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, sqle);
                }
            } else {
//                JOptionPane.showMessageDialog(null, "false");
                /////////////////////////////////////---------insert-------///////////////////////////////////////////        
                try {
                    String insert = "insert into  c_" + text_cid.getText() + " (com_name, Quantity,t_cost) values(?,?,?)";
                    pstm = log.con.prepareStatement(insert);
                    pstm.setString(1, c_name);                             //comb_comName1.getSelectedItem().toString());
                    pstm.setString(2, text_quantity.getText());
                    pstm.setString(3, text_totalCost.getText());

                    int up = pstm.executeUpdate();
                    if (up > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Buy");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void updateQuantitysale() {
        int qty = 0;
        double tcost = 0;
        String addQuantity = "Select quantity,t_cost from c_" + text_cid.getText()
                + " where com_name= '" + comb_comName1.getSelectedItem() + "'";
        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery(addQuantity);
            while (rs.next()) {
                qty = rs.getInt("quantity");
                tcost = rs.getDouble("t_cost");
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }

        int qty2 = Integer.parseInt(text_quantity.getText());
        double tcost2 = Double.parseDouble(text_totalCost.getText());
        int nqty = qty - qty2;
        double ntcost = tcost - tcost2;

        try {
            String updateQuantity = "update c_" + text_cid.getText() + " set Quantity=?,t_cost=? "
                    + " where com_name ='" + comb_comName1.getSelectedItem() + "'";
            pstm = log.con.prepareStatement(updateQuantity);
            pstm.setInt(1, nqty);
            pstm.setDouble(2, ntcost);
            int up = pstm.executeUpdate();
            if (up > 0) {
                JOptionPane.showMessageDialog(null, "Successfully Sale");
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }

    public void insertAll() {

        try {
            String insert = "insert into transactionTable(date,c_id,status,com_name,u_price,quantity,comm,t_cost,received,payment)"
                    + " values(?,?,?,?,?,?,?,?,?,?)";
            pstm = log.con.prepareStatement(insert);
            pstm.setString(1, text_date1.getText());
            pstm.setString(2, text_cid.getText());
            pstm.setString(3, jComboBox_status.getSelectedItem().toString());
            pstm.setString(4, comb_comName1.getSelectedItem().toString());
            pstm.setString(5, text_uPrice.getText());
            pstm.setString(6, text_quantity.getText());
            pstm.setString(7, text_comm.getText());
            pstm.setString(8, text_totalCost.getText());

            double received = 0;
            pstm.setString(9, String.valueOf(received));

            double payment = 0;
            pstm.setString(10, String.valueOf(payment));

            int up = pstm.executeUpdate();
            if (up > 0) {
//                JOptionPane.showMessageDialog(null, "Data Inserted");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
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

        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        text_stock = new javax.swing.JTextField();
        text_quantity = new javax.swing.JTextField();
        text_avgCost = new javax.swing.JTextField();
        jComboBox_status = new javax.swing.JComboBox();
        text_comm = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        text_totalCost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Date sd= new Date();
        String stdDate =(1900+sd.getYear())+"-"+(sd.getMonth()+1)
        +"-"+(sd.getDate());
        text_date1 = new javax.swing.JTextField(stdDate);
        jLabel9 = new javax.swing.JLabel();
        text_uPrice = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comb_comName1 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        javax.swing.JButton Submite_Button = new javax.swing.JButton();
        text_cid = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        Resat_Button = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Portfolio");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ABC SECURITIES LTD.");

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buy/Sale", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Quantity:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Status:");

        text_stock.setEditable(false);
        text_stock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        text_quantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_quantity.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_quantityKeyReleased(evt);
            }
        });

        text_avgCost.setEditable(false);
        text_avgCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox_status.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox_status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select--", "Buy", "Sale" }));
        jComboBox_status.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_statusItemStateChanged(evt);
            }
        });

        text_comm.setEditable(false);
        text_comm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Rate:");

        text_totalCost.setEditable(false);
        text_totalCost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Available Quantity:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Date:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Average Rate:");

        text_date1.setEditable(false);
        text_date1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Commission:");

        text_uPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        text_uPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Total Cost:");

        comb_comName1.setEditable(true);
        comb_comName1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Client ID:");

        Submite_Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Submite_Button.setForeground(new java.awt.Color(0, 153, 0));
        Submite_Button.setText("Submit");
        Submite_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Submite_ButtonActionPerformed(evt);
            }
        });

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

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Company Name:");

        Resat_Button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Resat_Button.setForeground(new java.awt.Color(255, 0, 51));
        Resat_Button.setText("Reset");
        Resat_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Resat_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(75, 75, 75)
                                .addComponent(jComboBox_status, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(87, 87, 87)
                                .addComponent(text_uPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(61, 61, 61)
                                .addComponent(text_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(63, 63, 63)
                                    .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(15, 15, 15)
                                    .addComponent(comb_comName1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(73, 73, 73))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Submite_Button)
                        .addGap(33, 33, 33)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(100, 100, 100)
                        .addComponent(text_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(47, 47, 47)
                            .addComponent(text_avgCost, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Resat_Button)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(text_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(62, 62, 62)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_totalCost, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_comm, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(47, 47, 47))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(text_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_avgCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_comm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(text_totalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel11))
                            .addComponent(text_cid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel13))
                            .addComponent(comb_comName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel5))
                            .addComponent(jComboBox_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(text_uPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(text_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Submite_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Resat_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 2, 1, new java.awt.Color(0, 0, 255)));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 102, 0));
        jButton3.setText("Ledger");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setText("Portfolio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 0, 204));
        jButton5.setText("Cash Receipt");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 0));
        jButton1.setText("Add Client");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("ABC SECURITIES LID.");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder/images (2).jpg"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Motijheel C/A Dhaka-1000");

        jMenu1.setMnemonic('f');
        jMenu1.setText("File");

        jMenu3.setText("Client");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Add Client");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Client List");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenu1.add(jMenu3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("All Transaction");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Portfolio");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Ledger");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Exit");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(225, 225, 225))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new AddClient().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new Receipt().setVisible(true);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new AddClient().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            new AddClient().setVisible(true);
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new Ledger().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new portfolio().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void text_quantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_quantityKeyReleased
        double uPrice = Double.parseDouble(text_uPrice.getText());
        int qun = Integer.parseInt(text_quantity.getText());
        double total = (uPrice * qun);
        double comm = 0;
        if (total < 1000) {
            comm = (total * 0.005 + 10);
        }
        if (total > 1000) {
            comm = (total * 0.005);
        }
        double tcost = total + comm;

        text_comm.setText(String.format("%.2f", comm));
        text_totalCost.setText(String.format("%.2f", tcost));
    }//GEN-LAST:event_text_quantityKeyReleased

    private void jComboBox_statusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_statusItemStateChanged

        double avg = 0;
        String avgCost = "Select quantity,t_cost from c_" + text_cid.getText() + " where com_name = '" + comb_comName1.getSelectedItem() + "'";
        try {
            stm = log.con.createStatement();
            rs = stm.executeQuery(avgCost);
            while (rs.next()) {
                int qty = rs.getInt("quantity");
                double tcost = rs.getDouble("t_cost");
                avg = tcost / qty;
                text_stock.setText(String.valueOf(qty));
                text_avgCost.setText(String.format("%.3f", avg));
            }
        } catch (SQLException sqle) {
//            JOptionPane.showMessageDialog(null, sqle);
        }
    }//GEN-LAST:event_jComboBox_statusItemStateChanged

    private void Submite_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Submite_ButtonActionPerformed
        if (jComboBox_status.getSelectedItem().equals("--Select--")) {
            JOptionPane.showMessageDialog(null, "Please select Buy or Sale", "", JOptionPane.ERROR_MESSAGE);
        }
        //==================================================================================================
        if (jComboBox_status.getSelectedItem().equals("Buy")) {
            double tcost = Double.parseDouble(text_totalCost.getText());
            double tbuy = 0;
            double tsale = 0;
            double bal = 0;
            int depo = 0;
            int wdraw = 0;
            try {
                String totalBuy = "select t_cost from transactiontable where c_id=" + text_cid.getText() + " and status = 'Buy'";
                stm = log.con.createStatement();
                rs = stm.executeQuery(totalBuy);
                while (rs.next()) {
                    double t = Double.parseDouble(rs.getString("t_cost"));
                    tbuy += t;
//                    System.out.println("buy: " + tbuy);
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
//                    System.out.println("sale: " + tsale);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
            bal = depo - tbuy + tsale - wdraw;
//            System.out.println(bal);
            if (tcost > bal) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance", "", JOptionPane.ERROR_MESSAGE);
            } else {
                insertAll();
                updateQuantitybuy();
            }

        }
        if (jComboBox_status.getSelectedItem().equals("Sale")) {
            int s = Integer.parseInt(text_stock.getText());
            int q = Integer.parseInt(text_quantity.getText());
            if (q > s) {
                JOptionPane.showMessageDialog(null, "Quantity not available", "", JOptionPane.ERROR_MESSAGE);
            } else {
                insertAll();
                updateQuantitysale();
            }

        }

    }//GEN-LAST:event_Submite_ButtonActionPerformed

    private void text_cidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_text_cidFocusGained
        clearField();
    }//GEN-LAST:event_text_cidFocusGained

    private void text_cidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_cidKeyReleased

        comb_comName1.removeAllItems();
        comb_comName1.setSelectedItem("");
        try {

            stm = log.con.createStatement();
            rs = stm.executeQuery("Select com_name from c_" + text_cid.getText());
            while (rs.next()) {
                comb_comName1.addItem(rs.getString("com_name"));
            }
        } catch (SQLException e) {
            //            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_text_cidKeyReleased

    private void Resat_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Resat_ButtonActionPerformed
        clearField();
    }//GEN-LAST:event_Resat_ButtonActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new AllTransaction().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new portfolio().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new Ledger().setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        ClientList cl = new ClientList();
        cl.setVisible(true);
        cl.Show();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
            java.util.logging.Logger.getLogger(Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tasks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Resat_Button;
    private javax.swing.JComboBox comb_comName1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox_status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField text_avgCost;
    private javax.swing.JTextField text_cid;
    private javax.swing.JTextField text_comm;
    private javax.swing.JTextField text_date1;
    private javax.swing.JTextField text_quantity;
    private javax.swing.JTextField text_stock;
    private javax.swing.JTextField text_totalCost;
    private javax.swing.JTextField text_uPrice;
    // End of variables declaration//GEN-END:variables
}
