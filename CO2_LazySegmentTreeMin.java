import java.util.*;

/**
 * CO2 Case Study: IoT Lab Temperature Monitoring using Segment Tree
 * with Lazy Propagation. Supports range minimum query and range add update.
 */
public class CO2_LazySegmentTreeMin {
    static class LazySegmentTree {
        private final int[] tree;
        private final int[] lazy;
        private final int n;

        LazySegmentTree(int[] values) {
            this.n = values.length;
            this.tree = new int[4 * n];
            this.lazy = new int[4 * n];
            build(values, 1, 0, n - 1);
        }

        private void build(int[] values, int node, int left, int right) {
            if (left == right) {
                tree[node] = values[left];
                return;
            }

            int mid = (left + right) / 2;
            build(values, 2 * node, left, mid);
            build(values, 2 * node + 1, mid + 1, right);
            tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
        }

        private void push(int node) {
            if (lazy[node] != 0) {
                int value = lazy[node];

                tree[2 * node] += value;
                lazy[2 * node] += value;

                tree[2 * node + 1] += value;
                lazy[2 * node + 1] += value;

                lazy[node] = 0;
            }
        }

        public void rangeAdd(int queryLeft, int queryRight, int value) {
            rangeAdd(1, 0, n - 1, queryLeft, queryRight, value);
        }

        private void rangeAdd(int node, int left, int right, int queryLeft, int queryRight, int value) {
            if (queryRight < left || right < queryLeft) return;

            if (queryLeft <= left && right <= queryRight) {
                tree[node] += value;
                lazy[node] += value;
                return;
            }

            push(node);
            int mid = (left + right) / 2;
            rangeAdd(2 * node, left, mid, queryLeft, queryRight, value);
            rangeAdd(2 * node + 1, mid + 1, right, queryLeft, queryRight, value);
            tree[node] = Math.min(tree[2 * node], tree[2 * node + 1]);
        }

        public int queryMin(int queryLeft, int queryRight) {
            return queryMin(1, 0, n - 1, queryLeft, queryRight);
        }

        private int queryMin(int node, int left, int right, int queryLeft, int queryRight) {
            if (queryRight < left || right < queryLeft) return Integer.MAX_VALUE;

            if (queryLeft <= left && right <= queryRight) {
                return tree[node];
            }

            push(node);
            int mid = (left + right) / 2;
            int leftMin = queryMin(2 * node, left, mid, queryLeft, queryRight);
            int rightMin = queryMin(2 * node + 1, mid + 1, right, queryLeft, queryRight);
            return Math.min(leftMin, rightMin);
        }

        public int rootMinimum() {
            return tree[1];
        }
    }

    public static void main(String[] args) {
        int[] readings = {4, 7, 2, 9, 5, 3, 6, 8};
        LazySegmentTree segmentTree = new LazySegmentTree(readings);

        System.out.println("CO2 - IoT Lab Lazy Segment Tree");
        System.out.println("--------------------------------");
        System.out.println("Initial Readings: " + Arrays.toString(readings));
        System.out.println("Root Minimum [1,8]: " + segmentTree.rootMinimum());

        // Zones are 1-based in the question, converted to 0-based here.
        int q1 = segmentTree.queryMin(2, 6);
        System.out.println("Q1 Minimum from zone 3 to 7: " + q1);

        segmentTree.rangeAdd(3, 5, 2); // Add +2 to zones 4 to 6
        System.out.println("Applied calibration update: +2 to zones 4 to 6");

        int q2 = segmentTree.queryMin(2, 6);
        System.out.println("Q2 Minimum from zone 3 to 7 after update: " + q2);

        System.out.println("\nExpected Output:");
        System.out.println("Q1 = 2");
        System.out.println("Q2 = 2");
        System.out.println("\nTime Complexity: Range Update/Query = O(log n)");
    }
}
