package pl.coderslab.zadanie1;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/Cucumber/Features/add-new-address.feature", plugin = {"pretty", "html:out.html"})
public class AddNewAddressTest {
}