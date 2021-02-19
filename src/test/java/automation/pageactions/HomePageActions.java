package automation.pageactions;


import automation.pageobjects.HomePage;
import automation.utilities.Constants;
import groovy.lang.Singleton;
import org.apache.log4j.Logger;
import java.util.List;


public class HomePageActions extends CommonPageActions {

    HomePage homePage =new HomePage();
    static Logger logger = Logger.getLogger("HomePageActions");

    public HomePageActions() {
        super();
    }

    /**
     * search for an item in home page
     * @param item
     * @return the search item
     */
    public String searchForAnItem(String item) {
        logger.info("*****************Search for an item*********************");
        utils.safeClick(homePage.searchBox, "Clicked on Search Box", Constants.MEDIUMWAIT);
        utils.safeClearAndType(homePage.searchBox, item, "Search Box", Constants.MEDIUMWAIT);
        utils.safeClick(homePage.searchButton, "Clicked on Search Button", Constants.MEDIUMWAIT);
        return item;
    }

    /**
     * Method to sort thr elements in ascending order
     * @param list
     * @param <T>
     * @return boolean value (true/false)
     */
    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list) {
        logger.info("******************Compare lists whether they are in ascending order or not*********************");
        for (int i = 0; i <= list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }


}
