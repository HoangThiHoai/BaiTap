package demo.test;

import demo.pages.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class DemoE2ECheckoutTest extends DemoBaseTest {

    @DataProvider(name = "e2eData")
    public Object[][] getE2EData() {
        List<Map<String, String>> dataList = ExcelUtils.readExcelData("DemoTestData.xlsx", "E2E_Test");
        Object[][] data = new Object[dataList.size()][6];
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, String> row = dataList.get(i);
            data[i][0] = row.get("Username");
            data[i][1] = row.get("Password");
            data[i][2] = row.get("ProductName");
            data[i][3] = row.get("FirstName");
            data[i][4] = row.get("LastName");
            data[i][5] = row.get("ZipCode");
        }
        return data;
    }

    @Test(dataProvider = "e2eData")
    public void testEndToEndCheckout(String username, String password, String productName,
                                     String firstName, String lastName, String zipCode) {
        
        System.out.println("=== BẮT ĐẦU E2E TEST MUA HÀNG ===");
        
        // 1. Đăng nhập
        DemoLoginPage loginPage = new DemoLoginPage(driver);
        loginPage.login(username, password);
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login Failed");
        System.out.println("1. Đăng nhập thành công.");

        // 2. Lấy dữ liệu sản phẩm chuẩn từ Inventory
        DemoInventoryPage inventoryPage = new DemoInventoryPage(driver);
        String expectedPrice = inventoryPage.getItemPrice(productName);
        System.out.println("-> Giá gốc lấy từ Inventory cho '" + productName + "': " + expectedPrice);

        inventoryPage.clickAddToCart(productName);
        System.out.println("2. Đã thêm sản phẩm '" + productName + "' vào giỏ hàng.");
        
        // 3. Vào giỏ hàng & Verify Cross-page
        inventoryPage.clickCart();
        DemoCartPage cartPage = new DemoCartPage(driver);
        
        Assert.assertEquals(cartPage.getItemName(productName), productName);
        Assert.assertEquals(cartPage.getItemPrice(productName), expectedPrice);
        System.out.println("3. [VERIFIED] Dữ liệu trong giỏ hàng khớp 100% với màn hình chính.");
        
        // 4. Bắt đầu Checkout
        cartPage.clickCheckout();
        DemoCheckoutInfoPage infoPage = new DemoCheckoutInfoPage(driver);
        infoPage.enterInfo(firstName, lastName, zipCode);
        infoPage.clickContinue();
        System.out.println("4. Đã điền thông tin Checkout: " + firstName + " " + lastName + ", Zip: " + zipCode);

        // 5. Checkout Overview & Verify Logic
        DemoCheckoutOverviewPage overviewPage = new DemoCheckoutOverviewPage(driver);
        
        Assert.assertEquals(overviewPage.getItemName(productName), productName);
        Assert.assertEquals(overviewPage.getItemPrice(productName), expectedPrice);
        System.out.println("5. [VERIFIED] Dữ liệu ở màn Overview khớp 100% với màn hình chính.");
        
        double actualItemTotal = overviewPage.getSubtotal();
        double actualTax = overviewPage.getTax();
        double actualTotal = overviewPage.getTotal();
        
        // Assert Logic 1: Cộng dồn giá lẻ == Item total
        List<Double> itemPrices = overviewPage.getIndividualItemPrices();
        double calculatedItemTotal = 0;
        for (double p : itemPrices) {
            calculatedItemTotal += p;
        }
        Assert.assertEquals(calculatedItemTotal, actualItemTotal, 0.01, "Tổng giá các sản phẩm không khớp với Item total");
        
        // Assert Logic 2: Item total + Tax == Total cuối cùng
        Assert.assertEquals(actualItemTotal + actualTax, actualTotal, 0.01, "Tổng giá cuối (Total) tính toán bị sai");
        System.out.println("   [VERIFIED] Logic tính toán Subtotal + Tax = Total hoàn toàn chính xác.");

        // Hoàn tất mua hàng
        overviewPage.clickFinish();

        // 6. Verify Complete
        DemoCheckoutCompletePage completePage = new DemoCheckoutCompletePage(driver);
        String completeMessage = completePage.getCompleteMessage();
        Assert.assertEquals(completeMessage, "Thank you for your order!", "Checkout failed: Incorrect completion message.");
        System.out.println("6. Đặt hàng thành công! Thông báo: " + completeMessage);
        System.out.println("=== KẾT THÚC E2E TEST MUA HÀNG ===");
    }
}
