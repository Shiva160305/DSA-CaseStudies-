import java.util.*;

/**
 * CO3 Case Study: Rural Water Pipeline Planning using Kruskal MST.
 * Uses Union-Find to select the minimum-cost pipeline network.
 */
public class CO3_RuralPipelineKruskal {
    static class Edge implements Comparable<Edge> {
        int u, v, weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) return false;

            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }

            return true;
        }
    }

    static String villageName(int index) {
        return String.valueOf((char) ('A' + index));
    }

    static int kruskal(int vertices, List<Edge> edges) {
        Collections.sort(edges);
        UnionFind uf = new UnionFind(vertices);
        int cost = 0;
        int count = 0;

        System.out.println("Accepted MST Edges:");

        for (Edge edge : edges) {
            if (uf.union(edge.u, edge.v)) {
                System.out.println(villageName(edge.u) + " - " + villageName(edge.v) + " = " + edge.weight);
                cost += edge.weight;
                count++;
                if (count == vertices - 1) break;
            }
        }

        return cost;
    }

    public static void main(String[] args) {
        int vertices = 7; // A to G
        List<Edge> routes = new ArrayList<>();

        routes.add(new Edge(0, 1, 6)); // A-B
        routes.add(new Edge(0, 2, 2)); // A-C
        routes.add(new Edge(1, 2, 4)); // B-C
        routes.add(new Edge(1, 3, 5)); // B-D
        routes.add(new Edge(2, 3, 3)); // C-D
        routes.add(new Edge(2, 4, 7)); // C-E
        routes.add(new Edge(3, 4, 4)); // D-E
        routes.add(new Edge(3, 5, 6)); // D-F
        routes.add(new Edge(4, 5, 2)); // E-F
        routes.add(new Edge(4, 6, 5)); // E-G
        routes.add(new Edge(5, 6, 3)); // F-G

        System.out.println("CO3 - Rural Water Pipeline Kruskal MST");
        System.out.println("--------------------------------------");

        int totalCost = kruskal(vertices, routes);
        System.out.println("Total MST Cost: " + totalCost);

        System.out.println("\nIf route E-F(2) is unavailable:");
        System.out.println("One valid replacement is E-G(5), making new cost 21.");
        System.out.println("Cost increase = 3");

        System.out.println("\nTime Complexity: O(E log E)");
    }
}
