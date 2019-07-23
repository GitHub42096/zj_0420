package utils.QQBoomUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;


/**
 * @author zj
 * @version 1.0
 * @description 使用很简单，有jdk环境，运行java代码，打开QQ或微信电脑端聊天窗口
 * 原理就是往电脑剪贴版里赋你想要发送的话，然后调用键盘事件ctrl+V粘贴到聊天窗口，最后enter进行发送，所以是乞丐版的
 * 转：https://blog.csdn.net/xiao_xiao_b/article/details/93619747
 * @create 2019/7/23 11:44
 **/
public class QQBoom {
    public static void main(String[] args) throws AWTException {
        String sentence = "从前有座山,山里有座庙,庙里有个老和尚和小和尚,和尚对小和尚说：";// 定义要发送的话
        Robot robot = new Robot();// 创建Robot对象
        robot.delay(3000);// 延迟三秒，主要是为了预留出打开窗口的时间，括号内的单位为毫秒
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        String[] authors = sentence.split("[,]");// 字符串根据,分割
        for (int j = 0; j < 50; j++) {//循环次数
            for (int i = 0; i < authors.length; i++) {
                String sentencet = authors[i];
                Transferable tText = new StringSelection(sentencet);
                clip.setContents(tText, null);
                // for (int j = 1; j <= 1; j++) {
                // 以下两行按下了ctrl+v，完成粘贴功能
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);

                robot.keyRelease(KeyEvent.VK_CONTROL);// 释放ctrl按键，像ctrl，退格键，删除键这样的功能性按键，在按下后一定要释放，不然会出问题。crtl如果按住没有释放，在按其他字母按键是，敲出来的回事ctrl的快捷键。
                robot.delay(500);// 延迟一秒再发送，不然会一次性全发布出去，因为电脑的处理速度很快，每次粘贴发送的速度几乎是一瞬间，所以给人的感觉就是一次性发送了全部。这个时间可以自己改，想几秒发送一条都可以
                robot.keyPress(KeyEvent.VK_ENTER);// 回车
                // }
            }
        }
    }
}
