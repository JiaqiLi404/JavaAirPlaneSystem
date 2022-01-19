package AirPlaneSystem;

import AirPlaneSystem.Database.MyConnection;
import AirPlaneSystem.Objects.Admin;
import AirPlaneSystem.Objects.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;

public class Main extends JFrame{
	JFrame mainFrame;
	Main mmainFrame;
	Button buttonChange;
	Button buttonSearch;
	Button buttonBook;
	Button buttonUnsubscribe;
	Button buttonLogin;
	Button buttonRegister;
	Button buttonInfo;
	Button buttonExit;
	JPanel panelAppearence;
	JPanel panelButtons;
	JPanel panelLogin;
	Label labelWelcome;
	Label labelUsername;
	Label labelPassword;
	Label labelType;
	JTextField textUsername;
	JPasswordField textPassword;
	JComboBox<String> boxType;
	public User user;
	MyConnection database;
	Admin admin;
	public Main(String title)
	{
		super(title);
		//this.setExtendedState(MAXIMIZED_BOTH);
		this.setSize(1920,900);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(null);
		mainFrame=this;
		mmainFrame=this;
		user=null;admin=null;
		//建立数据库对象
		database=new MyConnection();
		//设置字体
		Font buttonFont =new Font("黑体", Font.PLAIN, 30);
		Font welcomeFont=new Font("黑体",Font.BOLD,40);
		//插入面板
		panelAppearence=new JPanel();
		panelButtons=new JPanel();
		panelButtons.setLayout(null);
		panelLogin=new JPanel();
		GridLayout glayout=new GridLayout(4,2);
		glayout.setHgap(50);
		glayout.setVgap(50);
		panelLogin.setLayout(glayout);
		//插入控件
		buttonLogin = new Button("登录");
		buttonLogin.setBounds(0,0,200,200);
		buttonLogin.setFont(buttonFont);

		buttonRegister = new Button("注册");
		buttonRegister.setBounds(400,0,200,200);
		buttonRegister.setFont(buttonFont);


		buttonSearch = new Button("查询航班");
		buttonSearch.setBounds(0,0,200,200);
		buttonSearch.setFont(buttonFont);

		buttonBook = new Button("预订航班");
		buttonBook.setBounds(400,0,200,200);
		buttonBook.setFont(buttonFont);

		buttonChange = new Button("改定航班");
		buttonChange.setBounds(800,0,200,200);
		buttonChange.setFont(buttonFont);

		buttonUnsubscribe = new Button("退订航班");
		buttonUnsubscribe.setBounds(0,280,200,200);
		buttonUnsubscribe.setFont(buttonFont);

		buttonInfo = new Button("个人信息");
		buttonInfo.setBounds(400,280,200,200);
		buttonInfo.setFont(buttonFont);

		buttonExit = new Button("退出登录");
		buttonExit.setBounds(800,280,200,200);
		buttonExit.setFont(buttonFont);

		labelWelcome=new Label("欢迎使用机票管理系统");
		labelWelcome.setFont(welcomeFont);
		labelWelcome.setForeground(Color.RED);

		labelUsername=new Label("手机号：");
		labelUsername.setFont(buttonFont);
		labelPassword=new Label("密码：");
		labelPassword.setFont(buttonFont);
		textUsername=new JTextField();
		textUsername.setFont(buttonFont);
		textPassword=new JPasswordField();
		textPassword.setFont(buttonFont);
		labelType=new Label("登录类型：");
		labelType.setFont(buttonFont);
		boxType=new JComboBox<String>();
		boxType.addItem("用户");
		boxType.addItem("管理员");
		boxType.setSelectedIndex(0);
		boxType.setFont(buttonFont);

		//调整控件位置
		panelLogin.add(labelUsername);
		panelLogin.add(textUsername);
		panelLogin.add(labelPassword);
		panelLogin.add(textPassword);
		panelLogin.add(labelType);
		panelLogin.add(boxType);
		panelLogin.add(buttonLogin);
		panelLogin.add(buttonRegister);


		panelAppearence.add(labelWelcome);
		panelButtons.add(buttonUnsubscribe);
		panelButtons.add(buttonSearch);
		panelButtons.add(buttonChange);
		panelButtons.add(buttonBook);
		panelButtons.add(buttonInfo);
		panelButtons.add(buttonExit);

		panelButtons.setBounds(410,200,1000,800);
		panelLogin.setBounds(480,350,800,300);
		panelAppearence.setBounds(400,50,1000,100);

		add(panelButtons);
		panelButtons.setVisible(false);
		add(panelLogin);
		add(panelAppearence);


		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=textUsername.getText();
				String password=String.valueOf(textPassword.getPassword());
				if(boxType.getSelectedIndex()==0){
					if(database.check_user(username,password)){
						user=database.get_user(username);
						panelLogin.setVisible(false);
						panelButtons.setVisible(true);
						labelWelcome.setText(labelWelcome.getText()+","+user.name);
						buttonBook.setEnabled(true);
						buttonChange.setEnabled(true);
						buttonUnsubscribe.setEnabled(true);
					}else{
						JOptionPane.showMessageDialog(mainFrame,"手机号密码错误","警告",
								JOptionPane.WARNING_MESSAGE);
					}
				}else{
					if(database.check_admin(username,password)){
						admin=database.get_admin(username);
						panelLogin.setVisible(false);
						panelButtons.setVisible(true);
						labelWelcome.setText(labelWelcome.getText()+",管理员 "+admin.name);
						buttonBook.setEnabled(false);
						buttonChange.setEnabled(false);
						buttonUnsubscribe.setEnabled(false);
					}else{
						JOptionPane.showMessageDialog(mainFrame,"工号密码错误","警告",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		buttonRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=textUsername.getText();
				String password=String.valueOf(textPassword.getPassword());
				if(boxType.getSelectedIndex()==1){
					JOptionPane.showMessageDialog(mainFrame,"不允许在界面注册管理员用户！","警告",
							JOptionPane.WARNING_MESSAGE);
				}
				else if(username.length()!=11 || password.length()<5||password.length()>=20){
					JOptionPane.showMessageDialog(mainFrame,"请设置11位手机号和长度在6-20位之间的密码！","警告",
							JOptionPane.WARNING_MESSAGE);
				}
				else if(database.add_user(username,password)){
					panelLogin.setVisible(false);
					panelButtons.setVisible(true);
					JOptionPane.showMessageDialog(mainFrame,"用户注册成功","提示",
							JOptionPane.INFORMATION_MESSAGE);
					user=database.get_user(username);
					System.out.println(user.name);
					labelWelcome.setText(labelWelcome.getText()+","+user.name);
					panelAppearence.updateUI();
				}else{
					JOptionPane.showMessageDialog(mainFrame,"用户已存在","警告",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		buttonSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setVisible(false);
				boolean isUser=boxType.getSelectedIndex()==0;
				if(isUser)
					new FlightSearch(user,database,mainFrame).setVisible(true);
				else
					new FlightSearch(admin,database,mainFrame).setVisible(true);
			}
		});
		buttonBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new TicketBook(user,database,mainFrame).setVisible(true);
			}
		});
		buttonChange.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new TicketChange(user,database,mainFrame).setVisible(true);
				} catch (ParseException parseException) {
					parseException.printStackTrace();
				}
			}
		});
		buttonUnsubscribe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new TicketUnsubscribe(user,database,mainFrame).setVisible(true);
				} catch (ParseException parseException) {
					parseException.printStackTrace();
				}
			}
		});
		buttonInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isUser=boxType.getSelectedIndex()==0;
				if(isUser) {
					new InfoChange(user, database, mmainFrame).setVisible(true);
				}else{
					new InfoChange(admin, database,mmainFrame).setVisible(true);
				}
			}
		});
		buttonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				labelWelcome.setText("欢迎使用机票管理系统");
				user=null;admin=null;
				panelButtons.setVisible(false);
				panelLogin.setVisible(true);
				textPassword.setText("");
				textUsername.setText("");
			}
		});
		boxType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("comboBoxChanged")){
					if(boxType.getSelectedIndex()==0)
						labelUsername.setText("手机号:");
					else
						labelUsername.setText("工号:");
					panelButtons.updateUI();
				}

			}
		});


	}
	public static void main(String[] args) {
		//入口
		new Main("机票管理系统").setVisible(true);
	}
	public void reset_welcome(String username){
		user=database.get_user(username);
		System.out.println(user.name);
		labelWelcome.setText("欢迎使用机票管理系统"+","+user.name);
		panelAppearence.updateUI();
	}

}
