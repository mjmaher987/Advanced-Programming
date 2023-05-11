import java.util.Random;

public class RandomShape2 implements Drawable {
    private double perimeter;
    private double surface;
    private int side;

    public RandomShape2(){
        Random rd = new Random();
        this.perimeter = rd.nextDouble()*100;
        this.surface = rd.nextDouble()*100;
        this.side = rd.nextInt()%10+1;
    }

    @Override
    public double getPerimeter() {
        return perimeter;
    }

    @Override
    public double getSurface() {
        return surface;
    }

    @Override
    public int getSide() {
        return side;
    }

}
