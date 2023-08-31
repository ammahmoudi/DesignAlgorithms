package ObjectListWithindices;

import ObjectArray.Coordinate;

import java.util.*;

public class ObjecListWithIndicesFinder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Coordinate> points = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            Long x = sc.nextLong();
            Long y = sc.nextLong();

            points.add(new Coordinate(x, y));
        }
        FinderModule module = new FinderModule();
//        module.merge_sort(points, 0, 0, points.size() - 1);
//        System.out.println("start sorting:"+points.size());
        List<List<Coordinate>> sorted = module.sortCoordinates(points);
//        System.out.println(module.merge_sort(points,0,0,points.size()-1));
//        System.out.println("sorted done!");
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

class FinderModule {
    public void printPoints(Long[][] points) {
        for (Long[] i : points) {
            System.out.println(Arrays.toString(i));
        }
    }

    public List<List<Coordinate>> sortCoordinates(List<Coordinate> points) {
        List<List<Coordinate>> result = new LinkedList<>();
        result.add(new LinkedList(merge_sort(points, 0, 0, points.size() - 1)));
//        System.out.println(result.get(0));

        result.add(merge_sort(points, 1, 0, points.size() - 1));
//        System.out.println(result.get(1));
        return result;
    }

    public Coordinate[] nearestNeighbours(List<Coordinate> X, List<Coordinate> Y) {

        if (X.size() < 4) {
            return nearestNeighboursFinderBruteForce(X);
        } else {

            Coordinate[] result;
            List<List<Coordinate>> splits = ArraySplitter(X);
            List<Coordinate> leftArrayX = splits.get(0);
            List<Coordinate> rightArrayX = splits.get(1);
            int midpoint = X.size() / 2;
            Coordinate median_X = X.get(midpoint);
            List<Coordinate> rightArrayY = new LinkedList<>();
            List<Coordinate> leftArrayY = new LinkedList<>();
            for (Coordinate point : Y) {
                if (point.x < median_X.x) {
                    leftArrayY.add(point);
                } else {
                    rightArrayY.add(point);
                }
            }

            Coordinate[] nearestPairsLeft = nearestNeighbours(leftArrayX, leftArrayY);
            Coordinate[] nearestPairsRight = nearestNeighbours(rightArrayX, rightArrayY);
//            System.out.println("-");
//            printPoints(leftArrayX);
//            System.out.println("--");
//            printPoints(leftArrayY.toArray(new Long[0][0]));
//            System.out.println("---");
//            System.out.println(Arrays.toString(nearestPairsLeft));
//            System.out.println(Arrays.toString(nearestPairsRight));


            Coordinate[] nearestPoints = Coordinate.nearest(nearestPairsRight, nearestPairsLeft);
            result = nearestPoints;
            Double min_distance = nearestPoints[0].distanceWith(nearestPoints[1]);
            Long X_m = leftArrayX.get(leftArrayX.size() - 1).x;
            List<Coordinate> b = new LinkedList<>();
            for (Coordinate py : Y) {
                if (X_m - min_distance < py.x
                        &&
                        py.x < X_m + min_distance
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
//        Double distance = point1.distanceWith(point2);
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
//        System.out.println("points:" + points.size());
        int length = end - start + 1;
        if (length == 1) {
            return points;
        } else if (length == 2) {
//            System.out.println(points);
            if (points.get(start).getDimValue(dim) > points.get(end).getDimValue(dim)) {
                Coordinate p1 = points.get(start);
                Coordinate p2 = points.get(end);
                points.set(start, p2);
                points.set(end, p1);
//                System.out.println(points);
                return points;
            } else {
                return points;
            }
        }
//        if(start>=end || (end-start)<1){
//            return points;
//        }
        else {
//        if ((end - start) == 1) {
//            if (points.get(start).getDimValue(dim) > points.get(end).getDimValue(dim)) {
//                System.out.println("2mode:" + start + ":" + end);
//                Coordinate p1 = points.get(start);
//                Coordinate p2 = points.get(end);
////                System.out.println(points);
//                points.set(start, p2);
//                points.set(end, p1);
//
////                System.out.println(points);
//                return points;
//            }
//        } else if (start < end && (end - start) >= 2) {
            int middle = (start + end) / 2;
            points = merge_sort(points, dim, start, middle);
            points = merge_sort(points, dim, middle + 1, end);
//            System.out.println("sorting based on " + dim);
//            System.out.println("call:" + start + ":" + end + ":" + ((start + end) / 2));

            points = merge(points, dim, start, middle, end);
//            System.out.println("result:" + result.size());
//            System.out.println("-");
//            printPoints(leftArray_sorted);
//            System.out.println("--");
//            printPoints(rightArray_sorted);
//            System.out.println("---");
//            printPoints(result);

        }
        return points;

    }

    public List<List<Coordinate>> ArraySplitter(List<Coordinate> array) {
        int length = array.size();
        int length_left = length / 2;
        int length_right;
//            if (length % 2 == 0) {
//                length_right = length_left;
//            } else {
//                length_right = length_left + 1;
//            }
        List<Coordinate> leftArray = array.subList(0, length_left);
        List<Coordinate> rightArray = array.subList(length_left, length);
        List<List<Coordinate>> result = new LinkedList<>();
        result.add(leftArray);
        result.add(rightArray);
        return result;
    }

    List<Coordinate> merge(List<Coordinate> points, int dim, int start, int middle, int end) {
//        System.out.println("start merging!" + points.size());
//        for (Coordinate c : points) {
//            System.out.println(c);
//        }
//
        List<Coordinate> result = new LinkedList<>();
        int l = start;
        int r = middle + 1;
        for(int i=0;i<start;i++){
            result.add(points.get(i));
        }

        while(l<=middle && r<=end){
            if (points.get(l).getDimValue(dim) <= points.get(r).getDimValue(dim)) {
                result.add(points.get(l));
                l++;
            } else {
                result.add(points.get(r));
                r++;
            }
        }
        while(l<=middle){

                result.add(points.get(l));
                l++;

        }
        while(r<=end){

            result.add(points.get(r));
            r++;

        }
        for(int i=end+1;i<points.size();i++){
            result.add(points.get(i));
        }
//        for (int i = 0; i < result.size(); start++) {
//            points.set(start, result.get(i++));
//        }
//        System.out.println(start + "-" + middle + "-" + end);
//        for (int i = start; i <= end; i++) {
//            System.out.println(i + ":" + l + ":" + r);
//            System.out.println(points);
//            Coordinate pl=points.get(l);
//            Coordinate pr=points.get(r);
//            if (r == end) {
//                points.add(end+1,pl);
//                points.remove(l);
//                l++;
//            } else if (l == middle) {
//                points.add(middle+1,pr);
//                points.remove(r);
//
//                r++;
//            }else {
//                if (pl.getDimValue(dim) > pr.getDimValue(dim)) {
//                    points.set(l, pr);
//                    points.set(r, pl);
//                    r++;
//                } else if (points.get(l).getDimValue(dim) <= points.get(r).getDimValue(dim)) {
//                    l++;
//                }
//            }
//        }
//        System.out.println("Done merging!" + result.size());
//        System.out.println("l:" + l);
//        System.out.println("r:" + r);
        return result;
    }

}



