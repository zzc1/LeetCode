import jdk.dynalink.NamedOperation;

import java.util.*;

public class SolutionB {
    public int kthGrammar(int N, int K) {
        ArrayList<Integer> list = new ArrayList<>();
        int t = K;
        for (int i = 0; i < N; i++) {
            list.add(t);
            t = t % 2 == 0 ? t / 2 : (t + 1) / 2;
        }
        int re = 0;
        for (int i = 0; i < list.size(); i++) {
            int k = list.get(i);
            if (re == 0) {
                re = k % 2 == 0 ? 1 : 0;
            } else {
                re = k % 2 == 0 ? 0 : 1;
            }
        }
        return re;
    }

    // Definition for a Node.
    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    HashMap<Integer, ArrayList<Integer>> hashMap;
    HashMap<Integer, Node> nodeHashMap;
    HashSet<Integer> isvi;

    public Node cloneGraph(Node node) {
        if (node == null)
            return null;
        Queue<Node> queue = new LinkedList<>();
        hashMap = new HashMap<>();
        nodeHashMap = new HashMap<>();
        isvi = new HashSet<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node head = queue.poll();
            isvi.add(head.val);
            if (!hashMap.containsKey(head.val))
                hashMap.put(head.val, new ArrayList<>());
            for (Node node1 : head.neighbors) {
                if (!isvi.contains(node1.val)) {
                    queue.add(node1);
                    isvi.add(node1.val);
                    hashMap.get(head.val).add(node1.val);
                }
            }
        }
        Set<Map.Entry<Integer, ArrayList<Integer>>> entrySet = hashMap.entrySet();
        Set<Integer> set = hashMap.keySet();
        for (Integer x : set) {
            nodeHashMap.put(x, new Node(x, new ArrayList<>()));
        }
        for (Map.Entry<Integer, ArrayList<Integer>> entry : entrySet) {
            Node nodet = nodeHashMap.get(entry.getKey());
            for (Integer integer : entry.getValue()) {
                nodet.neighbors.add(nodeHashMap.get(integer));
                nodeHashMap.get(integer).neighbors.add(nodet);
            }
        }
        return nodeHashMap.get(node.val);
    }


    int[][] direct = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int disMe = 0;
    int disGh = Integer.MAX_VALUE;
    HashSet<Point> hashSet = new HashSet<>();
    int[][] myghosts;

    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Point p = (Point) obj;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return x * y + 16 * x + 21 * y;
        }
    }

    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        disMe = targetDis(0, 0, target[0], target[1]);
        myghosts = ghosts;
        for (int i = 0; i < ghosts.length; i++) {
            disGh = Math.min(disGh, targetDis(ghosts[i][0], ghosts[i][1], target[0], target[1]));
        }
        if (disGh <= disMe)
            return false;
        else {
            return dfs(0, 0, 0, target[0], target[1]);
        }
    }

    public boolean dfs(int x, int y, int k, int targetx, int targety) {
        if (k >= disGh)
            return false;
        if (x == targetx && y == targety)
            return true;
        for (int i = 0; i < 4; i++) {
            int curx = x + direct[i][0];
            int cury = y + direct[i][1];
            Point point = new Point(curx, cury);
            if (Math.abs(curx) <= 10000 && Math.abs(cury) <= 10000 && !canGhostArriave(curx, cury, k + 1) && !hashSet.contains(point)) {
                hashSet.add(point);
                dfs(curx, cury, k + 1, targetx, targety);
                hashSet.remove(point);
            }
        }
        return false;
    }

    public boolean canGhostArriave(int x, int y, int k) {
        for (int i = 0; i < myghosts.length; i++) {
            int dis = targetDis(myghosts[i][0], myghosts[i][1], x, y);
            if (dis == k)
                return false;
        }
        return true;
    }

    public int targetDis(int x, int y, int targetx, int targety) {
        return Math.abs(targetx - x) + Math.abs(targety - y);
    }


    public boolean isPossibleDivide(int[] nums, int k) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]))
                map.put(nums[i], map.get(nums[i]) + 1);
            else
                map.put(nums[i], 1);
        }
        Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        int[][] count = new int[set.size()][2];
        int j = 0;
        for (Map.Entry<Integer, Integer> entry : set) {
            count[j][0] = entry.getKey();
            count[j][1] = entry.getValue();
            j++;
        }
        for (int i = 0; i < j; i++) {
            int start = count[i][0];
            int len = count[i][1];
            int m = 0;
            int nexti = 0;
            boolean flag = false;
            if (len == 0)
                continue;
            while (m < k - 1) {
                int cur = i + m + 1;
                if (cur >= j)
                    return false;
                int curn = count[cur][0];
                int curlen = count[cur][1];
                if (curn != start + m + 1)
                    return false;
                else {
                    if (curlen < len)
                        return false;
                    else {
                        count[cur][1] -= len;
                        if (count[cur][1] != 0 && flag == false) {
                            flag = true;
                            nexti = cur;
                        }
                    }
                }
                m++;
            }
            if (flag == true)
                i = nexti - 1;
        }
        return true;
    }


    public boolean isBalanced(TreeNode root) {
        return height(root) >= 0;
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        } else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }


    //      Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //      Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode sortedListToBST(ListNode head) {
        ListNode lead = head;
        List<Integer> list = new ArrayList<>();
        while (lead != null) {
            list.add(lead.val);
            lead = lead.next;
        }
        return createBST(0, list.size() - 1, list);
