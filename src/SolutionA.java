import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.IntConsumer;

public class SolutionA {
    public int numSplits(String s) {
        int[] left = new int[26];
        int[] right = new int[26];
        int ln = 0, rn = 0, result = 0;
        for (int i = 0; i < s.length(); i++) {
            right[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < right.length; i++) {
            if (right[i] > 0)
                rn++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (right[s.charAt(i) - 'a'] == 1) rn--;
            if (left[s.charAt(i) - 'a'] == 0) ln++;
            left[s.charAt(i) - 'a']++;
            right[s.charAt(i) - 'a']--;
            if (ln == rn) result++;
        }
        return result;
    }

    //    在 initial中选择任意子数组，并将子数组中每个元素增加 1 。
    public int minNumberOperations(int[] target) {
        int res = 0;

        return res;
    }


    int[] intbreak = new int[60];

    public int integerBreak(int n) {
        int maxr = 0;
        if (n == 1) return 1;
        if (n == 2) return 1;
        for (int i = 1; i <= n / 2; i++) {
            if (intbreak[i] == 0)
                intbreak[i] = integerBreak(i);
            if (intbreak[n - i] == 0)
                intbreak[n - i] = integerBreak(n - i);
            int max1 = Math.max(intbreak[i] * intbreak[n - i], intbreak[i] * (n - i));
            int max2 = Math.max(intbreak[n - i] * i, i * (n - i));
            int max3 = Math.max(max1, max2);
            maxr = Math.max(max3, maxr);
        }
        intbreak[n] = maxr;
        return maxr;
    }

    int[][] dirp = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int minlen = Integer.MAX_VALUE;
    int jiguan = 0;
    int unreachable_jaguan = 0;
    int[][] visitp;
    int[][] visitb;
    int pre = Integer.MAX_VALUE;
    HashMap<Point, Integer> hashMap = new HashMap<>();

    public class Point {
        int x;
        int y;
        int step;

        Point(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Point))
                return false;
            Point point = (Point) obj;
            return this.x == point.x && this.y == point.y;
        }

        @Override
        public int hashCode() {
            return this.x * this.y + this.x + this.y;
        }
    }

    public int minimalSteps(String[] maze) {
        int res = 0, startx = 0, starty = 0;
        visitp = new int[maze.length][maze[0].length()];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length(); j++) {
                if (maze[i].charAt(j) == 'M') {
                    visitb = new int[maze.length][maze[0].length()];
                    int blen = bfs_stone(i, j, 0, maze);
                    if (blen == -1)
                        unreachable_jaguan++;
                    else
                        hashMap.put(new Point(i, j, 0), blen);
                    jiguan++;
                } else if (maze[i].charAt(j) == 'S') {
                    hashMap.put(new Point(i, j, 0), Integer.MAX_VALUE);
                    startx = i;
                    starty = j;
                }
            }
        }
        if (jiguan == unreachable_jaguan && jiguan != 0) {
//            System.out.println("0");
            return -1;
        }
        visitp[startx][starty] = 1;
        cbdfs(startx, starty, startx, starty, 0, 0, maze, false);
        if (minlen == Integer.MAX_VALUE)
            return -1;
        res += minlen;
        return res;
    }

    //["...O.",
    // ".S#M.",
    // "..#T.",
    // "....."
