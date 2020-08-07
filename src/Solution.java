import jdk.dynalink.NamedOperation;
import jdk.nashorn.api.tree.Tree;

import javax.naming.InsufficientResourcesException;
import javax.print.attribute.EnumSyntax;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.RunnableFuture;


class Solution {
    //    public int minHeightShelves(int[][] books, int shelf_width) {
//
//    }
    public int minArray(int[] numbers) {
        int left = 0, right = numbers.length - 1;
        int border = numbers[numbers.length - 1];
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (numbers[mid] > border) {
                left = mid + 1;
            } else if (numbers[mid] < border) {
                right = mid;
            } else {
                right -= 1;
            }
        }
        return numbers[left];
    }

    //[]
    public void sortColors(int[] nums) {
        int i = 0, j = nums.length - 1;
        for (int k = 0; k < nums.length; k++) {
            if (k > j) {
                break;
            }
            if (nums[k] == 0) {
                int tmp = nums[i];
                nums[i] = nums[k];
                nums[k] = tmp;
                i++;
                if (nums[i] == 2)
                    k--;

            } else if (nums[k] == 2) {
                int tmp = nums[j];
                nums[j] = nums[k];
                nums[k] = tmp;
                j--;
                if (nums[j] == 0)
                    k--;
            }
        }
        return;
    }

//    public int maxProduct(int[] nums) {
//        int[] tmp = new int[nums.length];
//        int maxk = Integer.MIN_VALUE;
//        for (int i = 0; i < nums.length; i++)
//            for (int j = i; j < nums.length; j++) {
//                if (i == j)
//                    tmp[j] = nums[j];
//                else
//                    tmp[j] = tmp[j - 1] nums[j];
//                if (tmp[j] > maxk)
//                    maxk = tmp[j];
//            }
//        return maxk;
//    }

    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int n = 0;
        for (int i = 1; i < 5; i++) {
            if (nums[i] == nums[i - 1] && nums[i] != 0)
                return false;
            if (nums[i] == 0) n++;
        }
        if (n == 0) {
            if (nums[4] - nums[0] == 4)
                return true;
            else
                return false;
        } else if (n == 1) {
            int t = nums[4] - nums[1];
            if (t > 4)
                return false;
            else if (t <= 4) {
                return true;
            }
        } else if (n == 2) {
            int t = nums[4] - nums[2];
            if (t <= 4)
                return true;
            return false;
        } else if (n == 3) {
            int t = nums[4] - nums[3];
            if (t <= 4)
                return true;
            else
                return false;
        }
        return false;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    private int pathnum = 0;

    public int pathSum(TreeNode root, int sum) {
        HashMap<Integer, Integer> map = new HashMap<>();
        search(map, root, 0, sum);
        return pathnum;
    }

    public void search(HashMap<Integer, Integer> map, TreeNode root, int cur, int sum) {
        if (root == null)
            return;
        cur += root.val;
        if (cur == sum) {
            pathnum++;
            //System.out.println(root.val);
        }

        if (map.containsKey(cur - sum)) {
//            int k=map.get(cur-sum);
            pathnum += map.get(cur - sum);
            //System.out.println(root.val + "mid");
        }
        if (map.containsKey(cur)) {
            map.put(cur, 1);
        } else {
            map.put(cur, map.get(cur) + 1);
        }
        search(map, root.left, cur, sum);
        search(map, root.right, cur, sum);
        map.remove(cur);
    }


    int[][] dir = {{0, 1}, {1, 0}};
    int minsum = Integer.MAX_VALUE;
    int[][] visit;

    public int minPathSum(int[][] grid) {
        visit = new int[grid.length][grid[0].length];
        dfs(0, 0, grid[0][0], grid);
        return minsum;
    }

    public void dfs(int curx, int cury, int pathsum, int[][] grid) {
//        System.out.println(curx+": "+cury+": "+pathsum);
        if (pathsum > minsum)
            return;
        if (curx == grid.length - 1 && cury == grid[0].length - 1) {
            if (pathsum < minsum)
                minsum = pathsum;
            return;
        }
        for (int i = 0; i < dir.length; i++) {
            int nx = curx + dir[i][0];
            int ny = cury + dir[i][1];
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && visit[nx][ny] == 0) {
                visit[nx][ny] = 1;
                dfs(nx, ny, pathsum + grid[nx][ny], grid);
                visit[nx][ny] = 0;
            }
        }
    }

    int start, end;

    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = i; j < matrix[0].length - 2 * i - 1; j++) {
                start = i;
                end = j;
                myrotate(matrix[i][j], i, j, matrix);
            }
        }

    }

    public void myrotate(int cur, int m, int n, int[][] matrix) {
        int nm = n;
        int nn = matrix[0].length - m - 1;
        int tmp = matrix[nm][nn];
        matrix[nm][nn] = cur;
        if (nm == start && nn == end) {
            return;
        }
        myrotate(tmp, nm, nn, matrix);
        return;
    }

    //[
