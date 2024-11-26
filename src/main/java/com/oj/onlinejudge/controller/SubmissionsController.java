package com.oj.onlinejudge.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oj.onlinejudge.common.*;
import com.oj.onlinejudge.domain.dto.SubmissionDTO;
import com.oj.onlinejudge.domain.map.LanguageMapper;
import com.oj.onlinejudge.domain.response.Judge0Response;
import org.springframework.http.*;
import com.oj.onlinejudge.util.CodeFormatter;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 提交表 前端控制器
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@RestController
@RequestMapping("/submissions")
public class SubmissionsController {
    @PostMapping("/submit")
    public ApiResponse<?> submitCode(@RequestBody SubmissionDTO request) {
        // 格式化代码
        String formattedCode = request.getCode();
        boolean isPoorStyle = false;
        try {
            isPoorStyle = AdvancedCodingStyleChecker.hasPoorCodingStyle(formattedCode);
            System.out.println("Is the code style poor? " + isPoorStyle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 静态分析和语法检查
        if (!CodeStructureChecker.hasMainFunction(formattedCode)) {
            return new ApiResponse<>(400, "代码中必须包含 main 函数", null);
        }

        if (!CodeStructureChecker.checkParenthesesBalance(formattedCode)) {
            return new ApiResponse<>(400, "代码中存在未平衡的括号", null);
        }

        if (!CodeStructureChecker.checkStringBalance(formattedCode)) {
            return new ApiResponse<>(400, "代码中存在未关闭的字符串", null);
        }

//        if (VariableDeclarationChecker.hasUndeclaredVariables(formattedCode)) {
//            return new ApiResponse<>(400, "代码包含未声明的变量", null);
//        }

//        if (CodingStyleChecker.hasPoorCodingStyle(formattedCode)) {
//            return new ApiResponse<>(400, "代码风格不符合规范", null);
//        }
        // 发送请求到 Judge0
        ResponseEntity<String> response = submitToJudge0(formattedCode, request.getLanguageCode());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Judge0Response judge0Response = objectMapper.readValue(response.getBody(), Judge0Response.class);
//            System.out.println("Response: " + judge0Response);
//            System.out.println(Base64.decodeStr(judge0Response.getStdout()));
            // 解码 stdout
            if (judge0Response.getStdout() != null) {
                String s = Base64.decodeStr(judge0Response.getStdout());
                judge0Response.setStdout(s);
                return new ApiResponse<>(200, "提交成功", judge0Response);
            } else if(judge0Response.getCompileOutput() != null) {
                String s = Base64.decodeStr(judge0Response.getCompileOutput());
                judge0Response.setCompileOutput(s);
                return new ApiResponse<>(200, "代码运行出错啦", s);
            } else{
                return new ApiResponse<>(500, "未知错误", null);

            }

        } catch (Exception e) {
            return new ApiResponse<>(500, "解析响应失败: " + e.getMessage(), null);
        }
    }

    private ResponseEntity<String> submitToJudge0(String code, String languageCode) {
        // 构建请求
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.21.129:2358/submissions?base64_encoded=true&wait=true";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        // 获取语言 ID
        Integer languageId = LanguageMapper.getLanguageId(languageCode);

        if (languageId == null) {
            throw new IllegalArgumentException("Unsupported language code: " + languageCode);
        }
        String base64Code = Base64.encode(code);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("language_id", languageId); // C++ 语言 ID
        requestBody.put("source_code", base64Code);
//        requestBody.put("language_code", languageCode); // 包含语言代码

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        JSONObject jsonObject = JSONUtil.parseObj(responseEntity.getBody());
//        System.out.println("js   :"+jsonObject);
        System.out.println("token: " + jsonObject.getStr("token"));
        // 使用 token 发送第二个请求

        // 返回最终结果
        return getSubmissionResult(jsonObject.getStr("token"));
    }


    @PostMapping("/test")
    public String test(@RequestBody String code) {
        System.out.println(code);
        System.out.println(CodeFormatter.formatCode(code));
        return CodeFormatter.formatCode(code);
    }



    private ResponseEntity<String> getSubmissionResult(String token) {

        // 构建请求
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.21.129:2358/submissions/" + token + "?base64_encoded=true";
        String response = HttpUtil.get(url);
        // 发送 GET 请求
//        System.out.println("getSubmissionResult"+response);
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);




        // 返回响应
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

}
