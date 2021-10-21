package Yelp.steps;

import Yelp.pageobject.MainPage;
import net.serenitybdd.core.steps.ScenarioActor;
import net.thucydides.core.annotations.Steps;

public class UserYelp extends ScenarioActor {

    String actor;

    @Steps(shared=true)
    MainPage mainPage;

    public void navigatesTo(){
        mainPage.setDefaultBaseUrl("https://www.yelp.com/");
        mainPage.open();
    }

    public void searchFor(){
        mainPage.searchRestaurant();
    }

    public void searchRestaurantForSushi(String restaurant){
        mainPage.searchRestaurantForSushi(restaurant);
    }

    public void filterByDistance(String filter){
        mainPage.selectFilter(filter);
    }

    public void clickOnFirstResult(){
        mainPage.selectFirstResult();
    }

    public void showRestaurantInformation(){
        mainPage.getRestaurantMainInformation();
    }


}
