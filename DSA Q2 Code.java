
class SegmentTree {

    int[] tree;
    int[] lazy;
    int n;

    SegmentTree(int arr[]) {
        n = arr.length;

        tree = new int[4 * n];
        lazy = new int[4 * n];

        build(1, 0, n - 1, arr);
    }

    void build(int node, int start, int end, int arr[]) {

        if (start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end) / 2;

        build(2 * node, start, mid, arr);
        build(2 * node + 1, mid + 1, end, arr);

        tree[node] = Math.max(tree[2 * node],
                tree[2 * node + 1]);
    }

    void update(int node, int start, int end,
            int l, int r, int val) {

        if (lazy[node] != 0) {

            tree[node] += lazy[node];

            if (start != end) {
                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }

            lazy[node] = 0;
        }

        if (start > r || end < l) {
            return;
        }

        if (start >= l && end <= r) {

            tree[node] += val;

            if (start != end) {
                lazy[2 * node] += val;
                lazy[2 * node + 1] += val;
            }
            return;
        }

        int mid = (start + end) / 2;

        update(2 * node, start, mid, l, r, val);
        update(2 * node + 1, mid + 1, end, l, r, val);

        tree[node] = Math.max(tree[2 * node],
                tree[2 * node + 1]);
    }

    int query(int node, int start, int end,
            int l, int r) {

        if (start > r || end < l) {
            return Integer.MIN_VALUE;
        }

        if (lazy[node] != 0) {

            tree[node] += lazy[node];

            if (start != end) {
                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }

            lazy[node] = 0;
        }

        if (start >= l && end <= r) {
            return tree[node];
        }

        int mid = (start + end) / 2;

        return Math.max(
                query(2 * node, start, mid, l, r),
                query(2 * node + 1, mid + 1, end, l, r));
    }

    public static void main(String[] args) {

        int arr[] = {1, 2, 3, 2, 1, 4, 2, 3};

        SegmentTree st = new SegmentTree(arr);

        st.update(1, 0, 7, 2, 6, 2);

        int max = st.query(1, 0, 7, 1, 7);

        System.out.println("Maximum Surge = " + max);
    }
}
