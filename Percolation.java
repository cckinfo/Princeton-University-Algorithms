import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public static void main(String[] args) {

    }
    private int n;
    private boolean[][] opened;
    private int numberOpened;
    private WeightedQuickUnionUF union;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0 ) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        opened = new boolean[n][n];
        numberOpened = 0;
        // union find data structure initialized with n elements 0 to n-1
        // 0 = virtual top, n*n-1 = virtual bottom, thus the +2
        union = new WeightedQuickUnionUF(n * n + 2);
    }

    // top left row is supposed to be (1|1), not (0|0), so we check the bounds
    public void checkBounds(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IndexOutOfBoundsException();
        }
    }
    public int xyTo1D(int row, int col) {
        checkBounds(row, col);
        return (row - 1) * this.n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        if (!(isOpen(row, col))) {
            opened[row - 1][col - 1] = true;
            numberOpened++;

            // if site on first row is opened, it's connected to the virtual top
            if (row == 1) {
                union.union(xyTo1D(row, col), 0);
            }
            // if site on the last row is opened, it's connected to the virtual bottom
            if (row == this.n) {
                union.union(xyTo1D(row, col), (n * n + 1));
            }
            // if site above / below is opened, connect to it
            if (row > 1 && isOpen(row - 1, col)) {
                union.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row < this.n && isOpen(row + 1, col)) {
                union.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            // if site left / right is opened, connect to it
            if (col > 1 && isOpen(row, col - 1)) {
                union.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col < this.n && isOpen(row, col + 1)) {
                union.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return opened[row - 1][col -1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return union.find(0) == union.find(xyTo1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(0) == union.find(n * n - 1);

    }

    // // test client (optional)
    // public static void main(String[] args)
}
