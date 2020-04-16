package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

  @RequestMapping("/status")
  public ResponseEntity<Map<String, Object>> status() {
    Map<String, Object> bodyMap = new HashMap<String, Object>();
    bodyMap.put("status", "good");
    return ResponseEntity.ok(bodyMap);
  }

}
