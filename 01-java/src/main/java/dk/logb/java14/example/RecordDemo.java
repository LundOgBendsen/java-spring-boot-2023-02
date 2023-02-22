package dk.logb.java14.example;
record Point(int x, int y) { }
record Square(Point topLeft, Point bottomRight) {
    public Square {
        if (topLeft.x() > bottomRight.x() || topLeft.y() > bottomRight.y()) {
            throw new IllegalArgumentException("Invalid square");
        }
    }
    public int area() {
        return (bottomRight.x() - topLeft.x()) * (bottomRight.y() - topLeft.y());
    }

    public void draw() {
        for (int y = topLeft.y(); y <= bottomRight.y(); y++) {
            for (int x = topLeft.x(); x <= bottomRight.x(); x++) {
                if (x == topLeft.x() || x == bottomRight.x() || y == topLeft.y() || y == bottomRight.y()) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

public class RecordDemo {
    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        System.out.println(p1.equals(p2));
        System.out.println(p1 == p2);
        System.out.println(p1);

        Square square = new Square(new Point(1, 2), new Point(10, 11));
        System.out.println(square.area());
        square.draw();

    }
}
