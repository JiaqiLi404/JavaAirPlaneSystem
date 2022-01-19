package AirPlaneSystem.Objects;

public class User {
    public User(String phone,String name,String type,double discount,double wallet){
        this.phone=phone;this.name=name;this.type=type;this.discount=discount;this.wallet=wallet;

    }
    public String phone,name,type;
    public double discount,wallet;
}
