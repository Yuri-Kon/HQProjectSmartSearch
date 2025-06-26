
package bighomework.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import bighomework.web.entity.QueryResult;
import bighomework.web.service.QueryParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DifyQueryParserImpl implements QueryParser {

  private static final String DIFY_API_URL = "https://api.dify.ai/v1/workflows/run";
  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public QueryResult parser(String query) throws Exception {
    // 构建请求体
    Map<String, Object> requestBody = new HashMap<>();
    Map<String, String> inputs = new HashMap<>();
    inputs.put("query", query);
    requestBody.put("inputs", inputs);
    requestBody.put("user", "test-user");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer app-cJXKkS4dPJm32rxcM6jvOkKp");
    System.out.println("📤 发送到 Dify 的 query：" + requestBody);
    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        DIFY_API_URL,
        HttpMethod.POST,
        entity,
        String.class);
    System.out.println("📥 Dify 响应体：" + response.getBody());
    if (response.getStatusCode() != HttpStatus.OK) {
      throw new RuntimeException("调用Dify失败，状态码：" + response.getStatusCode());
    }

    // 解析响应体

    JsonNode root = objectMapper.readTree(response.getBody());
    JsonNode structuredOutput = root.path("data").path("outputs").path("structured_output");

    // 安全检查
    if (structuredOutput == null || structuredOutput.isMissingNode() || !structuredOutput.isObject()) {
      throw new RuntimeException("Dify 响应缺少 structured_output 字段，无法解析：" + root.toPrettyString());
    }
    return objectMapper.treeToValue(structuredOutput, QueryResult.class);

  }

}