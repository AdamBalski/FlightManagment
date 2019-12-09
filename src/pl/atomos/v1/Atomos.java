package pl.atomos.v1;

public class Atomos
{
    public static int longestSubsequence(String a, String b)
    {
        int[][] dp = new int[a.length()][b.length()];

        for(int i = 0; i < a.length(); i++)
        {
            for(int j = 0; j < b.length(); j++)
            {
                if(a.charAt(i) == b.charAt(j))
                {
                    dp[i][j] = j == 0 || i == 0 ? 0 : dp[i-1][j-1] + 1;
                }
                else
                {
                    dp[i][j] = Math.max(j == 0 ? 0 : dp[i][j-1], i == 0 ? 0 : dp[i-1][j]);
                }
            }
        }

        return Atomos.max(dp);
    }

    public static int max(int[][] a)
    {
        int  result = Integer.MIN_VALUE;

        for(int[] b: a)
        {
            for(int c: b)
            {
                if(c > result)  result = c;
            }
        }

        return result;
    }
}
