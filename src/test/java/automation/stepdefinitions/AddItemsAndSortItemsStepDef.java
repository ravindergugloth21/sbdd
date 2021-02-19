package automation.stepdefinitions;


import automation.pageactions.HomePageActions;
import automation.pageobjects.HomePage;
import automation.pageobjects.ProductPage;
import automation.utilities.Constants;
import automation.utilities.Pojo;
import automation.utilities.Utils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static automation.utilities.Asserts.expectToBeTrue;

public class AddItemsAndSortItemsStepDef {
    HomePageActions homePageActions = new HomePageActions();
    Pojo pojo = Pojo.getInstance();
    HomePage homePage = new HomePage();
    ProductPage productPage = new ProductPage();
    Logger logger = Logger.getLogger("AddItemsAndSortItemsStepDef");
    Utils utils;
    private WebDriver webDriver;

    public AddItemsAndSortItemsStepDef() {
        utils = Utils.getInstance();
        webDriver = utils.getDriver();
    }

    /**
     * Search for an item in amazon
     *
     * @param arg0
     */
    @When("^User search for an item with \"([^\"]*)\"$")
    public void userSearchForAnItemWith(String arg0) {
        logger.info("********************Search for an item*********************");
        pojo.setValue(homePageActions.searchForAnItem(arg0));

    }

    /**
     * Sort products by prices
     */
    @Then("^Sort items by applying filters$")
    public void sortItemsByApplyingFilters() {
        logger.info("**********************Sort items*******************");
        utils.elementWait(homePage.sortFeature, Constants.MEDIUMWAIT);
        utils.safeSelectOptionByVisibleText(homePage.sortFeature, "Price: Low to High", Constants.MEDIUMWAIT);
        ArrayList<String> obtainedList = new ArrayList<>();
        List<WebElement> elementList = homePage.priceList;
        System.out.println(elementList);
        for (WebElement we : elementList) {
            obtainedList.add(utils.safeGetText(we, "Price list", Constants.MEDIUMWAIT));
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (String s : obtainedList) {
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        System.out.println(sortedList);
        expectToBeTrue(sortedList.equals(obtainedList), "Both lists are not equal");
        homePageActions.isSorted(obtainedList);
    }

    /**
     * Verify the entered product name and displayed product name are equal
     */
    @Then("^Verify the product name$")
    public void verifyTheProductName() {
        logger.info("********************Verify product name*********************");
        utils.elementWait(homePage.selectedItemText, Constants.MEDIUMWAIT);
        pojo.setProductName(utils.safeGetText(homePage.selectedItemText, "Selected Item Text", Constants.MEDIUMWAIT));
        logger.info(pojo.getProductName());
        utils.safeClick(homePage.selectedItem, "Clicked On Selected Item", Constants.MEDIUMWAIT);
        utils.switchToChildWindow();
        utils.elementWait(productPage.productTitle, Constants.MEDIUMWAIT);
        pojo.setItemName(utils.safeGetText(productPage.productTitle, "product title", Constants.MEDIUMWAIT));
        logger.info(pojo.getItemName());
        expectToBeTrue(pojo.getProductName().equals(pojo.getItemName()), "Product name and item name are not equal");
    }

    /**
     * Verify the delivery options are available for the product in product page
     */
    @And("^Options available for the product$")
    public void optionsAvailableForTheProduct() {

    }
}
