package com.oj.onlinejudge.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : F
 * @项目名称 : OnlineJudge
 * @创建者 : flower
 * @date : 2024/11/22 下午11:46
 */
public class Judge0Response {
    @JsonProperty("stdout")
    private String stdout;

    @JsonProperty("time")
    private String time;

    @JsonProperty("memory")
    private int memory;

    @JsonProperty("stderr")
    private String stderr;

    @JsonProperty("token")
    private String token;

    @JsonProperty("compile_output")
    private String compileOutput;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private Status status;

    // Getter and Setter methods
    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCompileOutput() {
        return compileOutput;
    }

    public void setCompileOutput(String compileOutput) {
        this.compileOutput = compileOutput;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Status {
        @JsonProperty("id")
        private int id;

        @JsonProperty("description")
        private String description;

        // Getter and Setter methods
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
