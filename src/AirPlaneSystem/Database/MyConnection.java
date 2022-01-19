package AirPlaneSystem.Database;

import AirPlaneSystem.Objects.*;
import AirPlaneSystem.SQLSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyConnection {
    public MyConnection() {
    }

    public boolean check_user(String phone, String password) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
             sql= SQLSet.get_user(phone);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    System.out.println("用户登录成功" + phone);
                    BaseConnection.closeRes(rs, ps, conn);
                    return true;
                }
                System.out.println("用户密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户登录出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return false;
    }

    public boolean check_admin(String username, String password) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
             sql= SQLSet.get_admin(username);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("jpassword").equals(password)) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户登录出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return false;
    }

    public boolean add_user(String phone, String name,double discount,String type,double wallet,String password) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        boolean judge = false;
        String sql = SQLSet.add_user(phone, name,discount,type,wallet,password);

        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("用户增加成功");
            } else {
                System.out.println("用户增加失败，手机号已注册");
                BaseConnection.closeRes( ps, conn);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(ps, conn);
        }
        return false;
    }
    public boolean add_user(String phone, String password) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        boolean judge = false;
        String sql = SQLSet.add_user(phone, password);

        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("用户增加成功");
            } else {
                System.out.println("用户增加失败，手机号已注册");
                BaseConnection.closeRes( ps, conn);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(ps, conn);
        }
        return false;
    }

    public User get_user(String phone) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_user(phone);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next())
                System.out.println("用户查找出错");
            else {
                User u=new User(rs.getString("telephone"), rs.getString("name"),
                        rs.getString("type1"), rs.getFloat("discount"), rs.getFloat("wallet"));
                BaseConnection.closeRes(rs, ps, conn);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return null;
    }

    public Admin get_admin(String number) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_admin(number);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next())
                System.out.println("管理员查找出错");
            else {
                Admin admin=new Admin(rs.getString("jnum"), rs.getString("jname"));
                BaseConnection.closeRes(rs, ps, conn);
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("管理员查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return null;
    }

    public ArrayList<String> get_Loc() {
        ArrayList<String> array = new ArrayList<String>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_cities();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(rs.getString("city"));
            }
            return array;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("城市查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return null;
    }

    public ArrayList<Flight> get_flight(String flightnum, String stCity, String enCity, String stdate, String endate) throws ParseException {
        SimpleDateFormat sdf = get_sdf();
        ArrayList<Flight> flightlist = new ArrayList<Flight>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            
            if (!flightnum.equals("") && stCity.equals("全部") && enCity.equals("全部"))
                sql = SQLSet.select_flight_n(flightnum, stdate, endate);
            else if (!flightnum.equals("") && stCity.equals("全部"))
                sql = SQLSet.select_flight_n_ec(flightnum, enCity, stdate, endate);
            else if (!flightnum.equals("") && enCity.equals("全部"))
                sql = SQLSet.select_flight_n_sc(flightnum, stCity, stdate, endate);
            else if (!flightnum.equals(""))
                sql = SQLSet.select_flight_n_sc_ec(flightnum, stCity, enCity, stdate, endate);
            else if (stCity.equals("全部") && enCity.equals("全部")) sql = SQLSet.select_flight_a(stdate, endate);
            else if (stCity.equals("全部")) sql = SQLSet.select_flight_ec(enCity, stdate, endate);
            else if (enCity.equals("全部")) sql = SQLSet.select_flight_sc(stCity, stdate, endate);
            else sql = SQLSet.select_flight_sc_ec(stCity, enCity, stdate, endate);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Plane p = get_Plane(rs.getString("anum"));
                flightlist.add(new Flight(rs.getString("fnum"), rs.getString("departure"),
                        rs.getString("destination"), rs.getTimestamp("stime"), rs.getTimestamp("etime"),
                        p.number, p.seats, p.seats - get_owned_seat(rs.getString("fnum"), rs.getTimestamp("stime")),rs.getFloat("price")));
                System.out.println("航班查找成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("航班查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return flightlist;
    }

    public ArrayList<Plane> get_Planes() {
        ArrayList<Plane> array = new ArrayList<Plane>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_planes();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(new Plane(rs.getString("anum"),rs.getInt("number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("飞机查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return array;
    }

    public Plane get_Plane(String name) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_plane(name);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next())
                System.out.println("飞机查找出错");
            else {
                Plane p=new Plane(rs.getString("anum"), rs.getInt("number"));
                BaseConnection.closeRes(rs, ps, conn);
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("飞机查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return null;
    }

    public int get_owned_seat(String flightname, Date startdate) {
        SimpleDateFormat sdf = get_sdf();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num=0;
        String sql = null;
        try {
             sql= SQLSet.get_passenger(flightname,sdf.format(startdate));
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                num++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("乘客查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return num;
    }

    public boolean delete_flight(String number, Date starttime) {
        SimpleDateFormat sdf = get_sdf();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        boolean judge = false;
        String sql = SQLSet.delete_flight(number, sdf.format(starttime));
        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("航班删除成功");
            } else {
                System.out.println("航班删除失败");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            BaseConnection.closeRes(ps, conn);
        }
        return true;
    }

    public boolean add_flight(Flight flight) {
        SimpleDateFormat sdf = get_sdf();

        System.out.println(flight.starttime+" "+sdf.format(flight.starttime));

        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        String sql = SQLSet.add_flight(flight.number,flight.startcity,flight.endcity,sdf.format(flight.starttime),sdf.format(flight.endtime),flight.plane,flight.price);
        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("航班新增/修改成功");
            } else {
                System.out.println("航班新增失败");
                BaseConnection.closeRes(ps, conn);
                return false;
            }
        } catch (Exception e) {
            System.out.println("航班新增失败" + sql);
            e.printStackTrace();
            BaseConnection.closeRes(ps, conn);
            return false;
        }
        BaseConnection.closeRes(ps, conn);
        return true;
    }

    public int get_plane_seats(String plane) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_plane(plane);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next())
                System.out.println("飞机查找出错");
            else {
                int i=rs.getInt("number");
                BaseConnection.closeRes(rs, ps, conn);
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("飞机查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return 0;
    }

    public ArrayList<Flight> get_flight(String flightnum, String stCity, String enCity, String stdate) throws ParseException {
        SimpleDateFormat sdf = get_sdf();
        ArrayList<Flight> flightlist = new ArrayList<Flight>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            if(flightnum.equals(""))
                sql = SQLSet.select_flight_sc_ec(stCity, enCity, stdate+" 00:00",stdate+" 23:59");
            else
                sql=SQLSet.select_flight_n_sc_ec(flightnum,stCity,enCity,stdate+" 00:00",stdate+" 23:59");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Plane p = get_Plane(rs.getString("anum"));
                flightlist.add(new Flight(rs.getString("fnum"), rs.getString("departure"),
                        rs.getString("destination"), rs.getTimestamp("stime"), rs.getTimestamp("etime"),
                        p.number, p.seats, p.seats - get_owned_seat(rs.getString("fnum"), rs.getTimestamp("stime")),rs.getFloat("price")));
                System.out.println("航班查找成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("航班查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return flightlist;
    }

    public boolean buy_ticket(User user, Flight flight) {
        SimpleDateFormat sdf = get_sdf();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        String sql = SQLSet.add_ticket(get_seat(get_owned_seat(flight.number,flight.starttime),6),
                flight.price,user.phone,flight.number,sdf.format(flight.starttime));
        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("机票新增成功");
            } else {
                System.out.println("机票新增失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("航班新增失败"+sql);
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(ps, conn);
        }
        return true;
    }
    String get_seat(int num,int cols){
        char[] alpha= new char[]{'A','B','C','D','E','F','G','H','I','J','K','L'};
        int i=num/cols,j=(num%cols)+1;
        return alpha[i] +String.valueOf(j);
    }

    public ArrayList<Ticket> get_ticket(String phone)  {
        ArrayList<Ticket> ticketlist = new ArrayList<Ticket>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_ticket(phone);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ticketlist.add(new Ticket(rs.getInt("tnum"), rs.getString("snum"),
                        rs.getString("telephone"), rs.getString("fnum"),
                        rs.getTimestamp("stime"),rs.getFloat("price")));
            }
            System.out.println("机票查找成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("机票查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return ticketlist;
    }

    public Flight get_flight(String flight, Date starttime) {
        SimpleDateFormat sdf = get_sdf();
        Flight f=null;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql=SQLSet.select_flight_n_t(flight,sdf.format(starttime));
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Plane p = get_Plane(rs.getString("anum"));
                f=new Flight(rs.getString("fnum"), rs.getString("departure"),
                        rs.getString("destination"), rs.getTimestamp("stime"), rs.getTimestamp("etime"),
                        p.number, p.seats, p.seats - get_owned_seat(rs.getString("fnum"), rs.getTimestamp("stime")),rs.getFloat("price"));
                System.out.println("航班查找成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("航班查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return f;
    }

    public boolean unsubscribe_ticket(Ticket t) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        String sql = SQLSet.delete_ticket(t.number);
        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("机票删除成功");
            } else {
                System.out.println("机票删除失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("机票删除失败"+sql);
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(ps, conn);
        }
        return true;
    }

    public boolean change_user(User user, String phone, String name, String type, double discount, double wallet) {
        String sql;
        if(user.phone.equals(phone)){
            sql= SQLSet.alter_user(phone,name,discount,type,wallet);
        }else{
            sql= SQLSet.add_user(phone,name,discount,type,wallet,get_password(user.phone));
        }
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("个人信息修改成功");
                if(!user.phone.equals(phone)){
                    delete_user(user.phone);
                }
            } else {
                System.out.println("个人信息修改失败");
                BaseConnection.closeRes(ps, conn);
                return false;
            }
        } catch (Exception e) {
            System.out.println("个人信息修改失败"+sql);
            e.printStackTrace();
            BaseConnection.closeRes(ps, conn);
            return false;
        }
        BaseConnection.closeRes(ps, conn);
        return true;
    }

    void delete_user(String phone) {
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        String sql = SQLSet.delete_user(phone);
        try {
            ps = conn.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("机票删除成功");
            } else {
                System.out.println("机票删除失败");
            }
        } catch (Exception e) {
            System.out.println("机票删除失败"+sql);
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(ps, conn);
        }
    }

    String get_password(String phone){
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;String sql = null;
        try {
            sql = SQLSet.get_user(phone);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next())
                System.out.println("用户查找出错");
            else {
                String s=rs.getString("password");
                BaseConnection.closeRes(rs, ps, conn);
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户查找出错"+sql);
        } finally {
            BaseConnection.closeRes(rs, ps, conn);
        }
        return null;
    }
    SimpleDateFormat get_sdf(){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm");
    }
}
