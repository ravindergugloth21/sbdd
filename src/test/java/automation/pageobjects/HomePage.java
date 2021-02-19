package automation.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class HomePage extends MasterPage {


    public HomePage(){
       super();
    }

    @FindBy(className = "nav-logo-link")
    public WebElement amazonText;

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(css = "input[value='Go']")
    public WebElement searchButton;

    @FindBy(css = "span[class='a-color-state a-text-bold']")
    public WebElement searchItemDisplayedText;

    @FindBy(css = "span[class='a-size-medium a-color-base a-text-normal']")
    public WebElement selectedItemText;

    @FindBy(css = "div[class='a-section aok-relative s-image-fixed-height']")
    public WebElement selectedItem;

    @FindBy(css = "select[id='s-result-sort-select']")
    public WebElement sortFeature;

    @FindBy(xpath = "(//*[@class='a-price-whole'])")
    public List<WebElement> priceList;




}
