package com.awin.coffeebreak;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AwinCoffeeBreakApplicationTest {

    @Autowired
    WebApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }
}