package automation.pageactions;

import automation.utilities.*;
import org.openqa.selenium.WebDriver;


public class CommonPageActions {

    protected Utils utils;
    protected WebDriver webDriver;
    protected FileUtils fileUtils;

    public CommonPageActions() {
        utils = Utils.getInstance();
        webDriver = utils.getDriver();
        fileUtils=new FileUtils();
    }


}
