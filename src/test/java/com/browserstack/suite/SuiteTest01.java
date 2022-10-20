package com.browserstack.suite;
import com.browserstack.BrowserStackTest;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class SuiteTest01 extends BrowserStackTest {

    @Test
    public void test() throws Exception {

        open("https://www.bstackdemo.com");
        sleep(2000);

        String selectedProduct = $(By.xpath("//*[@id=\"1\"]/p")).text();
        $(By.xpath("//*[@id=\"1\"]/div[4]")).click();
        sleep(2000);

        // waiting for cart to load
        while(!$(".float-cart__content").isDisplayed()) {
            sleep(1000);
        }

        String productInCart = $(
            By.xpath(
                "//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]"
            )
        ).text();

        // assert the product clicked has been added to cart
        Assert.assertEquals(selectedProduct, productInCart);
    }
}