//        [ 5, 1, 9,11],
//        [ 2, 4, 8,10],
//        [13, 3, 6, 7],
//        [15,14,12,16]
//        ],
//
    int[] win = new int[1000];

    public boolean divisorGame(int N) {
        if (N == 1 || win[N] == -1)
            return false;
        else if (win[N] == 1)
            return true;
        for (int x = 1; x < N; x++) {
            if (N % x == 0) {
                if (win[N - x] != 0) {
                    if (win[N - x] == 1)
                        return false;
                    else
                        return true;
                } else {
                    if (divisorGame(N - x)) {
                        win[N - x] = 1;
                        return false;
                    } else {
                        win[N - x] = -1;
                        return true;
                    }
                }
            }
        }
        win[N] = 1;
        return true;
    }


    public int minimumLengthEncoding(String[] words) {
        String encoding = "";
        Arrays.sort(words,
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o2.length() - o1.length();
                    }
                });

        for (int i = 0; i < words.length; i++) {
            if (!encoding.contains(words[i])) {
                encoding += words[i] + "#";
            } else {
                int index = -1;
                boolean flag = false;
                while (index - 1 != -1) {
                    index = encoding.indexOf(words[i], index) + 1;
                    if (index != -1 && encoding.charAt(index + words[i].length() - 1) == '#') {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    encoding += "#" + words[i];
                }
            }

        }
        return encoding.length();
    }


    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int result = 0;
        while (!queue.isEmpty()) {
            TreeNode head = queue.poll();
            result = head.val;
            if (head.right != null)
                queue.add(head.right);
            if (head.left != null)
                queue.add(head.left);
        }
        return result;
    }

    public TreeNode convertBiNode(TreeNode root) {
        if (root == null)
            return null;
        if (root.left == null && root.right == null)
            return root;
        TreeNode head = convertBiNode(root.left);
        TreeNode tmp = head;
        while (tmp != null) {
            if (tmp.right == null)
                break;
            tmp = tmp.right;
        }
        TreeNode tail = convertBiNode(root.right);
        root.left = null;
        root.right = tail;
        if (tmp == null)
            head = root;
        else {
            tmp.right = root;
        }
        return head;
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort(arr2);
        int count = 0;
        for (int i = 0; i < arr1.length; i++) {
            int left = 0, right = arr2.length;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (arr1[i] > arr2[mid])
                    mid = left + 1;
                else if (arr1[i] < arr2[mid])
                    mid = right - 1;
            }
            if (arr2[left] != arr1[i]) {
                if (Math.abs(arr2[left] - arr1[i]) > 2 && Math.abs(arr2[left + 1 < arr2.length ? left + 1 : left] - arr1[i]) > 2 && Math.abs(arr2[left - 1 >= 0 ? left - 1 : left] - arr1[i]) > 2)
                    count++;
            }

        }
        return count;
    }

    public int longestSubsequence(int[] arr, int difference) {
        int longest = 0;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (hashMap.containsKey(arr[i] - difference)) {
                int k = hashMap.get(arr[i] - difference);
                hashMap.put(arr[i], k + 1);
                hashMap.remove(arr[i] - difference);
                if (k + 1 > longest) longest = k + 1;
            } else {
                hashMap.put(arr[i], 1);
                if (longest == 0) longest = 1;
            }
        }
        return longest;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        HashMap<Node, Node> hashMap = new HashMap<>();
        Node newhead = new Node(head.val);
        hashMap.put(head, newhead);
        Node p1 = newhead, p2 = head.next;
        while (p2 != null) {
            p1.next = new Node(p2.val);
            p1 = p1.next;
            hashMap.put(p2, p1);
            p2 = p2.next;

        }
        Node p3 = head, p4 = newhead;
        while (p3 != null) {
            p4.random = hashMap.get(p3.random);
            p3 = p3.next;
            p4 = p4.next;
        }
        return newhead;
    }

