package utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScpUtils {

    private Logger log = LoggerFactory.getLogger(getClass());

    private String host;
    private int port;
    private String username;
    private String password;

    private static ScpUtils instance;

    public static synchronized ScpUtils getInstance(String host, int port, String username, String password) {
        if (instance == null) {
            instance = new ScpUtils(host, port, username, password);
        }
        return instance;
    }

    public ScpUtils(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * 获取连接
     * @return
     */
    private Connection getConneciton(String host, int port) {
        Connection conn = new Connection(host, port);
        try {
            conn.connect();
            // 用户名密码认证
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                log.error("AuthenticateFail,host:{},port:{},username:{},password:{}", host, port, username, password);
                return null;
            }
            return conn;
        } catch (IOException e) {
            StringBuilder errLog = new StringBuilder();
            errLog.append("getConnectionError-->{")
                    .append("host=").append(host)
                    .append(",port=").append(port)
                    .append("}");
            log.error(errLog.toString(), e);
        }
        return null;
    }

    /**
     * 上传文件
     * @param localFile
     * @param remoteDirectory
     */
    public void putFile(String localFile, String remoteDirectory) {
        long start = System.currentTimeMillis();
        String sendTime = new SimpleDateFormat().format(new Date());
        log.info("SendFile,localFile:{},remoteDir:{},sendtime:{}", localFile, remoteDirectory, sendTime);
        Connection conn = getConneciton(host, port);
        if (conn == null) {
            log.error("GetConnectionFail,host:{},port:{},username:{},password:{}", host, port, username, password);
            return;
        }
        try {
            SCPClient client = new SCPClient(conn);
            client.put(localFile, remoteDirectory);
        } catch (Exception e) {
            StringBuilder errLog = new StringBuilder();
            errLog.append("SendFileError-->{")
                    .append("localFile=").append(localFile)
                    .append(",remoteDirectory=").append(remoteDirectory)
                    .append(",host=").append(host)
                    .append(",port=").append(port)
                    .append("}");
            log.error(errLog.toString(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        long end = System.currentTimeMillis();
        String endTime = new SimpleDateFormat().format(new Date());
        log.info("SendFileSuccess...localFile:{},remoteDir:{},endtime:{},use:{}", localFile, remoteDirectory, endTime, (end-start)/1000);
    }

    /**
     * 下载文件
     * @param remoteFile
     * @param localTargetDirectory
     */
    public void getFile(String remoteFile, String localTargetDirectory) {
        long start = System.currentTimeMillis();
        String sendTime = new SimpleDateFormat().format(new Date());

        log.info("DownloadFile,remoteFile:{},localDir:{},sendtime:{}", remoteFile, localTargetDirectory, sendTime);
        Connection conn = getConneciton(host, port);
        if (conn == null) {
            log.error("GetConnectionFail,host:{},port:{},username:{},password:{}", host, port, username, password);
            return;
        }
        try {
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, localTargetDirectory);
        } catch (Exception e) {
            StringBuilder errLog = new StringBuilder();
            errLog.append("DownloadFileError-->{")
                    .append("remoteFile=").append(remoteFile)
                    .append(",localDirectory=").append(localTargetDirectory)
                    .append(",host=").append(host)
                    .append(",port=").append(port)
                    .append("}");
            log.error(errLog.toString(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        long end = System.currentTimeMillis();
        String endTime = new SimpleDateFormat().format(new Date());
        log.info("DownloadFileSuccess...remoteFile:{},localDir:{},endtime:{},use:{}", remoteFile, localTargetDirectory, endTime, (end-start)/1000);
    }

    public static void main(String[] args) {
        String host = "*****";
        int port = 22;
        String username = "root";
        String password = "***";

        ScpUtils scpUtils = ScpUtils.getInstance(host, port, username, password);
        scpUtils.putFile("C:\\Users\\admin\\Desktop\\技术.txt", "/usr/test");
    }
}
