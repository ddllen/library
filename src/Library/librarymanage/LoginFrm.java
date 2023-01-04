package Library.librarymanage;

import Library.vipmanage.Vip;
import dao.VipDao;
import util.DbUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginFrm extends JFrame {
    String username;
    String password;
    DbUtil dbUtil=new DbUtil();
    VipDao vd=new VipDao();
    public LoginFrm() {
            Container container = this.getContentPane();
            JTextField jTextField = new JTextField(16);
            JTextField jTextField1 = new JTextField(16);
            JButton jButton = new JButton("登录");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    username = jTextField.getText();
                    password = jTextField1.getText();
                    if (StringUtil.isEmpty(username)) {
                        JOptionPane.showMessageDialog(null, "用户名不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(password)) {
                        JOptionPane.showMessageDialog(null, "密码不能为空");
                        return;
                    }
                    Vip vip = new Vip(username, password);
                    Connection con = null;
                    try {
                        con = dbUtil.getCon();
                        Vip currentVip = vd.login(con, vip);
                        if (currentVip != null) {
                            JOptionPane.showMessageDialog(null, "登陆成功");
                            BorrowReturnBook br=new BorrowReturnBook();
                            br.setName(username);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "用户名密码错误");
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            JButton jButton1 = new JButton("返回");
            jButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new LibraryMenu();
                    dispose();
                }
            });
            JButton jButton2 = new JButton("清空");
            jButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jTextField.setText(null);
                    jTextField1.setText(null);
                }
            });
            JLabel jLabel = new JLabel("账号:");
            JLabel jLabel1 = new JLabel("密码:");
            this.setLayout(new FlowLayout());
            container.add(jLabel);
            container.add(jTextField);
            container.add(jLabel1);
            container.add(jTextField1);
            container.add(jButton);
            container.add(jButton1);
            container.add(jButton2);
            this.setVisible(true);
            this.setBounds(1000, 500, 500, 200);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
}
