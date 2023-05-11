
public class Circle implements Drawable {
    private double r;

    public Circle(double x){
        r = x;
    }

    public double getR() {
        return r;
    }

    @Override
    public double getPerimeter() {
        return 2*3.1415*r;
    }

    @Override
    public double getSurface() {
        return 3.1415*r*r;
    }

    @Override
    public int getSide() throws SideNotDefinedException {
        throw new SideNotDefinedException();
    }
}