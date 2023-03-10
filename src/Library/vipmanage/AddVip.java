package Library.vipmanage;

import Library.librarymanage.Book;
import Library.librarymanage.LibraryMenu;
import dao.BookDao;
import dao.VipDao;
import util.DbUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddVip extends JFrame{
        DbUtil dbUtil=new DbUtil();
        VipDao vd=new VipDao();
    public AddVip(){
            Container container = this.getContentPane();
            this.setLayout(new FlowLayout());
            setBounds(700,500,550,200);
            JPanel jPanel = new JPanel();jPanel.setLayout(new FlowLayout());
            JPanel jPanel1 = new JPanel();jPanel1.setLayout(new FlowLayout());
            JPanel jPanel2 = new JPanel();jPanel2.setLayout(new FlowLayout());
            JPanel jPanel3 = new JPanel();jPanel3.setLayout(new FlowLayout());
            JLabel jLabel1 = new JLabel("用户名:");
            JLabel jLabel2= new JLabel("密码:");
            JLabel jLabel3 = new JLabel("性别:");
            JTextField jTextField1 = new JTextField(10);
            JTextField jTextField2 = new JTextField(10);
            JTextField jTextField3 = new JTextField(10);
            jPanel1.add(jLabel1);jPanel1.add(jTextField1);
            jPanel2.add(jLabel2);jPanel2.add(jTextField2);
            jPanel3.add(jLabel3);jPanel3.add(jTextField3);
            JButton jb1 = new JButton("确认");
            JButton jb2 = new JButton("返回");
            JButton jb3 = new JButton("清空");
            container.add(jPanel);
            container.add(jPanel1);
            container.add(jPanel2);
            container.add(jPanel3);
            container.add(jb1);
            container.add(jb2);
            container.add(jb3);
            this.setVisible(true);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jb1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                            String UserName = jTextField1.getText();
                            String PassWord = jTextField2.getText();
                            String Sex = jTextField3.getText();
                            if(StringUtil.isEmpty(UserName)||StringUtil.isEmpty(PassWord)||StringUtil.isEmpty(Sex)){
                                    JOptionPane.showMessageDialog(null,"内容不能为空");
                            }
                            else {
                                    Vip vip=new Vip(UserName,Sex,PassWord);
                                    jTextField1.setText(null);
                                    jTextField2.setText(null);
                                    jTextField3.setText(null);
                                    Connection con=null;
                                    try {
                                            int i=0;
                                            con= dbUtil.getCon();
                                            Statement statement=con.createStatement();
                                            ResultSet resultset=statement.executeQuery("select * from t_vip");
                                            while(resultset.next()){
                                                    if(UserName.equals(resultset.getString("username"))){i=1;}
                                            }
                                            if(i==0) {
                                                    int addNum = vd.add(con, vip);
                                                    if(addNum==1){
                                                            JOptionPane.showMessageDialog(null,"会员添加成功");
                                                    }
                                                    else{
                                                            JOptionPane.showMessageDialog(null,"会员添加失败");
                                                    }
                                            }
                                            else{JOptionPane.showMessageDialog(null,"用户名重复");}
                                    } catch (Exception ex) {
                                            throw new RuntimeException(ex);
                                    }finally {
                                            try {
                                                    dbUtil.closeCon(con);
                                            } catch (Exception ex) {
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
                            jTextField1.setText(null);
                            jTextField2.setText(null);
                            jTextField3.setText(null);
                    }
            });
    }
}
