package action;

import feature.ui.CheckoutOverviewPageUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutOverviewPage {
    private static WebDriver driver;
    private WebDriverWait wait;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickFinish() {
        driver.findElement(CheckoutOverviewPageUI.FINISH_BUTTON).click();
    }

    public WebElement getButtonFinishText() {
        WebElement itemButton = driver.findElement(CheckoutOverviewPageUI.FINISH_BUTTON);
        return itemButton;
    }

    public void clickCancel() {
        driver.findElement(CheckoutOverviewPageUI.CANCEL_BUTTON).click();
    }

    public WebElement getButtonCancelText() {
        WebElement itemButton = driver.findElement(CheckoutOverviewPageUI.CANCEL_BUTTON);
        return itemButton;
    }

    public List<Double> getItemPrices() {
        List<WebElement> elements = driver.findElements(CheckoutOverviewPageUI.ITEM_PRICES);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.getText().replace("$", "");
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public double getSubtotal() {
        String text = driver.findElement(CheckoutOverviewPageUI.SUBTOTAL_LABEL).getText().replace("Item total: $", "");
        return Double.parseDouble(text);
    }

    public double getTax() {
        String text = driver.findElement(CheckoutOverviewPageUI.TAX_LABEL).getText().replace("Tax: $", "");
        return Double.parseDouble(text);
    }

    public double getTotal() {
        String text = driver.findElement(CheckoutOverviewPageUI.TOTAL_LABEL).getText().replace("Total: $", "");
        return Double.parseDouble(text);
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(CheckoutOverviewPageUI.CART_BADGE);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(CheckoutOverviewPageUI.CART_BADGE).isEmpty();
    }
    public static List<Double> getIndividualItemPrices() {
        List<WebElement> elements = driver.findElements(CheckoutOverviewPageUI.ITEM_PRICES);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.getText().replace("$", "");
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }
}