package AirPlaneSystem;

import AirPlaneSystem.Database.MyConnection;
import AirPlaneSystem.Objects.Admin;
import AirPlaneSystem.Objects.Flight;
import AirPlaneSystem.Objects.Ticket;
import AirPlaneSystem.Objects.User;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

public class TicketChange extends JFrame{
    Label labelamin,labelFlight,labelStLoc,labelStTim,labelConLoc,labelSY,labelSM,labelSD;
    Label boxLabelFlight,boxLabelStLoc,boxLabelEnLoc,boxLabelStTime,boxLabelEnTime,boxLabelPrice,boxLabelPlane,boxLabelSeat;
    Label boxLabelTFlight,boxLabelTStLoc,boxLabelTEnLoc,boxLabelTStTime,boxLabelTEnTime,boxLabelTPrice,boxLabelTPlane,boxLabelTSeat,boxLabelTPhone;
    Button buttonOK,buttonQuerry,buttonReset,buttonExit;
    TextField textFlight;
    JList<String> listFlight,listTicket;
    JComboBox<Integer> boxStYear,boxStMon,boxStDay;
    JComboBox<String> boxStLoc,boxEnLoc;
    JFrame thisFrame,mainFrame;
    JPanel panelSearchTime,panelSearchLoc,panelSearchFlight,panelBoxLabel,panelButton,panelTBoxLabel;
    ArrayList<Flight> arrFlight = new ArrayList<Flight>();
    ArrayList<Ticket> arrticket = new ArrayList<Ticket>();
    boolean adminMode;
    MyConnection database;
    User user;
    Admin admin;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    Font buttonFont =new Font("黑体", Font.PLAIN, 20);
    Font labelFont =new Font("黑体", Font.PLAIN, 17);
    Font welcomeFont=new Font("黑体",Font.BOLD,30);