//        return null;
    }

    public TreeNode createBST(int start, int end, List<Integer> list) {
        if (start == end)
            return new TreeNode(list.get(start));
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = createBST(start, mid - 1, list);
        root.right = createBST(mid + 1, end, list);
        return root;
    }


    public int countSubstrings(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int left = i, right = i;
            while (left >= 0 && right < s.length()) {
                if (s.charAt(left) == s.charAt(right)) {
                    res++;
                    left--;
                    right++;
                } else {
                    break;
                }
            }
        }
        return res;
    }

    TreeNode cloTarget;

    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        dfsTree(original, cloned, target);
        return cloTarget;
    }

    public void dfsTree(TreeNode original, TreeNode cloned, TreeNode target) {
        if (original == null)
            return;
        if (original == target) {
            cloTarget = cloned;
            return;
        }
        dfsTree(original.left, cloned.left, target);
        dfsTree(original.right, cloned.right, target);
    }


//    public class Codec {
//
//        // Encodes a URL to a shortened URL.
//        public String encode(String longUrl) {
//
//        }
//
//        // Decodes a shortened URL to its original URL.
//        public String decode(String shortUrl) {
//
//        }
//    }

    int minDepth = Integer.MAX_VALUE;

    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        preOr(root, 1);
        return minDepth;
    }

    public void preOr(TreeNode root, int k) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            if (k < minDepth) {
                minDepth = k;
            }
            return;
        }
        preOr(root.left, k + 1);
        preOr(root.right, k + 1);
    }


    public boolean repeatedSubstringPattern(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }

    List<List<Integer>> subs_incr;
    HashSet<List<Integer>> set;

    public List<List<Integer>> findSubsequences(int[] nums) {
        subs_incr = new ArrayList<>();
        set = new HashSet<>();
        findSub_dfs(0, nums, new ArrayList<>());
        return subs_incr;
    }


    public void findSub_dfs(int k, int[] nums, List<Integer> list) {
        if (!set.contains(list) && list.size() > 1) {
            List<Integer> curlist=new ArrayList<>(list);
            subs_incr.add(curlist);
            set.add(curlist);
        }
        if(k==nums.length)
            return;
        int len = list.size();
        if (len == 0 || nums[k] >= list.get(len - 1)) {
            //findSub_dfs(k + 1, nums, list);
            list.add(nums[k]);
            findSub_dfs(k + 1, nums, list);
            list.remove(len);
            findSub_dfs(k + 1, nums, list);
        }
        return;
    }


    public static void main(String[] args) {
        SolutionB solutionB = new SolutionB();
//        for (int i = 1; i <= 8; i++)
//            System.out.println(solutionB.kthGrammar(4, i));
        int[] nums = {100,90,80,70,60,50,60,70,80,90,100};
        //    System.out.println(solutionB.isPossibleDivide(nums, 3));
        List<List<Integer>> subs_incr = solutionB.findSubsequences(nums);
        for (List<Integer> list : subs_incr) {
            for (Integer i : list) {
                System.out.print(i + " ");
            }
            System.out.print("\n");
        }

    }


}
