package com.geiko.view;

import com.geiko.model.ProtocolRow;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Андрей on 23.05.2017.
 */

public class ProtocolExcelView extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        HSSFSheet excelSheet = workbook.createSheet("PSO Protocol");
        ArrayList<List<ProtocolRow>> protocols = (ArrayList<List<ProtocolRow>> )model.get("protocols");
        setExcelRows(excelSheet,protocols);
    }

    private void setExcelRows(HSSFSheet excelSheet, ArrayList<List<ProtocolRow>> protocols) {
        int record = 0;
        int protocolN = 0;
        for(List<ProtocolRow> protocol:protocols){
            HSSFRow protocolNum = excelSheet.createRow(record++);
            protocolNum.createCell(0).setCellValue(++protocolN+"-ий повтор");
            setExcelHeader(excelSheet,protocol,record++);
            for (ProtocolRow row:protocol) {
                int i = 0;
                HSSFRow excelRow = excelSheet.createRow(record++);
                excelRow.createCell(i++).setCellValue(row.getK());
                for (Double x: row.getX()){
                    excelRow.createCell(i++).setCellValue(x);
                }
                excelRow.createCell(i++).setCellValue(row.getF());
                excelRow.createCell(i++).setCellValue(row.getE());
            }
            record+=3;
        }
    }

    public void setExcelHeader(HSSFSheet excelSheet,List<ProtocolRow> protocol, int record) {
        HSSFRow excelHeader = excelSheet.createRow(record);
        excelHeader.createCell(0).setCellValue("k");
        int i = 1;
        for (Double x:protocol.get(0).getX()) {
            excelHeader.createCell(i).setCellValue("x["+i+"]");
            i++;
        }
        excelHeader.createCell(i).setCellValue("f(x)");
        excelHeader.createCell(i+1).setCellValue("|old - gbest|");
    }
}