    void init_compenent() throws ParseException {
        //界面设置
        this.setTitle("机票预订界面");
        this.setSize(1920,900);
        this.setResizable(true);
        setLayout(null);
        thisFrame=this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainFrame.setVisible(true);
            }
        });
        //插入面板
        GridLayout glayout1=new GridLayout(1,7);
        glayout1.setHgap(10);
        glayout1.setVgap(10);
        panelSearchTime=new JPanel();
        panelSearchTime.setLayout(glayout1);

        GridLayout glayout2=new GridLayout(1,4);
        glayout2.setHgap(50);
        glayout2.setVgap(50);
        panelSearchLoc=new JPanel();
        panelSearchLoc.setLayout(glayout2);

        GridLayout glayout3=new GridLayout(1,2);
        glayout3.setHgap(50);
        glayout3.setVgap(50);
        panelSearchFlight=new JPanel();
        panelSearchFlight.setLayout(glayout3);

        GridLayout glayout4=new GridLayout(1,4);
        glayout4.setHgap(50);
        glayout4.setVgap(50);
        panelBoxLabel=new JPanel();
        panelBoxLabel.setLayout(glayout4);

        GridLayout glayout5=new GridLayout(1,3);
        glayout5.setHgap(50);
        glayout5.setVgap(50);
        panelButton=new JPanel();
        panelButton.setLayout(glayout5);

        GridLayout glayout6=new GridLayout(1,7);
        glayout6.setHgap(50);
        glayout6.setVgap(50);
        panelTBoxLabel=new JPanel();
        panelTBoxLabel.setLayout(glayout6);
        //插入控件
        labelFlight=get_label("航班号:");
        textFlight=new TextField();
        textFlight.setFont(buttonFont);
        panelSearchFlight.add(labelFlight);
        panelSearchFlight.add(textFlight);

        labelStLoc=get_label("城市:");
        boxStLoc=get_boxLoc();
        labelConLoc=get_label("到");
        boxEnLoc=get_boxLoc();
        panelSearchLoc.add(labelStLoc);
        panelSearchLoc.add(boxStLoc);
        panelSearchLoc.add(labelConLoc);
        panelSearchLoc.add(boxEnLoc);

        labelStTim=get_label("起飞时间:");
        labelSY=get_label("年");
        labelSM=get_label("月");
        labelSD=get_label("日");
        boxStYear=get_boxInt(2020,2022,2020);
        boxStMon=get_boxInt(1,12,1);
        boxStDay=get_boxInt(1,31,1);
        panelSearchTime.add(labelStTim);
        panelSearchTime.add(boxStYear);
        panelSearchTime.add(labelSY);
        panelSearchTime.add(boxStMon);
        panelSearchTime.add(labelSM);
        panelSearchTime.add(boxStDay);
        panelSearchTime.add(labelSD);

        boxLabelFlight=get_label("航班");
        boxLabelStLoc=get_label("起始地");
        boxLabelEnLoc=get_label("目的地");
        boxLabelStTime=get_label("起飞时间");
        boxLabelEnTime=get_label("到达时间");
        boxLabelPrice=get_label("价格");
        boxLabelPlane=get_label("机型");
        boxLabelSeat=get_label("座位数");
        panelBoxLabel.add(boxLabelFlight);
        panelBoxLabel.add(boxLabelStLoc);
        panelBoxLabel.add(boxLabelEnLoc);
        panelBoxLabel.add(boxLabelStTime);
        panelBoxLabel.add(boxLabelEnTime);
        panelBoxLabel.add(boxLabelPrice);
        panelBoxLabel.add(boxLabelPlane);
        panelBoxLabel.add(boxLabelSeat);

        buttonOK=get_button("改签");
        buttonOK.setEnabled(false);
        buttonQuerry=get_button("查找");
        buttonReset=get_button("重置");
        buttonExit=get_button("返回");
        panelButton.add(buttonOK);
        panelButton.add(buttonQuerry);
        panelButton.add(buttonReset);
        panelButton.add(buttonExit);

        boxLabelTFlight=get_label("航班号");
        boxLabelTSeat=get_label("座位号");
        boxLabelTPhone=get_label("手机号");
        boxLabelTStLoc=get_label("起始地");
        boxLabelTEnLoc=get_label("目的地");
        boxLabelTStTime=get_label("起飞时间");
        boxLabelTEnTime=get_label("到达时间");
        boxLabelTPrice=get_label("价格");
        boxLabelTPlane=get_label("机型");
        panelTBoxLabel.add(boxLabelTFlight);
        panelTBoxLabel.add(boxLabelTSeat);
        panelTBoxLabel.add(boxLabelTPhone);
        panelTBoxLabel.add(boxLabelTStLoc);
        panelTBoxLabel.add(boxLabelTEnLoc);
        panelTBoxLabel.add(boxLabelTStTime);
        panelTBoxLabel.add(boxLabelTEnTime);
        panelTBoxLabel.add(boxLabelTPrice);
        panelTBoxLabel.add(boxLabelTPlane);

        listFlight=new JList<String>();
        listFlight.setFont(labelFont);
        listTicket=new JList<String>();
        listTicket.setFont(labelFont);

        labelamin=new Label("您好 "+user.name+" ,您的余额为:"+String.valueOf(user.wallet));
        labelamin.setFont(welcomeFont);
        labelamin.setBounds(300,30,1050,30);
        add(labelamin);
        panelSearchFlight.setBounds(300,80,250,30);
        add(panelSearchFlight);
        panelSearchLoc.setBounds(300,130,550,30);
        add(panelSearchLoc);
        panelSearchTime.setBounds(300,180,1200,30);
        add(panelSearchTime);
        panelButton.setBounds(500,250,780,30);
        add(panelButton);
        panelBoxLabel.setBounds(300,330,1200,30);
        add(panelBoxLabel);
        listFlight.setBounds(300,370,1200,250);
        add(listFlight);
        panelTBoxLabel.setBounds(300,650,1200,30);
        add(panelTBoxLabel);
        listTicket.setBounds(300,690,1200,130);
        add(listTicket);

        search_ticket();

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!arrFlight.isEmpty()) {
                    change_ticket();
                    search_flight();
                    search_ticket();

                }
            }
        });
        buttonQuerry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_flight();
            }
        });
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listFlight.setListData(new String[]{});
                textFlight.setText("");
                boxStLoc.setSelectedIndex(0);
                boxStYear.setSelectedIndex(0);
                boxStMon.setSelectedIndex(0);
                boxStDay.setSelectedIndex(0);
                boxEnLoc.setSelectedIndex(0);
                    search_ticket();
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        listFlight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!arrFlight.isEmpty()&&!arrticket.isEmpty()&&!listTicket.isSelectionEmpty()&&e.getClickCount() >= 2){
                        change_ticket();
                }else if(!arrFlight.isEmpty()&&!arrticket.isEmpty()&&!listTicket.isSelectionEmpty()&&e.getClickCount() == 1){
                    buttonOK.setEnabled(true);
                }

            }
        });
        listTicket.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!arrFlight.isEmpty()&&!arrticket.isEmpty()&&!listFlight.isSelectionEmpty()&&e.getClickCount() >= 2){
                        change_ticket();
                }else if(!arrFlight.isEmpty()&&!arrticket.isEmpty()&&!listFlight.isSelectionEmpty()&&e.getClickCount() == 1){
                    buttonOK.setEnabled(true);
                }

            }
        });

    }
    public TicketChange(Admin admin, MyConnection database, JFrame mainFrame) throws ParseException {
        adminMode=true;
        this.mainFrame=mainFrame;
        this.database=database;
        this.admin=admin;
        init_compenent();

    }
    public TicketChange(User user, MyConnection database, JFrame mainFrame) throws ParseException {
        adminMode=false;
        this.mainFrame=mainFrame;
        this.database=database;
        this.user=user;
        init_compenent();
    }
    Label get_label(String text){
        Label l=new Label(text);
        l.setFont(labelFont);
        return l;
    }
    Button get_button(String text){
        Button b=new Button(text);
        b.setFont(buttonFont);
        return b;
    }
    JComboBox<String> get_boxLoc(){
        ArrayList<String> cities=database.get_Loc();
        JComboBox<String> box = new JComboBox<String>();
        for(String c:cities){
            box.addItem(c);
        }
        return box;
    }
    JComboBox<Integer> get_boxInt(int min,int max,int def){
        JComboBox<Integer> box = new JComboBox<Integer>();
        for(int i=min;i<=max;i++){
            box.addItem(i);
        }
        box.setSelectedIndex(def-min);
        return box;
    }
    void search_flight(){
        listFlight.setListData(new String[]{});
        listFlight.setListData(new String[]{"正在查询中。。。"});
        try {
            arrFlight=database.get_flight(textFlight.getText(),(String) boxStLoc.getSelectedItem(),
                    (String) boxEnLoc.getSelectedItem(),
                    (int) boxStYear.getSelectedItem() +"/"+
                            (int) boxStMon.getSelectedItem() +"/"+ (int) boxStDay.getSelectedItem());
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        Vector<String> vec=new Vector<String>();
        if(adminMode){
            vec.add("新增航班");
        }
        for(Flight f:arrFlight){
            vec.add(f.number+"              "+f.startcity+"             "+f.endcity+"            "+
                    sdf.format(f.starttime)+"  "+sdf.format(f.endtime)+ "   "+ f.price
                    +"              "+ f.plane+"             "+ f.free_seats +"/"+ f.sum_seats);
        }
        if(vec.isEmpty()) listFlight.setListData(new String[]{"没有符合条件的航班"});
        else listFlight.setListData(vec);
    }
    void change_ticket()  {
        Flight f=arrFlight.get(listFlight.getSelectedIndex());
        Ticket t=arrticket.get(listTicket.getSelectedIndex());
        if(f.free_seats==0||f.price>user.wallet+t.price){
            JOptionPane.showMessageDialog(thisFrame,"余额不足或该航班没有空余座位","提示", JOptionPane.ERROR_MESSAGE);
        }else if(JOptionPane.showConfirmDialog(thisFrame,"确定换购该班次吗?","提示", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            //Flight tempf=database.get_flight(t.flight,t.starttime);
            database.unsubscribe_ticket(t);
            database.buy_ticket(user,f);
            user.wallet+=t.price-f.price;
            database.change_user(user,user.phone,user.name,user.type,user.discount,user.wallet);
            JOptionPane.showMessageDialog(thisFrame,"机票改签成功！","提示", JOptionPane.INFORMATION_MESSAGE);
            search_flight();
            search_ticket();
            labelamin.setText("您好 "+user.name+" ,您的余额为:"+ user.wallet);
            buttonOK.setEnabled(false);
        }
    }
    void search_ticket()  {
        listTicket.setListData(new String[]{});
        listTicket.setListData(new String[]{"正在查询中。。。"});
        arrticket=database.get_ticket(user.phone);
        Vector<String> vec=new Vector<String>();
        for(Ticket t:arrticket){
            Flight f=database.get_flight(t.flight,t.starttime);
            vec.add(t.flight+"            "+t.seat+"             "+t.user+"    "+f.startcity+"           "+f.endcity+"       "+
                    sdf.format(f.starttime)+"  "+sdf.format(f.endtime)+ "  "+ t.price +"           "+ f.plane);
        }
        if(vec.isEmpty()) listTicket.setListData(new String[]{"您没有订购机票"});
        else listTicket.setListData(vec);
    }

}
