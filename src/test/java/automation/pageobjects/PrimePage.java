package automation.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class PrimePage extends MasterPage {


    public PrimePage(){
        super();
    }

    @FindBy(css = "a[class='nav-sprite nav-logo-tagline nav-prime-try']")
    public WebElement tryPrime;

    @FindBy(xpath = "(//*[@class='a-column a-span6 a-span-last'])")
    public List<WebElement> primeBenefitsList;



}
