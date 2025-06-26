package bighomework.web.service;

import bighomework.web.entity.QueryResult;

public interface QueryParser {

  QueryResult parser(String query) throws Exception;
}
