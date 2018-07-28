package utils.sqlUtil;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileReAndWrUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.*;

public class InsertSql {
    private static final Logger logger = LoggerFactory.getLogger(InsertSql.class);
    private static final int DATA_NOT_FOUND = 0;
    private static final int DATA_EMPTY = 1;
    private static final int DATA_OK = 2;

    static DataSource dataSource;
    public static Map jdbc;
    // 插入成功条数统计
    static Integer insertDataCount = 0;

    public static void main(String[] args){
        //获取jdbc连接
        getJdbc();
        // TODO:改为args参数
        String path = "C:\\Users\\Administrator\\Desktop\\新建文件夹";
       // String path = "C:\\Users\\Administrator\\Documents\\Tencent Files\\420964597\\FileRecv\\gash后台订单明细2015.5-2018.7\\7.19";
        String fileNameSuffix = "*.CSV"; //可为空

        File file = new File(path);
        if (file.isDirectory()){
            List<String> resultList = new ArrayList();
            FileReAndWrUtil.findFiles(path, fileNameSuffix, resultList);
            if (resultList.size() == 0) {
                logger.error("No File Fount->path:{}", path);
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    exe(resultList.get(i));
                }
            }
        }else if (file.isFile()){
            exe(path);
        }
    }

    private static void getJdbc() {
        jdbc = new HashMap();
        jdbc.put("driverClassName", "com.mysql.jdbc.Driver");
        jdbc.put("url", "jdbc:mysql://127.0.0.1:3306/sdk");
        jdbc.put("username", "root");
        jdbc.put("password", "123456");
        jdbc.put("connectionProperties", "characterEncoding=UTF-8;zeroDateTimeBehavior=convertToNull;useSSL=false");
        if (null != jdbc) {
            try {
                dataSource = DruidDataSourceFactory.createDataSource(jdbc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int query(String query) {
        Statement statement = null;
        Connection connection = null;
        int result = DATA_OK;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            long beginTime = System.currentTimeMillis();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String data = rs.getString(1);
                if (StringUtils.isEmpty(data)) {
                    result = DATA_EMPTY;
                } else {
                    result = DATA_OK;
                }
            } else {
                result = DATA_NOT_FOUND;
            }
//            logger.info("queryUseTime:{} sql:{} status:{}", System.currentTimeMillis() - beginTime, query, result);
        } catch (SQLException e) {
            logger.error("executeSqlError->data:{}", query, e);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return result;
    }

    private static Map execQuery(String query) {
        Statement statement = null;
        Connection connection = null;
        Map map = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            long beginTime = System.currentTimeMillis();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            String[] name = new String[count];
            for (int i = 0; i < count; i++) {
                name[i] = rsmd.getColumnName(i + 1);
            }
            while (rs.next()) {
                map = new HashMap();
                for (String s : name) {
                    map.put(s, rs.getString(s));
                }
            }
//            logger.info("queryUseTime:{} sql:{} status:{}", System.currentTimeMillis() - beginTime, query, result);
        } catch (Exception e) {
            logger.error("executeSqlError->data:{}", query, e);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return map;
    }

    private static int execSql(SQLBuilder value) {
        return execSql(value.build());
    }

    private static int execSql(String value) {
        if (null == dataSource) {
            return 0;
        }
        Statement statement = null;
        Connection connection = null;
        int row = 0;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            long beginTime = System.currentTimeMillis();
            row = statement.executeUpdate(value);
            logger.info("insertUseTime:{} row:{} sql:{}", System.currentTimeMillis() - beginTime, row, value);
        } catch (SQLException e) {
            logger.error("executeSqlError->data:{}", value, e);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return row;
    }

    /**
     * 对单个文件执行并插入数据表
     * @param path
     */
    public static void exe(String path){
        // 导入文件
        Set<String> set = FileReAndWrUtil.readFile(new File(path));
        if (set == null){
            logger.error("getFileDataError->file:{}", path);
            return;
        }
        logger.info("importFile->file:{}, line:{}", path, set.size());
        Iterator<String> iterator = set.iterator();
        // 开始执行
        Integer pay_type = 0;
        while (iterator.hasNext()) {
            String getOneLine = iterator.next();
            String[] getOneLineAttr = getOneLine.split(",");
            Integer pay_id = null;
            try {
                if (getOneLineAttr[2] == null || getOneLineAttr[2].contains("CP_TRANSACTION")){
                    continue;
                }
                String[] s = getOneLineAttr[2].split("P");
                pay_id = Integer.valueOf(s[0].replace("\"", ""));
            }catch (Exception e){
                logger.error("Exception->path:{} getOneLine:{} pay_id:{}", path, getOneLine, pay_id);
                continue;
            }
            if (pay_id == null){
                logger.error("payIdIsNull->path:{} getOneLine:{}", path, getOneLine);
                continue;
            }
//            //获取jdbc连接
//            getJdbc();
            // 数据库操作
            SQLBuilder sqlBuilderSelect = new SQLBuilder("select * from pay_map_gash where pay_id=" + pay_id);
            int status = query(sqlBuilderSelect.build());
            SQLBuilder sqlBuilderInsert = null;
            if (DATA_OK == status) { //已经存在
                logger.error("hasData->path:{} pay_id:{} getOneLine:{}", path, pay_id, getOneLine);
                continue;
            }
            else if (DATA_EMPTY == status) { //存在但是为空？
                logger.error("dataEmpty->path:{} pay_id:{}", path, pay_id);
                continue;
            }
            else if (DATA_NOT_FOUND == status){ //记录不存在
                sqlBuilderInsert = new SQLBuilder("insert into pay_map_gash(pay_id,pay_type)");
                sqlBuilderInsert.appendInt(pay_id).appendInt(pay_type);
            }
            int count = execSql(sqlBuilderInsert.build());
            if (1 != count) {
                logger.error("insertError->path:{} pay_id:{}, pay_type:{}, sql:{}", path, pay_id, pay_type, sqlBuilderInsert);
            }else {
//                insertDataCount++;
//                logger.info("插入成功->pay_id:{}, insertDataCount:{}", pay_id, insertDataCount);
            }
        }
    }

}
