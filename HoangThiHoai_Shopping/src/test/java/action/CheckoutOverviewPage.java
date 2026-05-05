package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutOverviewPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By finishButton = By.id("finish");
    private By itemPrices = By.className("inventory_item_price");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cancelButton = By.id("cancel");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getItemName(String name) {
        WebElement itemName = driver.findElement(By.xpath("//div[text()='" + name + "']"));
        return itemName;
    }

    public WebElement getItemPrice(String name) {
        WebElement itemPrice = driver.findElement(By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_price']"));
        return itemPrice;
    }

    public WebElement getItemDescription(String name) {
        WebElement itemDescription = driver.findElement(By.xpath("//div[text()='" + name + "']/ancestor::div[@class='cart_item']/descendant::div[@class='inventory_item_desc']"));
        return itemDescription;
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }
    public WebElement getButtonFinishText() {
        WebElement itemButton = driver.findElement(By.xpath("//button[@id='finish']"));
        return itemButton;
    }
    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }
    public WebElement getButtonCancelText() {
        WebElement itemButton = driver.findElement(By.xpath("//button[@id='cancel']"));
        return itemButton;
    }

    public List<Double> getItemPrices() {
        List<WebElement> elements = driver.findElements(itemPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.getText().replace("$", "");
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public double getSubtotal() {
        String text = driver.findElement(subtotalLabel).getText().replace("Item total: $", "");
        return Double.parseDouble(text);
    }

    public double getTax() {
        String text = driver.findElement(taxLabel).getText().replace("Tax: $", "");
        return Double.parseDouble(text);
    }

    public double getTotal() {
        String text = driver.findElement(totalLabel).getText().replace("Total: $", "");
        return Double.parseDouble(text);
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return "";
        }
        return badges.get(0).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(cartBadge).isEmpty();
    }
}