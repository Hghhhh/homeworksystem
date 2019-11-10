package com.hgh.homeworksystem.util;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

public class SimHashUtil {

    private static int hashbits = 64;

    public static String simHash(String tokens) throws IOException {
        // 定义特征向量/数组
        int[] v = new int[hashbits];
        // 英文分词
        // StringTokenizer stringTokens = new StringTokenizer(this.tokens);
        // while (stringTokens.hasMoreTokens()) {
        // String temp = stringTokens.nextToken();
        // }
        // 1、中文分词，分词器采用 IKAnalyzer3.2.8 ，仅供演示使用，新版 API 已变化。
        StringReader reader = new StringReader(tokens);
        // 当为true时，分词器进行最大词长切分
        IKSegmentation ik = new IKSegmentation(reader, true);
        Lexeme lexeme = null;
        String word = null;
        String temp = null;
        while ((lexeme = ik.next()) != null) {
            word = lexeme.getLexemeText();
            // 注意停用词会被干掉
            // System.out.println(word);
            // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = hash(word);
            for (int i = 0; i < hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
                // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
                if (t.and(bitmask).signum() != 0) {
                    // 这里是计算整个文档的所有特征的向量和
                    // 这里实际使用中需要 +- 权重，比如词频，而不是简单的 +1/-1，
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }

        StringBuffer strSimHash = new StringBuffer();
        for (int i = 0; i < hashbits; i++) {
            // 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            if (v[i] >= 0) {
                strSimHash.append("1");
            }else{
                strSimHash.append("0");
            }
        }
        return strSimHash.toString();
    }

    private static BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    public static int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    /**
     * 将hashbits位的指纹分为（distance+1）块
     */
    public static List<BigInteger> subByDistance(String strSimHash, int distance) {
        // 分成几组来检查
        int numEach = hashbits / (distance + 1);
        List<BigInteger> characters = new ArrayList<>();

        StringBuffer buffer = new StringBuffer();

        int k = 0;
        for (int i = 0; i < strSimHash.length(); i++) {
            // 当且仅当设置了指定的位时，返回 true
            boolean sr = strSimHash.charAt(i) == '1';

            if (sr) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }

            if ((i + 1) % numEach == 0) {
                // 将二进制转为BigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }
        }

        return characters;
    }

}