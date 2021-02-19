package automation.utilities;

import jline.internal.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static automation.utilities.Asserts.toBeFail;

public class Utils {
    private static final Utils UTILS = new Utils();
    public static WebDriver webDriver;
    static Logger log = Logger.getLogger("Utils");
    Properties properties = ReadPropertyFile.getInstance().getProperties();
    Pojo pojo = Pojo.getInstance();
    String sTestCaseName = "";
    JavascriptExecutor executor = (JavascriptExecutor) webDriver;

    /**
     * Webdriver initialisation
     * @param webDriver
     */
    public void intialize(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Wait for web element loading
     * @param element
     * @param sElementName
     * @param waitTime
     * @return boolean value (true/false) to verify whether element is completely loaded in DOM or not.
     */
    public boolean waitForElementToLoad(WebElement element, String sElementName, int waitTime) {
        try {
            log.info(sTestCaseName + "- Waiting until element " + sElementName + " is visible in time " + waitTime
                    + " secs");
            WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not visible in time - " + waitTime);
            return false;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            toBeFail("Element " + element + "is not attached to the page document");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            toBeFail("Unable to find the element " + sElementName);
            return false;
        }
        return true;
    }

    /**
     * Sleep method
     * @param secs
     */
    public void sleep(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waiting for page to load
     * @return boolean value (true/false) to verify whether page is completely loaded or not.
     */
    public boolean waitForPageToLoad() {
        log.info("Waiting for page to load");
        try {
            acceptAlert();
            int waitTime = 0;
            boolean isPageLoadComplete = false;
            do {
                isPageLoadComplete = ((String) ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")).equals("complete");
                sleep(1);
                waitTime++;
                if (waitTime > 500) {
                    log.info("Page Load Complete");
                    break;
                }
            } while (!isPageLoadComplete);
            {
            }

        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * Verify whether element is present or not
     * @param element
     * @param sElementName
     * @param waitTime
     * @return boolean value (true/false) represent whether element is present or not
     */
    public boolean isElementPresent(WebElement element, String sElementName, int waitTime) {

        boolean bFlag = false;
        log.info(
                sTestCaseName + "- Waiting for presence of element " + sElementName + " in time " + waitTime + " secs");
        try {
            waitForElementToLoad(element, sElementName, waitTime);
            if (element.isDisplayed() || element.isEnabled()) {
                bFlag = true;
                setHighlight(element);
                log.info(sTestCaseName + "- Element for " + sElementName + " is displayed");
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            toBeFail("No such element as " + sElementName);
        } catch (TimeoutException e) {
            e.printStackTrace();
            toBeFail("Timed out after waiting for the element " + sElementName);
        } catch (Exception e) {
            toBeFail("Element for " + sElementName + " is not displayed");
        }
        return bFlag;
    }

    /**
     * To get wait time
     * @param optionalWaitArray
     * @return wait time
     */
    public int getWaitTime(int[] optionalWaitArray) {
        if (optionalWaitArray.length <= 0) {
            return 10;
        } else {
            return optionalWaitArray[0];
        }
    }

    /**
     * Verify whether alert is present or not
     * @return boolean value (true/false) to verify whether alert is present or not.
     */
    public boolean isAlertPresent() {
        try {
            webDriver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    /**
     * Method to accept alert
     */
    public void acceptAlert() {
        while (isAlertPresent()) {
            try {
                Alert alert = webDriver.switchTo().alert();
                alert.accept();
                log.info("- \n--------------\n" + "AlertDialog - " + "\n--------------");
            } catch (NoAlertPresentException e) {
            }
        }
    }

    /**
     * Method to highlight the element
     * @param element
     */
    public void setFinalHighlight(WebElement element) {
        try {
            if (Boolean.valueOf(properties.getProperty("HighlightElement"))) {
                String attributevalue = "border:2px solid limegreen;";
                JavascriptExecutor executor = (JavascriptExecutor) UTILS;
                executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Method to highlight the element
     * @param element
     */
    public void setHighlight(WebElement element) {
        if (Boolean.valueOf(properties.getProperty("HighlightElement"))) {
            String attributevalue = "border:2px solid Crimson;";
            JavascriptExecutor executor = (JavascriptExecutor) webDriver;
            executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
        }
    }

    /**
     * Method to select an option in dropdown
     * @param element
     * @param sDropDownOptionName
     * @param textOfOptionToSelect
     * @param optionWaitTime
     */
    public void safeSelectOptionByVisibleText(WebElement element, String sDropDownOptionName, String textOfOptionToSelect, int... optionWaitTime) {
        try {
            int waitTime = getWaitTime(optionWaitTime);
            if (isElementPresent(element, sDropDownOptionName, waitTime)) {
                WebElement selectElement = element;
                setHighlight(selectElement);
                Select select = new Select(selectElement);
                select.selectByVisibleText(textOfOptionToSelect);
                log.info(sTestCaseName + " Successfully selected dropdown item by Visible Text option:"
                        + textOfOptionToSelect + " from " + sDropDownOptionName);
            }
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            toBeFail("ElementNotVisibleException occured while selection dropdown option-" + sDropDownOptionName);
        } catch (Exception e) {
            e.printStackTrace();
            toBeFail("Unable to select dropdown option " + sDropDownOptionName + " with value " + textOfOptionToSelect);
        }
        setFinalHighlight(element);
    }

    /**
     * Method to dismiss alert
     */
    public void disMissAlert() {
        while (isAlertPresent()) {
            try {
                webDriver.switchTo().alert().dismiss();
                log.info("- \n--------------\n" + "AlertDialog Dismiss- " + "\n--------------");
            } catch (NoAlertPresentException e) {
            }
        }
    }

    /**
     * Method to select an option in dropdown
     * @param element
     * @param textOfOptionToSelect
     * @param optionWaitTime
     */
    public void safeSelectOptionByVisibleText(WebElement element, String textOfOptionToSelect, int... optionWaitTime) {
        try {
            int waitTime = getWaitTime(optionWaitTime);
            if (isElementPresent(element, textOfOptionToSelect, waitTime)) {
                WebElement selectElement = element;
                setHighlight(selectElement);
                Select select = new Select(selectElement);
                select.selectByVisibleText(textOfOptionToSelect);
            }
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            toBeFail("ElementNotVisibleException occured while selection dropdown option-" + textOfOptionToSelect);
        } catch (Exception e) {
            e.printStackTrace();
            toBeFail("Unable to select dropdown option with value -" + textOfOptionToSelect);
        }
        setFinalHighlight(element);
    }

    /**
     * Method to wait for the element loading
     * @param Driver
     * @param Element
     * @param sElementName
     * @throws InterruptedException
     */
    public void WaitForEnabledElement(WebDriver Driver, WebElement Element, String sElementName) throws InterruptedException {
        waitForElement(Element);
        int iTimeOut = 250;
        for (int i = 0; i < iTimeOut; i++) {
            Thread.sleep(2500);
            if (Element.isDisplayed()) {
                if (Element.isEnabled()) {
                    break;
                }
            }
            if (i == iTimeOut) {
                log.info("No WebObject: " + sElementName + " FOUND, waited for: " + ((i * 2500) / 60) + "sec.");
                break;
            }
        }
    }

    /**
     * Method to wait for an webelement
     * @param element
     */
    public void waitForElement(WebElement element) {
        int i = 0;
        while (i < 5 * 3) {
            try {
                element.isDisplayed();
                break;
            } catch (StaleElementReferenceException e) {
                Log.info(e.getMessage() + "::StaleElementReferenceException::");
                i++;
                try {
                    Thread.sleep(300);
                } catch (Exception j) {
                    Log.info(j.getMessage());
                }
            } catch (NoSuchElementException e) {
                i++;
                try {
                    Thread.sleep(3000);
                } catch (Exception j) {
                    Log.info(j.getMessage());
                }

            } catch (WebDriverException e) {
                Log.info("::WebDriverException::");
                Log.info(e.getMessage());
                i++;
                try {
                    Thread.sleep(300);
                } catch (Exception j) {
                    Log.info(j.getMessage());
                }

            }

        }
    }



    /**
     * Method to retrieve substring from webelement text
     * @param element
     * @return subsrring of webelement text
     */
    public String locatorOf(WebElement element) {
        String webelement = element.toString();
        int beginindex = webelement.lastIndexOf(">") + 2;
        int lastindex = webelement.length() - 1;
        return webelement.substring(beginindex, lastindex);
    }

    /**
     * Method to click an element
     * @param element
     * @param sElementName
     * @param optimumTime
     */
    public void safeActionsClick(WebElement element, String sElementName, int optimumTime) {
        try {
            setHighlight(element);
            WebDriverWait wait = new WebDriverWait(webDriver, optimumTime);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions auctions = new Actions(webDriver);
            auctions.moveToElement(element).doubleClick(element).perform();
            log.info(sElementName + "Element clicked");
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " is not attached to the page document");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not found in DOM");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not clickable.");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not clickable.");
        }
    }

    /**
     * Method to get title of current page
     * @return the title of the current page
     */

    public String safeGetTitle() {
        String sCurrenTitle = null;
        try {
            waitForPageToLoad();
            sCurrenTitle = webDriver.getTitle();
            log.info("Captured current page title as: " + sCurrenTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sCurrenTitle;
    }

    /**
     * Method to wait upto element is loaded
     * @param xpath
     */
    public void waitForElement(String xpath) {
        Wait<WebDriver> wait = new FluentWait<>(webDriver).withTimeout(Long.valueOf(30), TimeUnit.SECONDS)
                .pollingEvery(400, TimeUnit.MILLISECONDS).ignoring
                        (NoSuchElementException.class).ignoring(StaleElementReferenceException.class).ignoring
                        (ElementNotVisibleException.class).ignoring(WebDriverException.class);
        waitForStaleElement(webDriver.findElement(By.xpath(xpath)));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    /**
     * Method to wait upto element is loaded
     * @param element
     */
    public void waitForStaleElement(WebElement element) {
        Wait<WebDriver> wait = new FluentWait<>(webDriver);
        Log.info("Waiting for stale element");
        try {
            wait.until(ExpectedConditions.stalenessOf(element));
        } catch (Exception e) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Method to click an element by text
     * @param text
     * @throws InterruptedException
     */
    public void clickOnElementByText(String text) throws InterruptedException {
        String xpath = "//*[contains(text(), '" + text + "')]";
        List<WebElement> list = webDriver.findElements(By.xpath(xpath));
        for (WebElement element : list) {
            if (element.isDisplayed()) {
                element.click();
                break;
            }
        }
    }

    /**
     * Method to wait for stale element to load
     * @param element
     */
    public void waitForStaleElementPresent(WebElement element) {
        int y = 0;
        boolean clicked = false;
        while (y <= 4 && !clicked) {
            try {
                waitUntilElementIsDisplayed(element);
                clicked = true;
            } catch (StaleElementReferenceException st) {
                Log.warn("::::::::: Trying to recover from a stale element :::::::::");
                y++;
                Log.info("::  Attempt Number :: " + y);
            } catch (WebDriverException we) {
                Log.warn("::::::::: WebDriverException Found :::::::::");
                y++;
                Log.info("::  Attempt Number :: " + y);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to wait until the element is displayed.
     * @param element
     */
    public void waitUntilElementIsDisplayed(WebElement element) {
        int i = 0;
        if (!element.isDisplayed()) {
            Log.warn("{ Element not Displayed, PLEASE WAIT... }");
            do {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                Log.warn("Waiting ...." + i);
            } while ((!element.isDisplayed()) && (i < 4));
        }
        if (!element.isDisplayed()) {
            Log.error("------------ Timeout ------------");
            Log.info("??? Element not displayed yet ???");
        }
    }

    /**
     * Method to wait for an element having 'By' as parameter
     * @param elementLocator
     * @param waitInSeconds
     */
    public void waitForElement(By elementLocator, int waitInSeconds) {
        new WebDriverWait(webDriver, waitInSeconds).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    /**
     * Method to wait for an element having 'Webelement' as parameter
     * @param webElement
     * @param waitInSeconds
     */
    public void waitForElement(WebElement webElement, int waitInSeconds) {
        new WebDriverWait(webDriver, waitInSeconds).until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Method to get an webelement
     * @param elementLocator
     * @param waitInSeconds
     * @return
     */
    public WebElement getElement(By elementLocator, int waitInSeconds) {
        waitForElement(elementLocator, waitInSeconds);
        return webDriver.findElement(elementLocator);
    }

    /**
     * Method to get an webelement
     * @param webElement
     * @param elementLocator
     * @param waitInSeconds
     * @return webelement
     */
    public WebElement getElementInElement(WebElement webElement, By elementLocator, int waitInSeconds) {
        waitForElement(webElement.findElement(elementLocator), waitInSeconds);
        return webElement.findElement(elementLocator);
    }

    /**
     * Method to get text from webelement
     * @param elementLocator
     * @param waitInSeconds
     * @return text from web element
     */
    public String getText(By elementLocator, int waitInSeconds) {
        return getElement(elementLocator, waitInSeconds).getText();
    }

    /**
     * Method to get text from webelement
     * @param webElement
     * @param elementLocator
     * @param waitInSeconds
     * @return text from webelement
     */
    public String getText(WebElement webElement, By elementLocator, int waitInSeconds) {
        return getElementInElement(webElement, elementLocator, waitInSeconds).getText();
    }

    /**
     * Method to click on an webelement
     * @param elementLocator
     * @param waitInSeconds
     */
    public void click(By elementLocator, int waitInSeconds) {
        try {
            getElement(elementLocator, waitInSeconds).click();
        } catch (WebDriverException e) {
            log.info(e.getMessage());
            scrollIntoElement(elementLocator, waitInSeconds, 200);
            getElement(elementLocator, waitInSeconds).click();

        }
    }

    /**
     * Method to scroll upto an webelement
     * @param elementLocator
     * @param waitInSeconds
     * @param coordinatesOfY
     */
    public void scrollIntoElement(By elementLocator, int waitInSeconds, int coordinatesOfY) {
        int y = getElement(elementLocator, waitInSeconds).getLocation().getY() - coordinatesOfY;
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript("scroll(0, " + y + ")");
    }

    /**
     * Method to clear and enter text in a text field
     * @param element
     * @param text
     * @param sElementName
     * @param optionWaitTime
     */
    public void safeClearAndType(WebElement element, String text, String sElementName, int... optionWaitTime) {
        int waitTime = getWaitTime(optionWaitTime);
        if (isElementPresent(element, sElementName, waitTime)) {
            setHighlight(element);
            element.clear();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            element.sendKeys(text);
        }
    }

    /**
     * Method to get an webelement
     * @param locatorType
     * @return webElement
     */
    public WebElement getWebElement(By locatorType) {
        WebElement element = null;
        try {
            element = webDriver.findElement(locatorType);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Some Exception occured while retreiving the webelement");
        }
        return element;
    }

    /**
     * Method to click on an webelement using Ajax
     * @param element
     * @param sElementName
     * @param optionWaitTime
     */
    public void safeAjaxClick(WebElement element, String sElementName, int optionWaitTime) {
        try {
            waitForElementToBeClickable(element, sElementName, optionWaitTime);
            element.click();
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " is not attached to the page document");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not found in DOM");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not clickable in time-" + optionWaitTime);
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not found in DOM");
            toBeFail("Element " + sElementName + " was not clickable in time-" + optionWaitTime);
        }
    }


    /**
     * Method to clear and enter text in text field
     * @param element
     * @param text
     * @param sElementName
     * @param optionWaitTime
     */
    public void safeAjaxClearAndType(WebElement element, String text, String sElementName, int... optionWaitTime) {
        try {
            int waitTime = getWaitTime(optionWaitTime);
            if (isElementPresent(element, sElementName, waitTime)) {
                element.clear();
                element.clear();
                element.sendKeys(text);
                log.info(sTestCaseName + "- Cleared the field and entered -** " + text + " ** in the element - "
                        + sElementName);
            } else {
                log.info(sTestCaseName + "- Unable to clear and enter " + text + " in field " + sElementName);
                toBeFail("Unable to clear and enter " + text + " in field " + sElementName);
            }
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for " + locatorOf(element) + " is not attached to the page document"
                    + e.getStackTrace());
            toBeFail("Element for " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.error(sTestCaseName + "- Element for " + sElementName + " was not found in DOM" + e.getStackTrace());
            toBeFail("Element for " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(sTestCaseName + "- Unable to clear and enter '" + text + "' text in field with element -"
                    + sElementName + e.getStackTrace());
            toBeFail("Unable to clear and enter '" + text + "' text in field with element -" + sElementName);
        }

    }

    /**
     * Method to enter an option in dropdown
     * @param e
     */
    public void sendDownAndEnterKeys(WebElement e) {
        try {
            sleep(3);
            e.sendKeys(Keys.ARROW_DOWN);
            e.sendKeys(Keys.ENTER);
        } catch (Exception exception) {
            log.info(sTestCaseName + "- Unable to send down keys in dropdown");
            log.info(exception.getMessage());
            exception.printStackTrace();
        }
    }


    /**
     * Method to wait for visibility of list of webelements
     * @param elements
     * @param sElementName
     * @param timeout
     * @return boolean value (true/false) to verify whether list of webelements present or not.
     */
    public boolean waitForVisibilityOfElements(List<WebElement> elements, String sElementName, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, timeout);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            log.info(sElementName + " are visible ");
            return true;
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        } catch (TimeoutException e) {
            log.info(sTestCaseName + "- Element for " + sElementName + " was not displayed in time - " + timeout
                    + e.getStackTrace());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method to highlight the element
     * @param element
     */
    public void setVerificationHighlight(WebElement element) {
        try {
            if (Boolean.valueOf(properties.getProperty("HighlightElement"))) {
                String attributevalue = "border:3px solid darkgreen;";
                JavascriptExecutor executor = (JavascriptExecutor) UTILS;
                executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
            }
        } catch (Exception e) {
        }
    }

    /**
     * This method is to verify if Element is Displayed or Not
     *
     * @param element
     * @return boolean value (true/false) to verify whether element is present or not.
     */
    public boolean isElementDisplayed(WebElement element) {
        boolean isElementDisplayed = false;
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Constants.VERYSHORTWAIT);
            WebElement spinner = wait.until(ExpectedConditions.visibilityOf(element));
            if (spinner.isDisplayed()) {
                isElementDisplayed = true;
            }
        } catch (Exception e) {
            isElementDisplayed = false;
        }
        return isElementDisplayed;
    }

    /**
     * Method to verify whether element is displayed or not
     * @param element
     * @param waitTime
     * @return boolean value (true/false) to verify whether element is present or not.
     */
    public boolean isElementDisplayedFailSafe(WebElement element, int waitTime) {
        try {
            waitForPageToLoad();
            if (pojo.getBrowserName().equals("chrome")) {
                WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
                wait.until(ExpectedConditions.visibilityOf(element));
                setVerificationHighlight(element);
                return true;
            } else {
                if (element.isDisplayed()) {
                    setVerificationHighlight(element);
                    return true;
                } else
                    return false;
            }
        } catch (Exception E) {
            return false;
        }
    }

    /**
     * Method to wait upto page is loaded
     */
    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
        }
    }

    /**
     * Method to select an dropdown option by value
     * @param element
     * @param valueOfOptionToSelect
     * @param sDropDownOptionName
     * @param optionWaitTime
     */
    public void safeSelectOptionByValue(WebElement element, String valueOfOptionToSelect, String sDropDownOptionName,
                                        int... optionWaitTime) {
        try {
            int waitTime = getWaitTime(optionWaitTime);
            if (isElementPresent(element, sDropDownOptionName, waitTime)) {
                WebElement selectElement = element;
                setHighlight(selectElement);
                Select select = new Select(selectElement);
                select.selectByValue(valueOfOptionToSelect);
                log.info("Successfully selected dropdown item by value option:" + valueOfOptionToSelect + " from "
                        + sDropDownOptionName);
            } else {
                log.info(sTestCaseName + "- Unable to select drop down option " + sDropDownOptionName + " "
                        + getStackTrace());
                toBeFail("Unable to select drop down option " + sDropDownOptionName);
            }
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for drop down option " + sDropDownOptionName
                    + " is not attached to the page document" + getStackTrace());
            toBeFail("Element for drop down option " + sDropDownOptionName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(
                    "Element for drop down option " + sDropDownOptionName + " was not found in DOM" + getStackTrace());
            toBeFail("Element for drop down option " + sDropDownOptionName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Unable to select drop down option " + sDropDownOptionName + " "
                    + getStackTrace());
            toBeFail("Unable to select option from " + sDropDownOptionName + " dropdown");
        }
        setFinalHighlight(element);
    }

    /**
     * Method to uncheck an checkbox
     * @param element
     * @param sCheckBoxName
     * @param optionWaitTime
     */
    public void safeUnCheck(WebElement element, String sCheckBoxName, int optionWaitTime) {
        try {
            waitForElementToLoad(element, sCheckBoxName, optionWaitTime);
            WebElement checkBox = element;
            setHighlight(checkBox);
            if (checkBox.isSelected())
                checkBox.click();
            else
                log.info(sTestCaseName + "- CheckBox for " + sCheckBoxName + "is already unselected");
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for Check Box " + locatorOf(element)
                    + " is not attached to the page document" + getStackTrace());
            toBeFail("Element for Check Box " + locatorOf(element) + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element for Check Box " + sCheckBoxName + " was not found in DOM"
                    + getStackTrace());
            toBeFail("Element for unCheck Box " + sCheckBoxName + " was not found in DOM");
        } catch (Exception e) {
            toBeFail("Unable to uncheck the checkbox for'" + sCheckBoxName + "'");
        }
    }

    /**
     * Method to right click on mouse
     * @param element
     * @param sElementName
     * @param optionWaitTime
     */
    public void safeContextMouseClick(WebElement element, String sElementName, int optionWaitTime) {
        try {
            isElementPresent(element, sElementName, optionWaitTime);
            Actions action = new Actions(webDriver);
            action.contextClick(element).build().perform();
            log.info(sTestCaseName + "- Sucessfully Right clicked on the element.");
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            toBeFail("Element " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not found in DOM");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not clickable");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not clickable ");
        }
        setFinalHighlight(element);
    }

    /**
     * Method to move to an particular webelement
     * @param element
     * @param sElementName
     * @param optionWaitTime
     */
    public void safeMoveToElement(WebElement element, String sElementName, int optionWaitTime) {
        try {
            isElementPresent(element, sElementName, optionWaitTime);
            Actions action = new Actions(webDriver);
            action.moveToElement(element).build().perform();
            log.info(sTestCaseName + "- Sucessfully moved mouse cursor To Element");
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " is not attached to the page document");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not found in DOM");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not clickable");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not clickable ");
        }
        setFinalHighlight(element);
    }

    /**
     * Method to error stacktrace
     * @return error information
     */
    public String getStackTrace() {
        String trace = "";
        int i;
        log.info("printing stracktrace");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (i = 1; i < stackTrace.length; i++) {
            trace = trace + "\n" + stackTrace[i];
        }
        return trace;
    }

    /**
     * Method to click on an webelement
     * @param element
     * @param sElementName
     * @param optionWaitTime
     */
    public void safeClick(WebElement element, String sElementName, int optionWaitTime) {
        try {
            setHighlight(element);
            WaitForEnabledElement(webDriver, element, sElementName);
            log.info("Trying to click on " + sElementName + " element");
            element.click();
            log.info("Clicked on " + sElementName + " element");
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " is not attached to the page document");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not found in DOM");
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not clickable in time-" + optionWaitTime);
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not clickable in time-" + optionWaitTime);
        }
        setFinalHighlight(element);

    }

    /**
     * Method to wait until element is clickable
     * @param element
     * @param sElementName
     * @param waitTime
     * @return boolean value (true/false) to verify whether element is clickable or not.
     */
    public boolean waitForElementToBeClickable(WebElement element, String sElementName, int waitTime) {
        try {
            log.info(sTestCaseName + "- Waiting until element " + sElementName + " is clickable in time " + waitTime
                    + " secs");
            WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
            wait.until(ExpectedConditions.elementToBeClickable((element)));
        } catch (TimeoutException e) {
            log.info(sTestCaseName + "- Element " + sElementName + " was not clickable in time - " + waitTime);
            e.printStackTrace();
            toBeFail("Element " + sElementName + " was not clickable in time - " + waitTime);
            return false;
        } catch (NoSuchElementException e) {
            log.info(
                    sTestCaseName + "- Element " + element + "is not attached to the page document" + e.getStackTrace());
            e.printStackTrace();
            toBeFail("Element " + element + "is not attached to the page document");
            return false;
        } catch (Exception e) {
            log.info("Unable to find the element " + sElementName);
            e.printStackTrace();
            toBeFail("Unable to find the element " + sElementName);
            return false;
        }
        return true;
    }

    /**
     * Method to check an checkbox
     * @param element
     * @param sCheckBoxName
     * @param optionWaitTime
     */
    public void safeCheck(WebElement element, String sCheckBoxName, int optionWaitTime) {
        try {
            waitForElementToLoad(element, sCheckBoxName, optionWaitTime);
            WebElement checkBox = element;
            setHighlight(checkBox);
            if (element.isSelected())
                log.info(sTestCaseName + "- CheckBox for " + sCheckBoxName + "is already selected");
            else
                element.click();
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for Check Box " + locatorOf(element)
                    + " is not attached to the page document" + e.getStackTrace());
            toBeFail("Element for Check Box " + locatorOf(element) + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element for Check Box " + sCheckBoxName + " was not found in DOM"
                    + e.getStackTrace());
            toBeFail("Element for Check Box " + sCheckBoxName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Unable to check the checkbox for " + sCheckBoxName + " " + e.getStackTrace());
            toBeFail("Unable to check the checkbox for'" + sCheckBoxName + "'");
        }
        setFinalHighlight(element);
    }

    /**
     * Method to check an checkbox using javascript executor
     * @param element
     * @param sCheckBoxName
     * @param optionWaitTime
     */
    public void safeCheckJavaScript(WebElement element, String sCheckBoxName, int optionWaitTime) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            waitForElementToLoad(element, sCheckBoxName, optionWaitTime);
            WebElement checkBox = element;
            setHighlight(checkBox);
            executor.executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for Check Box " + locatorOf(element)
                    + " is not attached to the page document" + e.getStackTrace());
            toBeFail("Element for Check Box " + locatorOf(element) + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element for Check Box " + sCheckBoxName + " was not found in DOM"
                    + e.getStackTrace());
            toBeFail("Element for Check Box " + sCheckBoxName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Unable to check the checkbox for " + sCheckBoxName + " " + e.getStackTrace());
            toBeFail("Unable to check the checkbox for'" + sCheckBoxName + "'");
        }
        setFinalHighlight(element);
    }

    /**
     * Method to wait for an element
     * @param element
     * @param sElementName
     */
    public void waitForElement(WebElement element, String sElementName) {
        log.info("waiting for " + sElementName + " element");
        int iTimeOut = 600;
        for (int i = 0; i <= iTimeOut; i++) {
            try {
                if (element.isDisplayed()) {
                    log.info("Element " + sElementName + " is displayed");
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (WebDriverException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException j) {
                }
            }
            if (i == iTimeOut) {
                log.info("No WebObject: " + sElementName + " FOUND, waited for: " + ((i * 1000) / 60) + "sec.");
                toBeFail("Element " + sElementName + " not found. Waited for " + ((i * 1000) / 60) + "sec");
                break;
            }
        }
    }

    /**
     * Method to wait for an visibility of an element
     * @param element
     * @param sElementName
     */
    public void waitForElementInvisibility(WebElement element, String sElementName) {
        log.info("waiting for " + sElementName + " element");
        int iTimeOut = 200;
        for (int i = 0; i <= iTimeOut; i++) {
            try {
                if (!(element.isDisplayed())) {
                    log.info("Element " + sElementName + " is displayed");
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (WebDriverException e) {
                break;
            }
            if (i == iTimeOut) {
                log.info("WebObject: " + sElementName + " Still visible, waited for: " + ((i * 1000) / 60) + "sec.");
                toBeFail("WebObject: " + sElementName + " Still visible, waited for: " + ((i * 1000) / 60) + "sec.");
                break;
            }
        }
    }

    /**
     * Method to wait for invisibility of an element
     * @param locator
     * @param sElementName
     */
    public void waitForInvisibilityOfElement(By locator, String sElementName) {
        log.info("waiting for " + sElementName + " element");
        int iTimeOut = 200;
        for (int i = 0; i <= iTimeOut; i++) {
            try {
                WebElement element = webDriver.findElement(locator);
                if (!(element.isDisplayed())) {
                    log.info("Element " + sElementName + " is displayed");
                    break;
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (WebDriverException e) {
                break;
            }
            if (i == iTimeOut) {
                log.info("WebObject: " + sElementName + " Still visible, waited for: " + ((i * 1000) / 60) + "sec.");
                toBeFail("WebObject: " + sElementName + " Still visible, waited for: " + ((i * 1000) / 60) + "sec.");
                break;
            }
        }
    }

    /**
     * Method to get an attribute of an element
     * @param element
     * @param sAttributeValue
     * @param sElementName
     * @param waitTime
     * @return attribute value for a particular attribute
     */
    public String safeGetAttribute(WebElement element, String sAttributeValue, String sElementName, int waitTime) {
        String sValue = null;
        try {
            if (isElementPresent(element, sElementName, waitTime)) {
                setVerificationHighlight(element);
                sValue = element.getAttribute(sAttributeValue);
                log.info(sTestCaseName + "- Got attribute value of the " + sAttributeValue + "type : " + sValue);
            } else {
                log.info(sTestCaseName + "- Unable to get attribute " + sAttributeValue + " from element "
                        + sElementName);
                toBeFail("Unable to get attribute " + sAttributeValue + " from element " + sElementName);
            }
        } catch (StaleElementReferenceException e) {
            log.error(sTestCaseName + "- Element for " + sElementName + " is not attached to the page document"
                    + getStackTrace());
            toBeFail("Element for " + sElementName + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element for " + sElementName + " was not found in DOM" + getStackTrace());
            toBeFail("Element for " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Unable to get attribute value of type " + sAttributeValue
                    + " from the element " + sElementName + "\n" + getStackTrace());
            toBeFail("Unable to get attribute value of type " + sAttributeValue + " from the element " + sElementName);
        }
        return sValue;
    }


    /**
     * Method to wait for an modal dialog
     * @param Driver
     * @param locator
     * @param sElementName
     * @throws InterruptedException
     */
    public void WaitForModalDialogInvisibility(WebDriver Driver, By locator, String sElementName) throws InterruptedException {
        WebElement Element = webDriver.findElement(locator);
        int iTimeOut = 250;
        for (int i = 0; i < iTimeOut; i++) {
            Thread.sleep(1000);
            if (!Element.isDisplayed()) {
                break;
            }
            if (i == iTimeOut) {
                log.info("WebObject: still Exist " + sElementName + " FOUND, waited for: " + ((i * 1000) / 60) + "sec.");
                break;
            }
        }
    }

    /**
     * Method to wait for invisibility of an element
     * @param locator
     * @param sElementName
     * @param timeout
     * @return boolean value (true/false)
     */
    public boolean waitForModalDialogInvisibilityOfElement(By locator, String sElementName, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            log.info(sElementName + " are visible ");
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * method to return webdriver
     * @return
     */
    public WebDriver getDriver() {
        return webDriver;
    }

    public static Utils getInstance() {
        return UTILS;
    }

    public Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Method convert string to webelement
     * @param sLocator
     * @param sText
     * @return webelement
     */
    public WebElement formatWebElement(String sLocator, String... sText) {
        sLocator = String.format(sLocator, sText);
        return getWebElement(By.xpath(sLocator));
    }

    /**
     * Method to get an text from webelement
     * @param element
     * @param sElementName
     * @param waitTime
     * @return text from an webelement
     */
    public String safeGetText(WebElement element, String sElementName, int waitTime) {
        setHighlight(element);
        String sValue = null;
        try {
            if (isElementPresent(element, sElementName, waitTime)) {
                sValue = element.getText();
            } else {
                toBeFail("Unable to get the text from the element " + sElementName);
            }
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for " + sElementName + "is not attached to the page document"
                    + e.getStackTrace());
            toBeFail("Element for " + sElementName + "is not attached to the page document");
        } catch (NoSuchElementException e) {
            log.info(sTestCaseName + "- Element for " + sElementName + " was not found in DOM" + e.getStackTrace());
            toBeFail("Element for " + sElementName + " was not found in DOM");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Unable to get the text from the element " + sElementName + "\n"
                    + e.getStackTrace());
            toBeFail("Unable to get the text from the element " + sElementName);
        }
        return sValue;
    }

    /**
     * Method to upto scroll upto an element and click on that element
     * @param element
     * @param time
     */
    public void scrollToClick(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(webDriver, time);

        try {
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            wait.until(ExpectedConditions.elementToBeClickable(element));
            js.executeScript("arguments[0].click();", element);
            System.out.println(element.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to wait for an element
     * @param element
     * @param time
     */
    public void elementWait(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(webDriver, time);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Method to wait for an element
     * @param xpath
     * @param time
     */
    public void elementWait(String xpath, int time) {

        WebDriverWait wait = new WebDriverWait(webDriver, time);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    /**
     * Method to click on an webelement using javascript
     * @param element
     * @param elementName
     * @param time
     */
    public void safeJavaScriptClick(WebElement element, String elementName, int time) {
        WebDriverWait wait = new WebDriverWait(webDriver, time);
        try {
            setHighlight(element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();", element);
            log.info("element clicked on  " + elementName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send text into text field after scrolling to that particular element
     * @param element
     * @param textToSend
     * @param elementName
     * @param time
     */
    public void scrollToSendKeys(WebElement element, String textToSend, String elementName, int time) {
        if (element.isDisplayed()) {
            try {
                setHighlight(element);
                WebDriverWait wait = new WebDriverWait(webDriver, time);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                Actions actions = new Actions(webDriver);
                actions.moveToElement(element).sendKeys(textToSend).perform();
                log.info(elementName + "element keys send as" + elementName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to clear the text in a text field
     * @param element
     * @param elementName
     * @param time
     */
    public void safeClear(WebElement element, String elementName, int time) {
        if (element.isDisplayed()) {
            try {
                setHighlight(element);
                WebDriverWait wait = new WebDriverWait(webDriver, time);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                Actions actions = new Actions(webDriver);
                actions.moveToElement(element).perform();
                element.clear();
                log.info(elementName + "Field cleared successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to send text into a text field
     * @param element
     * @param time
     * @param editSearch
     */
    public void safeSendKeys(WebElement element, int time, String editSearch) {
        if (element.isDisplayed()) {
            try {
                setHighlight(element);
                WebDriverWait wait = new WebDriverWait(webDriver, time);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
                javascriptExecutor.executeScript("arguments[1].value = arguments[0]; ", editSearch, element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to switch to window having windowtitle as parameter
     * @param sWindowTitle
     */
    public void switchToWindowOfTitle(String sWindowTitle) {
        try {
            waitForPageToLoad();
            Set<String> allWindowHandles = webDriver.getWindowHandles();
            for (String currentWindow : allWindowHandles) {
                Thread.sleep(2000);
                webDriver.switchTo().window(currentWindow);
                Thread.sleep(1000);
                if (webDriver.getTitle().contains(sWindowTitle)) {
                    log.info(sTestCaseName + "- Navigated succesfsully to window " + sWindowTitle);
                    webDriver.switchTo().window(currentWindow);
                    waitForPageToLoad();
                    break;
                }
            }
        } catch (Exception e) {
            log.error(sTestCaseName + "- Unable to switch to window with title " + sWindowTitle);
        }
    }

    /**
     * Method to switch to window having webelement as parameter
     * @param sWindowTitle
     */
    public void switchToWindowOfTitle(WebElement sWindowTitle) {
        try {
            waitForPageToLoad();
            webDriver.switchTo().window(String.valueOf(sWindowTitle));

        } catch (Exception e) {
            log.error(sTestCaseName + "- Unable to switch to window with title " + sWindowTitle);
        }
    }

    /**
     * Method to navigate to child window
     */
    public void switchToChildWindow() {
        String parentWindow = webDriver.getWindowHandle();
        try {
            for (String childWindowHandle : webDriver.getWindowHandles()) {
                //If window handle is not main window handle then navigate to child window
                if (!parentWindow.equals(childWindowHandle)) {
                    sleep(2);
                    webDriver.switchTo().window(childWindowHandle);
                }
            }
            waitForPageToLoad();
            log.info(sTestCaseName + "- Navigated successfully to window " + parentWindow);
        } catch (NoSuchWindowException e) {
            log.info(sTestCaseName + "- Window with specified number "
                    + " doesn't exists. Please check the window number or wait until the new window appears"
                    + getStackTrace());
            toBeFail("Window with specified number  "
                    + "doesn't exists. Please check specified the window number or wait until the new window appears");
        } catch (Exception e) {
            log.info(sTestCaseName + "- Some exception occurred while switching to new window with number: ");
            toBeFail("Some exception occurred while switching to new window with number: ");
        }
    }

    /**
     * Method to switch back to default frame
     */
    public void switchToDefault() {
        try {
            webDriver.switchTo().defaultContent();
            log.info("Navigated to back to web page from frame");
        } catch (Exception e) {
            log.error(sTestCaseName + " -Unable to switch back to main window from frame");
            toBeFail("Unable to switch back to main window from frame");
        }
    }

    /**
     * Method to verify whether element is present or not
     * @param by
     * @return  boolean value (true/false) to verify whether element is present or not.
     */
    public boolean verifyElementDoesNotExist(final By by) {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        final List<WebElement> eList = webDriver.findElements(by);
        final int i = eList.size();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (i > 0) {
            for (final WebElement webElement : eList) {
                if (webElement.isDisplayed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to switch to an frame with webelement as parameter
     * @param sFrameIndex
     */
    public void switchToFrame(WebElement sFrameIndex) {
        try {
            log.info("waiting for frame with index " + sFrameIndex);
            webDriver.switchTo().frame(sFrameIndex);
            log.info("Successfully switched to frame with index " + sFrameIndex);
        } catch (NoSuchFrameException e) {
            log.error(sTestCaseName + " -Unable to locate frame with index " + sFrameIndex);
            toBeFail(" -Unable to locate frame with index " + sFrameIndex);
        } catch (Exception e) {
            log.error(sTestCaseName + " -Unable to locate to frame with index " + sFrameIndex);
            toBeFail("Unable to locate frame with index " + sFrameIndex);
        }
    }

    /**
     * Method to switch to an frame with framename as parameter
     * @param sFrameName
     */
    public void switchToFrame(String sFrameName) {
        try {
            log.info("waiting for frame with index " + sFrameName);
            webDriver.switchTo().frame(sFrameName);
            log.info("Successfully switched to frame with index " + sFrameName);
        } catch (NoSuchFrameException e) {
            log.error(sTestCaseName + " -Unable to locate frame with index " + sFrameName);
            toBeFail(" -Unable to locate frame with index " + sFrameName);
        } catch (Exception e) {
            log.error(sTestCaseName + " -Unable to locate to frame with index " + sFrameName);
            toBeFail("Unable to locate frame with index " + sFrameName);
        }
    }

    /**
     * Method to switch to an frame with frameindex as parameter
     * @param sFrameIndex
     */
    public void switchToFrame(int sFrameIndex) {
        try {
            log.info("waiting for frame with index " + sFrameIndex);
            webDriver.switchTo().frame(sFrameIndex);
            log.info("Successfully switched to frame with index " + sFrameIndex);
        } catch (NoSuchFrameException e) {
            log.error(sTestCaseName + " -Unable to locate frame with index " + sFrameIndex);
            toBeFail(" -Unable to locate frame with index " + sFrameIndex);
        } catch (Exception e) {
            log.error(sTestCaseName + " -Unable to locate to frame with index " + sFrameIndex);
            toBeFail("Unable to locate frame with index " + sFrameIndex);
        }
    }

    /**
     * Method to reset to main window
     * @param parentWindow
     */
    public void resetToMainWindow(String parentWindow) {
        webDriver.switchTo().window(parentWindow);
        Set<String> setOfWindows = webDriver.getWindowHandles();
        Iterator<String> numberOfWindows = setOfWindows.iterator();
        while (numberOfWindows.hasNext()) {
            String childWindow = numberOfWindows.next();
            if (!parentWindow.equals(childWindow)) {
                webDriver.switchTo().window(childWindow);
            }
        }
        webDriver.close();
        webDriver.switchTo().window(parentWindow);
    }

    /**
     * Method to navigate to new window
     * @param URL
     */
    public void navigateToNewWindow(String URL) {
        String parentWindow = webDriver.getWindowHandle();
        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_T);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_T);
        for (String childWindowHandle : webDriver.getWindowHandles()) {
            //If window handle is not main window handle then navigate to child window
            if (!childWindowHandle.equals(parentWindow)) {
                sleep(2);
                webDriver.switchTo().window(childWindowHandle);
            }
        }
        webDriver.navigate().to(URL);
        waitForPageToLoad();
    }

    /**
     * Method to check whether a attribute is present or not
     * @param element
     * @param attribute
     * @return boolean value (true/false) to verify whether attribute is present or not.
     */
    public boolean isAttributePresent(WebElement element, String attribute) {
        try {
            String value = element.getAttribute(attribute);
            return (value != null);
        } catch (Exception e) {

        }
        return false;
    }
}
