package feature.yourCart;

import action.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ChromeOptionsUtils;


public class CartTest {
    WebDriver driver;

    @BeforeMethod
    public void loginBeforeTest() {
        ChromeOptions chromeOptions = ChromeOptionsUtils.GetChromeOptionsUtils();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductPage productPage = new ProductPage(driver);
        String targetName = "Sauce Labs Backpack";
        String targetName2 = "Sauce Labs Bike Light";
        productPage.clickAddToCart(targetName);
        productPage.clickAddToCart(targetName2);
        By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");
        driver.findElement(cartIcon).click();

    }

    @Test
    public void verifyTextCartPage() {

        WebElement title = driver.findElement(By.xpath("//span[@data-test='title']"));
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle, "Your Cart");
        Assert.assertTrue(title.isDisplayed(), "Title Products không disable");

        WebElement logo = driver.findElement(By.xpath("//div[@class='app_logo']"));
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo, "Swag Labs");
        Assert.assertTrue(logo.isDisplayed(), "Logo không disable");

        WebElement textQty = driver.findElement(By.xpath("//div[@class='cart_quantity_label']"));
        String actualTextQty = textQty.getText();
        Assert.assertEquals(actualTextQty, "QTY");
        Assert.assertTrue(textQty.isDisplayed(), "QTY không disable");

        WebElement textDesc = driver.findElement(By.xpath("//div[@class='cart_desc_label']"));
        String actualTextDesc = textDesc.getText();
        Assert.assertEquals(actualTextDesc, "Description");
        Assert.assertTrue(textDesc.isDisplayed(), "Description không disable");

        //Kiểm tra số lượng trên icon giỏ hàng
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Giỏ hàng không hiển thị");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2", "Số lượng giỏ hàng không khớp");

        //kiểm tra sản phẩm
        String targetName = "Sauce Labs Backpack";
        Assert.assertEquals(cartPage.getItemName(targetName).getText(), "Sauce Labs Backpack");
        Assert.assertTrue(cartPage.getItemName(targetName).isDisplayed(), "Tên sản phẩm chưa hiển thị");
        cartPage.getItemName(targetName).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"));
        driver.navigate().back();

        Assert.assertEquals(cartPage.getItemPrice(targetName).getText(), "$29.99");
        Assert.assertTrue(cartPage.getItemName(targetName).isDisplayed(), "Text giá bán chưa không hiển thị");

        Assert.assertEquals(cartPage.getItemDescription(targetName).getText(), "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertTrue(cartPage.getItemDescription(targetName).isDisplayed(), "Text Mô tả chưa hiển thị");

        Assert.assertEquals(cartPage.getButtonText(targetName).getText(), "Remove");
        Assert.assertTrue(cartPage.getButtonText(targetName).isDisplayed(), "Button Remove chưa hiển thị");

        //Kiểm tra text 2 button, số lượng

        //kiểm tra case xóa hết (các màn)

    }

    @Test
    public void verifyProductDetailPageWithCartPage() {
        CartPage cartPage = new CartPage(driver);
        String targetProductName = "Sauce Labs Backpack";

        // 1. Lấy dữ liệu từ trang CartPage
        String cartName = cartPage.getItemName(targetProductName).getText();
        String cartDesc = cartPage.getItemDescription(targetProductName).getText();
        String cartPrice = cartPage.getItemPrice(targetProductName).getText();
        String cartButtonText = cartPage.getButtonText(targetProductName).getText();

        // 2. Click mở trang Detail
        cartPage.clickProductByName(targetProductName);

        // 3. Lấy dữ liệu từ trang Detail
        ProductDetailPage detailPage = new ProductDetailPage(driver);

        // 4. Kiểm tra chéo
        Assert.assertEquals(detailPage.getItemName().getText(), cartName, "Tên sản phẩm không khớp!");
        Assert.assertEquals(detailPage.getItemDescription().getText(), cartDesc, "Mô tả không khớp!");
        Assert.assertEquals(detailPage.getItemPrice().getText(), cartPrice, "Giá không khớp!");
        Assert.assertEquals(detailPage.getButtonRemoveText().getText(), cartButtonText, "Button không khớp");

        // 5. Quay lại trang chủ
        detailPage.clickBackToProducts();
    }

    @Test
    public void testRemoveFromCartPage() {
        CartPage cartPage = new CartPage(driver);
        String targetName = "Sauce Labs Backpack";

        // 3. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Giỏ hàng không hiển thị");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2", "Số lượng giỏ hàng không khớp");

        //4. Xóa sản phẩm vừa thêm
        cartPage.clickRemoveButton(targetName);

        // 5. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Giỏ hàng không hiển thị");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "1", "Số lượng giỏ hàng không khớp");

    }

    @Test
    public void testRemoveFormProductDetail() {
        CartPage cartPage = new CartPage(driver);
        String targetProductName = "Sauce Labs Backpack";

        // 1. Click mở trang Detail
        cartPage.clickProductByName(targetProductName);

        // 2. Click Remove từ trang detail
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);
        productDetailPage.clickRemove();

        // 3. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(productDetailPage.isCartBadgeDisplayed(), "Giỏ hàng không hiển thị");
        Assert.assertEquals(productDetailPage.getCartBadgeCount(), "1", "Số lượng giỏ hàng không khớp");

    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
