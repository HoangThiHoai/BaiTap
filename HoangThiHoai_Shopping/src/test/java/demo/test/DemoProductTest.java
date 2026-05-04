package demo.test;

import demo.pages.DemoInventoryPage;
import demo.pages.DemoLoginPage;
import demo.pages.DemoProductDetailPage;
import demo.pages.ProductDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DemoProductTest extends DemoBaseTest {

    @BeforeMethod
    public void loginBeforeTest() {
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    public void testSortProductsByName() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        // Sort Z to A
        inventoryPage.selectSortOption("Name (Z to A)");
        List<String> actualNamesDesc = inventoryPage.getAllItemNames();
        List<String> expectedNamesDesc = new ArrayList<>(actualNamesDesc);
        expectedNamesDesc.sort(Collections.reverseOrder());
        Assert.assertEquals(actualNamesDesc, expectedNamesDesc, "Products are not sorted Z to A correctly");

        // Sort A to Z
        inventoryPage.selectSortOption("Name (A to Z)");
        List<String> actualNamesAsc = inventoryPage.getAllItemNames();
        List<String> expectedNamesAsc = new ArrayList<>(actualNamesAsc);
        Collections.sort(expectedNamesAsc);
        Assert.assertEquals(actualNamesAsc, expectedNamesAsc, "Products are not sorted A to Z correctly");
    }

    @Test
    public void testSortProductsByPrice() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        // Sort Low to High
        inventoryPage.selectSortOption("Price (low to high)");
        List<Double> actualPricesAsc = inventoryPage.getAllItemPrices();
        List<Double> expectedPricesAsc = new ArrayList<>(actualPricesAsc);
        Collections.sort(expectedPricesAsc);
        Assert.assertEquals(actualPricesAsc, expectedPricesAsc, "Products are not sorted by Price low to high correctly");

        // Sort High to Low
        inventoryPage.selectSortOption("Price (high to low)");
        List<Double> actualPricesDesc = inventoryPage.getAllItemPrices();
        List<Double> expectedPricesDesc = new ArrayList<>(actualPricesDesc);
        expectedPricesDesc.sort(Collections.reverseOrder());
        Assert.assertEquals(actualPricesDesc, expectedPricesDesc, "Products are not sorted by Price high to low correctly");
    }

    @Test
    public void testProductDetailCrossVerification() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        String targetProductName = "Sauce Labs Backpack";
        
        // 1. Lấy dữ liệu từ trang Inventory
        String inventoryName = inventoryPage.getItemName(targetProductName);
        String inventoryDesc = inventoryPage.getItemDescription(targetProductName);
        String inventoryPrice = inventoryPage.getItemPrice(targetProductName);
        
        // 2. Click mở trang Detail
        inventoryPage.clickProductByName(targetProductName);
        
        // 3. Lấy dữ liệu từ trang Detail
        DemoProductDetailPage detailPage = new DemoProductDetailPage(driver);
        
        // 4. Kiểm tra chéo (Cross-verification)
        Assert.assertEquals(detailPage.getProductName(), inventoryName, "Tên sản phẩm không khớp!");
        Assert.assertEquals(detailPage.getProductDescription(), inventoryDesc, "Mô tả không khớp!");
        Assert.assertEquals(detailPage.getProductPrice(), inventoryPrice, "Giá không khớp!");
        
        // 5. Quay lại trang chủ
        detailPage.clickBackToProducts();
    }

    @Test
    public void testInventoryPageTexts() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        
        // Verify Title
        String actualTitle = inventoryPage.getPageTitle();
        Assert.assertEquals(actualTitle, "Products", "Page title does not match design!");
        
        // Verify App Logo
        String actualLogo = inventoryPage.getAppLogoText();
        Assert.assertEquals(actualLogo, "Swag Labs", "App logo text does not match design!");
    }

    @Test
    public void testFullProductDetailsVerification() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        String targetName = "Sauce Labs Backpack";
        
        // So sánh trực tiếp với dữ liệu thiết kế
        Assert.assertEquals(inventoryPage.getItemName(targetName), "Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getItemDescription(targetName), "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop protection.");
        Assert.assertEquals(inventoryPage.getItemPrice(targetName), "$29.99");
        Assert.assertTrue(inventoryPage.getItemImage(targetName).contains("sauce-backpack"));
        Assert.assertEquals(inventoryPage.getItemButtonText(targetName), "Add to cart");
    }

    @Test
    public void testProductDetailAddToCartVerification() {
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        String targetProductName = "Sauce Labs Backpack";
        
        // Click mở trang Detail
        inventoryPage.clickProductByName(targetProductName);
        
        DemoProductDetailPage detailPage = new DemoProductDetailPage(driver);
        
        // 1. Kiểm tra ban đầu giỏ hàng trống (nếu login mới)
        // Lưu ý: Nếu có sản phẩm từ test trước thì count sẽ khác, nhưng TestNG chạy song song hoặc tuần tự tùy config.
        // Ở đây assume login mới trong @BeforeMethod nên giỏ trống.
        Assert.assertFalse(detailPage.isCartBadgeDisplayed(), "Cart badge should not be displayed initially");
        
        // 2. Click Add to Cart từ trang Detail
        detailPage.clickAddToCart();
        
        // 3. Kiểm tra số lượng trên icon giỏ hàng
        Assert.assertTrue(detailPage.isCartBadgeDisplayed(), "Cart badge should be displayed");
        Assert.assertEquals(detailPage.getCartBadgeCount(), "1", "Cart badge count should be 1");
    }
}
