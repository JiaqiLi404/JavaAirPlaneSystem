package AirPlaneSystem;

import java.util.Date;

public class SQLSet {
    public static String get_admin(String number){
        return "select * from administrator where jnum="+number;
    }
    public static String get_user(String phone){
        return "select * from customer where telephone="+phone;
    }
    public static String get_cities(){
        return "select * from city";
    }
    public static String add_user(String phone,String password){
        return "insert into customer "+"values('"+phone+"','"+
                "新用户"+"',1.0,"+"'普通用户'"+",0.0,'"+password+"')";
    }
    public static String add_user(String phone, String name,double discount,String type,double wallet,String password){
        return "insert into customer "+"values('"+phone+"','"+
                name+"',"+discount+",'"+type+"',"+wallet+",'"+password+"')";
    }

    public static String select_flight_n(String flightnum, String stdate,String endate) {
        return "select * from flight where fnum LIKE '%"+flightnum+"%' and stime>='"+stdate+"' and stime<='"+endate+"'";
    }

    public static String select_flight_n_ec(String flightnum, String enCity, String stdate, String endate) {
        return "select * from flight where fnum LIKE '%"+flightnum+"%' and stime>='"+stdate+"' and stime<='"+endate+"'"+" and destination='"+enCity+"'";
    }

    public static String select_flight_n_sc(String flightnum, String stCity, String stdate, String endate) {
        return "select * from flight where fnum LIKE '%"+flightnum+"%' stime>='"+stdate+"' and stime<='"+endate+"'"+" and departure='"+stCity+"'";
    }

    public static String select_flight_a(String stdate, String endate) {
        return "select * from flight where stime>='"+stdate+"' and stime<='"+endate+"'";
    }

    public static String select_flight_ec( String enCity, String stdate, String endate) {
        return "select * from flight where stime>='"+stdate+"' and stime<='"+endate+"'"+" and destination='"+enCity+"'";
    }

    public static String select_flight_sc(String stCity, String stdate, String endate) {
        return "select * from flight where stime>='"+stdate+"' and stime<='"+endate+"'"+" and departure='"+stCity+"'";
    }

    public static String select_flight_n_sc_ec(String flightnum, String stCity, String enCity, String stdate, String endate) {
        return "select * from flight where fnum LIKE '%"+flightnum+"%' and stime>='"+stdate+"' and stime<='"+endate+"'"+" and departure='"+stCity+"' and destination='"+enCity+"'";
    }

    public static String select_flight_sc_ec(String stCity, String enCity, String stdate, String endate) {
        return "select * from flight where stime>='"+stdate+"' and stime<='"+endate+"'"+" and departure='"+stCity+"' and destination='"+enCity+"'";
    }

    public static String get_planes() {
        return "select * from airplane";
    }

    public static String get_plane(String name) {
        return "select * from airplane where anum="+name;
    }

    public static String get_passenger(String flightname, String startdate) {
        return "select * from ticket where fnum='"+flightname+"' and stime='"+startdate+"'";
    }

    public static String delete_flight(String number, String starttime) {
        return "delete from flight where fnum='"+number+"' and stime='"+starttime+"'";
    }

    public static String add_flight(String number, String startcity, String endcity, String stime, String etime, String plane, double price) {
        return "insert into flight "+"values('"+number+"','"+
                startcity+"','"+endcity+"','"+stime+"','"+etime+"','"+plane+"',"+ price +")";
    }

    public static String select_flight_n_t(String flight, String starttime) {
        return "select * from flight where fnum='"+flight+"' and stime='"+starttime+"'";
    }

    public static String add_ticket(String seat, double price, String phone, String number, String time) {
        return "insert into ticket(snum,price,telephone,fnum,stime) "+"values('"+seat+"',"+
                price+",'"+phone+"','"+number+"','"+time+"')";
    }

    public static String get_ticket(String phone) {
        return "select * from ticket where telephone='"+phone+"'";
    }

    public static String delete_ticket(int number) {
        return "delete from ticket where tnum='"+number+"'";
    }

    public static String delete_user(String phone) {
        return "delete from customer where telephone='"+phone+"'";
    }

    public static String alter_user(String phone, String name, double discount, String type, double wallet) {
        return "update customer set name='"+name+"',discount="+discount+",type1='"+type+"',wallet="+wallet+" " +
                "where telephone='"+phone+"'";
    }
}
