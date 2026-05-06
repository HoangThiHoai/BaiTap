package action;

import feature.ui.CheckoutCompletePageUI;
import feature.ui.CheckoutOverviewPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage {
    WebDriver driver;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver=driver;
    }

    public boolean isCartEmpty() {
        return driver.findElements(By.className("cart_item")).isEmpty();
    }
    public String getCompleteMessage() {
        return driver.findElement(CheckoutCompletePageUI.COMPLETE_HEADER_LABEL).getText();
    }

}
