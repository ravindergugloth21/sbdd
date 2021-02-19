package automation.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PinCodePage extends MasterPage {


    public PinCodePage()
    {
        super();
    }

    @FindBy(id = "glow-ingress-line2")
    public WebElement selectYourAddress;

    @FindBy(id = "GLUXZipUpdateInput")
    public WebElement pinCodeTextBox;

    @FindBy(id = "GLUXZipUpdate-announce")
    public WebElement applyButton;



}
