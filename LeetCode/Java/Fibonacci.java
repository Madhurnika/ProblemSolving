class Solution {
    public int fib(int n) {
        int a=0;
        int b=1;
        int m;
        if(n==0)
            return 0;
        else if(n==1)
            return 1;
        for(int i=2;i<=n;i++){
                m=a+b;
                a=b;
                b=m;
        }
        return b;
    }
}
