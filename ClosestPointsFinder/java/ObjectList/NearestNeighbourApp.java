package ObjectList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NearestNeighbourApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Coordinate> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Long x = sc.nextLong();
            Long y = sc.nextLong();

            points.add(new Coordinate(x, y));
        }
        NearestPointFinder module = new NearestPointFinder();
        List<List<Coordinate>> sorted = module.sortCoordinates(points);

        Coordinate[] result = module.nearestNeighbours(sorted.get(0), sorted.get(1));
        if (result[0].x < result[1].x) {
            System.out.print(result[0].x + " " + result[0].y + " ");
            System.out.print(result[1].x + " " + result[1].y + " ");
        } else {
            System.out.print(result[1].x + " " + result[1].y + " ");
            System.out.print(result[0].x + " " + result[0].y + " ");
        }

    }


}

class NearestPointFinder {

    public List<List<Coordinate>> sortCoordinates(List<Coordinate> points) {
        List<List<Coordinate>> result = new ArrayList<>();
        result.add(new ArrayList(merge_sort(points, 0, 0, points.size() - 1)));

        result.add(merge_sort(points, 1, 0, points.size() - 1));
        return result;
    }

    public Coordinate[] nearestNeighbours(List<Coordinate> X, List<Coordinate> Y) {

        if (X.size() < 4) {
            return nearestNeighboursFinderBruteForce(X);
        } else {

            Coordinate[] result;
            List<List<Coordinate>> splits = ArraySplitter(X);
            List<Coordinate> leftX = splits.get(0);
            List<Coordinate> rightX = splits.get(1);
            int midpoint = X.size() / 2;
            Coordinate median_X = X.get(midpoint);
            List<Coordinate> rightY = new ArrayList<>();
            List<Coordinate> leftY = new ArrayList<>();
            for (Coordinate point : Y) {
                if (point.x < median_X.x) {
                    leftY.add(point);
                } else {
                    rightY.add(point);
                }
            }

            Coordinate[] nearestPairsLeft = nearestNeighbours(leftX, leftY);
            Coordinate[] nearestPairsRight = nearestNeighbours(rightX, rightY);


            Coordinate[] nearestPoints = Coordinate.nearest(nearestPairsRight, nearestPairsLeft);
            result = nearestPoints;
            Double min_distance = nearestPoints[0].distanceWith(nearestPoints[1]);
            Long X_m = median_X.x;
            List<Coordinate> b = new ArrayList<>();
            for (Coordinate py : Y) {
                if (X_m - 3*min_distance < py.x
                        &&
                        py.x < X_m + 3*min_distance
                ) {
                    b.add(py);

                }
            }
            for (int i = 0; i < b.size() - 1; i++) {
                for (int j = i + 1; j < Math.min(i + 7, b.size()); j++) {
                    Coordinate p1 = b.get(i);
                    Coordinate p2 = b.get(j);

                    if (p1.distanceWith(p2) < min_distance) {
                        result[0] = p1;
                        result[1] = p2;
                    }
                }
            }
            return result;
        }


    }


    public Coordinate[] nearestNeighboursFinderBruteForce(List<Coordinate> points) {
        Coordinate point1;
        Coordinate point2;

        point1 = points.get(0);
        point2 = points.get(1);
        Double distance = Double.MAX_VALUE;
        for (int i = 0; i < points.size() - 1; i++) {
            Coordinate point_i = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Coordinate point_j = points.get(j);
                if (point_i.distanceWith(point_j) < distance) {
                    point1 = point_i;
                    point2 = point_j;
                    distance = point_i.distanceWith(point_j);
                }
            }
        }
        Coordinate[] result = new Coordinate[2];
        result[0] = point1;
        result[1] = point2;
        return result;
    }

    public List<Coordinate> merge_sort(List<Coordinate> points, int dim, int start, int end) {

        int length = end - start + 1;
        if (length == 1) {
            return points;
        } else if (length == 2) {

            if (points.get(start).getDimValue(dim) > points.get(end).getDimValue(dim)) {
                Coordinate p1 = points.get(start);
                Coordinate p2 = points.get(end);
                points.set(start, p2);
                points.set(end, p1);

                return points;
            } else {
                return points;
            }
        } else {

            int middle = (start + end) / 2;
            points = merge_sort(points, dim, start, middle);
            points = merge_sort(points, dim, middle + 1, end);
            points = merge(points, dim, start, middle, end);

        }
        return points;

    }

    public List<List<Coordinate>> ArraySplitter(List<Coordinate> array) {
        int length = array.size();
        int length_left = length / 2;
        int length_right;
        List<Coordinate>leftArray=new ArrayList<>();
        List<Coordinate>rightArray=new ArrayList<>();
    for(int i=0;i<length_left;i++)
    {
     leftArray.add(array.get(i));
    }
        for(int i=length_left;i<array.size();i++)
        {
            rightArray.add(array.get(i));
        }
//        List<ObjectList.Coordinate> leftArray = array.subList(0, length_left);
//        List<ObjectList.Coordinate> rightArray = array.subList(length_left, length);
        List<List<Coordinate>> result = new ArrayList<>();
        result.add(leftArray);
        result.add(rightArray);
        return result;
    }

    List<Coordinate> merge(List<Coordinate> points, int dim, int start, int middle, int end) {

        List<Coordinate> result = new ArrayList<>();
        int l = start;
        int r = middle + 1;
        for (int i = 0; i < start; i++) {
            result.add(points.get(i));
        }

        while (l <= middle && r <= end) {
            if (points.get(l).getDimValue(dim) <= points.get(r).getDimValue(dim)) {
                result.add(points.get(l));
                l++;
            } else {
                result.add(points.get(r));
                r++;
            }
        }
        while (l <= middle) {

            result.add(points.get(l));
            l++;

        }
        while (r <= end) {

            result.add(points.get(r));
            r++;

        }
        for (int i = end + 1; i < points.size(); i++) {
            result.add(points.get(i));
        }

        return result;
    }

}

class Coordinate {
    Long x;
    Long y;


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
