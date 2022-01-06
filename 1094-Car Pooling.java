class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        int count = 0;
        HashMap<Integer, Integer> destinations = new HashMap<>();
        HashMap<Integer, Integer> arrivals = new HashMap<>();
        int last = 0;
        for(int[] trip : trips){
            destinations.putIfAbsent(trip[2], 0);
            destinations.put(trip[2], destinations.get(trip[2])+trip[0]);
            last = Math.max(last,trip[2]);
            arrivals.putIfAbsent(trip[1], 0);
            arrivals.put(trip[1], arrivals.get(trip[1])+trip[0]);
        }
        for(int i =0;i<=last;i++){
            if(arrivals.containsKey(i)){
                count += arrivals.get(i);
            }
            if(destinations.containsKey(i)){
                count -= destinations.get(i);
            }
            if(count>capacity){
                return false;
            }
        }
        return true;
    }
}
