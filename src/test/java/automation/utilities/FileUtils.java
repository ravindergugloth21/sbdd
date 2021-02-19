package automation.utilities;


import org.apache.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.util.zip.ZipFile;

import static automation.utilities.Asserts.toBeFail;

public class FileUtils {
    Utils utils = new Utils();
    String sTestCaseName = "";
    Logger log = Logger.getLogger("FileUtils");
    private static final FileUtils fileUtils = new FileUtils();

    /**
     * File separator
     *
     * @return
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * Used to get current directory
     *
     * @return current users working directory
     */
    public static String getCurrentDir() {
        return System.getProperty("user.dir");
    }

    /**
     * OS detector
     *
     * @return the type of operating system
     */
    public static String OSDetector() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "Windows";
        } else if (os.contains("nux") || os.contains("nix")) {
            return "Linux";
        } else if (os.contains("mac")) {
            return "Mac";
        } else if (os.contains("sunos")) {
            return "Solaris";
        } else {
            return "Other";
        }
    }

    /**
     * Resolve file
     *
     * @param fileName
     * @return
     * @throws URISyntaxException
     */
    public static File resolveFile(String fileName) throws URISyntaxException {
        return new File(FileUtils.class.getClassLoader().getResource(fileName).toURI());
    }

    @SuppressWarnings({"unused", "resource"})
    /**
     * Extract report from zip file
     */
    public static void extractReportFromZipFile(String fileName) {
        try {
            File zipFileLocation = resolveFile(fileName);
            ZipFile zipFile = new ZipFile(zipFileLocation);
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (URISyntaxException urie) {
            urie.printStackTrace();
        }
    }

    /**
     * Method to get the file size.
     *
     * @param sFilePath
     * @return file size
     */
    public long getFileSize(String sFilePath) {
        File file = new File(sFilePath);
        long length = file.length();
        return length;
    }

    /**
     * Method used for used for uploading a file
     *
     * @param element
     * @param sElementName
     * @param sFilePath
     * @param waitTime
     */
    public void uploadFile(WebElement element, String sElementName, String sFilePath, int waitTime) {
        try {
            if (utils.isElementPresent(element, sElementName, waitTime)) {
                utils.setHighlight(element);
                File path = new File(sFilePath);
                element.sendKeys(path.getAbsolutePath());
                log.info("Entered: " + sFilePath + " into " + sElementName);
            } else {
                log.info(sTestCaseName + "- Element for " + sElementName + " is not displayed");
                toBeFail(sElementName + " is not displayed");
            }
        } catch (StaleElementReferenceException e) {
            log.info(sTestCaseName + "- Element for " + sElementName + "is not attached to the page document");
            toBeFail(sElementName + "is not attached to the page document");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            log.info(sTestCaseName + "- Element for " + sElementName + " was not found in DOM");
            toBeFail(sElementName + " was not found in DOM");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(sTestCaseName + "- Unable to upload file - " + sFilePath + " in " + sElementName);
            toBeFail("Unable to upload file - " + sFilePath + " in " + sElementName);
        }
        utils.setFinalHighlight(element);
    }

    /**
     * Used for uploading an image
     *
     * @param sImagePath
     * @throws AWTException
     * @throws InterruptedException
     */
    public void uploadImage(String sImagePath) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.setAutoDelay(2000);
        StringSelection stringSelection = new StringSelection(sImagePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        utils.sleep(20);
    }

    /**
     * Method for single time initialization
     */
    public static FileUtils getInstance() {
        return fileUtils;
    }

    public Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
