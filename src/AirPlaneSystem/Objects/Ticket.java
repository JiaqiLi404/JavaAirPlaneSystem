package AirPlaneSystem.Objects;

import java.util.Date;

public class Ticket {
    public int number;
    public String seat,user,flight;
    public Date starttime;
    public double price;
    public Ticket(int number,String seat,String user,String flight,Date starttime,double price){
        this.number=number;this.seat=seat;this.user=user;this.flight=flight;this.starttime=starttime;this.price=price;

    }
}
