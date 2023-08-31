//import java.util.Arrays;
//
///* Java program for Merge Sort */
//class MergeSort {
//    // Merges two subarrays of arr[].
//    // First subarray is arr[l..m]
//    // Second subarray is arr[m+1..r]
//    void merge(Double[][] arr, int l, int m, int r,int dim)
//    {
//        // Find sizes of two subarrays to be merged
//        int n1 = m - l + 1;
//        int n2 = r - m;
//
//        /* Create temp arrays */
//        Double[][] L = new Double[n1][2];
//        Double[][] R = new Double[n2][2];
//        /*Copy data to temp arrays*/
//        for (int i = 0; i < n1; ++i)
//            L[i] = arr[l + i];
//        for (int j = 0; j < n2; ++j)
//            R[j] = arr[m + 1 + j];
//
//        /* Merge the temp arrays */
//
//        // Initial indexes of first and second subarrays
//        int i = 0, j = 0;
//
//        // Initial index of merged subarray array
//        int k = l;
//        while (i < n1 && j < n2) {
//            if (L[i][dim] <= R[j][dim]) {
//                arr[k] = L[i];
//                i++;
//            }
//            else {
//                arr[k] = R[j];
//                j++;
//            }
//            k++;
//        }
//
//        /* Copy remaining elements of L[] if any */
//        while (i < n1) {
//            arr[k] = L[i];
//            i++;
//            k++;
//        }
//
//        /* Copy remaining elements of R[] if any */
//        while (j < n2) {
//            arr[k] = R[j];
//            j++;
//            k++;
//        }
//    }
//
//    // Main function that sorts arr[l..r] using
//    // merge()
//    Double[][] sort(Double[][] arr, int l, int r,int dim)
//    {
//        if (l < r) {
//            // Find the middle point
//            int m = l + (r - l) / 2;
//
//            // Sort first and second halves
//            sort(arr, l, m,dim);
//            sort(arr, m + 1, r,dim);
//
//            // Merge the sorted halves
//            merge(arr, l, m, r,dim);
//        }
//        return arr.clone();
//    }
//
//    /* A utility function to print array of size n */
//    static void printArray(Double[][] arr)
//    {
//        int n = arr.length;
//        for (int i = 0; i < n; ++i)
//            System.out.print(Arrays.toString(arr[i]) + " ");
//        System.out.println();
//    }
//
//    // Driver code
//
//}
///* This code is contributed by Rajat Mishra */
//
