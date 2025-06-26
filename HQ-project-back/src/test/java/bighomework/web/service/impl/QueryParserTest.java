package bighomework.web.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import bighomework.web.entity.QueryResult;

public class QueryParserTest {

  @Test
  public void testParse() throws Exception {
    DifyQueryParserImpl queryParser = new DifyQueryParserImpl();

    String query = "我想找周三上午的英语课，张老师教的";
    QueryResult result = queryParser.parser(query);

    assertNotNull(result);
    assertEquals("英语课", result.getCourse_name());
    assertEquals("张老师", result.getTeacher_name());
    assertEquals("周三上午", result.getCourse_time_expr());
  }
}