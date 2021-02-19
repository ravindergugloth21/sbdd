package automation.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends MasterPage {


    public ProductPage(){
       super();
    }

    @FindBy(css = "span[id='productTitle']")
    public WebElement productTitle;

    @FindBy(id = "[id='add-to-cart-button']")
    public WebElement addToCart;

    @FindBy(id = "[id='buy-now-button']")
    public WebElement buyNow;

    @FindBy(css = "a[title='Add to Wish List']")
    public WebElement addToWishList;




}
