package bighomework.web.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TimeExpressionParser {

  private static final Map<String, DayOfWeek> dayOfWeekMap = new HashMap<>();

  static {
    dayOfWeekMap.put("周一", DayOfWeek.MONDAY);
    dayOfWeekMap.put("每周一", DayOfWeek.MONDAY);
    dayOfWeekMap.put("星期一", DayOfWeek.MONDAY);
    dayOfWeekMap.put("每星期一", DayOfWeek.MONDAY);

    dayOfWeekMap.put("周二", DayOfWeek.TUESDAY);
    dayOfWeekMap.put("每周二", DayOfWeek.TUESDAY);
    dayOfWeekMap.put("星期二", DayOfWeek.TUESDAY);
    dayOfWeekMap.put("每星期二", DayOfWeek.TUESDAY);

    dayOfWeekMap.put("周三", DayOfWeek.WEDNESDAY);
    dayOfWeekMap.put("每周三", DayOfWeek.WEDNESDAY);
    dayOfWeekMap.put("星期三", DayOfWeek.WEDNESDAY);
    dayOfWeekMap.put("每星期三", DayOfWeek.WEDNESDAY);

    dayOfWeekMap.put("周四", DayOfWeek.THURSDAY);
    dayOfWeekMap.put("每周四", DayOfWeek.THURSDAY);
    dayOfWeekMap.put("星期四", DayOfWeek.THURSDAY);
    dayOfWeekMap.put("每星期四", DayOfWeek.THURSDAY);

    dayOfWeekMap.put("周五", DayOfWeek.FRIDAY);
    dayOfWeekMap.put("每周五", DayOfWeek.FRIDAY);
    dayOfWeekMap.put("星期五", DayOfWeek.FRIDAY);
    dayOfWeekMap.put("每星期五", DayOfWeek.FRIDAY);

    dayOfWeekMap.put("周六", DayOfWeek.SATURDAY);
    dayOfWeekMap.put("每周六", DayOfWeek.SATURDAY);
    dayOfWeekMap.put("星期六", DayOfWeek.SATURDAY);
    dayOfWeekMap.put("每星期六", DayOfWeek.SATURDAY);

    dayOfWeekMap.put("周日", DayOfWeek.SUNDAY);
    dayOfWeekMap.put("每周日", DayOfWeek.SUNDAY);
    dayOfWeekMap.put("星期日", DayOfWeek.SUNDAY);
    dayOfWeekMap.put("每星期日", DayOfWeek.SUNDAY);
    dayOfWeekMap.put("星期天", DayOfWeek.SUNDAY);
    dayOfWeekMap.put("每星期天", DayOfWeek.SUNDAY);
  }

  public static List<LocalDate> parseCourseTimeExpr(String expr, int daysToSearch) {
    Set<DayOfWeek> matchedDays = new HashSet<>();
    Set<LocalDate> result = new TreeSet<>();

    if (expr == null || expr.isBlank()) {
      return new ArrayList<>();
    }

    // 尝试找出所有周x的表达
    for (Map.Entry<String, DayOfWeek> entry : dayOfWeekMap.entrySet()) {
      if (expr.contains(entry.getKey())) {
        matchedDays.add(entry.getValue());
      }
    }

    if (matchedDays.isEmpty()) {
      return new ArrayList<>();
    }

    // 生成未来日期
    LocalDate today = LocalDate.now();
    for (int i = 0; i < daysToSearch; i++) {
      LocalDate date = today.plusDays(i);
      if (matchedDays.contains(date.getDayOfWeek())) {
        result.add(date);
      }
    }
    return new ArrayList<>(result);
  }

  public static List<LocalDate> parseCourseTimeExpr(String expr) {
    return parseCourseTimeExpr(expr, 14);
  }
}
