package com.awin.coffeebreak

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest(classes = AwinCoffeeBreakApplication.class)
class AwinCoffeeBreakApplicationSpec extends Specification {

    @Autowired
    WebApplicationContext context

    def "context should load"() {
        expect:
        context != null
    }

}
