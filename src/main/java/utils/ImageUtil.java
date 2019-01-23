package utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/** 图片base64嘛相互转
 * @author liuzhenxing
 * @version 1.0
 * @date 2017/6/6 14:45
 */
public class ImageUtil {
    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
          *通过图片base64流判断图片等于多少字节
          *image 图片流
          */
        public static Integer imageSize(String image){
         String str=image; // 1.需要计算文件流大小，首先把头部的data:image/png;base64,（注意有逗号）去掉。
        Integer equalIndex= str.indexOf("=");//2.找到等号，把等号也去掉
        if(str.indexOf("=")>0) {
         str=str.substring(0, equalIndex);
        }
        Integer strLength=str.length();//3.原来的字符流大小，单位为字节
         Integer size=strLength-(strLength/8)*2;//4.计算后得到的文件流大小，单位为字节
         return size;
         }


    /**
     * 示例
     */
    public static void main(String[] args) {
        String strImg = getImageStr("C:\\Users\\admin\\Desktop\\a.png");
        Set<String> set = new HashSet<String>();
        set.add(strImg);
        //FileReAndWrUtil.writeFile(new File("C:\\Users\\admin\\Desktop\\ab.txt"),set );
        System.out.println(strImg.length());
        System.out.println(imageSize(strImg));
         generateImage(strImg,"C:\\Users\\admin\\Desktop\\113.png");

    }
}