//在什么地方取石头？
//    ["##TOO#O#",
//    "OO##O.S#",
//    "###.O###",
//    "#..O#O##"]
//    [".MM..",
//     "#..M.",
//      ".#..#",
//      "..O..",
//      ".S.OM",
//      ".#M#T",
//       "###..",
//       "....."]
    public void cbdfs(int prex, int prey, int x, int y, int k, int step, String[] maze, boolean flag) {
        if (maze[x].charAt(y) == 'T') {
            if (step < minlen && k == jiguan) {
                minlen = step;
            }
            return;
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dirp[i][0];
            int ny = y + dirp[i][1];
            if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze[0].length() && visitp[nx][ny] == 0 && maze[nx].charAt(ny) != '#') {
                int add = 0;
                int get_stone = 0;
                if (maze[nx].charAt(ny) == 'M') {
                    if (flag == false) {
                        int pre = hashMap.get(new Point(prex, prey, 0));
                        int cur = hashMap.get(new Point(nx, ny, 0));
                        if (pre < cur)
                            get_stone = pre;
                        else
                            get_stone = cur;
                    } else {
                        get_stone = 0;
                        flag = false;
                    }
                    add = 1;
                    prex = nx;
                    prey = ny;
                }
                if (maze[nx].charAt(ny) == 'O') {
                    flag = true;
                }
                visitp[nx][ny] = 1;
                cbdfs(prex, prey, nx, ny, k + add, step + 1 + get_stone * 2, maze, flag);
                visitp[nx][ny] = 0;
            }
        }
        return;
    }


    public int bfs_stone(int x, int y, int step, String[] maze) {
        Queue<Point> queue = new LinkedList();
        queue.add(new Point(x, y, 0));
        visitb[x][y] = 1;
        while (!queue.isEmpty()) {
            Point head = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = head.x + dirp[i][0];
                int ny = head.y + dirp[i][1];
                if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze[0].length() && maze[nx].charAt(ny) != '#' && visitb[nx][ny] == 0) {
                    if (maze[nx].charAt(ny) == 'O')
                        return head.step + 1;
                    visitb[nx][ny] = 1;
                    queue.add(new Point(nx, ny, head.step + 1));
                }
            }
        }
        return -1;
    }

    //    桶排序
    public int numMatchingSubseq(String S, String[] words) {
        int ans = 0;
        ArrayList<Node>[] heads = new ArrayList[26];
        for (int i = 0; i < 26; ++i)
            heads[i] = new ArrayList<Node>();

        for (String word : words)
            heads[word.charAt(0) - 'a'].add(new Node(word, 0));

        for (char c : S.toCharArray()) {
            ArrayList<Node> old_bucket = heads[c - 'a'];
            heads[c - 'a'] = new ArrayList<Node>();

            for (Node node : old_bucket) {
                node.index++;
                if (node.index == node.word.length()) {
                    ans++;
                } else {
                    heads[node.word.charAt(node.index) - 'a'].add(node);
                }
            }
            old_bucket.clear();
        }
        return ans;
    }


    class Node {
        String word;
        int index;

        public Node(String w, int i) {
            word = w;
            index = i;
        }
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int maxDiff = Integer.MIN_VALUE;

    public int maxAncestorDiff(TreeNode root) {
        treebfs(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return maxDiff;
    }

    public void treebfs(TreeNode root, int Max, int Min) {
        if (root == null)
            return;
        if (root.val > Max) {
            Max = root.val;
        }
        if (root.val < Min) {
            Min = root.val;
        }
        if (Math.abs(root.val - Max) > maxDiff)
            maxDiff = Math.abs(root.val - Max);
        if (Math.abs(root.val - Min) > maxDiff)
            maxDiff = Math.abs(root.val - Min);
        treebfs(root.left, Max, Min);
        treebfs(root.right, Max, Min);
    }

    public List<Integer> circularPermutation(int n, int start) {
        int k = 1;
        int tmp = start;
        List<Integer> list = new ArrayList<>();
        list.add(start);
        for (int i = 1; i <= (int) Math.pow(2, n - 1); i++) {
            tmp = tmp ^ k;
            list.add(tmp);
            k <<= 1;
        }
        return list;
    }

    public int findMagicIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i)
                return i;
        }
        return -1;
    }

    public int minSetSize(int[] arr) {
        int[] count = new int[100010];
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            count[arr[i]]++;
        }
        Arrays.sort(count);
        int result = 0;
        int t = len;
        for (int i = count.length - 1; i >= 0; i--) {
            if (count[i] != 0) {
                t -= count[i];
                result++;
            }
            if (t <= len / 2) return result;
        }
        return -1;
    }


    class FizzBuzz {
        private int n;
        private int i = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            synchronized (this) {
                while (i <= n) {
                    if (!(i % 3 == 0 && i % 5 != 0)) wait();
                    if (i > n) return;
                    if (i % 3 == 0 && i % 5 != 0) {
                        printFizz.run();
                        i++;
                        notifyAll();
                    }
                }
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            synchronized (this) {
                while (i <= n) {
                    if (!(i % 5 == 0 && i % 3 != 0)) wait();
                    if (i > n) return;
                    if (i % 5 == 0 && i % 3 != 0) {
                        printBuzz.run();
                        i++;
                        notifyAll();
                    }
                }
            }

        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            synchronized (this) {
                while (i <= n) {
                    if (!(i % 3 == 0 && i % 5 == 0)) wait();
                    if (i > n) return;
                    if (i % 3 == 0 && i % 5 == 0) {
                        printFizzBuzz.run();
                        i++;
                        notifyAll();
                    }
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            synchronized (this) {
                while (i <= n) {
                    if (!(i % 3 != 0 && i % 5 != 0)) wait();
                    if (i > n) return;
                    if (i % 3 != 0 && i % 5 != 0) {
                        printNumber.accept(i);
                        i++;
                        notifyAll();
                    }
                }
            }
        }
    }

    public int subarraySum(int[] nums, int k) {
        return 0;
    }

    public void flatten(TreeNode root) {

    }

    //    Kahn's algorithm
// L ← Empty list that will contain the sorted elements
// S ← Set of all nodes with no incoming edge
//
//while S is not empty do
//    remove a node n from S
//    add n to tail of L
//    for each node m with an edge e from n to m do
//    remove edge e from the graph
//        if m has no other incoming edges then
//    insert m into S
//
//if graph has edges then
//    return error   (graph has at least one cycle)
//else
//    return L   (a topologically sorted order)
//2,{1,0}
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] out = new int[numCourses];
//        HashSet<Integer> hashSet=new HashSet<>();
        int num = 0;
        Queue<Integer> queue = new LinkedList<>();
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            lists.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            out[prerequisites[i][1]]++;
            lists.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }
        for (int i = 0; i < numCourses; i++) {
            if (out[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int head = queue.poll();
            num++;
            for (int x : lists.get(head)) {
                out[x]--;
                if (out[x] == 0)
                    queue.add(x);
            }
        }
        if (num != numCourses)
            return false;
        return true;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    //    public ListNode reverse(ListNode head) {
//        ListNode p1 = head, tmp, newhead = null;
//        while (p1 != null) {
//            tmp = p1.next;
//            p1.next = newhead;
//            newhead = p1;
//            p1 = tmp;
//        }
//        return newhead;
//    }
//
//    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
//        ListNode p2 = headA, p3 = reverse(headB);
//        while (p2 != null || p3 != null) {
//            if (p2 == p3)
//                return p2;
//            p2 = p2.next;
//
//            p3 = p3.next;
//        }
//        headB=reverse(p3);
//        return null;
//    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA, p2 = headB;
        int k = 0;
        while (true) {
            if (p1 == null) {
                p1 = headB;
                k++;
            }
            if (p2 == null) {
                p2 = headA;
                k++;
            }
            if (k == 2)
                break;
            p1 = p1.next;
            p2 = p2.next;
        }
        while (p1 != null && p2 != null) {
            if (p1 == p2)
                return p1;
            p1 = p1.next;
            p2 = p2.next;
        }
        return null;
    }

//    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
////        if (headA == null || headB == null) return null;
////        ListNode pA = headA, pB = headB;
////        while (pA != pB) {
////            pA = pA == null ? headB : pA.next;
////            pB = pB == null ? headA : pB.next;
////        }
////        return pA;
////    }

//    pre mid

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0)
            return null;
        TreeNode root = new TreeNode(preorder[0]);
        if (preorder.length == 1)
            return root;
        int i = 0;
        for (; i < inorder.length; i++) {
            if (inorder[i] == preorder[0])
                break;
        }
