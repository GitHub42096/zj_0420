package utils;

import java.io.*;
import java.lang.annotation.Repeatable;
import java.util.*;

public class FileReAndWrUtil {

    /**
     * 读取文件
     * @param file
     * @param charsetName "UTF-8" "GBK" ````
     * @return
     */
    public static Set<String> readFile(File file, String charsetName){
        Set<String> set = new HashSet<String>();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
            bufferedReader = new BufferedReader(inputStreamReader);
            String data = null;
            while ((data = bufferedReader.readLine()) != null){
                set.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    /**
     * 读取文件返回List
     * @param file
     * @param charsetName "UTF-8" "GBK" ````
     * @return
     */
    public static List<String> readFileRetList(File file, String charsetName){
        List<String> list = new LinkedList<String>();
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
            bufferedReader = new BufferedReader(inputStreamReader);
            String data = null;
            while ((data = bufferedReader.readLine()) != null){
                list.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    /**
     * 写出文件
     * @param file
     * @param set
     */
    public static void writeFile(File file, Set<String> set){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()){
                String str = iterator.next();
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            }
             catch (IOException e) {
                e.printStackTrace();
             }
        }
    }

    /**
     * 递归查找文件
     * @param baseDirName  查找的文件夹路径
     * @param targetFileName  需要查找的文件名 : 为空则查找所有文件
     * @param fileList  查找到的文件集合
     */
    public static void findFiles(String baseDirName, String targetFileName, List fileList) {

        File baseDir = new File(baseDirName);		// 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {	// 判断目录是否存在
            System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName = null;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if(tempFile.isDirectory()){
                findFiles(tempFile.getAbsolutePath(), targetFileName, fileList);
            }else if(tempFile.isFile()){
                tempName = tempFile.getName();
                if (targetFileName == null || targetFileName.equals("")){
                    fileList.add(tempFile.getAbsoluteFile().getPath());
                }else {
                    if(wildcardMatch(targetFileName, tempName)){
                        // 匹配成功，将文件名添加到结果集
                        fileList.add(tempFile.getAbsoluteFile().getPath());
                    }
                }
            }
        }
    }

    /**
     * 通配符匹配
     * @param pattern    通配符模式
     * @param str    待匹配的字符串
     * @return    匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                //通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1),
                            str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                //通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    //表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

    public static void main(String[] args){

//        // 读取文本文件
//        Set<String> set = FileReAndWrUtil.readFile(new File("C:\\Users\\Administrator\\Desktop\\201505.CSV"), "UTF-8");
//        System.out.println(set.size());
//        Iterator<String> iterator = set.iterator();
//        while (iterator.hasNext()) {
//            String getOneLine = iterator.next();
//            System.out.println(getOneLine);
//        }
//        System.out.println(set.size());


        // 读取文本文件返回List
        List<String> list = FileReAndWrUtil.readFileRetList(new File("C:\\Users\\Administrator\\Desktop\\gash-order-miss-fix.txt"), "UTF-8");
        System.out.println(list.size());
        for (String s:list) {
            System.out.println(s);
        }



//        // 递归查找文件
//        String path = "C:\\Users\\Administrator\\Documents\\Tencent Files\\420964597\\FileRecv";
//        String fileNameSuffix = ""; //可为空  *.CSV
//        File file = new File(path);
//        if (file.isDirectory()){
//            List<String> resultList = new ArrayList();
//            findFiles(path, fileNameSuffix, resultList);
//            if (resultList.size() == 0) {
//                System.out.println("No File Fount.");
//            } else {
//                System.out.println("fileCount: "+resultList.size());
//                for (int i = 0; i < resultList.size(); i++) {
//                    System.out.println(resultList.get(i));//显示查找结果。
//                }
//            }
//        }else if (file.isFile()){
//            System.out.println("文件");
//        }

    }



}