//   public boolean biSearch(int t,int[] arr){
//        int left=0,right=arr.length;
//        while (left<right) {
//            int mid = left + (right - left) / 2;
//            if (t > arr[mid])
//                mid = left + 1;
//            else if (t < arr[mid])
//                mid = right - 1;
//        }
//
//        if(arr[])
//   }


//    public void myFindValue(TreeNode root,int level,int pos){
//        if(root.left==null&&root.right==null){
//            if(level>max_level&&pos<=left_pos){
//
//            }
//        }
//            return ;
//    }

    public String findLongestWord(String s, List<String> d) {
        return "";
    }

    public boolean isMatch(String s, String p) {
        int ps = 0, pp = 0;
        boolean res = true;
        while (p.charAt(pp) == '*') {
            pp++;
        }
        while (ps < s.length() && pp < p.length()) {
            if (s.charAt(ps) == p.charAt(pp)) {
                ps++;
                pp++;
            }
            if (s.charAt(ps) != p.charAt(pp)) {
                if (p.charAt(pp) == '.') {
                    if (pp + 1 < p.length() && p.charAt(pp + 1) == '*') {

                    } else {
                        ps++;
                        pp++;
                    }
                } else if (p.charAt(pp) == '*') {
                    if (pp - 1 >= 0) {
                        char pre = p.charAt(pp - 1);
                        while (p.charAt(++pp) == '*') {
                        }
                        pp--;
                        if (pre == s.charAt(ps)) {
                            while (s.charAt(++ps) == pre) {
                            }
                            while (p.charAt(++pp) == pre) {
                            }
                            ;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    public String restoreString(String s, int[] indices) {
        char[] ch = new char[indices.length];
        String res = "";
        for (int i = 0; i < indices.length; i++) {
            ch[indices[i]] = s.charAt(i);
        }
        for (int i = 0; i < indices.length; i++) {
            res += ch[i];
        }
        return res;
    }

    public int minFlips(String target) {
        int count = 0;
        for (int i = 0; i < target.length(); i++) {
            if (target.charAt(i) == '1') {
                count++;
                while (i < target.length() && target.charAt(i) == '1') i++;
            }
        }
        if (count == 0) return 0;
        else if (target.charAt(target.length() - 1) == 1)
            return (count - 1) * 2 + 1;
        return count * 2;
    }

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */
    HashMap<Integer, Integer> left = new HashMap<>();
    HashMap<Integer, Integer> right = new HashMap<>();
    // int minval=20;

    public int countPairs(TreeNode root, int distance) {
        if (root == null) return 0;
        int res = 0;
        res += countPairs(root.left, distance) + countPairs(root.right, distance) + FindPairs(root, distance);
        return res;
    }

    public void dfs(TreeNode root, int k, int flag, int distance) {
        if (root == null)
            return;
        if (k >= distance) return;
        if (root.left == null && root.right == null) {
            if (flag == -1) {
                if (!left.containsKey(k))
                    left.put(k, 1);
                else
                    left.put(k, left.get(k) + 1);
            } else {
                if (!right.containsKey(k))
                    right.put(k, 1);
                else
                    right.put(k, right.get(k) + 1);
            }
            return;
        }
        dfs(root.left, k + 1, flag, distance);
        dfs(root.right, k + 1, flag, distance);
    }


    public int FindPairs(TreeNode root, int distance) {
        left.clear();
        right.clear();
        int count = 0;
        dfs(root.left, 1, -1, distance);
        dfs(root.right, 1, 1, distance);
        Set<Map.Entry<Integer, Integer>> entrysr = left.entrySet();
        Set<Map.Entry<Integer, Integer>> entrysl = right.entrySet();
        for (Map.Entry<Integer, Integer> entry1 : entrysr) {
            for (Map.Entry<Integer, Integer> entry2 : entrysl) {
                if (entry1.getKey() + entry2.getKey() <= distance)
                    count += entry1.getValue() * entry2.getValue();
            }

        }
        return count;
    }

    /*
    数量    删除  减少
     1       1     1
     2       2
     */

//    public class Pair{
//        int number,delete;
//        double rate;
//        Pair(int number,int delete,double rate){
//            this.number=number;
//            this.delete=delete;
//            this.rate=rate;
//        }
//    }
//
//    public int getLengthOfOptimalCompression(String s, int k) {
//        String encoding="";
//        int[]   statistic=new int[101];
//        for(int i=0;i<s.length();i++) {
//            int count = 0;
//            char first = s.charAt(i);
//            while (s.charAt(i) == first) {
//                count++;
//                i++;
//            }
//            statistic[count]++;
//            i--;
//        }
//        int len=statistic[1]*1+statistic[100]*4;
//        for(int i=2;i<10;i++){
//            len+=statistic[i]*2;
//        }
//        for(int i=11;i<100;i++){
//            len+=statistic[i]*3;
//        }
//
//        Queue<Pair> queue=new PriorityQueue<>(new Comparator<Pair>() {
//            @Override
//            public int compare(Pair o1, Pair o2) {
//                if(o1.rate-o2.rate<0)
//                    return -1;
//                else if(o1.rate==o2.rate){
//                    return o1.delete-o2.delete;
//                }
//                else
//                    return 1;
//            }
//        });
//
//        for(int i=1;i<101;i++){
//            for(int j=1;j<=i;j++){
//                double delete=i,dec=j;
//                if(i==1&&j==1){
//                    queue.add(new Pair(i,j,dec/delete));
//                }
//                else if(i>=2&&i<=9){
//                    if(j==i-1) {
//                        delete=j;
//                        dec=1;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }else if(j==i){
//                        delete=j;
//                        dec=2;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }
//                }else if(i>=10&&i<=99){
//                    if(j>=i-9&&j<=i-2){
//                        delete=j;
//                        dec=1;
//                        queue.add(new Pair(i,j,dec/delete));
//                    } else if (j == i - 1) {
//                        delete=j;
//                        dec=2;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }else if(j==i){
//                        delete=j;
//                        dec=3;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }
//                }else if(i==100){
//                    if(j>=1&&j<=90){
//                        delete=j;
//                        dec=1;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }else if(j>=91&&j<=98){
//                        delete=j;
//                        dec=2;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }else if(j==99){
//                        delete=j;
//                        dec=3;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }else{
//                        delete=j;
//                        dec=4;
//                        queue.add(new Pair(i,j,dec/delete));
//                    }
//                }
//            }
//        }
//
//        int tmp=k,res=len;
//        HashMap<Integer,ArrayList<Pair>> map=new HashMap<>();
//        while(tmp>0){
//            Pair head=queue.poll();
//            int beflen=head.number;
//            int del=head.delete;
//            while(statistic[beflen]>0&&tmp-del>=0){
//                len-=del;
//                tmp-=del;
//                statistic[beflen]--;
//                statistic[beflen-del]++;
//            }
//        }
//
//    }

    public int getLengthOfOptimalCompression(String s, int k) {
        // dp[pos][k][c][size] represent solution when handling first pos character,
        // deleting k character, where last deleted character is c
        // and the number of char c being deleted is size
        return dfs(s, 0, k, 'a', 0, new Integer[s.length() + 1][k + 1][26][101]);
    }

    private int dfs(String s, int pos, int k, char c, int size, Integer[][][][] dp) {
        if (pos == s.length()) return 0;
        if (dp[pos][k][c - 'a'][size] != null) return dp[pos][k][c - 'a'][size];

        int ans = 101;
        // if delete
        if (k > 0) {
            ans = Math.min(ans, dfs(s, pos + 1, k - 1, c, size, dp));
        }

        // if not delete
        if (s.charAt(pos) == c) {
            // special case
            if (size == 0 || size == 1 || size == 9 || size == 99) {
                ans = Math.min(ans, dfs(s, pos + 1, k, c, size + 1, dp) + 1);
            } else {
                ans = Math.min(ans, dfs(s, pos + 1, k, c, size + 1, dp));
            }
        } else {
            ans = Math.min(ans, dfs(s, pos + 1, k, s.charAt(pos), 1, dp) + 1);
        }
        return dp[pos][k][c - 'a'][size] = ans;
    }


    /**
     * Your WordDictionary object will be instantiated and called as such:
     * WordDictionary obj = new WordDictionary();
     * obj.addWord(word);
     * boolean param_2 = obj.search(word);
     */

//    这个周赛国服第一的答案，代码非常简洁。
//    dp[i][j]表示从前i个字符中最多选择j个字符进行删除。
//    如果删除字符i，则此时dp[i][j] = dp[i-1][j-1].
//    如果保留字符i, 则此时后续尽量选择保留与字符i相同的字符。这个dp确实不好理解，但是仔细思考一下，最优解应该是包含在里面的。
//    Cpp
//    const int INF = 0x3f3f3f3f;
//    class Solution {
//        public:
//        inline int len(int k){
//            if(k <= 1) return 0;
//            else if(k > 1 && k < 10) return 1;
//            else if(k >= 10 && k < 100) return 2;
//            else return 3;
//        }
//
//        int getLengthOfOptimalCompression(string s, int k) {
//            int n = s.size();
//            vector<vector<int>> dp(n+1,vector<int>(k+2,INF));
//            dp[0][0] = 0;
//
//            for(int i = 1; i <= n; ++i){
//                for(int j = 0; j <= k && j <= i; ++j){
//                    if(j < k) dp[i][j+1] = min(dp[i][j+1],dp[i-1][j]);
//                    int same = 0;
//                    int del = 0;
//                    for(int m = i; m <= n; ++m){
//                        if(s[m-1] == s[i-1]) same++;
//                        else del++;
//                        if(j + del <= k){
//                            dp[m][j+del] = min(dp[m][j+del],len(same) + 1 + dp[i-1][j]);
//                        }else{
//                            break;
//                        }
//                    }
//                }
//            }
//
//            return dp[n][k];
//        }
//    };
    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        return Integer.max(maxDepth(root.right) + 1, maxDepth(root.left) + 1);
    }


    public class WordDictionary {
        public class DictNode {
            boolean isEnd;
            char aChar;
            DictNode[] children;

            DictNode(char aChar) {
                this.aChar = aChar;
                children = new DictNode[26];
            }
        }


        DictNode root;

        public void myaddWord(String word, DictNode root) {
            if (word.length() == 0)
                return;
            char ch = word.charAt(0);
            if (root.children[ch - 'a'] == null) {
                root.children[ch - 'a'] = new DictNode(ch);
            }
            root.children[ch - 'a'].isEnd = true;
            myaddWord(word.substring(1), root.children[ch - 'a']);

        }

        public boolean mysearch(String word, DictNode root) {
            if (root == null)
                return false;
            char ch = word.charAt(0);
            if (word.length() == 1) {
                if (root.children[ch - 'a'] != null && root.children[ch - 'a'].isEnd == true)
                    return true;
                else if (ch == '.') {
                    for (int i = 0; i < root.children.length; i++) {
                        if (root.children[i] != null && root.children[i].isEnd == true)
                            return true;
                    }
                    return false;
                } else
                    return false;
            }
            if (ch == '.') {
                for (int i = 0; i < root.children.length; i++) {
                    if (mysearch(word.substring(1), root.children[i])) {
                        return true;
                    }
                }
                return false;
            } else if (root.children[ch - 'a'] != null) {
                return mysearch(word.substring(1), root.children[ch - 'a']);
            } else {
                return false;
            }
        }

        /**
         * Initialize your data structure here.
         */
        public WordDictionary() {
            root = new DictNode('0');
        }

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            myaddWord(word, root);
        }

        /**
         * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            if (word.length() == 0)
                return true;
            return mysearch(word, root);
        }
    }


    public int rangeBitwiseAnd(int m, int n) {
        if (m == n) return m;
        int res = 0, i = m;
        if (m % 2 == 1) i = m + 1;
        res = i;
        for (; i <= n && i >= 0; i += 2) {
            int revbit = i ^ 0xffffffff;
            if (revbit >= m && revbit <= n && revbit != i) return 0;
            res &= i;
            if (res == 0) return 0;
        }
        if (m % 2 == 1) res &= m;
        return res;
    }

    class BSTIterator {
        Queue<Integer> queue;

        private void fun(TreeNode root) {
            if (root == null) return;
            fun(root.left);
            queue.add(root.val);
            fun(root.right);
        }

        public BSTIterator(TreeNode root) {
            fun(root);
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            return queue.poll();
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            if (queue.isEmpty())
                return false;
            else
                return true;
        }
    }


//
//    public int numberOf2sInRange(int n) {
//        int res=0;
//    }

    public int countOdds(int low, int high) {
        if (low % 2 == 1 && high % 2 == 1)
            return (high - low) / 2 + 1;
        else if (low % 2 == 0 && high % 2 == 0)
            return (high - low) / 2;
        else return (high - low - 1) / 2 + 1;
    }

    //超时
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1000000007;
        int len = arr.length, pre = 0, res = 0;
        System.out.println(arr.length);
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (i == j) {
                    pre = arr[i] % 2;
                    if (pre == 1) {
                        res = (res % MOD + pre % MOD) % MOD;
                        // System.out.println(i+" "+j);
                    }
                } else {
                    if ((pre + arr[j]) % 2 == 1) {
                        res = (res % MOD + (pre + arr[j]) % 2) % MOD;
                        // System.out.println(i+" "+j);
                    }
                    pre = (pre + arr[j]) % 2;
                }
            }
        }
        return res;
    }

//'aacaba'
//    public int numSplits(String s) {
//        int[] left = new int[26];
//        int[] right = new int[26];
//        int ln = 0, rn = 0, result = 0;
//        for (int i = 0; i < s.length(); i++) {
//            right[s.charAt(i) - 'a']++;
//        }
//        for (int i = 0; i < right.length; i++) {
//            if (right[i] == 1)
//                rn++;
//        }
//        for (int i = 1; i < s.length() - 1; i++) {
//            left[s.charAt(i) - 'a']++;
//            right[s.charAt(i) - 'a']--;
//            if (right[s.charAt(i) - 'a'] == 0) rn--;
//            if (left[s.charAt(i) - 'a'] == 1) ln++;
//            if (ln == rn) result++;
//        }
//        return result;
//    }

}