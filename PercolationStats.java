/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("N / T can't be <=0.");
        }

        results = new double[t];
        for (int i = 0; i < t; i++) {
            results[i] = calculateResults(n);
        }
    }
    private double calculateResults(int n) {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            int i = StdRandom.uniform(1, n + 1);
            int j = StdRandom.uniform(1, n + 1);
            if (!perc.isOpen(i, j)) {
                perc.open(i, j);
            }
        }
        return (double) perc.numberOfOpenSites() / (n * n);
    }
    public double mean() {
        return StdStats.mean(results);
    }
    public double stddev() {
        return StdStats.stddev(results);
    }
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(results.length));
    }
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(results.length));
    }
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("The Mean is-->" + percolationStats.mean());
        StdOut.println("The Standard Deviation is-->" + percolationStats.stddev());
        StdOut.println("The 95% confidence interval is-->" + percolationStats.confidenceLo() + ", "
                                   + percolationStats.confidenceHi());
    }
}

