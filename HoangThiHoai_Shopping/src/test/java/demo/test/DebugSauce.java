package demo.test;

import demo.pages.DemoLoginPage;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class DebugSauce extends DemoBaseTest {

    @Test
    public void debugLocators() throws Exception {
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        
        Thread.sleep(1000);
        
        // Go to Product Detail
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']/..")).click();
        Thread.sleep(1000);
        System.out.println("=== PRODUCT DETAIL BUTTONS ===");
        driver.findElements(By.tagName("button")).forEach(b -> {
            System.out.println("Button text: " + b.getText() + " | id: " + b.getAttribute("id") + " | name: " + b.getAttribute("name"));
        });
        
        // Go back
        driver.findElement(By.xpath("//button[contains(text(), 'Back')]")).click();
        Thread.sleep(1000);
        
        // Go to Cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);
        System.out.println("=== CART PAGE BUTTONS ===");
        driver.findElements(By.tagName("button")).forEach(b -> {
            System.out.println("Button text: " + b.getText() + " | id: " + b.getAttribute("id") + " | name: " + b.getAttribute("name"));
        });
    }
}
