package com.oj.onlinejudge;

import com.oj.onlinejudge.util.CodeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnlineJudgeApplicationTests {

    @Test
    void contextLoads() {
        String code = "#include <iostream>\n" +
                "using namespace std;\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "   cout << \"Hello World   wedw\";\n" +
                "   return 0;\n" +
                "}";
        System.out.println(CodeFormatter.formatCode(code));
    }

}
