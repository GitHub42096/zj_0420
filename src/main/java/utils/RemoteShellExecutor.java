package utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangjie [zhangjie02@yinhai.com]
 */
public class RemoteShellExecutor {
	private Connection conn;
	private String ip;
	private String username;
	private String password;
	private static final int TIME_OUT = 0;// 表示不超时

	/**
	 * 构造函数
	 *
	 * @param ip       远程ip
	 * @param username 远程机器用户名
	 * @param password 远程机器密码
	 */
	public RemoteShellExecutor(String ip, String username, String password) {
		this.ip = ip;
		this.username = username;
		this.password = password;
	}


	/**
	 * 登录
	 *
	 * @return
	 * @throws IOException
	 */
	private boolean login() throws IOException {
		conn = new Connection(ip, 22);
		conn.connect();
		return conn.authenticateWithPassword(username, password);
	}

	/**
	 * 执行脚本
	 *
	 * @param shell
	 * @return
	 * @throws Exception
	 */
	public int exec(String shell) throws Exception {
		int ret = -1;
		try {
			if (login()) {
				Session session = conn.openSession();
				session.execCommand(shell);
				session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
				ret = session.getExitStatus();
				System.out.println();
				InputStream in = session.getStdout();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024]; // 1KB
				//每次读取到内容的长度
				int len = -1;
				//<3>开始读取输入流中的内容
				while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
					baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
				}
				String content = baos.toString();
				System.out.println("111"+ content);

			} else {
				throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		try {
			RemoteShellExecutor executor = new RemoteShellExecutor("*******", "root", "*******");
			executor.exec("sh /u01/mysqlun.sh 5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

