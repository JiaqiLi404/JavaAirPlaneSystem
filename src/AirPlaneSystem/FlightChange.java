package AirPlaneSystem;

import AirPlaneSystem.Database.MyConnection;
import AirPlaneSystem.Objects.Admin;
import AirPlaneSystem.Objects.Flight;
import AirPlaneSystem.Objects.Plane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FlightChange extends JFrame {
    Label mainlabel,labelStLoc,labelConLoc,labelStTim,labelEnTim,labelSY,labelSM,labelSD,labelSH,labelSMIN,labelEY,labelEM,labelED,
            labelEH,labelEMIN,labelPlain,labelSeats,labelPrice;
    Button buttonDel,buttonOK,buttonReset,buttonExit;
    JComboBox<Integer> boxStYear,boxStMon,boxStDay,boxStHour,boxStMin,boxEnHour,boxEnMin,boxEnYear,boxEnMon,boxEnDay;
    JComboBox<String> boxStLoc,boxEnLoc,boxPlane;
    JTextField textSeats,textPrice,textFlight;
    JFrame thisFrame;
    FlightSearch mainFrame;
    JPanel panelTime,panelLoc,panelPlane,panelButton;
    MyConnection database;
    Admin admin;
    Flight flight;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    Font buttonFont =new Font("黑体", Font.PLAIN, 20);
    Font labelFont =new Font("黑体", Font.PLAIN, 17);
    Font welcomeFont=new Font("黑体",Font.BOLD,20);

    public FlightChange(FlightSearch mainFrame,MyConnection database, Admin admin, Flight flight){
        this.mainFrame=mainFrame;
        this.admin=admin;
        this.database=database;
        this.flight=flight;
        init_compenent();
        reset_compenent();
    }
    public FlightChange(FlightSearch mainFrame,MyConnection database, Admin admin){
        this.mainFrame=mainFrame;
        this.admin=admin;
        this.database=database;
        this.flight=null;
        init_compenent();
        reset_compenent();
    }

    void init_compenent() {
        //界面设置
        this.setTitle("航班界面");
        this.setSize(1920, 900);
        this.setResizable(true);
        setLayout(null);
        thisFrame = this;

        //插入面板
        GridLayout glayout1 = new GridLayout(2, 11);
        glayout1.setHgap(10);
        glayout1.setVgap(10);
        panelTime = new JPanel();
        panelTime.setLayout(glayout1);

        GridLayout glayout2 = new GridLayout(1, 4);
        glayout2.setHgap(50);
        glayout2.setVgap(50);
        panelLoc = new JPanel();
        panelLoc.setLayout(glayout2);

        GridLayout glayout3=new GridLayout(1,4);
        glayout3.setHgap(50);
        glayout3.setVgap(50);
        panelButton=new JPanel();
        panelButton.setLayout(glayout3);

        GridLayout glayout4=new GridLayout(1,6);
        glayout4.setHgap(50);
        glayout4.setVgap(50);
        panelPlane=new JPanel();
        panelPlane.setLayout(glayout4);

        labelStLoc = get_label("城市:");
        boxStLoc = get_boxLoc();
        labelConLoc = get_label("到");
        boxEnLoc = get_boxLoc();
        panelLoc.add(labelStLoc);
        panelLoc.add(boxStLoc);
        panelLoc.add(labelConLoc);
        panelLoc.add(boxEnLoc);

        labelStTim = get_label("起飞:");
        labelSY = get_label("年");
        labelSM = get_label("月");
        labelSD = get_label("日");
        labelSH = get_label("时");
        labelSMIN= get_label("分");
        labelEnTim = get_label("到达:");
        labelEY = get_label("年");
        labelEM = get_label("月");
        labelED = get_label("日");
        labelEH = get_label("时");
        labelEMIN= get_label("分");
        boxStYear = get_boxInt(2020, 2022, 2020);
        boxStMon = get_boxInt(1, 12,1);
        boxStDay = get_boxInt(1, 31,1);
        boxStHour=get_boxInt(0,23,0);
        boxStMin=get_boxInt(0,59,0);
        boxEnYear = get_boxInt(2020, 2022, 2020);
        boxEnMon = get_boxInt(1, 12, 1);
        boxEnDay = get_boxInt(1, 31, 1);
        boxEnHour=get_boxInt(0,23,0);
        boxEnMin=get_boxInt(0,59,0);
        panelTime.add(labelStTim);
        panelTime.add(boxStYear);
        panelTime.add(labelSY);
        panelTime.add(boxStMon);
        panelTime.add(labelSM);
        panelTime.add(boxStDay);
        panelTime.add(labelSD);
        panelTime.add(boxStHour);
        panelTime.add(labelSH);
        panelTime.add(boxStMin);
        panelTime.add(labelSMIN);
        panelTime.add(labelEnTim);
        panelTime.add(boxEnYear);
        panelTime.add(labelEY);
        panelTime.add(boxEnMon);
        panelTime.add(labelEM);
        panelTime.add(boxEnDay);
        panelTime.add(labelED);
        panelTime.add(boxEnHour);
        panelTime.add(labelEH);
        panelTime.add(boxEnMin);
        panelTime.add(labelEMIN);

        labelPlain =get_label("机型:");
        boxPlane=get_boxPlane();
        labelSeats=get_label("最大座位数:");
        textSeats=get_text();
        labelPrice=get_label("价格:");
        textPrice=get_text();
        panelPlane.add(labelPlain);
        panelPlane.add(boxPlane);
        panelPlane.add(labelSeats);panelPlane.add(textSeats);
        panelPlane.add(labelPrice);panelPlane.add(textPrice);


        buttonDel=get_button("删除");
        if(flight==null){
            buttonOK = get_button("新增");
            buttonDel.setEnabled(false);
            labelSeats.setVisible(false);
            textSeats.setVisible(false);
        }
        else buttonOK = get_button("修改");
        buttonReset = get_button("重置");
        buttonExit = get_button("返回");
        panelButton.add(buttonDel);
        panelButton.add(buttonOK);
        panelButton.add(buttonReset);
        panelButton.add(buttonExit);

        if(flight!=null){
            mainlabel=new Label("管理员"+admin.name+",您正在修改航班 "+flight.number+" ,从 "+sdf.format(flight.starttime)+
                    flight.startcity+" 到 "+sdf.format(flight.endtime)+flight.endcity+" ,\n机型 "+flight.plane+" ,最大座位"+
                    flight.sum_seats+",机票价格 "+flight.price);
        }else {
            mainlabel = new Label("管理员" + admin.name + ",您正在新增航班");
            textFlight=new JTextField();
            textFlight.setFont(labelFont);
            textFlight.setBounds(440,60,100,30);
            add(textFlight);
        }
        mainlabel.setFont(welcomeFont);
        mainlabel.setForeground(Color.BLUE);
        mainlabel.setBounds(150,50,1200,50);
        add(mainlabel);
        panelLoc.setBounds(300, 130, 550, 30);
        add(panelLoc);
        panelTime.setBounds(300, 180, 1200, 100);
        add(panelTime);
        panelPlane.setBounds(300,300,1200,30);
        add(panelPlane);
        panelButton.setBounds(500, 380, 780, 30);
        add(panelButton);


        buttonDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(thisFrame,"确定删除该航班吗","警告", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
                    if(database.delete_flight(flight.number,flight.starttime)){
                        JOptionPane.showMessageDialog(thisFrame,"航班删除成功","提示", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(thisFrame,"航班删除失败，该航班仍存在旅客","提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    thisFrame.dispose();
                    mainFrame.query();
                }
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flight==null){
                    if(textFlight.getText().length()<2||textPrice.getText().length()==0){
                        JOptionPane.showMessageDialog(thisFrame,"航班信息不完全","提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(JOptionPane.showConfirmDialog(thisFrame,"确定新增该航班吗","提示", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
                        Date sttime = null,entime = null;
                        try {
                            sttime=sdf.parse(String.valueOf(boxStYear.getSelectedItem())+"/"+String.valueOf(boxStMon.getSelectedItem())+"/"
                                    +String.valueOf(boxStDay.getSelectedItem())+" "+String.valueOf(boxStHour.getSelectedItem())+
                                    ":"+String.valueOf(boxStMin.getSelectedItem()));
                            entime=sdf.parse(String.valueOf(boxEnYear.getSelectedItem())+"/"+String.valueOf(boxEnMon.getSelectedItem())+"/"
                                    +String.valueOf(boxEnDay.getSelectedItem())+" "+String.valueOf(boxEnHour.getSelectedItem())+
                                    ":"+String.valueOf(boxEnMin.getSelectedItem()));
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                            JOptionPane.showMessageDialog(thisFrame,"航班已经存在","提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                        flight=new Flight(textFlight.getText(),(String) boxStLoc.getSelectedItem(),(String) boxEnLoc.getSelectedItem(),
                                sttime,entime,(String) boxPlane.getSelectedItem(),database.get_plane_seats((String) boxPlane.getSelectedItem()),
                                database.get_plane_seats((String) boxPlane.getSelectedItem()),Double.parseDouble(textPrice.getText()));
                        if(database.add_flight(flight)){
                            JOptionPane.showMessageDialog(thisFrame,"航班新增成功","提示", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(thisFrame,"航班已经存在","警告", JOptionPane.WARNING_MESSAGE);
                        }
                        thisFrame.dispose();
                        mainFrame.query();
                    }
                }else{
                    if(!database.delete_flight(flight.number,flight.starttime)){
                        JOptionPane.showMessageDialog(thisFrame,"航班存在乘客，无法修改信息","警告", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Date sttime = null,entime = null;
                    try {
                        sttime=sdf.parse(boxStYear.getSelectedItem() +"/"+ boxStMon.getSelectedItem() +"/"
                                + boxStDay.getSelectedItem() +" "+ boxStHour.getSelectedItem() +
                                ":"+ boxStMin.getSelectedItem());
                        entime=sdf.parse(String.valueOf(boxEnYear.getSelectedItem())+"/"+String.valueOf(boxEnMon.getSelectedItem())+"/"
                                + boxEnDay.getSelectedItem() +" "+String.valueOf(boxEnHour.getSelectedItem())+
                                ":"+String.valueOf(boxEnMin.getSelectedItem()));
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }


                    flight=new Flight(flight.number,(String) boxStLoc.getSelectedItem(),(String) boxEnLoc.getSelectedItem(),
                            sttime,entime,(String) boxPlane.getSelectedItem(),database.get_plane_seats((String) boxPlane.getSelectedItem()),
                            database.get_plane_seats((String) boxPlane.getSelectedItem()),Double.parseDouble(textPrice.getText()));
                    if(database.add_flight(flight)){
                        JOptionPane.showMessageDialog(thisFrame,"航班修改成功","提示", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(thisFrame,"航班已经存在或航班存在乘客，无法修改信息","警告", JOptionPane.WARNING_MESSAGE);
                    }
                    thisFrame.dispose();
                    mainFrame.query();
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
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset_compenent();
            }
        });
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
    JTextField get_text(){
        JTextField b=new JTextField();
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
    JComboBox<String> get_boxPlane(){
        ArrayList<Plane> planes=database.get_Planes();
        JComboBox<String> box = new JComboBox<String>();
        for(Plane p:planes){
            box.addItem(p.number);
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
        void reset_compenent(){
            if(flight!=null){
                mainlabel=new Label("管理员"+admin.name+",您正在修改航班 "+flight.number+" ,从 "+sdf.format(flight.starttime)+
                        flight.startcity+" 到 "+sdf.format(flight.endtime)+flight.endcity+" ,\n机型 "+flight.plane+" ,最大座位"+
                        flight.sum_seats+",机票价格 "+flight.price);
                boxStLoc.setSelectedItem(flight.startcity);
                boxEnLoc.setSelectedItem(flight.endcity);
                boxStYear.setSelectedItem(flight.date_year(flight.starttime));
                boxStMon.setSelectedItem(flight.date_month(flight.starttime));
                boxStDay.setSelectedItem(flight.date_day(flight.starttime));
                boxStHour.setSelectedItem(flight.date_hour(flight.starttime));
                boxStMin.setSelectedItem(flight.date_minute(flight.starttime));
                boxEnYear.setSelectedItem(flight.date_year(flight.endtime));
                boxEnMon.setSelectedItem(flight.date_month(flight.endtime));
                boxEnDay.setSelectedItem(flight.date_day(flight.endtime));
                boxEnHour.setSelectedItem(flight.date_hour(flight.endtime));
                boxEnMin.setSelectedItem(flight.date_minute(flight.endtime));
                boxPlane.setSelectedItem(flight.plane);
                textSeats.setText(String.valueOf(flight.sum_seats));
                textPrice.setText(String.valueOf(flight.price));
            }else {
                mainlabel = new Label("管理员" + admin.name + ",您正在新增航班");
            }

        }


    }
