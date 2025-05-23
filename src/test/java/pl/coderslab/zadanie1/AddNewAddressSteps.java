package pl.coderslab.zadanie1;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class AddNewAddressSteps {
    private WebDriver driver;

    @Given("User is logged in to the shop")
    public void userIsLoggedInToTheShop() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get("https://mystore-testlab.coderslab.pl/");
        driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"field-email\"]")).sendKeys("yejedav192@minduls.com");
        driver.findElement(By.xpath("//*[@id=\"field-password\"]")).sendKeys("Password123");
        driver.findElement(By.xpath("//*[@id=\"submit-login\"]")).click();
    }

    @When("User goes to ADDRESSES")
    public void userGoesToAddresses() {
        driver.findElement(By.xpath("//*[@id=\"addresses-link\"]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]/a")).click();
    }

    @Then("User enters alias {string}, address {string}, city {string}, zip or postal code {string} and phone {string}")
    public void userEntersAliasAddressCityZipPostalCodeCountryPhone(String alias, String address, String city, String code, String phone) {
        driver.findElement(By.xpath("//*[@id=\"field-alias\"]")).sendKeys(alias);
        driver.findElement(By.xpath("//*[@id=\"field-address1\"]")).sendKeys(address);
        driver.findElement(By.xpath("//*[@id=\"field-city\"]")).sendKeys(city);
        driver.findElement(By.xpath("//*[@id=\"field-postcode\"]")).sendKeys(code);
        driver.findElement(By.xpath("//*[@id=\"field-phone\"]")).sendKeys(phone);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/form/footer/button")).click();

        String expectedAddress = "First name Last name\n" + address + "\n" + city + "\n" + code + "\nUnited Kingdom\n" + phone;
        String actualAddress = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div[2]/article/div[1]/address")).getText();
        Assert.assertEquals(expectedAddress, actualAddress);
    }

    @And("User deletes new address")
    public void userDeletesNewAddress() {
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div[2]/article/div[2]/a[2]/span")).click();
        Assert.assertEquals("Address successfully deleted!", driver.findElement(By.xpath("//*[@id=\"notifications\"]/div/article")).getText());
    }

    @And("User closes the browser")
    public void userClosesTheBrowser() {
        driver.quit();
    }
}