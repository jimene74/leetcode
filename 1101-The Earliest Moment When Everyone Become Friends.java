class Solution {
    int countJoins;
    public int earliestAcq(int[][] logs, int n) {
        Arrays.sort(logs, (a,b)->a[0]-b[0]);
        UnionFind uf = new UnionFind(n);
        countJoins = 0;
        for(int[] log : logs){
            uf.union(log[1],log[2]);
            //System.out.println(Arrays.toString(uf.parents));
            if(countJoins==n-1){
                return log[0];
            }
        }
        return -1;
    }
    
    class UnionFind{
        int[] parents;
        UnionFind(int size){
            parents = new int[size];
            for(int i = 0;i<size;i++){
                parents[i] = i;
            }
        }
        
        public int find(int i){
            while(parents[i]!=i){
                i = parents[i];
            }
            return i;
        }
        
        public void union(int i, int j){
            int parentI = find(i);
            int parentJ = find(j);
            if(parentI!=parentJ){
                parents[parentJ] = parentI;
                countJoins++;
            }
        }
        
    }
}
