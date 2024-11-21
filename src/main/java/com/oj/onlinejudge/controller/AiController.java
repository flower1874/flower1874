package com.oj.onlinejudge.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/11 下午11:37
 */
@RestController
@RequestMapping("/ai")
public class AiController {
    private final Generation generation;

    @Value("${ai.api.key}")
    private String appKey;

    @Autowired
    public AiController(Generation generation) {
        this.generation = generation;
    }

    @PostMapping(value = "/send")
    public Mono<Map<String, String>> aiTalk(@RequestBody String question, HttpServletResponse response) {
        // 构建消息对象
        Message message = Message.builder().role(Role.USER.getValue()).content(question).build();

        // 构建通义千问参数对象
        GenerationParam param = GenerationParam.builder()
                .model(Generation.Models.QWEN_PLUS)
                .messages(Collections.singletonList(message))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.75)  // 控制采样的多样性
                .maxTokens(2048)  // 增加最大生成的 token 数量
                .temperature(0.7F)  // 控制生成文本的随机性
                .apiKey(appKey)
                .build();

        Instant start = Instant.now();

        // 执行文本生成操作，并等待结果完成
        return Mono.fromCallable(() -> {
            try {
                GenerationResult result = generation.call(param);
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                System.out.println("API 调用耗时: " + duration.toMillis() + " 毫秒");

                String content = result.getOutput().getChoices().get(0).getMessage().getContent();
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("answer", content);
                return responseMap;
            } catch (NoApiKeyException | InputRequiredException e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "请求错误: " + e.getMessage());
                return errorResponse;
            } catch (Exception e) {
                // 其他异常处理
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "内部错误: " + e.getMessage());
                return errorResponse;
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
//        // 执行文本生成操作，并等待结果完成
//        return Mono.fromCallable(() -> {
//            try {
//                GenerationResult result = generation.call(param);
//                System.out.println(result);
//                String content = result.getOutput().getChoices().get(0).getMessage().getContent();
//                Map<String, String> responseMap = new HashMap<>();
//                responseMap.put("answer", content);
//                return responseMap;
//            } catch (NoApiKeyException | InputRequiredException e) {
//                Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("error", "请求错误: " + e.getMessage());
//                return errorResponse;
//            } catch (Exception e) {
//                // 其他异常处理
//                Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("error", "内部错误: " + e.getMessage());
//                return errorResponse;
//            }
//        }).subscribeOn(Schedulers.boundedElastic());
//    }
    @PostMapping(value = "/optimize-code", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, String>> optimizeCode(@RequestBody String code, HttpServletResponse response) {
        // 构建提示词
        String prompt = "请优化以下C++代码:\n" + code + "\n优化后的代码为：\n";

        // 构建消息对象
        Message message = Message.builder().role(Role.USER.getValue()).content(prompt).build();

        // 构建通义千问参数对象
        GenerationParam param = GenerationParam.builder()
                .model(Generation.Models.QWEN_PLUS)
                .messages(Arrays.asList(message))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.75)  // 控制采样的多样性
                .maxTokens(2048)  // 增加最大生成的 token 数量
                .temperature(0.7F)  // 控制生成文本的随机性
                .apiKey(appKey)
                .build();

        // 执行文本生成操作，并等待结果完成
        return Mono.fromCallable(() -> {
            try {
                GenerationResult result = generation.call(param);
                String content = result.getOutput().getChoices().get(0).getMessage().getContent();
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("optimized_code", content.trim());  // 移除可能的空白字符
                return responseMap;
            } catch (NoApiKeyException | InputRequiredException e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "请求错误: " + e.getMessage());
                return errorResponse;
            } catch (Exception e) {
                // 其他异常处理
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "内部错误: " + e.getMessage());
                return errorResponse;
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/test")
    public String test(){
        return "hello";
    }
}