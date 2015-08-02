/*-------------------------------------------------------------------------
 * Author:Yixin Luo
 * Written:27/1/2015
 * 
 * Function:This class is to model a percolation system,provide the APIs.
 * 
 *-----------------------------------------------------------------------*/
public class Percolation {
        //this is to mark whether it is open.
        private boolean[] status;
        //the input N value
        private int size;
        //the union-find structure
        private WeightedQuickUnionUF uf, ufBottom;
        public Percolation(int N)
        {
            if (N <= 0)
                 throw new java.lang.IllegalArgumentException(""
                  +"negative number!");
            //the sites need N*N and one for virtual top sites
            size = N;
            status = new boolean[N * N + 1];
            //create a UnioN-Find structure with size of N*N+1
            uf = new WeightedQuickUnionUF(N * N + 1);
            ufBottom = new WeightedQuickUnionUF(N * N + 2);
            status[0] = true; //the top is true
            for (int i = 1; i < N*N+1; i++)
            {
                //initialize the array status
                status[i] = false;
            }
        }
        private int xyToi(int i, int j)
        //change the row x and col y to the status[] index
        {
             if (i < 1 ||  i > size || j < 1 || j > size)
                 throw new java.lang.IndexOutOfBoundsException(""
                       +"x or y out of bound!");
             return (i-1)*size+j;
        }
        public void open(int i, int j)
        //to open the site
       {
           if (i < 1 || i > size || j < 1 || j > size)
                 throw new java.lang.IndexOutOfBoundsException(""
                  +"x or y out of bound!");
            if (!status[xyToi(i, j)])
            {
                status[xyToi(i, j)] = true;
                //connect virtual top site and first row sites
                if (i == 1)
                {     
                	uf.union(0, xyToi(i, j));
                	ufBottom.union(0, xyToi(i, j));
                }
                if(i==size)
                	ufBottom.union(size*size+1, xyToi(i, j));
                 //connect sites which is neighbor
                if (i < size && status[xyToi(i+1, j)])
                {
                     uf.union(xyToi(i, j), xyToi(i+1, j));
                     ufBottom.union(xyToi(i, j), xyToi(i+1, j));
                }
                if (i > 1 && status[xyToi(i-1, j)])
                {
                     uf.union(xyToi(i, j), xyToi(i-1, j));
                     ufBottom.union(xyToi(i, j), xyToi(i-1, j));
                }
                if (j < size && status[xyToi(i, j+1)])
                {
                     uf.union(xyToi(i, j), xyToi(i, j+1));
                     ufBottom.union(xyToi(i, j), xyToi(i, j+1));
                }
                if (j > 1 && status[xyToi(i, j-1)])
                {
                     uf.union(xyToi(i, j), xyToi(i, j-1));
                     ufBottom.union(xyToi(i, j), xyToi(i, j-1));
                }
            }
        }
        public boolean isOpen(int i, int j)
        // to judge whether the site is open
        {
        if (i < 1 || i > size || j < 1 || j > size)
                 throw new java.lang.IndexOutOfBoundsException(""
                +"x or y out of bound!");
        return status[xyToi(i, j)];
        }
        public boolean isFull(int i, int j)
        //judge whether the site connect to the top
        {
            if (i < 1 || i > size || j < 1 || j > size)
                  throw new java.lang.IndexOutOfBoundsException(""
                      +"x or y out of bound!");
             return uf.connected(0, xyToi(i, j));
        }
        public boolean percolates()
        //to judge percolates?
        {
              return ufBottom.connected(0, size*size+1);
         }
}
