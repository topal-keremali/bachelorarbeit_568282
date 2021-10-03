package com.htwberlin.azebe;

import com.htwberlin.azebe.model.Role;
import com.htwberlin.azebe.model.Shift;
import com.htwberlin.azebe.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestVariables {

    public static User one = new User(1, "test user", Role.EMPLOYEE, "test_user_1", "123456");
    public static User two = new User(2, "test user", Role.EMPLOYEE, "test_user_2", "123456");
    public static User admin = new User(3, "admin test user", Role.IT_ADMIN, "test_user_2", "123456");
    public static User employer = new User(3, "employer test user", Role.EMPLOYER, "test_user_3", "123456");
    public static Shift shiftOne = new Shift(1, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1).plusHours(1), one);
    public static Shift shiftTwo = new Shift(1, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2).plusHours(1), one);
    public static Shift closedShift = new Shift(2, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2).plusHours(1), two);
    public static Shift openShift = new Shift(LocalDateTime.now().minusMinutes(6), one);
    public static Shift blockedShift = new Shift(LocalDateTime.now(), one);
    public static List<Shift> shiftList = new ArrayList<>() {{
        add(shiftOne);
        add(closedShift);
        add(openShift);
    }};

    public static String validAdminJwt = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsIm5hbWUiOiJBZG1pbiIsImlzcyI6ImF6ZS1iZSIsImlkIjo0MSwidXIiOiJhZG1pbkBBZG1pbiJ9.JBJt5qAme6qbmtmjnOZQqwRlM6crQdknUgudMatWCTcZkj0i1_CVS_VgRprA-Q7TZRbH4hBLs8GsM2HiJ-WNmw";
    //id=2
    public static String validEmployeeJwt = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0cGVyc29uMSIsIm5hbWUiOiJUZXN0cGVyc29uMSIsImlzcyI6ImF6ZS1iZSIsImlkIjoyLCJ1ciI6InRlc3RwZXJzb24xQFRlc3RwZXJzb24xIn0.Vd9CZ0n1l5Kmoi_7Fcyd9L5B5vEzQe2D3obrEIUe70jG6NiVQT7yGEBMBnA1jYWLprVfeQElrrkAm0b6u0Rl4Q";

}
