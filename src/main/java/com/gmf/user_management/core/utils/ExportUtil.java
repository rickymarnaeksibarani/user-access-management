package com.gmf.user_management.core.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExportUtil {
    public static void handleDownloadExcel(XSSFWorkbook workbook, HttpServletResponse response, String suffix) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String _prefix = dtf.format(now).replaceAll("[/\\s:]","_");
        String fileName = _prefix + suffix + ".xlsx";

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        // Write workbook to the response output stream
        workbook.write(response.getOutputStream());

        // Close the workbook
        workbook.close();
    }
}
