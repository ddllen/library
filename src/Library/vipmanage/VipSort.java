package Library.vipmanage;

import Library.librarymanage.Book;
import dao.BookDao;
import dao.VipDao;
import util.DbUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

public class VipSort extends JFrame {
    JTable jTable;
    DefaultTableModel dtm;
    Connection ct=null;
    DbUtil dbUtil=new DbUtil();
    public VipSort(){
        DefaultTableModel model = new DefaultTableModel();
        JButton jButton = new JButton("用户名排序");
        JButton jButton1=new JButton("按性别排序");
        JButton jButton2=new JButton("返回");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String a="username";
                try{
                    ct= dbUtil.getCon();
                    VipDao.sort(ct,a);
                    fillTable(new Vip());
                }catch(Exception e){
                    throw new RuntimeException(e);
                }finally {
                    try {
                        dbUtil.closeCon(ct);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String a="sex";
                try{
                    ct= dbUtil.getCon();
                    VipDao.sort(ct,a);
                    fillTable(new Vip());
                }catch(Exception e){
                    throw new RuntimeException(e);
                }finally {
                    try {
                        dbUtil.closeCon(ct);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new operateVip();
                dispose();
            }
        });
        Vector columnNames = new Vector();
        columnNames.add("username");
        columnNames.add("sex");
        jTable=new JTable(model){
            public boolean isCellEditing(int row,int column){return false;}
        };
        JScrollPane jsp=new JScrollPane(jTable);
        Vector data=new Vector();
        model.setDataVector(data,columnNames);
        RowSorter sorter=new TableRowSorter(model);
        jTable.setRowSorter(sorter);
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());
        jPanel1.add(jButton);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        fillTable(new Vip());
        this.setLayout(new BorderLayout());
        jPanel.add(jsp);
        this.add(jPanel,BorderLayout.NORTH);
        this.add(jPanel1,BorderLayout.SOUTH);
        this.setLocation(700,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }
    private void fillTable(Vip vip){
        dtm= (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(0);
        try{
            ct= dbUtil.getCon();
            ResultSet rs= VipDao.list(ct,vip);
            while(rs.next()){
                Vector v=new Vector();
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
}
