package cn.mvc.tools;

import cn.mvc.service.Service_interface_Login;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExcelTools {
    @Autowired
    private Service_interface_Login service;


      // @Scheduled(cron = "30 * * * * ?")
     @Scheduled(cron = "0 30 7 * * ?")
    public void run_excel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Workbook workbook = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
       //calendar.setTime(sdf.parse("2020-08-01"));
        String endTime = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String startTime = sdf.format(calendar.getTime());
        int mouth = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        String fileName = "D:/自動化線线生產数据" + year + "年" + mouth + "月.xlsx";
        //String fileName = "D:/自動化線线生產数据" + mouth + "月.xlsx";
        FileInputStream fs;
        int lie = calendar.get(Calendar.DAY_OF_MONTH) + 4;
        int hang = 12;
        try {
            fs = new FileInputStream(fileName);
            if (fileName.endsWith("xls")) {
                //  2003版本
                workbook = new HSSFWorkbook(fs);
            } else if (fileName.endsWith("xlsx")) {
                //  2007版本
                workbook = new XSSFWorkbook(fs);
            }
            int sss = workbook.getNumberOfSheets();
            for (int i = 1; i <= 8; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String line = sheet.getSheetName().substring(0, 4);
                //String productModel = sheet.getRow(2).getCell(0).getStringCellValue().substring(3);
                Row row = sheet.getRow(1);
                row.getCell(0).setCellValue("月份：" + year + "年" + mouth + "月");
                row = sheet.getRow(4);
                row.getCell(3).setCellValue(mouth + "月目标");
                FileOutputStream out = new FileOutputStream(fileName);
                Map<String, Object> param = new HashMap<>();
                param.put("line", line);
                param.put("startDateTime", startTime);
                param.put("endDateTime", endTime);
                Map<String, Object> map = service.getDataInfo(param);
                if (map.size() > 0) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        //2020-07-20 插入实际产能
                        if ("real_out".equals(entry.getKey())){
                            row = sheet.getRow(9);
                            row.getCell(lie).setCellValue(entry.getValue().toString());
                        }else{
                            row = sheet.getRow(hang);
                            row.getCell(lie).setCellValue(entry.getValue().toString());
                            hang++;
                        }
                    }
                    hang = 12;
                }
                out.flush();
                workbook.write(out);
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
