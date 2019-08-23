package com.automationtasks;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class XlsxWork {
    private static final String NAME = "BNM";
    private static final String XLSX_EXTENSION = ".xlsx";
    private static final String[] LIST_OF_HEADER_VALUES = {"ID", "NumCode", "CharCode", "Nominal", "Name", "Value"};


    public static void createXlsxFile(List<ValCurs> listValCursObjects) throws IOException {
        Workbook wb;
        OutputStream fileOut;
        Sheet sheet;
        Row headerRow, row;
        List<Valute> listValute;

        wb = new XSSFWorkbook();
        for (ValCurs valCursObject : listValCursObjects) {
            // Creating sheet using date as safe name
            sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(valCursObject.getDate()));
            listValute = valCursObject.getValuteList();
            headerRow = sheet.createRow(0);

            for (int i = 0; i < LIST_OF_HEADER_VALUES.length; i++) {
                headerRow.createCell(i).setCellValue(LIST_OF_HEADER_VALUES[i]);
            }

            int i = 1;
            for (Valute valute : listValute) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(valute.getId());
                row.createCell(1).setCellValue(valute.getNumCode());
                row.createCell(2).setCellValue(valute.getCharCode());
                row.createCell(3).setCellValue(valute.getNominal());
                row.createCell(4).setCellValue(valute.getName());
                row.createCell(5).setCellValue(valute.getValue());
                i++;
            }
        }
        fileOut = new FileOutputStream(NAME + XLSX_EXTENSION);
        wb.write(fileOut);
    }
}

