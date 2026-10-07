public class BujurSangkar extends Bentuk{

    private double sisi;

    public BujurSangkar(double sisi, String warna){
        super(warna);
        this.sisi = sisi;
    }

    public double getSisi(){return sisi;}

    public void setSisi(double sisi){this.sisi = sisi;}

    public double hitungLuas(){
        return getSisi() * getSisi();
    }

    @Override 
    public void printInfo(){
        System.out.println("Bujursangkar berwarna " + getWarna() + ", luas = " + hitungLuas());
    }
}
