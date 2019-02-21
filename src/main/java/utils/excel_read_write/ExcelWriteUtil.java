package utils.excel_read_write;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * excel导出工具类
 */
public class ExcelWriteUtil {

//    private static Logger log = LoggerFactory.getLogger(ExcelWriteUtil.class);
//
//    /**
//     * 导出
//     * @param title 标题集合
//     * @param fields 字段集合
//     * @param data 数据 支持List<Map<String, Object>>  List<Class>
//     * @param fileName 文件名
//     * @param type 文件类型
//     * @param response response
//     * @throws Exception
//     */
//    public static void writeExcel(List<String> title, List<String> fields, List<Object> data, String fileName, String type, HttpServletResponse response){
//        if (data == null || fields == null) {
//            return;
//        }
//        // 文件名为空，则默认为exportData
//        if (StringUtils.isEmpty(fileName)){
//            fileName = "exportData";
//        }
//        // 文件类型为空，则默认csv
//        if (StringUtils.isEmpty(type)){
//            type = "csv";
//        }
//        // 处理xls
//        if ("xls".equals(type)) {
//            writeExcel2003(title, fields, data, fileName, response);
//        }
//        // 处理xlsx
//        else if ("xlsx".equals(type)) {
//            writeExcel2007(title, fields, data, fileName, response);
//        }
//        // 处理csv
//        else if ("csv".equals(type)) {
//            writeCSV(title, fields, data, fileName, type, response);
//        }
//        // 处理txt
//        else if ("txt".equals(type)) {
//            writeCSV(title, fields, data, fileName, type, response);
//        }
//        else {
//            log.error("导出格式不支持");
//            return;
//        }
//    }
//
//    /**
//     * 导出文件
//     * @param titles
//     * @param fields
//     * @param data
//     * @param fileName
//     * @param type
//     * @param response
//     * @throws IOException
//     */
//    public static void writeCSV( List<String> titles, List<String> fields, List<Object> data, String fileName, String type, HttpServletResponse response) {
//        OutputStream out = null;
//        OutputStreamWriter outputStreamWriter = null;
//        BufferedWriter bufferedWriter = null;
//        try {
//            response.setContentType("application/" + type + ";charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment; filename="+ fileName +"." + type);
//            out = response.getOutputStream();
//            outputStreamWriter = new OutputStreamWriter(out, "utf-8");
//            bufferedWriter = new BufferedWriter(outputStreamWriter);
//            // 写入标题
//            if (titles != null){
//                for(String title : titles){
//                    bufferedWriter.write(title);
//                    bufferedWriter.write(",");
//                }
//                // 换行
//                bufferedWriter.write("\r\n");
//            }
//            // 写内容
//            if (data != null){
//                Field[] classFields = new Field[0];
//                for(Object obj : data){
//                    if (obj instanceof Map){
//                        Map<String, Object> map = (Map<String, Object>) obj;
//                        for(String field : fields){
//                            bufferedWriter.write(map.get(field).toString());
//                            bufferedWriter.write(",");
//                        }
//                        //写完一行换行
//                        bufferedWriter.write("\r\n");
//                    }else {
//                        //利用反射获取所有字段
//                        if (classFields == null || classFields.length == 0){
//                            classFields = obj.getClass().getDeclaredFields();
//                        }
//                        for(String field : fields){
//                            for(Field classField : classFields){
//                                //设置字段可见性
//                                classField.setAccessible(true);
//                                if(field.equals(classField.getName())){
//                                    bufferedWriter.write(classField.get(obj).toString());
//                                    bufferedWriter.write(",");
//                                }
//                            }
//                        }
//                        //换行
//                        bufferedWriter.write("\r\n");
//                    }
//                }
//            }
//            bufferedWriter.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedWriter.close();
//                outputStreamWriter.close();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 导出2003
//     * @param title
//     * @param fields
//     * @param data
//     * @param fileName
//     * @param response
//     */
//    @SuppressWarnings({"all"})
//    public static void writeExcel2003(List<String> title, List<String> fields, List<Object> data, String fileName, HttpServletResponse response){
//        HSSFWorkbook workbook = null;
//        OutputStream output = null;
//        try {
//            workbook = new HSSFWorkbook();
//            HSSFSheet sheet = workbook.createSheet(fileName);
//            int hasTitle = 0;
//            // 写入标题
//            if (title != null){
//                HSSFRow row = sheet.createRow(0);
//                for (int i = 0; i < title.size(); i++) {
//                    HSSFCell cell = row.createCell(i);
//                    cell.setCellValue(title.get(i));
//                }
//                hasTitle += 1;
//            }
//            // 写入正文
//            if (data != null){
//                int rowSize = data.size();
//                Field[] classFields = new Field[0];
//                for (int rowNum = 0; rowNum < rowSize; rowNum++) {
//                    HSSFRow row = sheet.createRow(rowNum + hasTitle);
//                    Object obj = data.get(rowNum);
//                    if (obj instanceof Map){
//                        int fieldsSize = fields.size();
//                        Map<String, Object> map = (Map<String, Object>) obj;
//                        if (map != null){
//                            for (int colCell = 0; colCell < fieldsSize; colCell++) {
//                                HSSFCell cell = row.createCell(colCell);
//                                cell.setCellValue(map.get(fields.get(colCell)).toString());
//                            }
//                        }
//                    }else {
//                        //利用反射获取所有字段
//                        if (classFields == null || classFields.length == 0){
//                            classFields = obj.getClass().getDeclaredFields();
//                        }
//                        int colCell = 0;
//                        for(String field : fields){
//                            for(Field classField : classFields){
//                                //设置字段可见性
//                                classField.setAccessible(true);
//                                if(field.equals(classField.getName())){
//                                    HSSFCell cell = row.createCell(colCell);
//                                    cell.setCellValue(classField.get(obj).toString());
//                                    colCell ++;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
//            response.setContentType("application/vnd.ms-excel;");
//            output = response.getOutputStream();
//            workbook.write(output);
//            output.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (workbook != null){
//                    workbook.close();
//                }
//                if (output != null){
//                    output.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     *
//     * @param title
//     * @param fields
//     * @param data
//     * @param fileName
//     * @param response
//     */
//    @SuppressWarnings({"all"})
//    public static void writeExcel2007(List<String> title, List<String> fields, List<Object> data, String fileName, HttpServletResponse response){
//        XSSFWorkbook workbook = null;
//        OutputStream output = null;
//        try {
//            workbook = new XSSFWorkbook();
//            XSSFSheet sheet = workbook.createSheet(fileName);
//            int hasTitle = 0;
//            // 写入标题
//            if (title != null){
//                XSSFRow row = sheet.createRow(0);
//                for (int i = 0; i < title.size(); i++) {
//                    XSSFCell cell = row.createCell(i);
//                    cell.setCellValue(title.get(i));
//                }
//                hasTitle += 1;
//            }
//            // 写入正文
//            int rowSize = data.size();
//            Field[] classFields = new Field[0];
//            for (int rowNum = 0; rowNum < rowSize; rowNum++) {
//                XSSFRow row = sheet.createRow(rowNum + hasTitle);
//                Object obj = data.get(rowNum);
//                if (obj instanceof Map){
//                    int fieldsSize = fields.size();
//                    Map<String, Object> map = (Map<String, Object>) obj;
//                    if (map != null){
//                        for (int colCell = 0; colCell < fieldsSize; colCell++) {
//                            XSSFCell cell = row.createCell(colCell);
//                            cell.setCellValue(map.get(fields.get(colCell)).toString());
//                        }
//                    }
//                }else {
//                    //利用反射获取所有字段
//                    if (classFields == null || classFields.length == 0){
//                        classFields = obj.getClass().getDeclaredFields();
//                    }
//                    int colCell = 0;
//                    for(String field : fields){
//                        for(Field classField : classFields){
//                            //设置字段可见性
//                            classField.setAccessible(true);
//                            if(field.equals(classField.getName())){
//                                XSSFCell cell = row.createCell(colCell);
//                                cell.setCellValue(classField.get(obj).toString());
//                                colCell ++;
//                            }
//                        }
//                    }
//                }
//            }
//            response.setHeader("Content-Disposition", "attachment;filenaxme="+fileName+".xls");
//            response.setContentType("application/vnd.ms-excel;");
//            output = response.getOutputStream();
//            workbook.write(output);
//            output.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (workbook != null){
//                    workbook.close();
//                }
//                if (output != null){
//                    output.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}