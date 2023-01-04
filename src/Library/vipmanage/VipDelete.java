package Library.vipmanage;

import Library.librarymanage.Book;
import dao.BookDao;
import dao.VipDao;
import util.DbUtil;
import util.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class VipDelete extends JFrame {
    DefaultTableModel dtm = null;
    JTable jt;
    Connection ct = null;
    DbUtil dbUtil = new DbUtil();

    public VipDelete() {
        JLabel jLabel = new JLabel("会员名");
        JLabel jLabel1 = new JLabel("性别");
        JTextField jTextField = new JTextField(8);
        JTextField jTextField1 = new JTextField(8);
        JButton jButton = new JButton("返回");
        JButton jButton1 = new JButton("查询");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String UserName = jTextField.getText();
                String sex = jTextField1.getText();
                Vip vip = new Vip();
                vip.setSex(sex);
                vip.setUserName(UserName);
                fillTable(vip);

            }
        });
        this.add(jLabel);
        this.add(jTextField);
        this.add(jLabel1);
        this.add(jTextField1);
        this.add(jButton1);
        this.add(jButton);
        DefaultTableModel model = new DefaultTableModel();
        Vector columnNames = new Vector();
        columnNames.add("编号");
        columnNames.add("用户名");
        columnNames.add("性别");
        jt=new JTable(model){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JScrollPane jsp = new JScrollPane(jt);
        Vector data = new Vector();
        model.setDataVector(data, columnNames);
        fillTable(new Vip());
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new operateVip();
                dispose();
            }
        });
        this.add(jsp);
        JLabel jLabel2 = new JLabel("编号");
        JLabel jLabel3 = new JLabel("用户名");
        JLabel jLabel4 = new JLabel("性别");
        JTextField jTextField2 = new JTextField(6);
        jTextField2.setEditable(false);
        JTextField jTextField3 = new JTextField(6);
        jTextField3.setEditable(false);
        JTextField jTextField4 = new JTextField(6);
        jTextField4.setEditable(false);
        this.add(jLabel2);this.add(jTextField2);
        this.add(jLabel3);this.add(jTextField3);
        this.add(jLabel4);this.add(jTextField4);
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row=jt.getSelectedRow();
                jTextField2.setText((String) jt.getValueAt(row,0));
                jTextField3.setText((String) jt.getValueAt(row,1));
                jTextField4.setText((String) jt.getValueAt(row,2));
            }
        });
        JButton jButton2 = new JButton("删除");
        this.add(jButton2);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String num=jTextField2.getText();
                if(StringUtil.isEmpty(num)){
                    JOptionPane.showMessageDialog(null,"请选择你要删除的用户");
                }
                Connection con=null;
                try{
                    con=dbUtil.getCon();
                    int modifyNum= VipDao.delete(con, Integer.parseInt(num));
                    if(modifyNum==1){
                        JOptionPane.showMessageDialog(null,"删除成功");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"删除失败");
                    }
                    fillTable(new Vip());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        this.setLayout(new FlowLayout());
        this.setBounds(700, 500, 500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void fillTable(Vip vip) {
        dtm = (DefaultTableModel) jt.getModel();
        dtm.setRowCount(0);
        try {
            ct = dbUtil.getCon();
            ResultSet rs = VipDao.list(ct, vip);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(4));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(ct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
