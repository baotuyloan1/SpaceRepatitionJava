package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * @author BAO 7/21/2023
 */
@Component
public class DateTimeUtils {

  public String formatDateToString(Date date) {
    String formatStr = "HH:mm:ss dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    simpleDateFormat.applyPattern(formatStr);
    return simpleDateFormat.format(date);
  }
}
