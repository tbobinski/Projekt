package pl.coderslab.zadanie2;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/Cucumber/Features/make-new-purchase.feature", plugin = {"pretty", "html:out.html"})
public class MakeNewPurchaseTest {
}