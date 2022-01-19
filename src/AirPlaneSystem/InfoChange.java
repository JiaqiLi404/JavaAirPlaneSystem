package AirPlaneSystem;

import AirPlaneSystem.Database.MyConnection;
import AirPlaneSystem.Objects.Admin;
import AirPlaneSystem.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InfoChange extends JFrame {
    Label labelPhone, labelName, labelType, labelTyper, labelDiscount, labelDiscountr, labelWallet, labelWalletr;
    JTextField textPhone, textName, textDiscount, textWallet;
    JComboBox<String> boxType;
    Button buttonEnqueue, buttonWallet, buttonOK, buttonReset, buttonExit;
    JPanel panelbase, panelButton;
    JFrame thisFrame;
    Main mainFrame;
    boolean adminMode;
    MyConnection database;
    User user;
    Admin admin;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Font buttonFont = new Font("黑体", Font.PLAIN, 20);
    Font labelFont = new Font("黑体", Font.PLAIN, 17);
    Font welcomeFont = new Font("黑体", Font.BOLD, 30);

    public InfoChange(Admin admin, MyConnection database, Main mainFrame) {
        adminMode = true;
        this.mainFrame = mainFrame;
        this.database = database;
        this.admin = admin;
        init_compenent();

    }

    private void init_compenent() {
        //界面设置
        this.setTitle("个人信息修改");
        this.setSize(1920, 900);
        this.setResizable(true);
        setLayout(null);
        thisFrame = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainFrame.setVisible(true);
            }
        });
        //插入面板
        GridLayout glayout1 = new GridLayout(3, 4);
        glayout1.setHgap(10);
        glayout1.setVgap(90);
        panelbase = new JPanel();
        panelbase.setLayout(glayout1);

        GridLayout glayout2 = new GridLayout(1, 4);
        glayout2.setHgap(50);
        glayout2.setVgap(50);
        panelButton = new JPanel();
        panelButton.setLayout(glayout2);
        //插入控件
        labelPhone = get_label("手机号:");
        labelName = get_label("姓名:");
        labelType = get_label("用户类型:");
        labelDiscount = get_label("购票折扣:");
        labelWallet = get_label("钱包余额:");
        textPhone = get_text();
        textName = get_text();
        labelWalletr = get_label("");
        if (!adminMode) {
            labelTyper = get_label(user.type);
            labelDiscountr = get_label(String.valueOf(user.discount));
            labelWalletr.setText(String.valueOf(user.wallet));
        } else {
            boxType = get_usertype();
            textDiscount = get_text();
        }
        panelbase.add(labelPhone);
        panelbase.add(textPhone);
        panelbase.add(labelName);
        panelbase.add(textName);
        panelbase.add(labelType);
        if (!adminMode)
            panelbase.add(labelTyper);
        else
            panelbase.add(boxType);
        panelbase.add(labelDiscount);
        if (!adminMode)
            panelbase.add(labelDiscountr);
        else
            panelbase.add(textDiscount);
        panelbase.add(labelWallet);
        panelbase.add(labelWalletr);

        buttonEnqueue = get_button("查询");
        buttonWallet = get_button("充值");
        buttonOK = get_button("确认");
        buttonReset = get_button("重置");
        buttonExit = get_button("返回");
        if (adminMode)
            panelButton.add(buttonEnqueue);
        panelButton.add(buttonWallet);
        panelButton.add(buttonOK);
        panelButton.add(buttonReset);
        panelButton.add(buttonExit);

        panelbase.setBounds(300, 30, 1200, 500);
        add(panelbase);
        panelButton.setBounds(300, 550, 1200, 50);
        add(panelButton);

        if (adminMode) {
            buttonWallet.setEnabled(false);
            buttonOK.setEnabled(false);
        }else{
            get_user_info();
        }


        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(thisFrame, "确定修改信息吗?", "提示", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    String type=user.type;
                    double discount=user.discount;
                    if(adminMode){
                        type= (String) boxType.getSelectedItem();
                        discount= Double.parseDouble(textDiscount.getText());
                    }
                    if(database.change_user(user, textPhone.getText(), textName.getText(),type, discount, user.wallet)){
                        user = new User(textPhone.getText(), textName.getText(), type, discount, user.wallet);
                        if(!adminMode){
                            mainFrame.reset_welcome(user.phone);
                        }

                        JOptionPane.showMessageDialog(thisFrame, "信息修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(thisFrame, "信息不合法或该手机号已被占用！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });
        buttonWallet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double money=0;
                try {
                    money = Double.parseDouble(JOptionPane.showInputDialog("请输入金额:"));
                    if( money<=0) throw new Exception("充值金额为负数");
                    if (JOptionPane.showConfirmDialog(thisFrame, "确定充值 "+String.valueOf(money)+" 元吗?", "提示", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                        database.change_user(user, textPhone.getText(), textName.getText(), user.type, user.discount, user.wallet+money);
                        user.wallet+=money;
                        JOptionPane.showMessageDialog(thisFrame, "充值成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        get_user_info();
                    }
                }catch (Exception exp){
                    JOptionPane.showMessageDialog(thisFrame, "金额输入不合法！", "警告", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adminMode) {
                    buttonWallet.setEnabled(false);
                    buttonOK.setEnabled(false);
                    textPhone.setText("");
                    textName.setText("");
                    textDiscount.setText("");
                    boxType.setSelectedIndex(0);
                    labelWalletr.setText("");
                }else{
                    get_user_info();
                }
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        buttonEnqueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user=database.get_user(textPhone.getText());
                if(user==null){
                    JOptionPane.showMessageDialog(thisFrame, "查找的用户不存在！", "警告", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                get_user_info();
                if(adminMode){
                    buttonWallet.setEnabled(true);
                    buttonOK.setEnabled(true);
                }
            }
        });

    }

    void get_user_info() {
        textPhone.setText(user.phone);
        textName.setText(user.name);
        labelWalletr.setText(String.valueOf(user.wallet));
        if (adminMode) {
            boxType.setSelectedItem(user.type);
            textDiscount.setText(String.valueOf(user.discount));
        }
        panelbase.updateUI();
        panelButton.updateUI();
        this.repaint();
    }

    private JComboBox<String> get_usertype() {
        JComboBox<String> box = new JComboBox<String>();
        box.addItem("会员");
        box.addItem("普通用户");
        return box;
    }

    public InfoChange(User user, MyConnection database, Main mainFrame) {
        adminMode = false;
        this.mainFrame = mainFrame;
        this.database = database;
        this.user = user;
        init_compenent();
    }

    Label get_label(String text) {
        Label l = new Label(text);
        l.setFont(labelFont);
        return l;
    }

    Button get_button(String text) {
        Button b = new Button(text);
        b.setFont(buttonFont);
        return b;
    }

    JTextField get_text() {
        JTextField t = new JTextField();
        t.setFont(labelFont);
        return t;
    }

}
