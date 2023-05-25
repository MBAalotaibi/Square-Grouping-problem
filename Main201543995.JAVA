import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Main201543995 {

    // Reads data from a file and returns a list of strings
    public static ArrayList<String> ReadData(String pathname) {
        ArrayList<String> strlist = new ArrayList<>();
        try {
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                strlist.add(line);
            }
            return strlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strlist;
    }
    
    // Takes a list of strings and converts them to a list of lists of integers
    public static ArrayList<ArrayList<Integer>> DataWash(ArrayList<String> Datalist) {
        ArrayList<ArrayList<Integer>> AIS = new ArrayList<>();
        ArrayList<Integer> IS = new ArrayList<>();
        for (String Tstr : Datalist) {
            if (!Tstr.equals("A")) {
                IS.add(Integer.parseInt(Tstr));
            }
            if (Tstr.equals("A")) {
                AIS.add(new ArrayList<>(IS));
                IS.clear();
            }
        }
        return AIS;
    }

    // Takes an integer array, the length of the array, and the number of groups
    // Returns the minimum sum of squares of group sums
    public static int SquareGrouping(int[] A, int n, int k) {


        // Description of the main ideas of your solution:
        // The solution uses dynamic programming to solve the problem.
        // The recursive relation is defined as follows:
        // dp[i][j] represents the minimum possible value of the objective function
        // for the first i elements divided into j groups.
        // dp[i][j] = min(dp[p][j-1] + (sum(A[p+1]...A[i]))^2) for p in [0, i)

        // Running time analysis:
        // The running time of the solution depends on n (length of the input sequence) and k (number of groups).
        // The solution has a time complexity of O(n^2 * k) as there are n*k states and each state takes O(n) time to compute.
    


        int[][] dp = new int[n + 1][k + 1];
        int[] prefixSum = new int[n + 1];
    
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + A[i - 1];
        }
    
        for (int i = 1; i <= n; i++) {
            dp[i][1] = prefixSum[i] * prefixSum[i];
        }
    
        for (int i = 1; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int p = 0; p < i; p++) {
                    int currentSum = prefixSum[i] - prefixSum[p];
                    int currentCost = dp[p][j - 1] + currentSum * currentSum;
                    dp[i][j] = Math.min(dp[i][j], currentCost);
                }
            }
        }
    
        return dp[n][k];
    }
    

    public static int Computation(ArrayList<Integer> Instance, int opt) {
        int NGounp = 0;
        int size = 0;
        int Correct = 0;
        size = Instance.size();
        int[] A = new int[size - opt];
        NGounp = (Integer) Instance.get(0);
        for (int i = opt; i < size; i++) {
            A[i - opt] = (Integer) Instance.get(i);
        }
        int Size = A.length;
        if (NGounp > Size) {
            return (-1);
        } else {
            int R = SquareGrouping(A, Size, NGounp);
            return (R);
        }
    }

    public static String Test;

    public static void main(String[] args) {
        Test = args[0];
        int opt = 2;
        String pathname = "data2.txt";
        if (Test.equals("-opt1")) {
            opt = 1;
            pathname = "data1.txt";
        }
        ArrayList<String> Datalist = new ArrayList<>();
        Datalist = ReadData(pathname);
        ArrayList<ArrayList<Integer>> AIS = DataWash(Datalist);
        int Nins = AIS.size();
        int NGounp = 0;
        int size = 0;
        if (Test.equals("-opt1")) {
            for (int t = 0; t < Nins; t++) {
                int Correct = 0;
                int Result = 0;
                ArrayList<Integer> Instance = AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
            }
        } else {
            int wrong_no = 0;
            int Correct = 0;
            int Result = 0;
            ArrayList<Integer> Wrong = new ArrayList<>();
            for (int t = 0; t < Nins; t++) {
                ArrayList<Integer> Instance = AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
                Correct = Instance.get(1);
                if (Correct != Result) {
                    Wrong.add(t + 1);
                    wrong_no = wrong_no + 1;
                }
            }
            if (Wrong.size() > 0) {
                System.out.println("Index of wrong instance(s):");
            }
            for (int j = 0; j < Wrong.size(); j++) {
                System.out.print(Wrong.get(j));
                System.out.print(",");
            }
            System.out.println("");
            System.out.println("Percentage of correct answers:");
            System.out.println(((double) (Nins - wrong_no) / (double) Nins) * 100);
        }
    }
}