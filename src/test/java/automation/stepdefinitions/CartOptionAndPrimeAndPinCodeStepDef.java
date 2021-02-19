package automation.stepdefinitions;

import automation.pageobjects.CartPage;
import automation.pageobjects.PinCodePage;
import automation.pageobjects.PrimePage;
import automation.utilities.Constants;
import automation.utilities.Pojo;
import automation.utilities.Utils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import static automation.utilities.Asserts.expectToBeTrue;

public class CartOptionAndPrimeAndPinCodeStepDef {

    Pojo pojo = Pojo.getInstance();
    Logger logger = Logger.getLogger("CartOptionAndPrimeAndPinCodeStepDef");
    PinCodePage pinCodePage=new PinCodePage();
    CartPage cartPage=new CartPage();
    PrimePage primePage=new PrimePage();
    Utils utils;
    WebDriver webDriver;

    public CartOptionAndPrimeAndPinCodeStepDef(){
        utils=Utils.getInstance();
        webDriver=utils.getDriver();
    }

    /**
     * User add PinCode for the address
     * @param arg0
     */
    @When("^User add \"([^\"]*)\" for the address$")
    public void userAddForTheAddress(String arg0) {
        logger.info("***************User add the address*******************");
        pojo.setPinCode(arg0);
        utils.safeClick(pinCodePage.selectYourAddress, "Select Your Address is clicked", Constants.MEDIUMWAIT);
        utils.safeClearAndType(pinCodePage.pinCodeTextBox, arg0, "Text Entered", Constants.MEDIUMWAIT);
        utils.safeJavaScriptClick(pinCodePage.applyButton, "Clicked On Apply Button", Constants.MEDIUMWAIT);
    }

    /**
     * Verify whether user entered PinCode displayed or not
     */
    @Then("^Verify the pincode is added or not$")
    public void verifyThePinCodeIsAddedOrNot() {
        logger.info("********************Verify the pincode*******************");
        utils.elementWait(pinCodePage.selectYourAddress, Constants.LONGWAIT);
        pojo.setPinCode(utils.safeGetText(pinCodePage.selectYourAddress, "Select your address", Constants.MEDIUMWAIT));
        utils.safeClick(pinCodePage.selectYourAddress,"Select your address pincode",Constants.MEDIUMWAIT);
        utils.elementWait(pinCodePage.pinCodeTextBox,Constants.MEDIUMWAIT);
        expectToBeTrue(pojo.getPinCode().contains(utils.safeGetText(pinCodePage.pinCodeTextBox,"Pin code text box",Constants.MEDIUMWAIT)), "PinCode text is not verified");
    }


}
