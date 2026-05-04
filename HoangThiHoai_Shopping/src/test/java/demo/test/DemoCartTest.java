package demo.test;

import demo.pages.DemoCartPage;
import demo.pages.DemoInventoryPage;
import demo.pages.DemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DemoCartTest extends DemoBaseTest {

    @BeforeMethod
    public void setupAndLogin() {
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    public void testDeepCartVerificationAndRemove() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        
        // 1. Lấy dữ liệu từ Inventory trước khi thêm vào giỏ
        String invPrice1 = inventoryPage.getItemPrice(product1);
        String invPrice2 = inventoryPage.getItemPrice(product2);
        
        inventoryPage.clickAddToCart(product1);
        inventoryPage.clickAddToCart(product2);
        
        // 2. Vào giỏ hàng
        inventoryPage.clickCart();
        DemoCartPage cartPage = new DemoCartPage(driver);
        
        // 3. Kiểm tra số lượng
        List<String> cartItems = cartPage.getCartItemNames();
        Assert.assertEquals(cartItems.size(), 2, "Cart item count mismatch");
        
        // 4. Kiểm tra chéo dữ liệu sản phẩm 1
        Assert.assertEquals(cartPage.getItemName(product1), product1);
        Assert.assertEquals(cartPage.getItemPrice(product1), invPrice1);
        Assert.assertEquals(cartPage.getQuantityByName(product1), 1);

        // 5. Kiểm tra chéo dữ liệu sản phẩm 2
        Assert.assertEquals(cartPage.getItemName(product2), product2);
        Assert.assertEquals(cartPage.getItemPrice(product2), invPrice2);
        Assert.assertEquals(cartPage.getQuantityByName(product2), 1);
        
        // 6. Test chức năng Remove
        cartPage.clickRemoveByName(product1);
        Assert.assertFalse(cartPage.isProductInCart(product1), "Product 1 was not removed from cart");
        Assert.assertEquals(cartPage.getCartItemNames().size(), 1, "Cart should only have 1 item left");
        
        // 7. Test Continue Shopping
        cartPage.clickContinueShopping();
    }

    @Test
    public void testEmptyCart() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        inventoryPage.clickCart();
        
        DemoCartPage cartPage = new DemoCartPage(driver);
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty initially");
    }

    @Test
    public void testCartIconBadgeCountInitial() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        // 1. Kiểm tra ban đầu không có số
        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed(), "Cart badge should not be displayed initially");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "", "Cart badge count should be empty");
        
        // 2. Thêm 1 sản phẩm
        inventoryPage.clickAddToCart("Sauce Labs Backpack");
        Assert.assertTrue(inventoryPage.isCartBadgeDisplayed(), "Cart badge should be displayed after adding product");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1", "Cart badge count should be 1");
    }

    @Test
    public void testRemovePartialItemsAndVerifyBadge() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        // 1. Thêm 3 sản phẩm
        inventoryPage.clickAddToCart("Sauce Labs Backpack");
        inventoryPage.clickAddToCart("Sauce Labs Bike Light");
        inventoryPage.clickAddToCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "3", "Badge count should be 3");
        
        // 2. Vào giỏ hàng và xóa 1 sản phẩm
        inventoryPage.clickCart();
        DemoCartPage cartPage = new DemoCartPage(driver);
        cartPage.clickRemoveByName("Sauce Labs Backpack");
        
        // 3. Verify badge count giảm còn 2
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2", "Badge count should be 2 after removing 1 item");
        
        // 4. Xóa thêm 1 sản phẩm nữa
        cartPage.clickRemoveByName("Sauce Labs Bike Light");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "1", "Badge count should be 1 after removing 2nd item");
    }

    @Test
    public void testRemoveAllItemsAndVerifyBadge() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        // 1. Thêm 2 sản phẩm
        inventoryPage.clickAddToCart("Sauce Labs Backpack");
        inventoryPage.clickAddToCart("Sauce Labs Bike Light");
        
        // 2. Vào giỏ hàng và xóa sạch
        inventoryPage.clickCart();
        DemoCartPage cartPage = new DemoCartPage(driver);
        cartPage.clickRemoveByName("Sauce Labs Backpack");
        cartPage.clickRemoveByName("Sauce Labs Bike Light");
        
        // 3. Verify badge biến mất hoàn toàn
        Assert.assertFalse(cartPage.isCartBadgeDisplayed(), "Badge should not be displayed when all items are removed");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "", "Badge count should be empty");
    }

    @Test
    public void testPartialRemoveAndContinueShopping() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        inventoryPage.clickAddToCart("Sauce Labs Backpack");
        inventoryPage.clickAddToCart("Sauce Labs Bike Light");
        
        inventoryPage.clickCart();
        DemoCartPage cartPage = new DemoCartPage(driver);
        cartPage.clickRemoveByName("Sauce Labs Backpack");
        Assert.assertEquals(cartPage.getCartBadgeCount(), "1", "Badge count should be 1 in Cart");
        
        cartPage.clickContinueShopping();
        // Now back on Inventory page, verify badge count still 1
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1", "Badge count should persist correctly on Inventory page");
    }

    @Test
    public void testProductAddToCart() {
        DemoInventoryPage productPage = new DemoInventoryPage(driver);
        String targetName = "Sauce Labs Backpack";

        // 1. Kiểm tra ban đầu giỏ hàng trống (nếu login mới)
        Assert.assertFalse(productPage.isCartBadgeDisplayed(), "Cart badge should not be displayed initially");

        // 2. Click Add to Cart
        productPage.clickAddToCart(targetName);

        // 3. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(productPage.isCartBadgeDisplayed(), "Cart badge should be displayed");
        Assert.assertEquals(productPage.getCartBadgeCount(), "1", "Cart badge count should be 1");
    }
}
