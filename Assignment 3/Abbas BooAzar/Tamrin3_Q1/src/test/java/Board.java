import java.util.ArrayList;
import java.util.Collections;

public class Board<T extends Drawable> {
    private ArrayList<T> allShapes = new ArrayList<>();

    public void addNewShape(T shape) {
        allShapes.add(shape);
    }

    public double allPerimeter() {
        double answer = 0;
        for (T allShape : allShapes) {
            answer += allShape.getPerimeter();
        }
        return answer;
    }

    public double allSurface() {
        double answer = 0;
        for (T allShape : allShapes) {
            answer += allShape.getSurface();
        }
        return answer;
    }

    public double allSide() {
        int answer = 0;
        for (T allShape : allShapes) {
            try {
                answer += allShape.getSide();
            } catch (SideNotDefinedException e) {

            }

        }
        return answer;
    }

    public double allSideException() throws SideNotDefinedException {
        int answer = 0;
        for (T allShape : allShapes) {
            answer += allShape.getSide();
        }
        return answer;
    }

    public T minimumSurface() {
        if (allShapes.size() == 0) {
            return null;
        }
        else {
            double minimumSurface = 0;
            T answer = null;
            for (int i = 0; i < allShapes.size(); i++) {
                if (i == 0) {
                    minimumSurface = allShapes.get(i).getSurface();
                    answer = allShapes.get(i);
                }
                else {
                    if (allShapes.get(i).getSurface() < minimumSurface) {
                        answer = allShapes.get(i);
                        minimumSurface = allShapes.get(i).getSurface();
                    }
                }
            }
            return answer;
        }
    }

    public ArrayList<T> sortedList(double x) {
        ArrayList<T> answer = new ArrayList<>();
        for (T allShape : allShapes) {
            if (allShape.getPerimeter() > x) {
                answer.add(allShape);
            }
        }
        for (int i = 0; i < answer.size(); i++) {
            for (int j = i + 1; j < answer.size(); j++) {
                if (answer.get(i).getSurface() > answer.get(j).getSurface()) {
                    Collections.swap(answer, i, j);
                }
            }
        }
        return answer;
    }

}

