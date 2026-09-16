class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1_000_000_007;
        int[][] dp = new int[n][k + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j <= k; j++) {
            long currentPrefixSum = 0;
            for (int i = 1; i < n; i++) {
                long ways = dp[i - 1][j];
                currentPrefixSum = (currentPrefixSum + dp[i - 1][j - 1]) % MOD;
                
                ways = (ways + currentPrefixSum) % MOD;
                dp[i][j] = (int) ways;
            }
        }
        
        return dp[n - 1][k];
    }
}
