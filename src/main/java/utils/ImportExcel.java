package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ImportExcel {
    /**
     * 总行数
     */
    private int totalRows = 0;
    /**
     * 总列数
     */
    private int totalCells = 0;
    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 构造方法
     */
    public ImportExcel() {
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * @描述：验证excel文件
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public boolean validateExcel(String filePath) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            return false;
        }
        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;
    }

    /**
     * @描述：根据文件名读取excel文件
     * @参数：@param filePath 文件完整路径
     * @参数：@return
     * @返回值：List
     */
    public Set<String> read(String filePath, int cellIndex) {
        Set<String> set = new HashSet<String>();
        InputStream is = null;
        try {
            /** 验证文件是否合法 */
            if (!validateExcel(filePath)) {
                System.out.println(errorInfo);
                return null;
            }
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(filePath)) {
                isExcel2003 = false;
            }
            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);

            is = new FileInputStream(file);
            set = read(is, isExcel2003, cellIndex);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        /** 返回最后读取的结果 */
        return set;
    }

    /**
     * @描述：根据流读取Excel文件
     * @参数：@param inputStream
     * @参数：@param isExcel2003
     * @参数：@return
     * @返回值：List
     */
    public Set<String> read(InputStream inputStream, boolean isExcel2003, int cellIndex) {
        Set<String> set = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            set = read(wb, cellIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return set;
    }

    /**
     * @描述：读取数据
     * @参数：@param Workbook
     * @参数：@return
     * @返回值：List<List<String>>
     */
    private Set<String> read(Workbook wb, int cellIndex) {
        Set<String> set = new HashSet<String>();
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        this.totalRows = sheet.getPhysicalNumberOfRows();
        /** 得到Excel的列数 */
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        //找到正确的行
        Row row = sheet.getRow(0);
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            Cell cell = row.getCell(i);
            String cellValue = cell.getStringCellValue();
            if (cellValue.split("-").length == 5) {
                cellIndex = i;
                break;
            }
        }
        //


        /** 循环Excel的行 */
        for (int r = 0; r < this.totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            Cell cell = row.getCell(cellIndex);
            String cellValue = "";
            if (null != cell) {
                // 以下是判断数据的类型
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                        cellValue = cell.getNumericCellValue() + "";
                        break;

                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        cellValue = cell.getStringCellValue();
                        break;

                    case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                        cellValue = cell.getBooleanCellValue() + "";
                        break;

                    case HSSFCell.CELL_TYPE_FORMULA: // 公式
                        cellValue = cell.getCellFormula() + "";
                        break;

                    case HSSFCell.CELL_TYPE_BLANK: // 空值
                        cellValue = "";
                        break;

                    case HSSFCell.CELL_TYPE_ERROR: // 故障
                        cellValue = "非法字符";
                        break;

                    default:
                        cellValue = "未知类型";
                        break;
                }
                if (!set.add(cellValue)) {
                    System.out.println("重复数据：" + cellValue);
                }
            }
        }
        return set;
    }

}

/**
 * @描述：工具类
 */
class WDWUtil {
    /**
     * @描述：是否是2003的excel，返回true是2003
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @描述：是否是2007的excel，返回true是2007
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static void main(String[] args) {
        ImportExcel execl = new ImportExcel();
//        Set<String> set = execl.read("C:\\Users\\Administrator\\Desktop\\超杀默示录0429（3804）(1).xlsx", 0);
        Set<String> set = execl.read("C:\\Users\\Administrator\\Desktop\\201505.xls", 0);
        System.out.println(set.size());
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(set.size());
    }


}
