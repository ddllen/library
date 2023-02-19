package Library.librarymanage;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import dao.BookDao;
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

public class BookSort extends JFrame {
    JTable jTable;
    DefaultTableModel dtm;
    Connection ct=null;
    DbUtil dbUtil=new DbUtil();
    public BookSort(){
        DefaultTableModel model = new DefaultTableModel();
        JButton jButton = new JButton("按ISBN码排");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String a="ISBN";
                try {
                    ct=dbUtil.getCon();
                    BookDao.sort(ct,a);
                    fillTable(new Book());
                } catch (Exception e) {
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
        JButton jButton1 = new JButton("按书名排");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String a="name";
                try {
                    ct=dbUtil.getCon();
                    BookDao.sort(ct,a);
                    fillTable(new Book());
                } catch (Exception e) {
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
        JButton jButton2 = new JButton("按作者排");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String a="author";
                try {
                    ct=dbUtil.getCon();
                    BookDao.sort(ct,a);
                    fillTable(new Book());
                } catch (Exception e) {
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
        JButton jButton3 = new JButton("按出版社排");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String a="press";
                try {
                    ct=dbUtil.getCon();
                    BookDao.sort(ct,a);
                    fillTable(new Book());
                } catch (Exception e) {
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
        JButton jButton4 = new JButton("返回");
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new operateBook();
                dispose();
            }
        });
        Vector columnNames =new Vector();
        columnNames.add("ISBN");
        columnNames.add("书名");
        columnNames.add("作者");
        columnNames.add("出版社");
        columnNames.add("状态");
        jTable=new JTable(model){
            public boolean isCellEditable(int row,int column){
                return false;
            }
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
        jPanel1.add(jButton3);
        jPanel1.add(jButton4);
        fillTable(new Book());
        this.setLayout(new BorderLayout());
        jPanel.add(jsp);
        this.add(jPanel,BorderLayout.NORTH);
        this.add(jPanel1,BorderLayout.SOUTH);
        this.setLocation(700,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }
    private void fillTable(Book book){
        dtm= (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(0);
        try{
            ct= dbUtil.getCon();
            ResultSet rs= BookDao.list(ct,book);
            while(rs.next()){
                Vector v=new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
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
        new BookSort();
    }
}
