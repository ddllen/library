package Library.vipmanage;

import Library.librarymanage.Book;
import Library.librarymanage.operateBook;
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

public class InsertVip extends JFrame {
    DefaultTableModel dtm;
    JTable jt;
    Connection ct;
    DbUtil dbUtil=new DbUtil();
    VipDao vipDao=new VipDao();
    double id;
    public InsertVip(){
        Container container=this.getContentPane();
        JPanel jPanel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JLabel jLabel = new JLabel("用户名:");
        JLabel jLabel3 = new JLabel("密码");
        JLabel jLabel1 = new JLabel("性别:");
        JLabel jLabel2 = new JLabel("说明:选中某行后，将会在某行后面添加会员");
        JTextField jTextField = new JTextField(10);
        JTextField jTextField1 = new JTextField(10);
        JTextField jTextField2 = new JTextField(10);
        jPanel.add(jLabel);jPanel.add(jTextField);
        jPanel.add(jLabel3);jPanel.add(jTextField2);
        jPanel.add(jLabel1);jPanel.add(jTextField1);
        JButton jb1 = new JButton("确认");
        JButton jb2 = new JButton("返回");
        JButton jb3 = new JButton("清空");
        jPanel1.add(jb1);
        jPanel1.add(jb2);
        jPanel1.add(jb3);
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        Vector columnNames =new Vector();
        columnNames.add("用户名");
        columnNames.add("性别");
        jt=new JTable(model){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        JScrollPane jsp=new JScrollPane(jt);
        Vector data=new Vector();
        model.setDataVector(data,columnNames);
        fillTable(new Vip());
        container.add(jLabel2);
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new BorderLayout());
        jPanel3.add(jLabel2,BorderLayout.NORTH);
        jPanel3.add(jsp,BorderLayout.CENTER);
        container.add(jPanel3,BorderLayout.NORTH);
        container.add(jPanel,BorderLayout.CENTER);
        container.add(jPanel1,BorderLayout.SOUTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int row=jt.getSelectedRow();
                try{
                    ct=dbUtil.getCon();
                    ResultSet rs=VipDao.list(ct,new Vip());
                    while(rs.next()){
                        if(rs.getString("username").equals(jt.getValueAt(row,0))){
                            id=rs.getInt("id");
                        }
                    }
                }catch (Exception e){
                    throw new RuntimeException(e);
                }finally {
                    try{
                        dbUtil.closeCon(ct);
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = jTextField.getText();
                String sex = jTextField1.getText();
                String password = jTextField2.getText();
                if(StringUtil.isEmpty(username)||StringUtil.isEmpty(sex)||StringUtil.isEmpty(password)){
                    JOptionPane.showMessageDialog(null,"内容不能为空");
                }
                else{
                    Vip vip1=new Vip(username,sex,password);
                    vip1.setId(id+0.1);
                    jTextField.setText(null);
                    jTextField1.setText(null);
                    jTextField2.setText(null);
                    try{
                        ct= dbUtil.getCon();
                        int AddId=vipDao.incert(ct,vip1);
                        if(AddId==1){
                            JOptionPane.showMessageDialog(null,"Vip添加成功");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Vip添加失败");
                        }
                        fillTable(new Vip());
                    }catch (Exception ex){
                        throw new RuntimeException(ex);
                    }finally {
                        try{
                            dbUtil.closeCon(ct);
                        }catch (Exception ex){
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new operateVip();
                dispose();
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextField.setText(null);
                jTextField1.setText(null);
                jTextField2.setText(null);
            }
        });
        this.setLocation(700,300);
        this.pack();
    }
    private void fillTable(Vip vip){
        dtm= (DefaultTableModel) jt.getModel();
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
