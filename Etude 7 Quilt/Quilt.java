import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Quilt extends JFrame {

    public static Scanner input = new Scanner(System.in);

    public static ArrayList<Double> scales = new ArrayList<Double>();
    public static ArrayList<Color> colors = new ArrayList<Color>();
    public static Double resultScale = 0.0;
    public static int windowSize = 500;
    public static int startScales = 0;

    public static class Square {
        int x;
        int y;
        int size;
        Color color;

        public Square(int x, int y, int size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }
    }

    public static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return this.x + ", " + this.y;
        }

    }

    public static void main(String[] args) {

        // colors.add(new Color(255,0,0));
        // colors.add(new Color(0,255,0));
        // colors.add(new Color(0,0,255));
        // scales.add(1.0);
        // scales.add(0.8);
        // scales.add(0.1)

        while (input.hasNextLine()) {
            String stringIn = input.nextLine();
            // System.out.println(stringIn);
            if (!stringIn.startsWith("#") && !stringIn.isBlank()) {
                String[] inputCleaned = stringIn.split(" ");
                scales.add(Double.parseDouble((inputCleaned[0])));
                colors.add(new Color(Integer.parseInt((inputCleaned[1])), Integer.parseInt((inputCleaned[2])),
                        Integer.parseInt((inputCleaned[3]))));
                resultScale = resultScale += Double.parseDouble(inputCleaned[0]);
            }
        }
        // System.out.println("Scale size: " + scales.size());
        Double result = windowSize * (scales.get(0) / resultScale);
        startScales = result.intValue();
        Quilt frame = new Quilt();
        frame.setVisible(true);
        frame.setSize(windowSize + 100, windowSize + 100);

    }

    public void paint(final Graphics g) {

        ArrayList<Coordinate> corners = new ArrayList<Coordinate>();
        ArrayList<Coordinate> tempCorners = new ArrayList<Coordinate>();
        ArrayList<Square> squares = new ArrayList<Square>();
        Double scale = scales.get(0) * windowSize / resultScale;
        int scaleInt = scale.intValue();
        int center = windowSize / 2 - scaleInt / 2;
        boolean first = true;
        /*
         * Double firstSize = scales.get(0) * windowSize / resultScale;
         * int sizeInt = firstSize.intValue();
         * Color firstColor = colors.get(0);
         * Square firstSquare = new Square(center, center, sizeInt, 1, firstColor);
         * squares.add(firstSquare);
         * corners.add(new Coordinate(center, center));
         * corners.add(new Coordinate(center + sizeInt, center));
         * corners.add(new Coordinate(center + sizeInt, center + sizeInt));
         * corners.add(new Coordinate(center, center + sizeInt));
         * parentSquares.add(firstSquare);
         */

        for (int i = 0; i < scales.size(); i++) {
            Color color = colors.get(i);
            // System.out.println("I: " + i);
            // System.out.println("Current scale: " + scales.get(i));
            Double scaleToSize = scales.get(i) * windowSize / resultScale;
            int size = scaleToSize.intValue();

            if (first) {
                Square firstSquare = new Square(center, center, size, color);
                squares.add(firstSquare);
                corners.add(new Coordinate(center, center));
                corners.add(new Coordinate(center + size, center));
                corners.add(new Coordinate(center + size, center + size));
                corners.add(new Coordinate(center, center + size));
                first = false;
                continue;
            }
            for (Coordinate corner : corners) {
                Square s = new Square(corner.x - size / 2, corner.y - size / 2, size, color);
                squares.add(s);
                tempCorners.add(new Coordinate(s.x, s.y));
                tempCorners.add(new Coordinate(s.x + size, s.y));
                tempCorners.add(new Coordinate(s.x + size, s.y + size));
                tempCorners.add(new Coordinate(s.x, s.y + size));
            }
            corners = new ArrayList<Coordinate>(tempCorners);
            // System.out.println("Corners size: " + corners.size());
            tempCorners.clear();
        }
        // System.out.println("Squares size: " + squares.size());
        for (Square square : squares) {
            g.setColor(square.c);
            g.fillRect(square.x + 50, square.y + 50, square.size, square.size);
        }
    }
}
