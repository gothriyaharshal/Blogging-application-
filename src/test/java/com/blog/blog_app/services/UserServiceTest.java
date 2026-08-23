package com.blog.blog_app.services;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserServiceTest {
    //we have to write test cases here

    //here i want to write that particular method name which i want to test



    @BeforeAll
    public static void init()
    {
        System.out.println("inti");
        System.out.println("Started at" + LocalDateTime.now());
    }

    @BeforeEach
    public  void beforeEachTestCase()
    {
        System.out.println("beforeEachTestCase");
    }

    @Test
    public void addTwoNumbers_Test() {


        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6);

        Assertions.assertIterableEquals(list2,list);
       // list.add(23);
       // list.add(4);

        for (Integer integer : list) {
            System.out.println(integer);
        }

    }
    @AfterAll
    public static void desearilization()
    {
        System.out.println("After all test cases");
    }
}
