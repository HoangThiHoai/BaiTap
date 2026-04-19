package HapyCaseCheckoutSuccess;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {
    static void main(String[] args) {
        //tắt popup change paswword của chomre
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        try {

            driver.manage().window().maximize();

            //region Step1 : Login vào https://www.saucedemo.com/
            driver.get("https://www.saucedemo.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement elementUserName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-name']")));
            elementUserName.sendKeys("standard_user");

            WebElement elementPassWord = driver.findElement(By.xpath("//input[@id='password']"));
            elementPassWord.sendKeys("secret_sauce");

            WebElement elementLogin = driver.findElement(By.xpath("//input[@id='login-button']"));
            elementLogin.click();
            Thread.sleep(3000);
            System.out.println("Bạn đang ở màn hình " + driver.getTitle() + " có URL là: " + driver.getCurrentUrl());
            //endregion

            //region Step 2: Chọn tìm kiếm droplist Price (low to high)
            Select selectSort = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
            selectSort.selectByIndex(2);
            //endregion

            //region Step 3: Add to cart 2 sản phẩm bất kì
            //kiểm tra xem có element giỏ hàng không. Nếu không có gán=0
            String itemName1 = "Sauce Labs Backpack";
            String itemName2 = "Sauce Labs Bolt T-Shirt";
            List<WebElement> elementShoppingCart = driver.findElements(By.xpath("//span[@class='shopping_cart_badge']"));
            int quantyShoppingCartBefor = 0;
            if (!elementShoppingCart.isEmpty()) {
                quantyShoppingCartBefor = Integer.parseInt(elementShoppingCart.getFirst().getText());
            }

            int count = 0;
            WebElement elementAddToCart1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='inventory_item_description']/descendant::button"));
            elementAddToCart1.click();
            count++;

            WebElement elementAddToCart2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='inventory_item_description']/descendant::button"));
            elementAddToCart2.click();
            count++;

            //lấy số lượng giỏ hàng sau khi thêm saản phẩm
            WebElement elementShoppingCartAfter = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
            int quantyShoppingCartAfter = Integer.parseInt(elementShoppingCartAfter.getText());
            //so sánh số lượng vừa thêm với số lượng ở icon giỏ hàng
            if (quantyShoppingCartAfter - quantyShoppingCartBefor == count) {
                System.out.println("Có " + quantyShoppingCartAfter + " sản phẩm trong giỏ hàng");

            } else {
                System.out.println("FAIL: Số lượng sản phẩm vừa thêm không khớp số lượng trong giỏ hàng");
            }
            //endregion

            //region Step 4: Click vào giỏ hàng
            WebElement elementClickShoppingCart =driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
            elementClickShoppingCart.click();
            //lấy thông tin tên, giá của sản phẩm ở màn hình danh sách
            WebElement elementNameProduct1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']"));
            WebElement elementNameProduct2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']"));
            WebElement elementPrice1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']"));
            WebElement elementPrice2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']"));

            //lấy thông tin tên, giá của sản phẩm ở màn gior hàng


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
