package utils.bisai;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author: 张杰
 * @description: excel工具类
 **/
public class ExcelUtil {

    private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0.00");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");
    // 日期格式
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    // 字符编码
    private static final String CHARSET = "utf-8";

    /**
     * 读取excel, 按照字段长度读取数据长度
     *
     * @param inputStream 文件输入流
     * @param path 文件路径
     * @param fieldNames 字段名列表，以,分隔（若为空则读取excel 表头的第一行作为fieldNames）
     * @param domainClassName 要转化为对象的类名（若为空则返回list<map>）
     * @param titleRows 标题行，参数必须大于0
     * @return ArrayList<Object>
     * @throws Exception
     */
    public static ArrayList<Object> readExcel(InputStream inputStream, String path, String fieldNames, String domainClassName, int titleRows){
        if (inputStream == null || StringUtils.isEmpty(path) || titleRows < 0) {
            log.error("参数有误");
          //  throw new AppException("参数有误");
        }
        // 处理xls
        if (path.matches("^.+\\.(?i)(xls)$")) {
            return readExcel2003RetMap(inputStream, fieldNames, domainClassName, titleRows);
        }
        // 处理xlsx
        else if (path.matches("^.+\\.(?i)(xlsx)$")) {
            return readExcel2007RetMap(inputStream, fieldNames, domainClassName, titleRows);
        }
        // 处理csv
        else if (path.matches("^.+\\.(?i)(csv)$")) {
            return readCSV(inputStream, fieldNames, domainClassName, titleRows);
        }
        // 处理txt
        else if (path.matches("^.+\\.(?i)(txt)$")) {
            return readCSV(inputStream, fieldNames, domainClassName, titleRows);
        } else {
            log.error("格式不支持");
           // throw new AppException("格式不支持");
        }
        return null;
    }

    /**
     * 读取csv格式文件
     *
     * @param inputStream 文件流
     * @param fieldNames 字段名列表，以,分隔
     * @param domainClassName 要转化为对象的类名
     * @param titleRows 标题行
     * @return ArrayList<Object>
     */
    private static ArrayList<Object> readCSV(InputStream inputStream, String fieldNames, String domainClassName, int titleRows){
        ArrayList<Object> resList = new ArrayList<Object>();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HashMap<String, Object> rowMap = null;
        int errorRow = 1;
        try {
            inputStreamReader = new InputStreamReader(inputStream, CHARSET);
            bufferedReader = new BufferedReader(inputStreamReader);
            String data = null;
            String[] fieldName = null;
            // 第一行标识
            boolean isOneLine = true;
            // 定义读取行数
            int rowNum = 1;
            while ((data = bufferedReader.readLine()) != null) {
                rowMap = new HashMap<String, Object>();
                // 读取fieldName
                if (StringUtils.isEmpty(fieldNames) && isOneLine){
                    fieldName = data.split(",");
                    isOneLine = false;
                    continue;
                }else if (isOneLine){
                    fieldName = fieldNames.split(",");
                    isOneLine = false;
                }
                // 读取数据
                if (rowNum > titleRows){
                    String[] values = data.split(",");
                    if (StringUtils.isEmpty(domainClassName)){
                        for (int i = 0; i < fieldName.length; i++) {
                            if (i < values.length){
                                rowMap.put(fieldName[i], values[i]);
                            }else {
                                rowMap.put(fieldName[i], "");
                            }
                        }
                        resList.add(rowMap);
                        continue;
                    }
                    Object tmpObj = ReflectUtil.generatorObjectFromArray(fieldName, values, domainClassName);
                    resList.add(tmpObj);
                }
                rowNum ++;
                errorRow = rowNum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resList;
    }


    /**
     * 读取2003版excel返回list<map>
     *
     * @param inputStream 文件输入流
     * @param fieldNames 字段名列表，以,分隔
     * @param domainClassName 要转化为对象的类名
     * @param titleRows 标题行
     * @return ArrayList<Object>
     */
    @SuppressWarnings({"all"})
    public static ArrayList<Object> readExcel2003RetMap(InputStream inputStream, String fieldNames, String domainClassName, int titleRows) {
        ArrayList<Object> resList = new ArrayList<Object>();
        HashMap<String, Object> rowMap = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        Object value = null;
        HSSFWorkbook wb = null;
        int errorRow = 1;
        try {
            wb = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rowsSize = sheet.getPhysicalNumberOfRows();
            // 定义读取行的数量及读取的开始行号
            int readRowCount = 0, readRowNum = sheet.getFirstRowNum();
            String[] fieldName;
            // 导入字段没有填的时候，默认读取excel 表头的第一行作为fieldNames
            if(StringUtils.isEmpty(fieldNames)){
                int firstRow = sheet.getFirstRowNum();
                int fieldNameLenth = sheet.getRow(firstRow).getLastCellNum();
                fieldName = new String[fieldNameLenth];
                for (int titleCell = 0; titleCell < fieldNameLenth; titleCell++) {
                    fieldName[titleCell] = sheet.getRow(firstRow).getCell(titleCell).toString();
                }
            }else{
                fieldName = fieldNames.split(",");
            }
            if (titleRows >= 0){
                readRowCount += titleRows;
                readRowNum += titleRows;
            }
            // 读取数据
            for (int rowNum = readRowNum, rowCount = readRowCount; rowCount < rowsSize; rowNum++) {
                errorRow = rowCount + 1;
                rowMap = new HashMap<String, Object>();
                row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                rowCount++;
                int lastRowSize = fieldName.length; // row.getLastCellNum();
                // 遍历一行中的数据
                for (int colNum = row.getFirstCellNum(); colNum < lastRowSize; colNum++) {
                    cell = row.getCell(colNum);
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        // 判断是否是该行中最后一个单元格
                        if (colNum != lastRowSize) {
                            rowMap.put(fieldName[colNum], "");
                        }
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            // value = numOfImport(cell);
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                value = String.valueOf(cell.getRichStringCellValue().getString());
                                // value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = LocalDateUtil.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), DATE_FORMAT);
                            }
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            value = Boolean.valueOf(cell.getBooleanCellValue());
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            value = "";
                            break;
                        default:
                            value = cell.toString();
                    }
                    rowMap.put(fieldName[colNum], value);
                }
                // domainClassName不为空
                if (!StringUtils.isEmpty(domainClassName)){
                    int i = 0;
                    String [] values = new String[fieldName.length];
                    for (String field : fieldName){
                        if (rowMap.get(field) == null){
                            values[i] = "";
                        }else {
                            values[i] = rowMap.get(field).toString();
                        }
                        i++;
                    }
                    Object tmpObj = ReflectUtil.generatorObjectFromArray(fieldName,
                            values, domainClassName);
                    resList.add(tmpObj);
                    continue;
                }
                resList.add(rowMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resList;
    }


    /**
     * 读取2007版excel返回list<map>
     *
     * @param inputStream 文件输入流
     * @param fieldNames 字段名列表，以,分隔
     * @param domainClassName 要转化为对象的类名
     * @param titleRows 标题行
     * @return ArrayList<Object>
     */
    @SuppressWarnings({"all"})
    public static ArrayList<Object> readExcel2007RetMap(InputStream inputStream, String fieldNames, String domainClassName, int titleRows){
        ArrayList<Object> resList = new ArrayList<Object>();
        HashMap<String, Object> rowMap = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        Object value = null;
        XSSFWorkbook wb = null;
        int errorRow = 1;
        try {
            wb = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            int rowsSize = sheet.getPhysicalNumberOfRows();
            // 定义读取行的数量及读取的开始行号
            int readRowCount = 0, readRowNum = sheet.getFirstRowNum();
            String[] fieldName;
            // 导入字段没有填的时候，默认读取excel 表头的第一行作为fieldNames
            if(StringUtils.isEmpty(fieldNames)){
                int firstRow = sheet.getFirstRowNum();
                int fieldNameLenth = sheet.getRow(firstRow).getLastCellNum();
                fieldName = new String[fieldNameLenth];
                for (int titleCell = 0; titleCell < fieldNameLenth; titleCell++) {
                    fieldName[titleCell] = sheet.getRow(firstRow).getCell(titleCell).toString();
                }
            }else{
                fieldName = fieldNames.split(",");
            }
            if (titleRows >= 0){
                readRowCount += titleRows;
                readRowNum += titleRows;
            }
            // 读取数据
            for (int rowNum = readRowNum, rowCount = readRowCount; rowCount < rowsSize; rowNum++) {
                errorRow = rowCount + 1;
                rowMap = new HashMap<String, Object>();
                row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                rowCount++;
                int lastRowSize = fieldName.length; //row.getLastCellNum();
                // 遍历一行中的数据
                for (int colNum = row.getFirstCellNum(); colNum < lastRowSize; colNum++) {
                    cell = row.getCell(colNum);
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        // 判断是否是该行中最后一个单元格
                        if (colNum != lastRowSize) {
                            rowMap.put(fieldName[colNum], "");
                        }
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                value = String.valueOf(cell.getRichStringCellValue().getString());
                                //value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = LocalDateUtil.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()), DATE_FORMAT);
                            }
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            value = Boolean.valueOf(cell.getBooleanCellValue());
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            value = "";
                            break;
                        default:
                            value = cell.toString();
                    }
                    rowMap.put(fieldName[colNum], value);
                }
                // domainClassName不为空
                if (!StringUtils.isEmpty(domainClassName)){
                    int i = 0;
                    String [] values = new String[fieldName.length];
                    for (String field : fieldName){
                        if (rowMap.get(field) == null){
                            values[i] = "";
                        }else {
                            values[i] = rowMap.get(field).toString();
                        }
                        i++;
                    }
                    Object tmpObj = ReflectUtil.generatorObjectFromArray(fieldName,
                            values, domainClassName);
                    resList.add(tmpObj);
                    continue;
                }
                resList.add(rowMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resList;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\admin\\Desktop\\test\\xls\\test200.xls");
        File file07 = new File("C:\\Users\\admin\\Desktop\\test\\xlsx\\test500.xlsx");
        File fileCsv = new File("C:\\Users\\admin\\Desktop\\test50.csv");
        //ArrayList<Object> result = ExcelUtil.readExcel(new FileInputStream(file07), file07.getPath(), "idCard,name,sex,birthday,type,account,email,field1,field2,field3", "com.yinhai.modules.fileimport.domain.demo.TestDomain");
        ArrayList<Object> result = ExcelUtil.readExcel(
                new FileInputStream(fileCsv),
                fileCsv.getPath(),
                "idCard,name,sex,birthday,type,account,email,field1,field2,field3",
                "com.example.demo.bisai.TestDomain"
                , 0);
        System.out.println("开始");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        System.out.println("总条数"+ result.size());

    }
}