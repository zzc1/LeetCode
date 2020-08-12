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

    public Node cloneGraph(Node node) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node head = queue.poll();
            if (!hashMap.containsKey(head.val))
                hashMap.put(head.val, new ArrayList<>());
            for (Node node1 : head.neighbors) {
                queue.add(node1);
                hashMap.get(head.val).add(node1.val);
            }
        }
        Set<Map.Entry<Integer, ArrayList<Integer>>> entrySet = hashMap.entrySet();
        Set<Integer> set=hashMap.keySet();
        for(Integer x:set){
            nodeHashMap.put(x,new Node(x,new ArrayList<>()));
        }
        for(Map.Entry<Integer, ArrayList<Integer>> entry:entrySet){
                Node nodet=nodeHashMap.get(entry.getKey());
                for(Integer integer:entry.getValue())
                    nodet.neighbors.add(nodeHashMap.get(integer));
        }
        return nodeHashMap.get(node.val);
    }


    int[][] direct={{1,0},{-1,0},{0,1},{0,-1}};
    int disMe=0;
    int disGh=Integer.MAX_VALUE;
    HashSet<Point> hashSet=new HashSet<>();
    int[][] myghosts;
    class Point{
        int x;
        int y;
        Point(int x,int y){
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object obj) {
            Point p=(Point)obj;
            return this.x==p.x&&this.y==p.y;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
    public boolean escapeGhosts(int[][] ghosts, int[] target) {
        disMe=targetDis(0,0,target[0],target[1]);
        myghosts=ghosts;
        for(int i=0;i<ghosts.length;i++){
            disGh=Math.min(disGh,targetDis(ghosts[i][0],ghosts[i][1],target[0],target[1]));
        }
        if(disGh<=disMe)
            return false;
        else{
            return dfs(0,0,0,target[0],target[1]);
        }
    }

    public boolean dfs(int x,int y,int k,int targetx,int targety){
        if(k>=disGh)
            return false;
        if(x==targetx&&y==targety)
            return true;
        for(int i=0;i<4;i++){
            int curx=x+direct[i][0];
            int cury=y+direct[i][1];
            Point point=new Point(curx,cury);
            if(!canGhostArriave(curx,cury,k+1)&&!hashSet.contains(point)){
                hashSet.add(point);
                dfs(curx,cury,k+1,targetx,targety);
                hashSet.remove(point);
            }
        }
        return false;
    }

    public boolean canGhostArriave(int x,int y,int k){
        for(int i=0;i<myghosts.length;i++){
            int dis=targetDis(myghosts[i][0],myghosts[i][1],x,y);
            if(dis==k)
                return false;
        }
        return true;
    }

    public int targetDis(int x,int y,int targetx,int targety){
        return Math.abs(targetx-x)+Math.abs(targety-y);
    }


    public static void main(String[] args) {
        SolutionB solutionB = new SolutionB();
        for (int i = 1; i <= 8; i++)
            System.out.println(solutionB.kthGrammar(4, i));
    }
}
