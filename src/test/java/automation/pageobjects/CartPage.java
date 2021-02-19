package automation.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends MasterPage {


    public CartPage(){
        super();
    }

    @FindBy(id = "nav-cart")
    public WebElement addToCart;

    @FindBy(css="[class='sc-empty-cart-header']")
    public WebElement emptyCartText;



}
