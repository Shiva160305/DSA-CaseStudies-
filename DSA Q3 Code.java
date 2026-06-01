
class FenwickTree {

    int[] bit;
    int n;

    FenwickTree(int size) {
        n = size;
        bit = new int[n + 1];
    }

    void update(int index, int value) {

        while (index <= n) {
            bit[index] += value;
            index += index & (-index);
        }
    }

    int prefixSum(int index) {

        int sum = 0;

        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }

        return sum;
    }

    int rangeSum(int left, int right) {
        return prefixSum(right)
                - prefixSum(left - 1);
    }

    public static void main(String[] args) {

        int arr[] = {
            1000, 1500, 2000, 1200,
            1800, 900, 1600, 1100
        };

        FenwickTree ft
                = new FenwickTree(arr.length);

        for (int i = 0; i < arr.length; i++) {
            ft.update(i + 1, arr[i]);
        }

        int diff = 1700 - 1200;

        ft.update(4, diff);

        int total = ft.rangeSum(2, 6);

        System.out.println(
                "Total Spending = ₹" + total);
    }
}
