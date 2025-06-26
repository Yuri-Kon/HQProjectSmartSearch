package bighomework.web.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class TimeExpressionParserTest {

  @Test
  void shouldReturnAllWednesdayWithin14Days() {
    List<LocalDate> result = TimeExpressionParser.parseCourseTimeExpr("周三");

    assertFalse(result.isEmpty());
    assertTrue(result.size() <= 3); // 最多三个周三
    for (LocalDate date : result) {
      assertEquals(DayOfWeek.WEDNESDAY, date.getDayOfWeek());
    }
  }

  @Test
  void shouldMatchExtendedExpressions() {
    List<LocalDate> result = TimeExpressionParser.parseCourseTimeExpr("周三或周四");

    assertFalse(result.isEmpty());
    Set<DayOfWeek> days = result.stream().map(LocalDate::getDayOfWeek).collect(Collectors.toSet());

    assertTrue(days.contains(DayOfWeek.WEDNESDAY));
    assertTrue(days.contains(DayOfWeek.THURSDAY));
  }

  @Test
  void shouldReturnEmptyWhenNoMatch() {
    List<LocalDate> result = TimeExpressionParser.parseCourseTimeExpr("明天中午或者大后天");
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnCustonSearchLength() {
    List<LocalDate> result = TimeExpressionParser.parseCourseTimeExpr("周日", 30);

    assertFalse(result.isEmpty());
    assertTrue(result.size() <= 5);
    for (LocalDate date : result) {
      assertEquals(DayOfWeek.SUNDAY, date.getDayOfWeek());
    }
  }
}
