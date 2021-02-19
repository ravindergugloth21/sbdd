package automation.pageactions;

import automation.pageobjects.CartPage;
import org.apache.log4j.Logger;

public class ProductPageActions extends CommonPageActions {

    CartPage cartPage = new CartPage();
    static Logger logger = Logger.getLogger("HomePageActions");

    public ProductPageActions() {
        super();
    }
    public void verifyOptions(){

    }
}
