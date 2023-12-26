package com.arjun.ReadExcelSheet.service;

import com.arjun.ReadExcelSheet.model.User;
import io.micrometer.core.instrument.MultiGauge;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReadExcelService {

    private Integer parseIntegerValue(String value) throws NumberFormatException{
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Value is null or empty");
        }
        return Integer.parseInt(value);
    }


    private Double parseDoubleValue(String value )throws NumberFormatException{
        if (value ==null || value.isEmpty() ){
            throw new NumberFormatException("Value is null or empty");
        }
        return Double.parseDouble(value);
    }


    public List<User> readExcelToList(String filePath) {
        List<User> userList = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet dataTypeSheet = workbook.getSheetAt(0);

            Stream<Row> rowStream = StreamSupport.stream(dataTypeSheet.spliterator(), false);
            Iterator<Row> rowIterator = rowStream.iterator();


            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();   // Skips the first row (header)
            }


            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                List<String> rowData = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellType() == CellType.STRING) {
                        rowData.add(currentCell.getStringCellValue());
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        rowData.add(String.valueOf(currentCell.getNumericCellValue()));
                    }
                }

                if (!rowData.isEmpty()) {

                    try {
                        double idAsDouble = Double.parseDouble(rowData.get(0));
                        int id = (int) idAsDouble;
                        // int id = Integer.parseInt(rowData.get(0));

                        String firstName = rowData.get(1);
                        String lastName = rowData.get(2);

                        double ageAsDouble = Double.parseDouble(rowData.get(3));
                        int age = (int) ageAsDouble;
                        //Integer age = parseIntegerValue(rowData.get(3));
                        String email = rowData.get(4);
                        String phoneNumber = rowData.get(5);
                        Double salary = parseDoubleValue(rowData.get(6));
                        String country = rowData.get(7);
                        String state = rowData.get(8);
                        String street = rowData.get(9);


                        User user = new User(id,
                                firstName,
                                lastName,
                                age,
                                email,
                                phoneNumber,
                                salary,
                                country,
                                state,
                                street
                        );
                        userList.add(user);

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Skipped record due to data format exception:: " + e.getMessage());
                    }
                }
            }
            workbook.close();
            excelFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    //Lets take an example to read the excel sheet



    public static void main(String[] args) {
      ReadExcelService readExcelService = new ReadExcelService();
      List<User> userList = readExcelService.readExcelToList("C:\\Users\\aj629\\IdeaProjects\\GEET_API\\src\\main\\resources\\static\\users.xlsx");

      userList.forEach(
              e -> System.out.println(e.getId() +"  :  " +e.getFirstName())
      );
    }




}
