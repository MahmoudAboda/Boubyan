/*
 * Copyright (c)  Boubyan Bank
 * @Author : Mahmoud Aboda
 * @Date: 29/8/2023
 */

package com.oschool.student.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportUtil {
    private static final String EMAIL_REGEX ="^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PHONE_NUMBER_REGEX =  "[0-9]+";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public static LocalDate parseDate(int index, Row row, LocalDate centuryStart) {
        String dateString = cellValue(index, row).orElse(null);
        if (dateString != null) {
            try {
                return centuryStart.plusDays(Long.parseLong(dateString) - 2);
            } catch (Exception ignored) {
                return null;
            }
        }
        return null;
    }

    public static Optional<String> cellValue(int index, Row row) {
        Cell cell = row.getCell(index);
        return Optional.ofNullable(cell).map(cell1 -> getCellValue(cell));
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return null;
        }
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }


}
