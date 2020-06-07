package com.cy.controller;

import com.cy.entities.Student;
import com.cy.mapper.FileMapper;
import com.cy.utils.ImportUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    FileMapper fileMapper;

    /**
     * Excel文件内容的上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public String uploadExcel(HttpServletRequest request, Model model) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        InputStream inputStream = null;
        List<List<Object>> list = null;

        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            model.addAttribute("ImportMsg", "文件不能为空");
            return "import/msg";
        }
        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);
        if (!fileName.contains(".")) {
            model.addAttribute("ImportMsg", "上传文件格式不正确，应为Excel文件");
            return "import/msg";
        }
        String name = fileName.substring(fileName.lastIndexOf("."));
        if (!".xls".equals(name) && !".xlsx".equals(name)) {
            model.addAttribute("ImportMsg", "上传文件格式不正确，应为Excel文件");
            return "import/msg";
        }

        inputStream = file.getInputStream();
        list = ImportUtil.getBankListByExcel(inputStream, fileName);
        inputStream.close();

        int count = 0;
        //连接数据库部分
        for (int i = 0; i < list.size(); i++) {
            count = list.size();
            List<Object> lo = list.get(i);
            fileMapper.insert(String.valueOf(lo.get(0)), String.valueOf(lo.get(1)), Integer.parseInt(String.valueOf(lo.get(2))),
                    String.valueOf(lo.get(3)), String.valueOf(lo.get(4)), String.valueOf(lo.get(5)));
        }

        model.addAttribute("ImportMsg", "上传成功，操作数据" + count + "条");
        return "import/msg";
    }

    /**
     * 下载Excel模版
     *
     * @param response
     * @return
     * @throws Exception
     */

    @GetMapping("/downloadExcel")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadTest(HttpServletResponse response) throws Exception {

        File file = ResourceUtils.getFile("classpath:excel/2017计科学生工程实践录入表.xlsx");
        String fileName = file.getName();
        InputStream inputStream = new FileInputStream(file);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);
    }

    /**
     * 导出信息生成Excel
     *
     * @param classes
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcel")
    public void downloadAllClassmate(HttpServletResponse response,HttpServletRequest request) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生实践项目信息录入表");

        //查询出的数据
        List<Student> ExcelList = fileMapper.expoetExcel();

        //文件名称  必须用英文
        String fileName = "StudentProInfo" + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        //根据实际情况编写列表名（顺序必须一一对应）
        String[] headers = {"学号", "名字", "性别",  "项目名","项目链接", "班级"};
        //headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        String gender = null;

        //在表中存放查询到的数据放入对应的列
        for (Student excelStudent : ExcelList) {
            HSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(excelStudent.getId());
            row1.createCell(1).setCellValue(excelStudent.getLastName());
            if (excelStudent.getGender() == 1) {
                gender = "男";
            } else {
                gender = "女";
            }
            row1.createCell(2).setCellValue(gender);
            row1.createCell(3).setCellValue(excelStudent.getProjectName());
            row1.createCell(4).setCellValue(excelStudent.getProject());
            row1.createCell(5).setCellValue(excelStudent.getClasses());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


}