//        System.arraycopy();
        root.left = buildTree(Arrays.copyOfRange(preorder, 1, i + 1), Arrays.copyOfRange(inorder, 0, i));
        root.right = buildTree(Arrays.copyOfRange(preorder, i + 1, preorder.length), Arrays.copyOfRange(inorder, i + 1, inorder.length));
        return root;
    }
//    public TreeNode buildTree(int[] preorder, int[] inorder) {
//        if (preorder == null || preorder.length == 0) {
//            return null;
//        }
//        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
//        int length = preorder.length;
//        for (int i = 0; i < length; i++) {
//            indexMap.put(inorder[i], i);
//        }
//        TreeNode root = buildTree(preorder, 0, length - 1, inorder, 0, length - 1, indexMap);
//        return root;
//    }
//
//    public TreeNode buildTree(int[] preorder, int preorderStart, int preorderEnd, int[] inorder, int inorderStart, int inorderEnd, Map<Integer, Integer> indexMap) {
//        if (preorderStart > preorderEnd) {
//            return null;
//        }
//        int rootVal = preorder[preorderStart];
//        TreeNode root = new TreeNode(rootVal);
//        if (preorderStart == preorderEnd) {
//            return root;
//        } else {
//            int rootIndex = indexMap.get(rootVal);
//            int leftNodes = rootIndex - inorderStart, rightNodes = inorderEnd - rootIndex;
//            TreeNode leftSubtree = buildTree(preorder, preorderStart + 1, preorderStart + leftNodes, inorder, inorderStart, rootIndex - 1, indexMap);
//            TreeNode rightSubtree = buildTree(preorder, preorderEnd - rightNodes + 1, preorderEnd, inorder, rootIndex + 1, inorderEnd, indexMap);
//            root.left = leftSubtree;
//            root.right = rightSubtree;
//            return root;
//        }
//    }


