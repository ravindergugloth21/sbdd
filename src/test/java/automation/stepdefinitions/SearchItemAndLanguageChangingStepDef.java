package automation.stepdefinitions;

import automation.pageactions.HomePageActions;
import automation.pageobjects.HomePage;
import automation.pageobjects.LanguageChangingPage;
import automation.utilities.Constants;
import automation.utilities.Pojo;
import automation.utilities.Utils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static automation.utilities.Asserts.expectToBeTrue;


public class SearchItemAndLanguageChangingStepDef {
    HomePageActions homePageActions =new  HomePageActions();
    Logger logger = Logger.getLogger("SearchItemAndLanguageChangingStepDef");
    Pojo pojo = Pojo.getInstance();
    HomePage homePage = new HomePage();
    LanguageChangingPage languageChangingPage = new LanguageChangingPage();
    Utils utils;
    WebDriver webDriver;

    public SearchItemAndLanguageChangingStepDef() {
        utils = Utils.getInstance();
        webDriver = utils.getDriver();
    }

    /**
     * Verify whether user is on homepage or not
     */
    @Given("^User is on home page$")
    public void userIsOnHomePage() {
        logger.info("******************Verify home page**********************");
        utils.elementWait(homePage.amazonText, Constants.VERYLONGWAIT);
        expectToBeTrue(utils.isElementPresent(homePage.amazonText, "Amazon text", Constants.MEDIUMWAIT), "Amazon Text is not Displayed");
    }

    /**
     * User search for an product in home page
     *
     * @param arg0
     */
    @When("^Search for an item with \"([^\"]*)\"$")
    public void searchForAnItemWith(String arg0) {
        logger.info("*********************Search for am item*********************");
        pojo.setValue(homePageActions.searchForAnItem(arg0));
    }

    /**
     * Verify whether the products are displayed according to search criteria.
     */
    @Then("^Verify the items displayed$")
    public void verifyTheItemsDisplayed() {
        logger.info("************Verify the items*********************");
        utils.elementWait(homePage.searchItemDisplayedText, Constants.MEDIUMWAIT);
        pojo.setSearchItemText(utils.safeGetText(homePage.searchItemDisplayedText, "Search Item Displayed text", Constants.MEDIUMWAIT));
        expectToBeTrue(pojo.getSearchItemText().contains(pojo.getValue()), "Search Item text is not displayed");
    }

    /**
     * User Navigate to language settings page
     */
    @When("^User navigate to language settings$")
    public void userNavigateToLanguageSettings() {
        logger.info("*******************Language Settings**********************");
        utils.safeClick(languageChangingPage.languageSettings, "Clicked On Language Settings", Constants.MEDIUMWAIT);
        utils.elementWait(languageChangingPage.languageSettingsText, Constants.MEDIUMWAIT);
        pojo.setLanguageSettings(utils.safeGetText(languageChangingPage.languageSettingsText, "Language Settings", Constants.MEDIUMWAIT));
        expectToBeTrue(pojo.getLanguageSettings().equals("Language Settings"), "Language Settings text is not Displayed");
    }

    /**
     * Verify whether language is changing or not according to selected language option
     */
    @Then("^Verify the items in language settings$")
    public void verifyTheItemsInLanguageSettings() {
        logger.info("*************Verify language radio button and language translation********************");
        utils.safeJavaScriptClick(languageChangingPage.languageRadioButtons, "Language radio button", Constants.MEDIUMWAIT);
        utils.elementWait(languageChangingPage.englishTranslationText, Constants.MEDIUMWAIT);
        pojo.setEnglishTranslationText(utils.safeGetText(languageChangingPage.englishTranslationText, "English Translation Text", Constants.MEDIUMWAIT));
        expectToBeTrue(pojo.getEnglishTranslationText().equalsIgnoreCase("Translation"), "Language Translation Text is not Verified");
        utils.isAttributePresent(languageChangingPage.languageRadioButtons, "checked");
    }
}
