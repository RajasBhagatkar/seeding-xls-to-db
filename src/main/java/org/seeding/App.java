package org.seeding;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.scheduling.annotation.AsyncResult;
import org.seeding.entity.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.CompletableFuture;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pincode_table_test", "root", "");

            int start = 1;
            long start_time = System.currentTimeMillis();
            Integer totalRows = getTotalRows() - start;

            HashMap<Integer, String> pinMap = new HashMap<>();

            System.out.println(Thread.currentThread().getName());
            Integer step = 10000; // 10K
            while (totalRows != 0) {
                if (totalRows > step) {
                    gettingValueFromXls(start, start + step, pinMap, connection);

                    start += step;
                    totalRows -= step;
                } else {
                    Integer endRowNumber = start + (step - (step - totalRows));

                    gettingValueFromXls(start, endRowNumber, pinMap, connection);

                    totalRows -= (step - (step - totalRows));

                    start += (step - (step - totalRows));

                }

            }

//            gettingValueFromXls(1, 30, connection);
            long end_time = System.currentTimeMillis();
            long totalT = end_time - start_time;

            System.out.println("complete task of inserting "+ (totalRows - start) + "rows took - " + totalT + " /ms");


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static Integer getTotalRows() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("path-to-file.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        return sheet.getLastRowNum();
    }


    public static void gettingValueFromXls(int start, int end, HashMap<Integer, String> pinMap, Connection connection) {
        try {

            PreparedStatement state_info_stmt = connection.prepareStatement("INSERT INTO state_info (id,circle_name,district,division,pincode,region_name,state,created_date,updated_date) values (?,?,?,?,?,?,?,?,?)");
            PreparedStatement locality_info_stmt = connection.prepareStatement("INSERT INTO locality_info (id,locality,latitude,longitude,pincode,state_info,created_date,updated_date) values (?,?,?,?,?,?,?,?)");
            FileInputStream fileInputStream = new FileInputStream("path-to-file.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            System.out.println("range (" + start + ", " + end + ")");


            // get the first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);

            if (end > sheet.getLastRowNum())
                throw new ArrayIndexOutOfBoundsException("end is greater than last row number");

            /**
             *
             * key -> pincode
             * value -> primaryKey (state_info) table
             */

            for (int i = start; i < end; i++) {
                if (i > sheet.getLastRowNum()) break;

                XSSFRow row = sheet.getRow(i);

                Pincode pincode = new Pincode();

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);

                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        String cellValue = cell.getStringCellValue();
                        // circle name (string)
                        if (j == 0) {
                            pincode.setCircleName(cellValue);
                            // statement.setString(2, cellValue);
                            // System.out.print(cellValue + " | ");
                        }
                        // region name (string)
                        else if (j == 1) {
                            pincode.setRegionName(cellValue);
                            // statement.setString(3, cellValue);
                            // System.out.print(cellValue + " | ");
                        }
                        // division name (string)
                        else if (j == 2) {
                            pincode.setDivisionName(cellValue);
                            //statement.setString(4, cellValue);
                            // System.out.print(cellValue + " | ");
                        }
                        // office name (string)
                        // locality
                        else if (j == 3) {
                            if (cellValue.contains(" B.O")) {
                                String output = cellValue.replaceAll("B\\.O", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" B.O.")) {
                                String output = cellValue.replaceAll("B\\.O\\.", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" B. O.")) {
                                String output = cellValue.replaceAll("\\sB\\.\\sO\\.", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" BO")) {
                                String output = cellValue.replaceAll("BO", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" S.O")) {
                                String output = cellValue.replaceAll("S\\.O", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" S.O.")) {
                                String output = cellValue.replaceAll("S\\.O\\.", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" S. O.")) {
                                String output = cellValue.replaceAll("\\sS\\.\\sO\\.", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" SO")) {
                                String output = cellValue.replaceAll("SO", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" HO")) {
                                String output = cellValue.replaceAll("HO", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" H.O")) {
                                String output = cellValue.replaceAll("H\\.O", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" H.O.")) {
                                String output = cellValue.replaceAll("H\\.O\\.", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else if (cellValue.contains(" H. O.")) {
                                String output = cellValue.replaceAll("\\sH\\.\\sO\\.", "");
                                pincode.setLocality(output);
                                // statement.setString(5, output);
                                // System.out.print(output + " | ");
                            } else {
                                pincode.setLocality(cellValue);
                                // System.out.print(cellValue + " | ");
                                // statement.setString(5, cellValue);
                                // System.out.println("till 5");
                            }

                        }
                        // district (string)
                        else if (j == 7) {
                            pincode.setDistrict(cellValue);
                            // statement.setString(7, cellValue);
                            // System.out.print(cellValue + " | ");
                        }
                        // state (string)
                        else if (j == 8) {
                            pincode.setState(cellValue);
                            // statement.setString(8, cellValue);
                            //  System.out.print(cellValue + " | ");
                        }
                        // this is latitude if the value -> NA
                        else if (j == 9) {
                            pincode.setLatitude(null);
                            // statement.setNull(9, Types.NULL);
                        }
                        // this is longitude if the value ->
                        else if (j == 10) {
                            pincode.setLongitude(null);
                            // statement.setDouble(10, Types.NULL);
                        }

                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        // pincode (integer)
                        if (j == 4) {
                            Integer pincodeNum = (int) cell.getNumericCellValue();
                            pincode.setPincode(pincodeNum);
                            // statement.setInt(6, pincodeNum);
                            //  System.out.print(pincodeNum + " | ");
                        }
                        // latitude (double)
                        else if (j == 9) {
                            double numeric = cell.getNumericCellValue();
                            pincode.setLatitude(numeric);
                            // statement.setDouble(9, numeric);
                            // System.out.print(numeric + " | ");

                        }
                        // longitude (double)
                        else if (j == 10) {
                            double numeric = cell.getNumericCellValue();
                            pincode.setLongitude(numeric);
                            // statement.setDouble(10, numeric);
                            // System.out.print(numeric + " | ");
                        }


                    }

                }
                // i will get all the  value here
                if (pinMap.get(pincode.getPincode()) == null) {
                    // generating the P-key for state_info table
                    // inerting the state_info table data into db
                    // storing the pincode as key and P-key as value in PinMap;
                    // String Id = "si_" + ((new Random()).nextInt(900000000) + 100000000);
                    // if value does not exists
                    if (pincode.getPincode() == 442401) {
                        System.out.println("this if block executed...");
                        System.out.println(pinMap.get(pincode.getPincode()));
                        System.out.println(pincode.getId());
                    }

//                    System.out.println("state_info = " + Id);
                    pinMap.put(pincode.getPincode(), pincode.getId());

                    if (pincode.getPincode() == 442401) {
                        System.out.println("validation step.");
                        System.out.println(pinMap.get(pincode.getPincode()));
                    }

                    state_info_stmt.setString(1, pincode.getId());
                    state_info_stmt.setString(2, pincode.getCircleName());
                    state_info_stmt.setString(3, pincode.getDistrict());
                    state_info_stmt.setString(4, pincode.getDivisionName());
                    state_info_stmt.setLong(5, pincode.getPincode());
                    state_info_stmt.setString(6, pincode.getRegionName());
                    state_info_stmt.setString(7, pincode.getState());
                    state_info_stmt.setDate(8, pincode.getCreated_date());
                    state_info_stmt.setDate(9, pincode.getUpdated_date());

                    state_info_stmt.executeUpdate();
                }
                // 2
                // generating P-key for "local" table
                // getting the primary key from pinMap using pincode
                // inserting data into local table
                locality_info_stmt.setString(1, UUID.randomUUID().toString());
                locality_info_stmt.setString(2, pincode.getLocality());
                if (pincode.getLatitude() != null) {
                    locality_info_stmt.setDouble(3, pincode.getLatitude());
                } else {
                    locality_info_stmt.setNull(3, Types.NULL);
                }
                if (pincode.getLongitude() != null) {
                    locality_info_stmt.setDouble(4, pincode.getLongitude());
                } else {
                    locality_info_stmt.setNull(4, Types.NULL);
                }
                locality_info_stmt.setInt(5, pincode.getPincode());
                locality_info_stmt.setString(6, pinMap.get(pincode.getPincode()));
                locality_info_stmt.setDate(7, pincode.getCreated_date());
                locality_info_stmt.setDate(8, pincode.getUpdated_date());


                locality_info_stmt.addBatch();
//                System.out.println("");
            }

            locality_info_stmt.executeBatch();

//            connection.commit();

            fileInputStream.close();


        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            System.out.println(e);
        }

    }

    public static String generateUniqueId(String suffix) {
        return suffix + ((new Random()).nextInt(900000000) + 100000000);
    }


}

