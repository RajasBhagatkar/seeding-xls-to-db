package org.seeding;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.AsyncResult;
import org.seeding.entity.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

//            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pinpoint", "root", "");
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO locality (id, locality, pincode, created_date, updated_date) values (?,?,?,?,?)");

            BalanceBatch balance = new BalanceBatch(10);

            balance.startAll();
            try {
                Pincode pincode = Pincode.builder()
                        .Id(new Date().getTime()/10000)
                        .circleName("Maharashtra Circle")
                        .regionName("Maharashtra Region")
                        .divisionName("Chandrapur Division")
                        .pincode(442401)
                        .district("Chandrapur")
                        .state("MH")
                        .latitude(null)
                        .longitude(null)
                        .build();

                balance.send(pincode);
//                System.out.println(pincode.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            balance.stopAll();

//            stmt.setInt(1,1231223);
//            stmt.setString(2, "Sadashiv Peth");
//            stmt.setInt(3,411030);
//            stmt.setDate(4, new java.sql.Date(new Date(System.currentTimeMillis()).getTime()));
//            stmt.setDate(5, new java.sql.Date(new Date(System.currentTimeMillis()).getTime()));
//
//            int output = stmt.executeUpdate();
//
//            System.out.println(" records inserted");


//            gettingValueFromXls(1, 10); // let it insert data into the db

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static void gettingValueFromXls(int start, int end) {
        try {


            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\RajasB\\Downloads\\pincode.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            // get the first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);

            if (end > sheet.getLastRowNum())
                throw new ArrayIndexOutOfBoundsException("end is greater than last row number");

            for (int i = start; i < end; i++) {

                if (i > sheet.getLastRowNum()) break;

                XSSFRow row = sheet.getRow(i);

//                Pincode pincode = new Pincode();

//                Locality locality = new Locality();

                for (int j = 0; j < row.getLastCellNum(); j++) {

                    XSSFCell cell = row.getCell(j);

                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        String cellValue = cell.getStringCellValue();
                        // circle name (string)
                        if (j == 0) {
                            System.out.print(cellValue + " | ");
                        }
                        // region name (string)
                        else if (j == 1) {
                            System.out.print(cellValue + " | ");
                        }
                        // division name (string)
                        else if (j == 2) {
                            System.out.print(cellValue + " | ");
                        }
                        // office name (string)
                        // locality
                        else if (j == 3) {
                            if (cellValue.contains(" B.O")) {
                                String output = cellValue.replaceAll("B\\.O$", "");
                                System.out.print(output + " | ");
                            } else if (cellValue.contains(" BO")) {
                                String output = cellValue.replaceAll("BO$", "");
                                System.out.print(output + " | ");
                            } else if (cellValue.contains(" S.O")) {
                                String output = cellValue.replaceAll("S\\.O$", "");
                                System.out.print(output + " | ");
                            } else if (cellValue.contains(" SO")) {
                                String output = cellValue.replaceAll("SO$", "");
                                System.out.print(output + " | ");
                            } else {
                                System.out.print(cellValue + " | ");
                            }

                        }
                        // district (string)
                        else if (j == 7) {
                            System.out.print(cellValue + " | ");
                        }
                        // state (string)
                        else if (j == 8) {
                            System.out.print(cellValue + " | ");
                        }

                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        // pincode (integer)
                        if (j == 4) {
                            Integer pincodeNum = (int) cell.getNumericCellValue();

                            System.out.print(pincodeNum + " | ");
                        }
                        // latitude (double)
                        else if (j == 9) {
                            double numeric = cell.getNumericCellValue();

                            System.out.print(numeric + " | ");

                        }
                        // longitude (double)
                        else if (j == 10) {
                            double numeric = cell.getNumericCellValue();
                            System.out.print(numeric + " | ");
                        }


                    }

                }
                System.out.println("");
            }


            fileInputStream.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error reading excel file:");
        }

    }


}

