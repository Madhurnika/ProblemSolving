import java.util.Arrays;

class Solution {
    // A representation of a Segment Tree node
    static class Node {
        int prod;
        int[] remain;

        Node(int k) {
            this.prod = 1;
            this.remain = new int[k];
        }
    }

    private int n;
    private int k;
    private Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.tree = new Node[4 * n];
        
        // Build the initial Segment Tree
        build(nums, 0, 0, n - 1);

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1] % k;
            int start = queries[i][2];
            int x = queries[i][3];

            // 1. Perform persistent point update
            update(0, 0, n - 1, index, value);

            // 2. Query the subarray [start, n - 1]
            Node resNode = query(0, 0, n - 1, start, n - 1);
            ans[i] = resNode.remain[x];
        }

        return ans;
    }

    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        Node parent = new Node(k);
        parent.prod = (left.prod * right.prod) % k;

        // Copy prefixes from the left child
        for (int i = 0; i < k; i++) {
            parent.remain[i] += left.remain[i];
        }

        // Add extended prefixes from the right child
        for (int i = 0; i < k; i++) {
            if (right.remain[i] > 0) {
                int targetMod = (left.prod * i) % k;
                parent.remain[targetMod] += right.remain[i];
            }
        }

        return parent;
    }

    private void build(int[] nums, int treeIndex, int lo, int hi) {
        tree[treeIndex] = new Node(k);
        if (lo == hi) {
            int val = nums[lo] % k;
            tree[treeIndex].prod = val;
            tree[treeIndex].remain[val] = 1;
            return;
        }
        int mid = lo + (hi - lo) / 2;
        build(nums, 2 * treeIndex + 1, lo, mid);
        build(nums, 2 * treeIndex + 2, mid + 1, hi);
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }

    private void update(int treeIndex, int lo, int hi, int i, int val) {
        if (lo == hi) {
            Arrays.fill(tree[treeIndex].remain, 0);
            tree[treeIndex].prod = val;
            tree[treeIndex].remain[val] = 1;
            return;
        }
        int mid = lo + (hi - lo) / 2;
        if (i <= mid) {
            update(2 * treeIndex + 1, lo, mid, i, val);
        } else {
            update(2 * treeIndex + 2, mid + 1, hi, i, val);
        }
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }

    private Node query(int treeIndex, int lo, int hi, int l, int r) {
        if (l <= lo && hi <= r) {
            return tree[treeIndex];
        }
        int mid = lo + (hi - lo) / 2;
        Node leftResult = null;
        Node rightResult = null;

        if (l <= mid) {
            leftResult = query(2 * treeIndex + 1, lo, mid, l, r);
        }
        if (r > mid) {
            rightResult = query(2 * treeIndex + 2, mid + 1, hi, l, r);
        }

        return merge(leftResult, rightResult);
    }
}
