package ObjectArray;

public class Coordinate {
    public Long x;
    public Long y;


    public Coordinate(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Long[] data) {
        this.x = data[0];
        this.y = data[1];
    }

    public Long getDimValue(int dim) {
        if (dim == 0) return x;
        else return y;
    }

    @Override
    public String toString() {
        return "{" + x +
                ", " + y +
                '}';
    }

    public Double distanceWith(Coordinate point2) {
        return Math.sqrt(Math.pow((this.x - point2.x), 2) + Math.pow((this.y - point2.y), 2));
    }

    public static Coordinate[] nearest(Coordinate[] pair1, Coordinate[] pair2) {
        if (pair1[0].distanceWith(pair1[1]) > pair2[0].distanceWith(pair2[1])) {
            return pair2;
        } else return pair1;

    }

}
