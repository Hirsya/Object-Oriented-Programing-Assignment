public class Lingkaran extends Bentuk{
    private double radius;

    public Lingkaran (double radius, String warna){
        super(warna);
        this.radius = radius;
    }

    public double getRadius(){return radius;}
    public void setRadius(double r){radius = r;}
    public double hitungLuas(){
        return Math.PI * (getRadius() * getRadius());
    }

    @Override 
    public void printInfo(){
        System.out.println("Lingkaran " + getWarna() + ", Luas = " + hitungLuas());
    }
}
