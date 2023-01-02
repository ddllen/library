package Library.vipmanage;

import Library.librarymanage.Book;
import Library.librarymanage.LibraryMenu;
import dao.BookDao;
import dao.VipDao;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class VipEnquiry extends JFrame {
    DefaultTableModel dtm=null;
    JTable jt;
    Connection ct=null;
    DbUtil dbUtil=new DbUtil();
    public VipEnquiry(){
        JLabel jLabel = new JLabel("会员名");
        JLabel jLabel1 = new JLabel("性别");
        JTextField jTextField = new JTextField(8);
        JTextField jTextField1 = new JTextField(8);
        JButton jButton = new JButton("返回");
        JButton jButton1=new JButton("查询");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String UserName=jTextField.getText();
                String sex=jTextField1.getText();
                Vip vip=new Vip();
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
        Vector columnNames =new Vector();
        columnNames.add("编号");
        columnNames.add("用户名");
        columnNames.add("性别");
        jt=new JTable(model);
        JScrollPane jsp=new JScrollPane(jt);
        Vector data=new Vector();
        model.setDataVector(data,columnNames);
        fillTable(new Vip());
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VipMenu();
                dispose();
            }
        });
        this.add(jsp);
        this.setLayout(new FlowLayout());
        this.setBounds(700,500,500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private void fillTable(Vip vip){
        dtm= (DefaultTableModel) jt.getModel();
        dtm.setRowCount(0);
        try{
            ct= dbUtil.getCon();
            ResultSet rs= VipDao.list(ct,vip);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(4));
                dtm.addRow(v);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                dbUtil.closeCon(ct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new VipEnquiry();
    }
}
