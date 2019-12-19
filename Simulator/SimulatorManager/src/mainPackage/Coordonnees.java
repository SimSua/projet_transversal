package mainPackage;

public class Coordonnees {
    private double x_coord;
    private double y_coord;

    public Coordonnees(double x,double y) {
        this.x_coord = x;
        this.y_coord = y;
    }

    public double getX() {
        return this.x_coord;
    }

    public double getY() {
        return this.y_coord;
    }

    @Override
    public String toString() {
        return "Coordonnees{" +
                "x_coord=" + x_coord +
                ", y_coord=" + y_coord +
                '}';
    }
}
