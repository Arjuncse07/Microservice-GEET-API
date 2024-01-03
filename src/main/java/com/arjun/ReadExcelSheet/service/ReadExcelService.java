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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/*
Shortcut key IJ = surroundWith : CTRL+ALT+T

                     [A P A C H E -  P O I ]
Issue : Read 1 as 1.0 in Id Field
The issue you're encountering is related to the way numeric values are read from Excel using Apache POI.
When you read a numeric value from an Excel cell, it is returned as a double by default.
--> double doubleIdValue = parseDoubleValue(rowData.get(0));
        int id = (int)doubleIdValue;


 */

public class ReadExcelService {

    private Integer parseIntegerValue(String value) throws NumberFormatException{
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Value is null or empty");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }


    private Double parseDoubleValue(String value )throws NumberFormatException{
        if (value ==null || value.isEmpty() ){
            throw new NumberFormatException("Value is null or empty");
        }
        return Double.parseDouble(value);
    }

private boolean isRowNonBlank(Row row){
        // check if any cell in the row is not blank
    return StreamSupport.stream(row.spliterator(),false)
            .anyMatch(cell -> cell != null && cell.getCellType() != CellType.BLANK);
}

    private boolean isCellNullOrBlank(Cell cell) {
        System.out.println("Your Cell is Blank::");
        return cell == null || cell.getCellType() == CellType.BLANK;
    }




   /* public List<User> readExcelToList(String filePath) {
        List<User> userList = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(excelFile);  // XSSFWorkbook: It is a class representing the XLSX file.
            Sheet dataTypeSheet = workbook.getSheetAt(0); //creating a Sheet object to retrieve the object

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

                        String firstName = rowData.get(1);
                        String lastName = rowData.get(2);

                        String age = rowData.get(3);

                        String email = rowData.get(4);
                        String phoneNumber = rowData.get(5);
                        String salary = rowData.get(6);
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
    }*/





    //Lets take an example to read the excel sheet throw streams API

    private List<String> extractRowData(Row row) {
        Iterator<Cell> cellIterator = row.iterator();
        List<String> rowData = new ArrayList<>();

        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();

            if (isCellNullOrBlank(currentCell)) {
                return null; // Skip the entire row
            }

            switch (currentCell.getCellType()) {
                case STRING:
                    rowData.add(currentCell.getStringCellValue());
                    break;
                case NUMERIC:
                    rowData.add(String.valueOf(currentCell.getNumericCellValue()));
                    break;
                // Add other cell types as needed
            }
        }

        return rowData;
    }

    private User convertRowToUser(Row row){
        List<String> rowData = extractRowData(row);

        if (rowData == null) {
            System.out.println(" Row Data is null ");
            return null;
        }

        try {
            double doubleIdValue = parseDoubleValue(rowData.get(0));
            int id = (int)doubleIdValue;
            String firstName = rowData.get(1);
            String lastName = rowData.get(2);
            String age = rowData.get(3);
            String email = rowData.get(4);
            String phoneNumber = rowData.get(5);
            String salary = rowData.get(6);
            String country = rowData.get(7);
            String state = rowData.get(8);
            String street = rowData.get(9);

            return new User(
                    id,
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





    public List<User> readExcelToList(String filePath){
        List<User> userList = new ArrayList<>();

        try{
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile); // Create an Workbook
            Sheet sheet = workbook.getSheetAt(0); // create a sheet object

            Stream<Row> rowStream = StreamSupport.stream(sheet.spliterator(), false);
            Iterator<Row> rowIterator = rowStream.iterator();

            //Skip the row Header
            if(rowIterator.hasNext()){
                Row currentRow = rowIterator.next();
                Cell headerCell = currentRow.getCell(0);

                //Skip the blank headers
                try {
                    if (headerCell == null || headerCell.getCellType() == CellType.BLANK){
                        System.out.println("Your cell Header is Blank::");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            User user;
            while (rowIterator.hasNext()){
               Row row = rowIterator.next();
               if(!isRowNonBlank(row)){
                   user = convertRowToUser(row);
                   System.out.println(user);
                   if (user != null){
                       userList.add(user);
                   }
               }
           }
        }catch (Exception e){
            e.printStackTrace();
        }
      return userList;
    }



    public static void main(String[] args) {
      ReadExcelService readExcelService = new ReadExcelService();
      List<User> userList = readExcelService.readExcelToList("C:\\Users\\aj629\\IdeaProjects\\GEET_API\\src\\main\\resources\\static\\users.xlsx");

      userList.forEach(
              e -> System.out.println(e.getId() +"  :  " +e.getFirstName())
      );
    }




}
