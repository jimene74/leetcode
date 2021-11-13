FLOYD WARSHALL
All pairs shortest path
Find the City With the Smallest Number of Neighbors at a Threshold Distance

class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dp = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for(int i=0;i<n;i++){
            dp[i][i]=0;
        }
        for(int[] edge : edges){
            dp[edge[0]][edge[1]] = edge[2];
            dp[edge[1]][edge[0]] = edge[2];
        }
        
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j =0;j<n;j++){
                    if(dp[i][k]==Integer.MAX_VALUE || dp[k][j]==Integer.MAX_VALUE){
                        continue;
                    }
                    if(dp[i][k]+dp[k][j]<dp[i][j]){
                        dp[i][j] = dp[i][k]+dp[k][j];
                    }
                }
            }
        }
        
        
        int city = n-1;
        int minCount = Integer.MAX_VALUE;
        for(int i =0;i<n;i++){
            int count = 0;
            for(int j =0;j<n;j++){
                if(dp[i][j]<=distanceThreshold){
                    count++;
                }
            }
            if(count<=minCount){
                city = i;
                minCount = count;
            }
            
        }
        return city;
    }
}

PRIMS
Minimum spanning tree
1584. Min Cost to Connect All Points

class Solution {
    public int minCostConnectPoints(int[][] points) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]); //pq by distance
        Map<Integer, List<int[]>> map =new HashMap<>();
        for(int k =0;k<points.length;k++){
            map.put(k,new ArrayList<>());
        }
        HashSet<Integer> visited = new HashSet<>(); //keep track of visited
        for(int i =0; i<points.length;i++){
            for(int j =i+1 ;j<points.length;j++){ //[distance , destination]
                map.get(i).add(new int[]{Math.abs(points[i][0]-points[j][0])+Math.abs(points[i][1]-points[j][1]),j});
                map.get(j).add(new int[]{Math.abs(points[j][0]-points[i][0])+Math.abs(points[j][1]-points[i][1]),i});
            }
        }
        
        pq.add(new int[]{0,0});
        int count = 0;
        while(visited.size()<points.length){
            
            int[] curr = pq.poll();
            if(visited.contains(curr[1])){ //if we visited skip it
                continue;
            }
            visited.add(curr[1]);
            count = count + curr[0]; //adding shortest connection that connect to new node since we are skipping visited above
            for(int[] connections : map.get(curr[1])){
                if(!visited.contains(connections[1])){
                    pq.add(connections);
                }
            }
        }
        
        return count;
    }
}

BELLMAN FORD
Single source shortest path negative weights are allowed at most k stops

787. Cheapest Flights Within K Stops

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] ports = new int[n];
        Arrays.fill(ports, Integer.MAX_VALUE); //distances like dijkstra
        ports[src] = 0;
        for(int i = 0;i<k+1;i++){ //k+1 time (at most)
            int[] temp = ports.clone(); //temp clone ports(this is biggest difference with bellman ford)
            for(int[] flight : flights){ //go thru flights
                if(ports[flight[0]]==Integer.MAX_VALUE){ //if infinity ignora
                    continue;
                }
                if(temp[flight[1]]>ports[flight[0]] + flight[2]){ //if temp[curent destination] > ports[current source] + dist
                    temp[flight[1]] = ports[flight[0]] + flight[2];
                }
            }
            ports = temp.clone(); //ports become temp
        }
        if(Math.abs(ports[dst])==Integer.MAX_VALUE){ //if distance[dst] is infinity then we never reached
            return -1;
        }
        System.out.println(Arrays.toString(ports));
        return ports[dst];
    }
}


DIJKSTRA
Single source path non-negative

743. Network Delay Time

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        
        //Adj list
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        for(int i=1;i<=n;i++){
            adjList.put(i-1,new ArrayList<>());
        }
        for(int[] time : times){
            adjList.get(time[0]-1).add(new int[]{time[1]-1,time[2]});
        }
        
        //distances
        int[] distance = new int[n];
        for(int a=0;a<distance.length;a++){
            distance[a] = Integer.MAX_VALUE;
        }
        int src = k-1;
        distance[src] = 0;
        
        //heap
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]- b[0]); //pq by dist
        pq.add(new int[]{0,k-1});
        
        while(!pq.isEmpty()){
            int[] curr = pq.poll(); //[distance , source]
            
            for(int[] i : adjList.get(curr[1])){ //get all destination nodes from source
                if(curr[0]+i[1]  <distance[i[0]]){ //if current distance + new node distance < distance array val
                    distance[i[0]] = curr[0]+i[1];
                    pq.add(new int[]{distance[i[0]],i[0]}); //add this dist and new node
                }
            }
        }
        
        System.out.println(Arrays.toString(distance));
        
        int response = 0;
        for(int l : distance){
            if(l==Integer.MAX_VALUE){
                return -1;
            }
            response = Math.max(response, l);
        }
        
        return response;
    }
}

KHANS TOPOLOGICAL SORT
(See 'Alien Dictionary' in my git)
