package ObjectArray;

import java.util.Scanner;
//nearest pairs using array of point object
//fastest
public class ObjectArrayClosestPairFinder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Coordinate[] points = new Coordinate[n];
        for (int i = 0; i < n; i++) {

            Long x = sc.nextLong();
            Long y = sc.nextLong();
            Coordinate p = new Coordinate(x, y);
            points[i] = p;


        }

        ObjectArrayFinderModule module = new ObjectArrayFinderModule();
        //sort array in both dimensions
        Coordinate[] Px = module.merge_sort(points, 0);
        Coordinate[] Py = module.merge_sort(points, 1);
        // find the closest pair
        Coordinate[] result = module.nearestNeighbours(Px, Py);


        if (result[0].x < result[1].x) {
            System.out.print(result[0].x + " " + result[0].y + " ");
            System.out.print(result[1].x + " " + result[1].y + " ");
        } else {
            System.out.print(result[1].x + " " + result[1].y + " ");
            System.out.print(result[0].x + " " + result[0].y + " ");


        }

    }


}

class ObjectArrayFinderModule {

    public Coordinate[] nearestNeighbours(Coordinate[] X, Coordinate[] Y) {
        // use bruteforce if length is less than 4
        if (X.length < 4) {
            return nearestNeighboursFinderBruteForce(X);
        } else {
            int mid_index = X.length / 2;
            Coordinate median_X = X[mid_index];

            Coordinate[] result;
            //split the X sorted array into two half
            Coordinate[] leftArrayX = new Coordinate[X.length / 2];
            Coordinate[] rightArrayX = new Coordinate[X.length - X.length / 2];
            for (int i = 0; i < leftArrayX.length; i++) {
                leftArrayX[i] = X[i];
            }
            for (int i = 0; i < rightArrayX.length; i++) {
                rightArrayX[i] = X[i + leftArrayX.length];
            }


            Coordinate[] leftArrayY = new Coordinate[leftArrayX.length];
            Coordinate[] rightArrayY = new Coordinate[rightArrayX.length];
            int l_counter = 0;
            int r_counter = 0;
            for (Coordinate point : Y) {
                if (point.x < median_X.x || (point.x == median_X.x && point.y < median_X.y) && l_counter < mid_index) {
                    leftArrayY[l_counter] = point;
                    l_counter++;
                } else {
                    rightArrayY[r_counter] = point;
                    r_counter++;
                }
            }
            //recurse
            Coordinate[] nearestPairsLeft = nearestNeighbours(leftArrayX, leftArrayY);
            Coordinate[] nearestPairsRight = nearestNeighbours(rightArrayX, rightArrayY);
            Coordinate[] nearestPoints = Coordinate.nearest(nearestPairsRight, nearestPairsLeft);
            result = nearestPoints;
            Double min_dist = result[0].distanceWith(result[1]);
            //check at most 6 closest points.
            Coordinate[] b = new Coordinate[X.length];
            int b_num = 0;
            for (Coordinate py : Y) {
                if (Math.abs(median_X.x - py.x) < min_dist) {
                    b[b_num] = py;
                    b_num++;
                }
            }
            for (int i = 0; i < b_num - 1; i++) {
                for (int j = i + 1; j < Math.min(i + 7, b_num); j++) {
                    Double dist = b[i].distanceWith(b[j]);

                    if (dist < min_dist) {
                        result[0] = b[i];
                        result[1] = b[j];
                        min_dist = dist;

                    }
                }
            }
            return result;
        }


    }


    public Coordinate[] nearestNeighboursFinderBruteForce(Coordinate[] points) {
        Coordinate[] result = new Coordinate[points.length];
        Double distance = Double.MAX_VALUE;
        for (int i = 0; i < points.length - 1; i++) {

            for (int j = i + 1; j < points.length; j++) {
                Double distance_ij = points[i].distanceWith(points[j]);
                if (distance_ij < distance) {
                    result[0] = points[i];
                    result[1] = points[j];
                    distance = distance_ij;
                }
            }
        }
        if (result[0] == null) {
            result[0] = points[0];
            result[1] = points[1];
        }

        return result;
    }

    public Coordinate[] merge_sort(Coordinate[] points, int dim) {
        int length = points.length;
        if (length == 1) {
            return points;
        } else if (length == 2) {
            if (points[0].getDimValue(dim) > points[1].getDimValue(dim)) {
                Coordinate[] sorted = new Coordinate[length];
                sorted[0] = points[1];
                sorted[1] = points[0];
                return sorted;
            } else {
                return points;
            }
        } else {
            Coordinate[] leftArray = new Coordinate[points.length / 2];
            Coordinate[] rightArray = new Coordinate[points.length - points.length / 2];
            for (int i = 0; i < leftArray.length; i++) {
                leftArray[i] = points[i];
            }
            for (int i = 0; i < rightArray.length; i++) {
                rightArray[i] = points[i + leftArray.length];
            }

            Coordinate[] leftArray_sorted = merge_sort(leftArray, dim);
            Coordinate[] rightArray_sorted = merge_sort(rightArray, dim);

            Coordinate[] result = merge(leftArray_sorted, rightArray_sorted, dim);

            return result;
        }

    }


    Coordinate[] merge(Coordinate[] leftArray, Coordinate[] rightArray, int dim) {
        Coordinate[] result = new Coordinate[leftArray.length + rightArray.length];
        int l = 0;
        int r = 0;
        for (int i = 0; i < result.length; i++) {
            if (r == rightArray.length) {
                result[i] = leftArray[l];
                l++;
            } else if (l == leftArray.length) {
                result[i] = rightArray[r];
                r++;
            } else if (leftArray[l].getDimValue(dim) > rightArray[r].getDimValue(dim)) {
                result[i] = rightArray[r];
                r++;

            } else if (leftArray[l].getDimValue(dim) <= rightArray[r].getDimValue(dim)) {
                result[i] = leftArray[l];
                l++;

            }

        }
        return result;
    }

}

