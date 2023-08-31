import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Runner {
    public static void main(String[] args) {
        int n = 3;
        LinkedList<Pair> d = new LinkedList<>();
//        String s = "0 2\n2 0";
//        String s = "0 1\n2 0";
//        String s = "0 1 2\n" +
//                "1 0 1\n" +
//                "2 1 0";

//        d.sort(Comparator.comparing(Pair::getD));
//        System.out.println(d);
//        System.out.println(d[0][0]);
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();

        for (int i = 0; i < n; i++) {

//            String[] line = scanner.nextLine().split(" ");


//            System.out.println(Arrays.toString(line));
            for (int j = 0; j < n; j++) {

                d.add(new Pair(i, j, scanner.nextInt()));
            }
        }
//            input.append(scanner.nextLine());
//            if(i!=n)
//            input.append("\n");


//        input.delete(0,1);
//        System.out.println(d);
//        d=string2list(input.toString(),n);
        ShortestPath2Graph module = new ShortestPath2Graph(n, d);
        module.makeGraph();
//        System.out.println(Arrays.deepToString(module.adj));


    }

    public static int[][] string2Matrix(String s, int n) {
        int[][] d = new int[n][n];
        String[] lines = s.split("\n");
        for (int i = 0; i < n; i++) {
            String[] line = lines[i].split(" ");
            for (int j = 0; j < n; j++) {
                d[i][j] = Integer.parseInt(line[j]);
            }
        }
        return d;
    }

    public static LinkedList<Pair> string2list(String s, int n) {
        LinkedList<Pair> d = new LinkedList<>();
//        System.out.println(s);
        String[] lines = s.split("\n");
//        System.out.println(lines[3]);
        for (int i = 0; i < n; i++) {
            String[] line = lines[i].split(" ");
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                int distance = Integer.valueOf(line[j]);
                d.add(new Pair(i, j, distance));
            }
        }
        return d;
    }

}

class Pair {
    int i;
    int j;
    int d;

    public Pair(int i, int j, int d) {
        this.i = i;
        this.j = j;
        this.d = d;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getD() {
        return d;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "i=" + i +
                ", j=" + j +
                ", d=" + d +
                '}';
    }
}

class ShortestPath2Graph {
    int n;
    LinkedList<Pair> d;
    Integer[][] adj;
    int[][] d_p;
    int edges_n = 0;

    public ShortestPath2Graph(int n, LinkedList<Pair> d) {

        this.n = n;
        this.d = d;
//        adj = new Integer[n][n];
        d_p = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d_p[i], Integer.MAX_VALUE/100);
            d_p[i][i] = 0;
        }
    }

    public Boolean makeGraph() {
        Collections.sort(d, Comparator.comparing(Pair::getD));

//        System.out.println(d);
        for(Pair current:d){
//        while (!d.isEmpty()) {
//            System.out.println(d);
//            Pair current = d.pop();
            int x = current.getI();
            int y = current.getJ();
            int distance = current.getD();
            if (distance < d_p[x][y]) {
//                print2DArray(d_p);

                d_p[x][y] = distance;
                d_p[y][x] = distance;
                updateDp(current);
//                adj[x][y] = distance;
                edges_n++;
//                System.out.println("after_update:");
//                print2DArray(d_p);

            } else if (distance > d_p[x][y]) {
                System.out.println(-1);
                return false;
            }
        }
        System.out.println(edges_n);
        return true;

    }

    public void updateDp(Pair data) {
        int x = data.getI();
        int y = data.getJ();
        int distance = data.getD();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                d_p[i][j] = Math.min(Math.min(d_p[i][x] + distance + d_p[y][j], d_p[i][y] + distance + d_p[x][j]), d_p[i][j]);
                d_p[j][i] = d_p[i][j];
            }
        }
    }

    public void print2DArray(Double[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(Arrays.deepToString(array[i]));

        }
    }
}


