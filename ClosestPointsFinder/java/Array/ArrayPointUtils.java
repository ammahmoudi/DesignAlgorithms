package Array;

class ArrayPointUtils {

    public static Double distanceWith(Double[] point1, Double[] point2) {
        return Math.sqrt(Math.pow((point1[0] - point2[0]), 2) + Math.pow((point1[1] - point2[1]), 2));
    }

    public static Double[][] nearest(Double[][] pair1, Double[][] pair2) {
        Double d1 = distanceWith(pair1[0], (pair1[1]));
        Double d2 = distanceWith(pair2[0], (pair2[1]));
        Double[][] result = new Double[3][2];
        if (d1 > d2) {
            result[0] = pair2[0];
            result[1] = pair2[1];
            result[2][0] = d2;

        } else {
            result[0] = pair1[0];
            result[1] = pair1[1];
            result[2][0] = d1;
        }
        return result;
    }

}