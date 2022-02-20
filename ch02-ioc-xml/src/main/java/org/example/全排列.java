package org.example;

import java.io.*;
import java.util.Arrays;

public class 全排列 {
//    static long startTime = System.currentTimeMillis(); //获取开始时间
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    static final int N = 10;
    static int n, q[] = new int[N];
    static char[] ch;
    static boolean[] st = new boolean[N];

    public static void main(String[] args) throws IOException {
        String str = in.readLine();
        ch = str.toCharArray();
        n = ch.length;
        Arrays.sort(ch);
        dfs(0);
        out.flush();
//        long endTime = System.currentTimeMillis(); //获取结束时间
//        System.out.println("字符串<=9运行时间：" + (endTime - startTime) + "ms");
    }

    static void dfs(int u) throws IOException {
        if (u == n) {
            for (int i = 0; i < n; i++)
                out.write(q[i]);
            out.write('\n');
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!st[i]) {
                q[u] = ch[i];
                st[i] = true;
                dfs(u + 1);
                st[i] = false;
            }
        }
    }
}
