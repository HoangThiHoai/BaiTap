package feature.products;

import action.LoginPage;
import action.ProductDetailPage;
import action.ProductPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductTest {
    WebDriver driver;
    @BeforeMethod
    public void loginBeforeTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }
    @Test
    public void sortProductByName(){
        ProductPage productPage = new ProductPage(driver);
        // Sort Z to A
        productPage.selectSortOption("Name (Z to A)");
        List<String> actualNamesDesc = productPage.getAllItemNames();
        List<String> expectedNamesDesc = new ArrayList<>(actualNamesDesc);
        expectedNamesDesc.sort(Collections.reverseOrder());
        Assert.assertEquals(actualNamesDesc,expectedNamesDesc,"Chưa sắp xếp sản phẩm đúng theo thứ tự Z -->A");

        // Sort A to Z
        productPage.selectSortOption("Name (A to Z)");
        List<String> actualNamesAsc = productPage.getAllItemNames();
        List<String> expectedNamesAsc = new ArrayList<>(actualNamesAsc);
        Collections.sort(expectedNamesAsc);
        Assert.assertEquals(actualNamesAsc,expectedNamesAsc,"Chưa sắp xếp sản phẩm đúng theo thứ tự A -->Z");
    }
    @Test
    public void sortProductByPrice(){
        ProductPage productPage = new ProductPage(driver);

        // Sort Low to High
        productPage.selectSortOption("Price (low to high)");
        List<Double> actualPricesAsc = productPage.getAllItemPrices();
        List<Double> expectedPricesAsc = new ArrayList<>(actualPricesAsc);
        Collections.sort(expectedPricesAsc);
        Assert.assertEquals(actualPricesAsc, expectedPricesAsc, "Chưa sắp xếp sản phẩm đúng theo thứ tự Price (low to high)");

        // Sort High to Low
        productPage.selectSortOption("Price (high to low)");
        List<Double> actualPricesDesc = productPage.getAllItemPrices();
        List<Double> expectedPricesDesc = new ArrayList<>(actualPricesDesc);
        expectedPricesDesc.sort(Collections.reverseOrder());
        Assert.assertEquals(actualPricesDesc, expectedPricesDesc, "Chưa sắp xếp sản phẩm đúng theo thứ tự Price (high to low)");

    }
    @Test
    public void verifyTextProductPage(){

        WebElement title = driver.findElement(By.xpath("//span[@data-test='title']"));
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle,"Products");
        Assert.assertTrue(title.isDisplayed(),"Title Products không disable");

        WebElement logo= driver.findElement(By.xpath("//div[@class='app_logo']"));
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo,"Swag Labs");
        Assert.assertTrue(logo.isDisplayed(),"Logo không disable");

        ProductPage productPage = new ProductPage(driver);
        String targetName ="Sauce Labs Backpack";
        Assert.assertEquals(productPage.getItemName(targetName).getText(),"Sauce Labs Backpack");
        Assert.assertTrue(productPage.getItemName(targetName).isDisplayed(),"Tên sản phẩm chưa hiển thị");
        productPage.getItemName(targetName).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"));
        driver.navigate().back();

        Assert.assertEquals(productPage.getItemPrice(targetName).getText(),"$29.99");
        Assert.assertTrue(productPage.getItemName(targetName).isDisplayed(),"Text giá bán chưa không hiển thị");

        Assert.assertEquals(productPage.getItemDescription(targetName).getText(),"carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertTrue(productPage.getItemDescription(targetName).isDisplayed(),"Text Mô tả chưa hiển thị");

        Assert.assertTrue(productPage.getItemImage(targetName).getAttribute("src").contains("sauce-backpack"));
        Assert.assertTrue(productPage.getItemImage(targetName).isDisplayed(),"Ảnh đang chưa hiển thị");
//        productPage.getItemImage(targetName).click();
//        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"));
//        driver.navigate().back();

        Assert.assertEquals(productPage.getButtonText(targetName).getText(),"Add to cart");
        Assert.assertTrue(productPage.getButtonText(targetName).isDisplayed(),"Button Add to cart chưa hiển thị");

        productPage.clickCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));
        driver.navigate().back();

    }
    @Test
    public void testProductAddToCart() {
        ProductPage productPage = new ProductPage(driver);
        String targetName = "Sauce Labs Backpack";

        // 1. Kiểm tra ban đầu giỏ hàng trống (nếu login mới)
        Assert.assertFalse(productPage.isCartBadgeDisplayed(), "Cart badge should not be displayed initially");

        // 2. Click Add to Cart
        productPage.clickAddToCart(targetName);

        // 3. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(productPage.isCartBadgeDisplayed(), "Cart badge should be displayed");
        Assert.assertEquals(productPage.getCartBadgeCount(), "1", "Cart badge count should be 1");
    }
    @Test
    public void verifyProductDetailPageWithProductPage() {
        ProductPage productPage = new ProductPage(driver);
        String targetProductName = "Sauce Labs Backpack";

        // 1. Lấy dữ liệu từ trang Inventory
        String inventoryName = productPage.getItemName(targetProductName).getText();
        String inventoryDesc = productPage.getItemDescription(targetProductName).getText();
        String inventoryPrice = productPage.getItemPrice(targetProductName).getText();

        // 2. Click mở trang Detail
        productPage.clickProductByName(targetProductName);

        // 3. Lấy dữ liệu từ trang Detail
        ProductDetailPage detailPage = new ProductDetailPage(driver);

        // 4. Kiểm tra chéo (Cross-verification)
        Assert.assertEquals(detailPage.getItemName().getText(), inventoryName, "Tên sản phẩm không khớp!");
        Assert.assertEquals(detailPage.getItemDescription().getText(), inventoryDesc, "Mô tả không khớp!");
        Assert.assertEquals(detailPage.getItemPrice().getText(), inventoryPrice, "Giá không khớp!");

        // 5. Quay lại trang chủ
        detailPage.clickBackToProducts();
    }
    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {
            driver.quit();
        }
    }

}
