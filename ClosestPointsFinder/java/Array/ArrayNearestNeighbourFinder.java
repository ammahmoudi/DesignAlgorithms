package Array;

import java.util.Scanner;
//nearest pairs using array
public class ArrayNearestNeighbourFinder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Double[][] points = new Double[n][2];
        for (int i = 0; i < n; i++) {

            points[i][0] = sc.nextDouble();
            points[i][1] = sc.nextDouble();

        }

        NearestModule module = new NearestModule();
        //sort array in both dimensions
        Double[][] Px = module.merge_sort(points, 0);
        Double[][] Py = module.merge_sort(points, 1);
        // find the closest pair
        Double[][] result = module.nearestNeighbours(Px, Py);


        if (result[0][0] < result[1][0]) {
            System.out.print(result[0][0].intValue() + " " + result[0][1].intValue() + " ");
            System.out.print(result[1][0].intValue() + " " + result[1][1].intValue() + " ");
        } else {
            System.out.print(result[1][0].intValue() + " " + result[1][1].intValue() + " ");
            System.out.print(result[0][0].intValue() + " " + result[0][1].intValue() + " ");


        }

    }


}