//    public int rob(TreeNode root) {
//        if(root==null)
//            return 0;
//
//        return  Math.max(Math.max(root.val+rob(root.left==null?null:root.left.left)+rob(root.right==null?null:root.right.right),root.val+rob(root.left==null?null:root.left.right)+rob(root.right==null?null:root.right.left)),
//                Math.max(root.val+rob(root.left==null?null:root.left.right)+rob(root.right==null?null:root.right.right),Math.max(root.val+rob(root.left==null?null:root.left.left)+rob(root.right==null?null:root.right.left),rob(root.right)+rob(root.left))));
//    }

//    class Solution {
//        Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
//        Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();
//
//        public int rob(TreeNode root) {
//            dfs(root);
//            return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
//        }
//
//        public void dfs(TreeNode node) {
//            if (node == null) {
//                return;
//            }
//            dfs(node.left);
//            dfs(node.right);
//            f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
//            g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) + Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
//        }
//    }

    public int rob(TreeNode root) {
        int[] rootStatus = dfs(root);
        return Math.max(rootStatus[0], rootStatus[1]);
    }

    public int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        int[] l = dfs(node.left);
        int[] r = dfs(node.right);
        int selected = node.val + l[1] + r[1];
        int notSelected = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
        return new int[]{selected, notSelected};
    }

    boolean same = false;

    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null || t == null)
            return false;
        if (s.val == t.val) {
            if (isSame(s, t))
                same = true;
        }
        isSubtree(s.left, t);
        isSubtree(s.right, t);
        return same;
    }

    public boolean isSame(TreeNode s, TreeNode t) {
        if ((s == null && t != null) || (s != null && t == null))
            return false;
        if (s == null && t == null)
            return true;
        else if (s.val == t.val) {
            if (isSame(s.left, t.left) && isSame(s.right, t.right)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    HashMap<String, Integer> rev = new HashMap<>();

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            rev.put(new StringBuffer(words[i]).reverse().toString(), i);
        }
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j <= words[i].length(); j++) {
                int m = words[i].length();
                int leftfind = findWord(word, j, m);
                int rightfind = findWord(word, 0, j);
                if (isPalindrome(word, 0, j) && leftfind != -1 && leftfind != i) {
                    lists.add(Arrays.asList(leftfind, i));
                }
                if (j != 0 && isPalindrome(word, j, m) && rightfind != -1 && rightfind != i) {
                    lists.add(Arrays.asList(i, rightfind));

                }
            }
        }
        return lists;
    }

    //[ )
    public boolean isPalindrome(String s, int start, int end) {
        for (int i = 0; i < (end - start) / 2; i++) {
            if (s.charAt(start + i) != s.charAt(end - i - 1)) {
                return false;
            }
        }
        return true;
    }


    // [ )
    public int findWord(String s, int start, int end) {
        return rev.getOrDefault(s.substring(start, end), -1);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        else if (p == null || q == null)
            return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public int[] fraction(int[] cont) {
        int[] result = frac(0, cont);
        System.out.println(result[0] + " " + result[1]);
        int g = gcd(result[1], result[0]);
        return new int[]{result[0] / g, result[1] / g};
    }

    public int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public int[] frac(int t, int[] cont) {
        if (cont.length - 1 == t)
            return new int[]{cont[t], 1};
        int[] k = frac(t + 1, cont);
        System.out.println(k[0] + " " + k[1]);
        int m = cont[t];
        return new int[]{k[0] * m + k[1], k[0]};
    }

    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0, right = letters.length - 1;
        int mid = left + (right - left) / 2;
        while (left < right) {
            if (target < letters[mid]) {
                right = mid;
            }
            if (target > letters[mid]) {
                left = mid + 1;
            } else {
                int index = mid + 1 < letters.length ? mid + 1 : 0;
                return letters[index];
            }
            mid = left + (right - left) / 2;
        }
        if (left >= letters.length)
            return letters[0];
        return letters[right];
    }

    public List<String> fizzBuzz(int n) {

        // ans list
        List<String> ans = new ArrayList<String>();

        // Hash map to store all fizzbuzz mappings.
        HashMap<Integer, String> fizzBizzDict =
                new HashMap<Integer, String>() {
                    {
                        put(3, "Fizz");
                        put(5, "Buzz");
                    }
                };

        for (int num = 1; num <= n; num++) {

            String numAnsStr = "";

            for (Integer key : fizzBizzDict.keySet()) {

                // If the num is divisible by key,
                // then add the corresponding string mapping to current numAnsStr
                if (num % key == 0) {
                    numAnsStr += fizzBizzDict.get(key);
                }
            }

            if (numAnsStr.equals("")) {
                // Not divisible by 3 or 5, add the number
                numAnsStr += Integer.toString(num);
            }

            // Append the current answer str to the ans list
            ans.add(numAnsStr);
        }

        return ans;
    }


    public int numUniqueEmails(String[] emails) {
        HashSet<String> set=new HashSet<>();
        for(String email:emails){
            int index=email.indexOf('@');
            String local=email.substring(0,index);
            String reset=email.substring(index);
            if(local.contains("+")){
                local=local.substring(0,local.indexOf('+'));
            }
            local=local.replaceAll("\\.","");
            set.add(local+reset);
        }
        return set.size();
    }
    public static void main(String[] args) {
        SolutionA solution = new SolutionA();
//      solution.numSplits("acbadbaada");
        String[] maze = {"S#O",
                "M.T",
                "M.."};
        String[] maze1 = {"...O.",
                ".S#M.",
                "..#T.",
                "....."};
        String[] maze3 = {".MM..", "#..M.", ".#..#", "..O..", ".S.OM", ".#M#T", "###..", "....."};
        System.out.println(solution.minimalSteps(maze3));
//            ListNode headA = new ListNode(1);
//            ListNode headB = headA;
//        solution.getIntersectionNode(headA, headB);
//            int[] a = {3, 2, 0, 2};
//            char[] letters = {'c', 'f', 'j'};
//        solution.fraction(a);
//            solution.nextGreatestLetter(letters, 'j');
    }
}
