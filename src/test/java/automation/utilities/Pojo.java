package automation.utilities;

public class Pojo {

    private static final Pojo pojo = new Pojo();

    private String scenarioName;
    private String browserName;
    private String environment;
    private String pinCode;
    private String itemName;
    private String productName;
    private String englishTranslationText;
    private String languageSettings;
    private String value;
    private String searchItemName;

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }


    public void setEnvironmeent(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {

        return environment;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public void setPinCode(String pinCode){
        this.pinCode=pinCode;
    }

    public String getPinCode(){
        return pinCode;
    }

    public void setProductName(String itemName){
        this.itemName=itemName;

    }
    public String getProductName(){
        return itemName;
    }

    public void setItemName(String productName){
        this.productName=productName;
    }

    public String getItemName(){
        return productName;
    }

    public void setEnglishTranslationText(String englishTranslationText){
        this.englishTranslationText=englishTranslationText;
    }
    public String getEnglishTranslationText(){
        return englishTranslationText;
    }

    public void setLanguageSettings(String languageSettings){
        this.languageSettings=languageSettings;
    }

    public String getLanguageSettings(){
        return languageSettings;
    }

    public void setValue(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }

    public void setSearchItemText(String searchItemName){
        this.searchItemName=searchItemName;
    }

    public String getSearchItemText(){
        return searchItemName;
    }

    public static Pojo getInstance() {
        return pojo;
    }

    public Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


}
