package com.hdu.sample;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 reproduction of MAT https://github.com/Naplues/MAT/
 paper "How far have we progressed in identifying self-admitted technical debts? A comprehensive empirical study"
 https://dl.acm.org/doi/10.1145/3447247
 */
public class MAT {
    private String[] keywords = {"todo", "hack", "fixme", "xxx"};
    private int isTD = 1;
    private int notTD = 0;

    public MAT(){

    }

    // predict whether the comment is TD or not
    public int classify(String comment){
        ArrayList<String> tokens = splitToTokens(comment);
        for (String token: tokens) {
            for (String keyword: keywords) {
                if (token.startsWith(keyword) || token.endsWith(keyword)) {
                    if (token.contains("xxx") && !token.equals("xxx")) {
                        return notTD;
                    }else{
                        return isTD;
                    }
                }
            }
        }
        return notTD;
    }

    // split comment to tokens
    private ArrayList<String> splitToTokens(String comment){
        ArrayList<String> words = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(comment);

        while (st.hasMoreTokens()){
            String word = filter(st.nextToken());
            if (2 < word.length() && word.length() < 20){
                words.add(word);
            }
        }
        return words;
    }

    /*
     Convert upper case letters to lower case letters,
     remove punctuations, etc.
     */
    private String filter(String word){
        String res = "";
        for (int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z'){
                res += c;
            }
        }
        return res.toLowerCase();
    }

    public static void testForAnt(){
        MAT m = new MAT();
        try {
            File file1 = new File("E:\\DownLoad\\SATD\\repos\\MAT-master\\dataset\\data--Ant.txt");
            FileReader fr = new FileReader(file1);
            BufferedReader br = new BufferedReader(fr);

            File file2 = new File("result--Ant.txt");
            FileWriter fw = new FileWriter(file2);
            BufferedWriter bw = new BufferedWriter(fw);

            String tmp = null;
            while ((tmp = br.readLine()) != null){
                bw.append(m.classify(tmp)+"\r\n");
            }
            br.close();
            fr.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // simple usage
    public static void main(String[] args) {
        MAT m = new MAT();
        String comment = "// hacked ignored attributes in a different NS ( maybe store them ? )";
        int res = m.classify(comment);
        System.out.println(res);
    }
}
