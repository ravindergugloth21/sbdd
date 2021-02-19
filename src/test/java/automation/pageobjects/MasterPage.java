package automation.pageobjects;

import automation.utilities.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MasterPage {

    public WebDriver webDriver;
    public Utils utils;

    public MasterPage() {
        utils = Utils.getInstance();
        webDriver = utils.getDriver();
        PageFactory.initElements(webDriver, this);
    }
}


