package automation.utilities;

import org.apache.log4j.Logger;
import org.junit.Assert;

import java.util.Collection;


public class Asserts {

    private static Logger log = Logger.getLogger("Asserts");

    public static void toBeFail(String sMessage) {
        Assert.fail(sMessage);
    }

    public static void expectToBeFalse(boolean condition) {
        log.info("---------------------Expecting value to be FALSE, Received:-" + condition + "---------------------");
        Assert.assertFalse(condition);
    }

    public static void expectToBeFalse(boolean condition, String sMessage) {
        log.info("---------------------Expecting value to be FALSE, Received:-" + condition + "---------------------");
        Assert.assertFalse(sMessage, condition);
    }

    public static void expectToBeNotEqual(boolean actual, boolean expected, String sMessage) {
        log.info("Comparing values--- Actual:- " + actual + ", Expected:- " + expected);
        org.testng.Assert.assertNotEquals(actual, expected, sMessage);
    }

    public static void expectToBeEqual(Object actual, Object expected) {
        log.info("Comparing values--- Actual:- " + actual + ", Expected:- " + expected);
        org.testng.Assert.assertEquals(actual, expected);
    }

    public static void expectToBeTrue(boolean condition) {
        org.testng.Assert.assertTrue(condition);
    }

    public static void expectToBeTrue(boolean condition, String sMessage) {
        log.info("---------------------Expecting value to be TRUE , Received:-" + condition + "---------------------");
        org.testng.Assert.assertTrue(condition, sMessage);
    }

    public static void expectArrayToBeEqual(Object[] actual, Object[] expected, String message) {
        org.testng.Assert.assertEquals(actual, expected, message);
    }

    public static void expectArrayToBeEqual(Object[] actual, Object[] expected) {
        org.testng.Assert.assertEquals(actual, expected);

    }

    public static void expectListToBeEqual(Collection<?> actual, Collection<?> expected) {
        org.testng.Assert.assertEquals(actual, expected);

    }

    public static void expectListToBeEqual(Collection<?> actual, Collection<?> expected, String message) {
        org.testng.Assert.assertEquals(actual, expected, message);
    }

    public static void expectToBeEqual(Object actual, Object expected, String sMessage) {
        log.info("Comparing values--- Actual:- " + actual + ", Expected:- " + expected);
        org.testng.Assert.assertEquals(actual, expected, sMessage);
    }

    public static void expectListToBeEqualNoOrder(Collection<?> actual, Collection<?> expected, String message) {
        Object[] objActual = actual.toArray();
        Object[] objExpect = expected.toArray();
        org.testng.Assert.assertEqualsNoOrder(objActual, objExpect, message);
    }

    public static void expectToBeNotEqual(Object actual, Object expected, String sMessage) {
        log.info("Comparing values--- Actual:- " + actual + ", Expected:- " + expected);
        org.testng.Assert.assertNotEquals(actual, expected, sMessage);
    }

}
