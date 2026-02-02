/**
 * The class Making Change solves a classic problem:
 * given a set of coins, how many ways can you make change for a target amount?
 *
 * @author Zach Blick
 * @author Vikram Saluja
 */

public class MakingChange {

    public static long countWays(int target, int[] coins) {
      // return countWaysMemoization(target, coins);
        return countWaysTabulation(target, coins);
    }

    public static long countWaysMemoization(int target, int[] coins){
        // Base case because there is only 1 way to make zero
        if(target == 0){
            return 1;
        }

        // Negative targets are invalid
        if(target < 0) {
            return 0;
        }

        // No coins means no way to make a target
        if (coins == null || coins.length == 0) {
            return 0;
        }

        int num = coins.length;

        // Stores the number of ways to make a sum
        long[][] memoization = new long[num][target + 1];

        // Initialize memoization table with -1 to show that they are not computed
        for(int i = 0; i < num; i++){
            for(int j = 0; j <= target; j++){
                memoization[i][j] = -1L;
            }
        }
        // Start the recursion using all coins
        return memoizationHelper(target, num - 1, coins, memoization);
    }

    public static long memoizationHelper(int sum, int coinIndex, int[] coins, long[][] memoization){
        // Base Cases
        if (sum == 0){
            return 1;
        }
        if (sum < 0) {
            return 0;
        }
        if (coinIndex < 0) {
            return 0;
        }

        // If this subproblem has already been solved then reuse it.
        if (memoization[coinIndex][sum] != -1L) {
            return memoization[coinIndex][sum];
        }

        // Include coin and stay at the same index
        long include = memoizationHelper(sum - coins[coinIndex], coinIndex, coins, memoization);

        // Exclude the current coin and move to previous index
        long exclude = memoizationHelper(sum, coinIndex - 1, coins, memoization);

        // Store and return the result
        memoization[coinIndex][sum] = include + exclude;
        return memoization[coinIndex][sum];
    }

    public static long countWaysTabulation(int target, int[] coins){

        // Represent the number of ways to make sum
        long[] ways = new long[target + 1];


        // Base case since there is only 1 way to make 0
        ways[0] = 1;

        // Do each coin one at a time
        for (int coin : coins) {
            // Update ways for all of the sums that can include this coin
            for (int sum = coin; sum <= target; sum++) {
                ways[sum] += ways[sum - coin];
            }
        }

        // Return final result
        return ways[target];

    }

}
