
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class KruskalsMST {


    // Defines edge structure
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        int query;

        public Edge(int src, int dest, int weight, int query) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
            this.query = query;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.weight > o.weight) {
                return 1;
            } else if (this.weight < o.weight) {
                return -1;
            } else {
                if (this.query > o.query) {
                    return -1;
                } else if (this.query < o.query) {
                    return 1;
                }
                return 0;
            }
        }

        @Override
        public String toString() {
            return
                     src +
                    ","+ dest +
                    "," + weight +
                    "," + query
                    ;
        }
    }

    static class Log {
        int x, parent_x;
        boolean changeParent;

        public Log(int x, int parent_x, boolean changeParent) {
            this.x = x;

            this.parent_x = parent_x;
            this.changeParent = changeParent;

        }


    }

    static Stack<Log> logs = new Stack<>();


    // Defines subset element structure
    static class Subset {
        int parent, rank;

        public Subset(int parent, int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    // Starting point of program execution
    public static void main(String[] args) throws IOException {
        Reader reader=new Reader();

        int V;
        int E;
        V=reader.nextInt();
        E=reader.nextInt();
        List<Edge> graphEdges = new ArrayList<>();
        for(int e=0;e<E;e++){
            graphEdges.add(new Edge(reader.nextInt()-1,reader.nextInt()-1, reader.nextInt(),0 ));
        }
        int Q;
        Q=reader.nextInt();
        for(int q=0;q<Q;q++){
            int ql=reader.nextInt();
            for(int qs=0;qs<ql;qs++){
                int index=reader.nextInt();
                Edge edge= graphEdges.get(index - 1);
                graphEdges.add(new Edge(edge.src,edge.dest,edge.weight,q+1));
            }
        }
//        for(int i=0;i<graphEdges.size();i++){
//            System.out.println(graphEdges.get(i));
//        }
//        List<Edge> graphEdges = new ArrayList<Edge>(
//                List.of(new Edge(0, 1, 2, 0),//1
//                        new Edge(0, 2, 2, 0),//2
//                        new Edge(1, 2, 1, 0),//3
//                        new Edge(1, 3, 1, 0),//4
//                        new Edge(2, 3, 1, 0),//5
//                        new Edge(2, 4, 2, 0),//6
//                        new Edge(3, 4, 2, 0),//7
//
//                        new Edge(1, 2, 1, 1),//3
//                        new Edge(1, 3, 1, 1),//4
//
//                        new Edge(1, 2, 1, 2),//3
//                        new Edge(1, 3, 1, 2),//4
//                        new Edge(2, 3, 1, 2),//5
//
//                        new Edge(0, 1, 2, 3),//1
//                        new Edge(3, 4, 2, 3),//7
//
//                        new Edge(0, 1, 2, 4),//1
//                        new Edge(0, 2, 2, 4)//2
//                      ));

        // Sort the edges in non-decreasing order
        // (increasing with repetition allowed)
//        graphEdges.sort(new Comparator<Edge>() {
//            @Override public int compare(Edge o1, Edge o2)
//            {
//                return o1.weight - o2.weight;
//            }
//        });
        Collections.sort(graphEdges);
//        System.out.println(graphEdges);

        kruskals(V, graphEdges,Q+1);
    }

    // Function to find the MST
    private static void kruskals(int V, List<Edge> edges, int Q) {
        int j = 0;
        int noOfEdges = 0;
        boolean[] results = new boolean[Q];
        Arrays.fill(results, true);
        // Allocate memory for creating V subsets
        Subset[] subsets = new Subset[V];
        // Allocate memory for results
//        Edge[] edges_result = new Edge[V];
        // Create V subsets with single elements
        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset(i, 0);
        }
        // Number of edges to be taken is equal to V-1
        while (noOfEdges < edges.size()) {
            // Pick the smallest edge. And increment
            // the index for next iteration
           if(j==edges.size())break;
           Edge nextEdge = edges.get(j);
            if (j > 1) {
                Edge prevEdge = edges.get(j - 1);
                if ((nextEdge.weight != prevEdge.weight) || (nextEdge.query != prevEdge.query)) {
                    while (!logs.isEmpty()) {
                        Log log = logs.pop();
                        if (log.changeParent) {
                            subsets[log.x].parent = log.parent_x;
                        } else {
                            subsets[log.x].rank = log.parent_x;
                        }
                    }
                }
            }
            int x = findRoot(subsets, nextEdge.src, nextEdge.query);
            int y = findRoot(subsets, nextEdge.dest, nextEdge.query);

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
//                edges_result[noOfEdges] = nextEdge;
                union(subsets, x, y, nextEdge.query);
                noOfEdges++;
            } else {
                results[nextEdge.query] = false;
            }
            j++;
        }

        // Print the contents of result[] to display the
        // built MST
//        System.out.println(
//                "Following are the edges of the constructed MST:");
//        int minCost = 0;
//        for (int i = 0; i < noOfEdges; i++) {
//            System.out.println(edges_result[i].src + " -- "
//                    + edges_result[i].dest + " == "
//                    + edges_result[i].weight);
//            minCost += edges_result[i].weight;
//        }
//        System.out.println("Total cost of MST: " + minCost);
        for(int i=1;i<results.length;i++){
            if (results[i]){
                System.out.println("YES");
            }else{
                System.out.println("NO");
            }

        }
//        System.out.println(Arrays.toString(results));
    }

    // Function to unite two disjoint sets
    private static void union(Subset[] subsets, int x,
                              int y, int q) {
        int rootX = findRoot(subsets, x, q);
        int rootY = findRoot(subsets, y, q);

        if (subsets[rootY].rank < subsets[rootX].rank) {
            if (q != 0)
                logs.push(new Log(rootY, subsets[rootY].parent, true));
            subsets[rootY].parent = rootX;
        } else if (subsets[rootX].rank
                < subsets[rootY].rank) {
            if (q != 0)
                logs.push(new Log(rootX, subsets[rootX].parent, true));
            subsets[rootX].parent = rootY;

        } else {
            if (q != 0) {
                logs.push(new Log(rootY, subsets[rootY].parent, true));
                logs.push(new Log(rootY, subsets[rootX].rank, false));
            }
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    // Function to find parent of a set
    private static int findRoot(Subset[] subsets, int i, int q) {
        int temp_p = subsets[i].parent;
        if (subsets[i].parent == i)
            return subsets[i].parent;

        subsets[i].parent
                = findRoot(subsets, subsets[i].parent, q);
        if (q != 0) {
            logs.push(new Log(i, temp_p, true));
        }
        return subsets[i].parent;
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }
}
// This code is contributed by Salvino D'sa
