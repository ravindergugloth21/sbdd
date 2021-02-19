package automation;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin={"pretty","html:target/cucumber","json:target/cucumber.json","rerun:Rerun/rerun.txt"},
        features={"classpath:features"},
        tags={"@Amazon"}, dryRun = false
)
public class CukesRunner {

}