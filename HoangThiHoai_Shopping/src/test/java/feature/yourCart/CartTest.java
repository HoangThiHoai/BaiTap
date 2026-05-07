package feature.yourCart;

import action.*;
import feature.ui.CartPageUI;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.BaseTest;


public class CartTest extends BaseTest {

    @BeforeMethod
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductPage productPage = new ProductPage(driver);
        String targetName = "Sauce Labs Backpack";
        String targetName2 = "Sauce Labs Bike Light";
        productPage.clickAddToCart(targetName);
        productPage.clickAddToCart(targetName2);
        driver.findElement(CartPageUI.CART_ICON).click();

    }

    @Test
    public void verifyUICartPage() {

        WebElement title = driver.findElement(CartPageUI.TITLE);
        String actualTitle = title.getText();
        Assert.assertEquals(actualTitle, "Your Cart");
        Assert.assertTrue(title.isDisplayed(), "Title Products không displayed");

        WebElement logo = driver.findElement(CartPageUI.APP_LOGO);
        String actualLogo = logo.getText();
        Assert.assertEquals(actualLogo, "Swag Labs");
        Assert.assertTrue(logo.isDisplayed(), "Logo không displayed");

        WebElement textQty = driver.findElement(CartPageUI.TEXT_QTY);
        String actualTextQty = textQty.getText();
        Assert.assertEquals(actualTextQty, "QTY");
        Assert.assertTrue(textQty.isDisplayed(), "QTY không displayed");

        WebElement textDesc = driver.findElement(CartPageUI.TEXT_DESCRIPTION);
        String actualTextDesc = textDesc.getText();
        Assert.assertEquals(actualTextDesc, "Description");
        Assert.assertTrue(textDesc.isDisplayed(), "Description không displayed");

        //Kiểm tra số lượng trên icon giỏ hàng
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Giỏ hàng không hiển thị");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2", "Số lượng giỏ hàng không khớp");

        //kiểm tra sản phẩm
        String targetName = "Sauce Labs Backpack";
        Assert.assertEquals(driver.findElement(CartPageUI.getItemName(targetName)).getText(), "Sauce Labs Backpack");
        Assert.assertTrue(driver.findElement(CartPageUI.getItemName(targetName)).isDisplayed(), "Tên sản phẩm chưa hiển thị");
        driver.findElement(CartPageUI.getItemName(targetName)).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"));
        driver.navigate().back();

        Assert.assertEquals(driver.findElement(CartPageUI.getItemPrice(targetName)).getText(), "$29.99");
        Assert.assertTrue(driver.findElement(CartPageUI.getItemPrice(targetName)).isDisplayed(), "Text giá bán chưa hiển thị");

        Assert.assertEquals(driver.findElement(CartPageUI.getItemDescription(targetName)).getText(), "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertTrue(driver.findElement(CartPageUI.getItemDescription(targetName)).isDisplayed(), "Text Mô tả chưa hiển thị");

        Assert.assertEquals(driver.findElement(CartPageUI.getRemoveButton(targetName)).getText(), "Remove");
        Assert.assertTrue(driver.findElement(CartPageUI.getRemoveButton(targetName)).isDisplayed(), "Button Remove chưa hiển thị");


        //Kiểm tra text link Continue Shopping
        String continueShoppingText=driver.findElement(CartPageUI.CONTINUE_SHOPPING_BUTTON).getText();
        Assert.assertEquals(continueShoppingText,"Continue Shopping","Text button Continue Shopping chưa khớp");

        //kiểm tra điều hướng click Continue Shopping
        cartPage.clickContinueShopping();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        driver.navigate().back();

        //Kiểm tra text link checkout
        String checkoutText=driver.findElement(CartPageUI.CHECKOUT_BUTTON).getText();
        Assert.assertEquals(checkoutText,"Checkout","Text button Continue Shopping chưa khớp");

        //kiểm tra điều hướng click checkout
        cartPage.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"));


    }
    @Test
    public void testEmptyCart() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickContinueShopping(); // Quay lại trang sản phẩm từ giỏ hàng

        ProductPage productPage = new ProductPage(driver);
        // Vì BeforeMethod đã thêm 2 sản phẩm này, click lại sẽ là REMOVE
        productPage.clickAddToCart("Sauce Labs Backpack");
        productPage.clickAddToCart("Sauce Labs Bike Light");
        productPage.clickCart();

        Assert.assertTrue(cartPage.isCartEmpty(), "Giỏ hàng phải trống sau khi click remove hết sản phẩm");
    }
    @Test
    public void testRemoveAllItemsAndVerifyBadge() {
        CartPage cartPage = new CartPage(driver);
        // Ở trang Cart (do BeforeMethod), ta thực hiện xóa luôn
        cartPage.clickRemoveButton("Sauce Labs Backpack");
        cartPage.clickRemoveButton("Sauce Labs Bike Light");

        // 3. Verify badge biến mất hoàn toàn
        Assert.assertFalse(cartPage.isCartBadgeDisplayed(), "Badge không được hiển thị khi giỏ hàng trống");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "", "Badge count phải rỗng");
    }

    @Test
    public void verifyProductDetailPageWithCartPage() {
        CartPage cartPage = new CartPage(driver);
        String targetProductName = "Sauce Labs Backpack";

        // 1. Lấy dữ liệu từ trang CartPage
        String cartName = driver.findElement(CartPageUI.getItemName(targetProductName)).getText();
        String cartDesc = driver.findElement(CartPageUI.getItemDescription(targetProductName)).getText();
        String cartPrice = driver.findElement(CartPageUI.getItemPrice(targetProductName)).getText();
        String cartButtonText = driver.findElement(CartPageUI.getRemoveButton(targetProductName)).getText();

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
        Assert.assertEquals(cartPage.getCartItemsCount(), 2, "Số lượng item trong DOM không khớp");

        //4. Xóa sản phẩm vừa thêm
        cartPage.clickRemoveButton(targetName);

        // 5. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Giỏ hàng không hiển thị");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "1", "Số lượng giỏ hàng không khớp");
        Assert.assertEquals(cartPage.getCartItemsCount(), 1, "Sản phẩm chưa thực sự bị xóa khỏi DOM");

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

    @Test

    public void testCartWithMaxItems() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickContinueShopping();

        ProductPage productPage = new ProductPage(driver);
        // Lấy tất cả tên sản phẩm và thêm những cái chưa có
        java.util.List<String> allProducts = productPage.getAllItemNames();
        for (String name : allProducts) {
            // Check if button is "Add to cart" not "Remove"
            // Tuy nhiên để đơn giản trong kịch bản này, ta có thể reset hoặc chỉ add những cái còn thiếu
            // Ở BeforeMethod đã add Backpack và Bike Light
            if (!name.equals("Sauce Labs Backpack") && !name.equals("Sauce Labs Bike Light")) {
                productPage.clickAddToCart(name);
            }
        }
        productPage.clickCart();

        Assert.assertEquals(cartPage.getCartBadgeCount(), "6", "Số lượng badge không phải 6 khi add max items");
    }

    @Test
    public void testCheckoutWithEmptyCart() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickRemoveButton("Sauce Labs Backpack");
        cartPage.clickRemoveButton("Sauce Labs Bike Light");

        Assert.assertTrue(cartPage.isCartEmpty(), "Giỏ hàng phải trống");
        
        cartPage.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Vẫn phải cho phép đi đến trang checkout thông tin");
    }

    @Test
    public void testItemQuantityIsAlwaysOne() {
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getItemQuantity("Sauce Labs Backpack"), "1", "Quantity không phải là 1");
        Assert.assertEquals(cartPage.getItemQuantity("Sauce Labs Bike Light"), "1", "Quantity không phải là 1");
    }

    @Test
    public void testCartStateAfterRefresh() {
        CartPage cartPage = new CartPage(driver);
        
        // 1. Verify trạng thái trước khi refresh
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2");
        Assert.assertEquals(cartPage.getCartItemsCount(), 2);

        // 2. Refresh trang
        driver.navigate().refresh();

        // 3. Verify trạng thái sau khi refresh
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2", "Badge bị mất sau khi refresh");
        Assert.assertEquals(cartPage.getCartItemsCount(), 2, "Sản phẩm bị mất khỏi DOM sau khi refresh");
    }

}

