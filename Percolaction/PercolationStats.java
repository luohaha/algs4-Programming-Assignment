/*****************************************
 * 
 * Author:Yixin Luo
 * Written:28/1/2015
 * 
 *Function:This class is to perform a series of computational experiments
 *
 *Execution:java PercolationStats N M
 *                              
 *
 **************************************/
public class PercolationStats {             
        //times of computation
        private double[]    sitesNumberPercolation; 
        //each computation's open site's number
        public PercolationStats(int N, int T)
        {
            if (N <= 0 || T <= 0)
                throw new java.lang.IllegalArgumentException(""
                        +"negative number!");
            sitesNumberPercolation = new double[T];
            for (int t = 0; t < T; t++)
            {
            	Percolation percolation = new Percolation(N);
                int count = 0; //to count the number of open sites 
                while (!percolation.percolates())
                {
                    int i = StdRandom.uniform(1, N+1);
                    //create a random number present the row i
                    int j = StdRandom.uniform(1, N+1);
                    //create a random number present the col j
                    if (!percolation.isOpen(i, j))
                    {
                        percolation.open(i, j);
                        count++; //we open a site
                    }
                }
                sitesNumberPercolation[t] = (double) count / (N * N);
            }
        }
        public double mean()
        //calculate the mean of threshold
        {
            return  StdStats.mean(sitesNumberPercolation);
        }
        public double stddev()
        //calculate the deviation 
        {
            return StdStats.stddev(sitesNumberPercolation);
        }
        private double halfInt()
        {
             return 1.96 * stddev() / Math.sqrt(sitesNumberPercolation.length);
        }
        public double confidenceLo()
        //calculation the low endpoint
        {
            return  mean() - halfInt();
        }
        public double confidenceHi()
        //calculation the high endpoint
        {
            return mean() + halfInt();
        }
        public static void main(String[] args)
        {
            PercolationStats ps;
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            ps = new PercolationStats(N, T);
            System.out.println("mean            =" + ps.mean());
            System.out.println("stddev          =" + ps.stddev());
            System.out.println("95% confidence interval =" 
            + ps.confidenceLo() + "," + ps.confidenceHi());
        }
}
