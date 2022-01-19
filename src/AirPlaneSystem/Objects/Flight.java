package AirPlaneSystem.Objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {

	public String number,startcity,endcity,plane;
	public Date starttime,endtime;
	public double price;
	public int sum_seats,free_seats;
	public Flight(String number,String startcity,String endcity,Date starttime,Date endtime,String plane,int sum_seats,int free_seats,double price){
		this.number=number;this.startcity=startcity;this.endcity=endcity;this.starttime=starttime;this.endtime=endtime;this.plane=plane;
		this.sum_seats=sum_seats;this.free_seats=free_seats;this.price=price;
	}
	public int date_year(Date date){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return Integer.parseInt(sdf.format(date));
	}
	public int date_month(Date date){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return Integer.parseInt(sdf.format(date));
	}
	public int date_day(Date date){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return Integer.parseInt(sdf.format(date));
	}
	public String date_time(Date date){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		return sdf.format(date);
	}
	public int date_hour(Date date){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		return Integer.parseInt(sdf.format(date));
	}
	public int date_minute(Date date){
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		return Integer.parseInt(sdf.format(date));
	}

}
