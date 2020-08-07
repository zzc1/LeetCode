import java.util.*;

public class IsNumber {
    public class Resource {
        private int c, r, h, id;

        Resource(int C, int R, int H, int id) {
            this.c = C;
            this.r = R;
            this.h = H;
            this.id = id;
        }

        public int getC() {
            return c;
        }

        public int getR() {
            return r;
        }

        public int getH() {
            return h;
        }

        public int getId() {
            return id;
        }
    }

    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int[] res = new int[requirements.length];
        int C = 0, R = 0, H = 0;
        Queue<Resource> resources = new PriorityQueue<>(new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                if (o1.getC() != o2.getC()) {
                    return o1.getC() - o2.getC();
                } else if (o1.getR() != o2.getR()) {
                    return o1.getR() - o2.getR();
                } else {
                    return o1.getH() - o2.getH();
                }

            }
        });
        for (int i = 0; i < requirements.length; i++) {
            Resource addre = new Resource(requirements[i][0], requirements[i][1], requirements[i][2], i);
            resources.add(addre);
        }
        for (int i = 0; i < increase.length; i++) {
            C += increase[i][0];
            R += increase[i][1];
            H += increase[i][2];
            while (true) {
                Resource re = resources.peek();
                if (C >= re.getC() && R >= re.getR() && H >= re.getH()) {
                    res[re.getId()] = i+1;
                    resources.poll();
                }else {
                    break;
                }
            }
        }
        while(!resources.isEmpty()){
            res[resources.poll().getId()]=-1;
        }
        for(int i=0;i<res.length;i++){
            System.out.println(res[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        IsNumber isNumber = new IsNumber();
        int[][] increase = {{2, 8, 4}, {2, 5, 0}, {10, 9, 8}};
        int[][] requirements = {{2, 11, 3}, {15, 10, 7}, {9, 17, 12}, {8, 1, 14}};
        isNumber.getTriggerTime(increase, requirements);

    }
}

