package com.humber.distribution_center_manager.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Controller
public class ApiDocsController {

    @GetMapping(value = "/api-docs", produces = "text/html")
    @ResponseBody
    public String getApiDocs() throws IOException {
        Resource resource = new ClassPathResource("static/api-docs.html");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
    
    @GetMapping(value = "/api-manager", produces = "text/html")
    @ResponseBody
    public String getApiManager() throws IOException {
        Resource resource = new ClassPathResource("static/api-manager.html");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }
} 