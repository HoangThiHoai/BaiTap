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
            //System.out.println: ở giỏ hàng hiển thị số 2
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
            //System.out.println 1: Thông tin Your Cart hiển thị đúng 2 sản phẩm với tên và giá tiền đúng
            //System.out.println 2: Màn hình có hiển thị 2 button Remove
            //lấy thông tin tên, giá của sản phẩm ở màn hình danh sách
            WebElement elementDSNameProduct1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']"));
            WebElement elementDSNameProduct2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']"));
            WebElement elementDSPrice1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']"));
            WebElement elementDSPrice2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='inventory_item_description']/descendant::div[@class='inventory_item_price']"));

            //get text element màn danh sách
            String elementDSNameProduct1Text = elementDSNameProduct1.getText();
            String elementDSNameProduct2Text = elementDSNameProduct2.getText();
            String elementDSPrice1Text = elementDSPrice1.getText();
            String elementDSPrice2Text = elementDSPrice2.getText();

            WebElement elementClickShoppingCart = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
            elementClickShoppingCart.click();

            //lấy thông tin tên, giá của sản phẩm ở màn gior hàng
            WebElement elementCTNameProduct1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']"));
            WebElement elementCTNameProduct2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']"));
            WebElement elementCTPrice1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']"));
            WebElement elementCTPrice2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']"));
            WebElement elementCTQty1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='cart_quantity']"));
            WebElement elementCTQty2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='cart_quantity']"));

            //get text sản phẩm màn chi tết giỏ hàng
            String elementCTNameProduct1Text = elementCTNameProduct1.getText();
            String elementCTNameProduct2Text = elementCTNameProduct2.getText();
            String elementCTPrice1Text = elementCTPrice1.getText();
            String elementCTPrice2Text = elementCTPrice2.getText();
            String elementQty1Text = elementCTQty1.getText();
            String elementQty2Text = elementCTQty2.getText();

            //so sánh tên và giá màn danh sách và màn sản phẩm
            if (elementDSNameProduct1Text.equals(elementCTNameProduct1Text) && elementDSPrice1Text.equals(elementCTPrice1Text)) {
                System.out.println("PASS: Sản phẩm " + elementCTNameProduct1Text + " màn hình danh sách khớp với màn hình chi tiết giỏ hàng");
                System.out.println("Sản phẩm " + elementCTNameProduct1Text + " có giá là " + elementCTPrice1Text);

            } else {
                System.out.println("FAIL: Sản phẩm " + elementCTNameProduct1Text + " màn danh sách và màn chi tiết giỏ không giống nhau");
            }

            if (elementDSNameProduct2Text.equals(elementCTNameProduct2Text) && elementDSPrice2Text.equals(elementCTPrice2Text)) {

                System.out.println("PASS: Sản phẩm " + elementCTNameProduct2Text + " màn hình danh sách khớp với màn hình chi tiết giỏ hàng");
                System.out.println("Sản phẩm " + elementCTNameProduct2Text + " có giá là " + elementCTPrice2Text);

            } else {
                System.out.println("FAIL: Sản phẩm " + elementCTNameProduct2Text + " màn danh sách và màn chi tiết giỏ không giống nhau");
            }
            int count1 = 0;
            List<WebElement> listButtonRemove = driver.findElements(By.xpath("//button[text()='Remove']"));
            for (int i = 0; i < listButtonRemove.size(); i++) {
                if (!listButtonRemove.get(i).equals(null)) {
                    count1++;
                }

            }
            System.out.println("Số lượng button Remove là " + count1);
            //endregion
            //region Step 5: Click checkout và nhập các thông tin Firts name, Last name, Zip code
            WebElement elementCheckout = driver.findElement(By.xpath("//button[@class='btn btn_action btn_medium checkout_button ']"));
            elementCheckout.click();
            WebElement elementFristName = driver.findElement(By.xpath("//input[@id='first-name']"));
            elementFristName.sendKeys("Hoàng");
            WebElement elementLastName = driver.findElement(By.xpath("//input[@id='last-name']"));
            elementLastName.sendKeys("Hoài");
            WebElement elementZipCode = driver.findElement(By.xpath("//input[@id='postal-code']"));
            elementZipCode.sendKeys("123456");
            //endregion

            //region Step 6: Click continute
            //System.out.println 1 : Thông tin Description hiển thị đúng 2 sản phẩm với số lượng, tên và giá tiền đúng
            //System.out.println 2: Shipping Information hiển thị đúng "Free Pony Express Delivery!"
            //System.out.println 3: Price Total hiển thị đúng tổng tiền 2 sản phẩm
            //System.out.println 4: Total hiển thị đúng tổng tiền của Item total + Tax
            //System.out.println 5: Button Finish hiển thị

            WebElement elementContinue = driver.findElement(By.xpath("//input[@id='continue']"));
            elementContinue.click();
            WebElement elementOVNameProduct1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']"));
            WebElement elementOVNameProduct2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']"));
            WebElement elementOVPrice1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']"));
            WebElement elementOVPrice2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']"));
            WebElement elementOVQty1 = driver.findElement(By.xpath("//div[text()='" + itemName1 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='cart_quantity']"));
            WebElement elementOVQty2 = driver.findElement(By.xpath("//div[text()='" + itemName2 + "']/ancestor::div[@class='cart_item']/descendant::div[@class='cart_quantity']"));

            String elementOVNameProduct1Text = elementOVNameProduct1.getText();
            String elementOVNameProduct2Text = elementOVNameProduct2.getText();
            String elementOVPrice1Text = elementOVPrice1.getText();
            String elementOVPrice2Text = elementOVPrice2.getText();
            String elementOVQty1Text = elementOVQty1.getText();
            String elementOVQty2Text = elementOVQty2.getText();

            //so sánh màn giỏ hàng và overview
            if (elementCTNameProduct1Text.equals(elementOVNameProduct1Text) && elementCTPrice1Text.equals(elementOVPrice1Text) && elementDSPrice1Text.equals(elementOVPrice1Text)) {

                System.out.println("PASS: Sản phẩm " + elementOVNameProduct1Text + " màn hình overview khớp với màn hình chi tiết giỏ hàng");
                System.out.println("Sản phẩm " + elementOVNameProduct1Text + " có giá là " + elementOVPrice1Text + " số lượng " + elementOVQty1Text);

            } else {
                System.out.println("FAIL: Sản phẩm " + elementOVNameProduct1Text + " màn danh sách và màn chi tiết giỏ không giống nhau");
            }

            if (elementCTNameProduct2Text.equals(elementOVNameProduct2Text) && elementCTPrice2Text.equals(elementOVPrice2Text) && elementDSPrice2Text.equals(elementOVPrice2Text)) {
                System.out.println("PASS: Sản phẩm " + elementOVNameProduct2Text + " màn hình overview khớp với màn hình chi tiết giỏ hàng");
                System.out.println("Sản phẩm " + elementOVNameProduct2Text + " có giá là " + elementOVPrice2Text + " số lượng " + elementOVQty2Text);

            } else {
                System.out.println("FAIL: Sản phẩm " + elementOVNameProduct2Text + " màn danh sách và màn chi tiết giỏ không giống nhau");
            }

            //kiểm tra Shipping Information có =Free Pony Express Delivery!
            String shipInForExpect = "Free Pony Express Delivery!";
            WebElement elementShipInfor = driver.findElement(By.xpath("//div[text()='Free Pony Express Delivery!']"));
            if (elementShipInfor.getText().equals(shipInForExpect)) {
                System.out.println("PASS: Shipping Information " + elementShipInfor.getText());
            } else {
                System.out.println("FAIL: Shipping Information " + elementShipInfor.getText());
            }

            //tổng tiền mong muốn
            //xóa ký tự thừ $
            double priceTotal1Expect = Double.parseDouble(elementOVPrice1.getText().replace("$", "")) * Double.parseDouble(elementOVQty1.getText());
            double priceTotal2Expect = Double.parseDouble(elementOVPrice2.getText().replace("$", "")) * Double.parseDouble(elementOVQty2.getText());
            double priceTotalExpect = (priceTotal1Expect + priceTotal2Expect);

            //tổng tiền thực tế
            WebElement elementPriceActual = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"));
            String elementPriceActualText = elementPriceActual.getText().replace("Item total: $", "");

            //so sánh tổng tiền
            if (priceTotalExpect == Double.parseDouble(elementPriceActualText)) {
                System.out.println("PASS: Price " + elementPriceActualText);
            } else {
                System.out.println("FAIL: Price " + elementPriceActualText);
            }
            //tổng tax mong muốn
            double taxTotal1Expect = Double.parseDouble(elementOVPrice1.getText().replace("$", "")) * Double.parseDouble(elementOVQty1.getText()) * 0.08;
            double taxTotal2Expect = Double.parseDouble(elementOVPrice2.getText().replace("$", "")) * Double.parseDouble(elementOVQty2.getText()) * 0.08;
            double taxTotalExpect = Math.round((taxTotal1Expect + taxTotal2Expect)*100.00)/100.00;

            //tổng tax thực tế
            WebElement elementTaxActual = driver.findElement(By.xpath("//div[@class='summary_tax_label']"));
            String elementTaxActualText = elementTaxActual.getText().replace("Tax: $", "");

            if (taxTotalExpect == Double.parseDouble(elementTaxActualText)) {
                System.out.println("PASS: Tax Total " + elementTaxActualText);
            } else {
                System.out.println("FAIL: Tax Total " + elementTaxActualText);
            }

            //total thực tế
            WebElement elementTotal = driver.findElement(By.xpath("//div[@class='summary_total_label']"));
            String elementTotalText = elementTotal.getText().replace("Total: $", "");

            //toatl mong muốn
            double totalExpect = Math.round((priceTotalExpect + taxTotalExpect)*100.00)/100.00;
            if (totalExpect == Double.parseDouble(elementTotalText)) {
                System.out.println("PASS: Total " + elementTotalText);
            } else {
                System.out.println("FAIL: Total " + elementTotalText);
            }

            WebElement elementFinish = driver.findElement(By.xpath("//button[@id='finish']"));
            System.out.println("PASS: Button Finish hiển thị " + elementFinish.getText());

            //endregion
            //region Step 7: Click Finish
            //System.out.println 1 : hiển thị "Checkout: Complete!"
            //System.out.println 2 : hiển thị "Thank you for your order!"
            //System.out.println 3 : hiển thị "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
            //System.out.println 3 : hiển thị button Back Home

            elementFinish.click();
            WebElement elementLabel1Actual = driver.findElement(By.xpath("//span[text()='Checkout: Complete!']"));
            String lable1Expected = "Checkout: Complete!";
            if (elementLabel1Actual.getText().equals(lable1Expected)) {
                System.out.println("PASS: Label 1 " + elementLabel1Actual.getText());
            }else {
                System.out.println("FAIL: Label 1 " + elementLabel1Actual.getText());
            }

            WebElement elementLable2Actual = driver.findElement(By.xpath("//h2[text()='Thank you for your order!']"));
            String lable2Expected = "Thank you for your order!";
            if (elementLable2Actual.getText().equals(lable2Expected)) {
                System.out.println("PASS: Label 2 " + elementLable2Actual.getText());
            }else {
                System.out.println("FAIL: Label 2 " + elementLable2Actual.getText());
            }

            WebElement elementLable3Actual =driver.findElement(By.xpath("//div[text()='Your order has been dispatched, and will arrive just as fast as the pony can get there!']"));
            String lable3Expected = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
            if (elementLable3Actual.getText().equals(lable3Expected)) {
                System.out.println("PASS: Label 3 " + elementLable3Actual.getText());
            }else {
                System.out.println("FAIL: Label 3 " + elementLable3Actual.getText());
            }
            WebElement elementBack = driver.findElement(By.xpath("//button[@id='back-to-products']"));
            System.out.println("PASS: Back To Products " + elementBack.getText());
            //endregion


        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            driver.quit();
        }
    }
}
