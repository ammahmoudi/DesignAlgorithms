package Array;

public class NearestModule {

    public Double[][] nearestNeighbours(Double[][] X, Double[][] Y) {
        // use bruteforce if length is less than 4
        if (X.length < 4) {
            return nearestNeighboursFinderBruteForce(X);
        } else {
            int mid_index = X.length / 2;
            Double[] median_X = X[mid_index];

            Double[][] result;
            //split the X sorted array into two half
            Double[][] leftArrayX = new Double[X.length / 2][2];
            Double[][] rightArrayX = new Double[X.length - X.length / 2][2];
            for (int i = 0; i < leftArrayX.length; i++) {
                leftArrayX[i] = X[i];
            }
            for (int i = 0; i < rightArrayX.length; i++) {
                rightArrayX[i] = X[i + leftArrayX.length];
            }


            Double[][] leftArrayY = new Double[leftArrayX.length][2];
            Double[][] rightArrayY = new Double[rightArrayX.length][2];
            int l_counter = 0;
            int r_counter = 0;
            for (Double[] point : Y) {
                if (point[0] < median_X[0] || (point[0] == median_X[0] && point[1] < median_X[1]) && l_counter < mid_index) {
                    leftArrayY[l_counter] = point;
                    l_counter++;
                } else {
                    rightArrayY[r_counter] = point;
                    r_counter++;
                }
            }
            //recurse
            Double[][] nearestPairsLeft = nearestNeighbours(leftArrayX, leftArrayY);
            Double[][] nearestPairsRight = nearestNeighbours(rightArrayX, rightArrayY);
            Double[][] nearestPoints = ArrayPointUtils.nearest(nearestPairsRight, nearestPairsLeft);
            result = nearestPoints;
            Double min_dist = result[2][0];
            //check at most 6 closest points.
            Double[][] b = new Double[X.length][2];
            int b_num = 0;
            for (Double[] py : Y) {
                if (Math.abs(median_X[0] - py[0]) < min_dist) {
                    b[b_num] = py;
                    b_num++;
                }
            }
            for (int i = 0; i < b_num - 1; i++) {
                for (int j = i + 1; j < Math.min(i + 7, b_num); j++) {
                    Double dist = ArrayPointUtils.distanceWith(b[i], b[j]);

                    if (dist < min_dist) {
                        result[0] = b[i];
                        result[1] = b[j];
                        min_dist = dist;
                        result[2][0] = dist;
                    }
                }
            }
            return result;
        }


    }


    public Double[][] nearestNeighboursFinderBruteForce(Double[][] points) {
        Double[][] result = new Double[3][2];
        Double distance = Double.MAX_VALUE;
        for (int i = 0; i < points.length - 1; i++) {

            for (int j = i + 1; j < points.length; j++) {
                Double distance_ij = ArrayPointUtils.distanceWith(points[i], points[j]);
                if (distance_ij < distance) {
                    result[0] = points[i];
                    result[1] = points[j];
                    distance = distance_ij;
                }
            }
        }
        if (result[0][0] == null) {
            result[0] = points[0];
            result[1] = points[1];
        }
        result[2][0] = distance;

        return result;
    }

    public Double[][] merge_sort(Double[][] points, int dim) {
        int length = points.length;
        if (length == 1) {
            return points;
        } else if (length == 2) {
            if (points[0][dim] > points[1][dim]) {
                Double[][] sorted = new Double[2][2];
                sorted[0] = points[1].clone();
                sorted[1] = points[0].clone();
                return sorted;
            } else {
                return points;
            }
        } else {
            Double[][] leftArray = new Double[points.length / 2][2];
            Double[][] rightArray = new Double[points.length - points.length / 2][2];
            for (int i = 0; i < leftArray.length; i++) {
                leftArray[i] = points[i];
            }
            for (int i = 0; i < rightArray.length; i++) {
                rightArray[i] = points[i + leftArray.length];
            }

            Double[][] leftArray_sorted = merge_sort(leftArray, dim);
            Double[][] rightArray_sorted = merge_sort(rightArray, dim);

            Double[][] result = merge(leftArray_sorted, rightArray_sorted, dim);

            return result;
        }

    }


    Double[][] merge(Double[][] leftArray, Double[][] rightArray, int dim) {
        Double[][] result = new Double[leftArray.length + rightArray.length][2];
        int l = 0;
        int r = 0;
        for (int i = 0; i < result.length; i++) {
            if (r == rightArray.length) {
                result[i] = leftArray[l];
                l++;
            } else if (l == leftArray.length) {
                result[i] = rightArray[r];
                r++;
            } else if (leftArray[l][dim] > rightArray[r][dim]) {
                result[i] = rightArray[r];
                r++;

            } else if (leftArray[l][dim] <= rightArray[r][dim]) {
//                System.out.println(r);
//                System.out.println(leftArray.length);
                result[i] = leftArray[l];
                l++;

            }

        }
        return result;
    }

}
