class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        if(p.length()>s.length()){
            return new ArrayList<>();
        }
        List<Integer> response = new ArrayList<>();
        int[] lettersS = new int[26];
        int[] lettersP = new int[26];
        for(char c : p.toCharArray()){
            lettersP[c-'a']++;
        }
        for(int i = 0;i<p.length();i++){
            lettersS[s.charAt(i)-'a']++;
        }
        int left = 0;
        int right = p.length();
        if(Arrays.equals(lettersP,lettersS)){
                response.add(left);
        }
        while(right<s.length()){
            lettersS[s.charAt(right)-'a']++;
            lettersS[s.charAt(left)-'a']--;
            right++;
            left++;
            if(Arrays.equals(lettersP,lettersS)){
                response.add(left);
            }
        }
        return response;
    }
}
