package com.oj.onlinejudge;

import com.oj.onlinejudge.common.AdvancedCodingStyleChecker;
import com.oj.onlinejudge.util.CodeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OnlineJudgeApplicationTests {

    @BeforeAll
    public static void setup() {
        // 确保模型已经初始化
        System.out.println("Model initialized successfully.");
    }


    @Test
    void testIsPoorStyle() throws Exception {
        String code = "#include <iostream>\\n#include <vector>\\n#include <map>\\n#include <string>\\n#include <algorithm>\\n\\nusing namespace std;\\n\\nclass Person {\\npublic:\\n    string name;\\n    int age;\\n    string city;\\n\\n    Person(string name, int age, string city) : name(name), age(age), city(city) {}\\n\\n    void display() const {\\n        cout << \\\"Name: \\\" << name << \\\", Age: \\\" << age << \\\", City: \\\" << city << endl;\\n    }\\n};\\n\\n// 函数声明\\nvoid displayPeople(const vector<Person>& people);\\nvoid displayAgeStatistics(const vector<Person>& people);\\nvoid displayCityDistribution(const vector<Person>& people);\\n\\nint main() {\\n    // 创建一些 Person 对象\\n    vector<Person> people = {\\n        Person(\\\"Alice\\\", 30, \\\"New York\\\"),\\n        Person(\\\"Bob\\\", 25, \\\"Los Angeles\\\"),\\n        Person(\\\"Charlie\\\", 35, \\\"Chicago\\\"),\\n        Person(\\\"David\\\", 28, \\\"New York\\\"),\\n        Person(\\\"Eve\\\", 32, \\\"Los Angeles\\\")\\n    };\\n\\n    // 显示所有人的信息\\n    displayPeople(people);\\n\\n    // 显示年龄统计信息\\n    displayAgeStatistics(people);\\n\\n    // 显示城市分布信息\\n    displayCityDistribution(people);\\n\\n    return 0;\\n}\\n\\n// 显示所有人的信息\\nvoid displayPeople(const vector<Person>& people) {\\n    cout << \\\"People in the list:\\\" << endl;\\n    for (const auto& person : people) {\\n        person.display();\\n    }\\n}\\n\\n// 显示年龄统计信息\\nvoid displayAgeStatistics(const vector<Person>& people) {\\n    if (people.empty()) {\\n        cout << \\\"No people to display age statistics.\\\" << endl;\\n        return;\\n    }\\n\\n    int minAge = people[0].age;\\n    int maxAge = people[0].age;\\n    double totalAge = 0;\\n\\n    for (const auto& person : people) {\\n        if (person.age < minAge) {\\n            minAge = person.age;\\n        }\\n        if (person.age > maxAge) {\\n            maxAge = person.age;\\n        }\\n        totalAge += person.age;\\n    }\\n\\n    double averageAge = totalAge / people.size();\\n\\n    cout << \\\"Age Statistics:\\\" << endl;\\n    cout << \\\"Minimum Age: \\\" << minAge << endl;\\n    cout << \\\"Maximum Age: \\\" << maxAge << endl;\\n    cout << \\\"Average Age: \\\" << averageAge << endl;\\n}\\n\\n// 显示城市分布信息\\nvoid displayCityDistribution(const vector<Person>& people) {\\n    map<string, int> cityCounts;\\n\\n    for (const auto& person : people) {\\n        cityCounts[person.city]++;\\n    }\\n\\n    cout << \\\"City Distribution:\\\" << endl;\\n    for (const auto& entry : cityCounts) {\\n        cout << entry.first << \\\": \\\" << entry.second << \\\" people\\\" << endl;\\n    }\\n}";

        boolean isPoorStyle = AdvancedCodingStyleChecker.hasPoorCodingStyle(code);
        System.out.println("Is the code style poor? " + isPoorStyle);
        assertTrue(isPoorStyle, "The code style should be poor");
    }

    @Test
    void testIsGoodStyle() throws Exception {
        String code = """
                #include <iostream>

                int main() {
                    std::cout << "Hello World" << std::endl;
                    int a = 10;
                    int b = 20;
                    int c = a + b;
                    return 0;
                }
                """;

        boolean isGoodStyle = !AdvancedCodingStyleChecker.hasPoorCodingStyle(code);
        System.out.println("Is the code style good? " + isGoodStyle);
        assertTrue(isGoodStyle, "The code style should be good");
    }
}