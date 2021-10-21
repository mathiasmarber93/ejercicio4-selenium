package Yelp.pageobject;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage extends PageObject {

    @FindBy(xpath="//*[@id='find_desc']")
    WebElementFacade findButton;

    @FindBy(xpath="//*[@data-suggest-query='Restaurants']")
    WebElementFacade restaurantOption;

    @FindBy(xpath = "//input[@id='search_description']")
    WebElementFacade searchBox;

    @FindBy(xpath="//form[@id='header_find_form']//button[@value='submit']")
    WebElementFacade searchButton;

    @FindBy(xpath = "//*[@id=\"main-content\"]/div/ul/li[22]/div/div[2]/span")
    WebElementFacade lastSearchPageNumber;

    @FindBy(xpath = "//h1[@class='css-m7s7xv']")
    WebElementFacade restaurantName;

    @FindBy(xpath = "//*[contains(text(),'Phone number')]//following-sibling::p")
    WebElementFacade restaurantPhone;

    @FindBy(xpath = "//*[contains(text(),'Get Directions')]//parent::p//following-sibling::p")
    WebElementFacade restaurantAddress;

    @FindBy(xpath = "//div[@class=' i-stars__373c0___sZu0 i-stars--large-4__373c0__1yIRU border-color--default__373c0__1yxBb overflow--hidden__373c0__1TJqF' and @aria-label='4 star rating']")
    WebElementFacade restaurantRating;

    @FindBy(xpath="//span[contains(text(),'Yelp Sort')]/parent::button")
    WebElementFacade reviewSortByButton;

    @FindBy(xpath="//*[contains(text(),'Newest First')]//ancestor::button")
    WebElementFacade newestSortButton;

    static String xpathRestaurantForSushi="//*[contains(@class,'ResultsContainer')]//descendant::h4";
    static Integer count;
    static Integer total;
    static List<WebElementFacade> myListOfSushiRestaurants;
    static List<WebElementFacade> myReviewsOfSushiRestaurant;

    public void searchRestaurant(){
        findButton.click();
        restaurantOption.click();
    }

    public void searchRestaurantForSushi(String restaurant){
        searchBox.clear();
        searchBox.sendKeys(restaurant);
        searchButton.click();
        myListOfSushiRestaurants=withTimeoutOf(30, TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurantForSushi));
        numberOfSearchInFirstPage(myListOfSushiRestaurants);
        numberOfSearchInAllPages(myListOfSushiRestaurants);
        String totalActualPage = "El total de busqueda de la pagina actual  es: " + count;
        String totalAllPages = "El total de busqueda de todas las paginas es: " + total;
        String totalReport = totalActualPage + "\n" + totalAllPages;
        Serenity.recordReportData().withTitle("Resultados de la busqueda").andContents(totalReport);
    }

    public void selectFilter(String distance){
        String filterXpath="//span[contains(text(),'"+distance+"')]";
        WebElement filterDistance=withTimeoutOf(20,TimeUnit.SECONDS).find((By.xpath(filterXpath)));
        filterDistance.click();
    }

    public void selectFirstResult(){
        myListOfSushiRestaurants=withTimeoutOf(20,TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurantForSushi));
        for(WebElementFacade inputElement : myListOfSushiRestaurants){
            if(inputElement.getText().contains(".")){
                Serenity.recordReportData().withTitle("Nombre del Primer Restaurante Sushi").andContents(inputElement.getText());
                inputElement.click();
                break;
            }
        }
    }

    public void getRestaurantMainInformation(){
        String name = "Nombre: " + restaurantName.getText();
        String phone = "Telefono: " + restaurantPhone.getText();
        String address = "Direccion: " + restaurantAddress.getText();
        String ratingToString = String.valueOf(restaurantRating.getAttribute("aria-label"));
        Integer ratingToInteger = Integer.parseInt(ratingToString.replace(" star rating", ""));
        String rating = "Rating: " + ratingToInteger + " estrellas";
        reviewSortByButton.click();
        newestSortButton.click();
        String xpathReviews="//p[@class='comment__373c0__Nsutg css-n6i4z7']";
        myReviewsOfSushiRestaurant=withTimeoutOf(20,TimeUnit.SECONDS).findAll(By.xpath(xpathReviews));
        String reviewTitle = "Reseñas mas recientes";
        String firstReview = "1. Primera reseña: " +myReviewsOfSushiRestaurant.get(0).getText();
        String secondReview = "2. Segunda reseña: " +myReviewsOfSushiRestaurant.get(1).getText();
        String thirdReview = "3. Tercera reseña: " +myReviewsOfSushiRestaurant.get(2).getText();
        String information = name + "\n" + phone + "\n" + address + "\n" + rating + "\n" + reviewTitle + "\n" + firstReview + "\n" + secondReview + "\n" + thirdReview;
        Serenity.recordReportData().withTitle("Informacion del Restaurante Sushi").andContents(information);
    }

    public void numberOfSearchInFirstPage(List<WebElementFacade> listOfSushiRestaurants){
        count=0;
        for(WebElementFacade inputSushi : listOfSushiRestaurants){
           if(inputSushi.getText().contains(".")){
                count=count+1;
            }
        }
    }

    public void numberOfSearchInAllPages(List<WebElementFacade> listOfSushiRestaurants){
        Integer totalSearchNumber = Integer.parseInt(lastSearchPageNumber.getText().replace("1 of ",""));
        count=0;
        for(WebElementFacade inputSushi : listOfSushiRestaurants){
            if(inputSushi.getText().contains(".")){
                count=count+1;
            }
        }
        total = totalSearchNumber * count;
    }

}
