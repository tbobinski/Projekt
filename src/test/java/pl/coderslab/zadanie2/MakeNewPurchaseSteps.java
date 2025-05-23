package pl.coderslab.zadanie2;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class MakeNewPurchaseSteps {
    WebDriver driver;
    WebDriverWait wait;
    String orderRef;
    String price;

    @Given("User is logged in the shop")
    public void userIsLoggedInTheShop() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://mystore-testlab.coderslab.pl/");
        driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"field-email\"]")).sendKeys("yejedav192@minduls.com");
        driver.findElement(By.xpath("//*[@id=\"field-password\"]")).sendKeys("Password123");
        driver.findElement(By.xpath("//*[@id=\"submit-login\"]")).click();
    }

    @When("User searches for Hummingbird Printed Sweater")
    public void UserSearchesForHummingbirdPrintedSweater() {
        driver.findElement(By.xpath("//*[@id=\"search_widget\"]/form/input[2]")).sendKeys("Hummingbird Printed Sweater");
        driver.findElement(By.xpath("//*[@id=\"ui-id-2\"]")).click();

        String discount = driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div[1]/div[2]/div/span[2]")).getText();
        Assert.assertEquals("SAVE 20%", discount);
    }

    @And("User picks size {string} and number {string} of items he wants to purchase")
    public void userPicksSizeAndNumberOfItemsHeWantsToPurchase(String size, String number) {
        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"group_1\"]")));
        dropdown.selectByVisibleText(size);
        wait.until(ExpectedConditions.textToBe((By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[1]/div/span")), "Size: " + size));

        driver.findElement(By.xpath("//*[@id=\"quantity_wanted\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"quantity_wanted\"]")).sendKeys(Keys.BACK_SPACE + number);
    }

    @Then("User adds the item to the cart and goes to checkout")
    public void userAddsTheItemToTheCartAndGoesToCheckout() {
        if (driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")).isEnabled()) {
            driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")).click();
            driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();
            driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[1]/div[2]/div/a")).click();
        } else {
            System.out.println("Unable to make a purchase");
            System.out.println("Message displayed on the site is: \n" + driver.findElement(By.xpath("//*[@id=\"product-availability\"]")).getText());
            driver.quit();
        }
    }

    @And("User confirms address, picks shipping and payment method and confirms order")
    public void userConfirmsAddressPicksShippingAndPaymentMethodAndConfirmsOrder() {
        driver.findElement(By.xpath("//*[@id=\"checkout-addresses-step\"]/div/div/form/div[2]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"js-delivery\"]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"payment-option-1\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"conditions_to_approve[terms-and-conditions]\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"payment-confirmation\"]/div[1]/button")).click();
        price = driver.findElement(By.xpath("//*[@id=\"order-items\"]/div[2]/div/div[3]/div/div[3]")).getText();
        orderRef = driver.findElement(By.xpath("//*[@id=\"order-reference-value\"]")).getText();
    }

    @And("User makes a screenshot with the confirmation of the order and the paid amount")
    public void userMakesAScreenshotWithTheConfirmationOfTheOrderAndThePaidAmount() {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File("screenshot.png");
        try {
            FileUtils.copyFile(screenshot, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("User checks if the order is available in the order history")
    public void userChecksIfTheOrderIsAvailableInTheOrderHistory() {
        driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a[2]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"history-link\"]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/table/tbody/tr[1]/td[6]/a[1]")).click();

        String orderCheck = "Order reference: " + driver.findElement(By.xpath("//*[@id=\"wrapper\"]/div/nav/ol/li[4]/span")).getText();
        String priceCheck = driver.findElement(By.xpath("//*[@id=\"order-products\"]/tbody/tr/td[4]")).getText();
        String statusCheck = driver.findElement(By.xpath("//*[@id=\"order-history\"]/table/tbody/tr/td[2]/span")).getText();
        Assert.assertEquals(orderRef, orderCheck);
        Assert.assertEquals(price, priceCheck);
        Assert.assertEquals("Awaiting check payment", statusCheck);
    }

    @And("User quits the browser")
    public void userQuitsTheBrowser() {
        driver.quit();
    }
}
