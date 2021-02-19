package automation.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LanguageChangingPage extends MasterPage {



    public LanguageChangingPage(){
       super();
    }

    @FindBy(id = "icp-nav-flyout")
    public WebElement languageSettings;

    @FindBy(id = "lop-heading")
    public WebElement languageSettingsText;

    @FindBy(css = "input[name='LOP']")
    public WebElement languageRadioButtons;

    @FindBy(css = "span[id='icp-sl-t-title']")
    public WebElement englishTranslationText;


}
