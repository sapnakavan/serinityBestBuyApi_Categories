package com.bestbuyapi.Categoriesinfo;

import com.bestbuyapi.categoriesinfo.CategoriesSteps;
import com.bestbuyapi.testbase.TestBase;
import com.bestbuyapi.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class CategoriesCURDTestWithSteps extends TestBase {
    static String  categoryId = "abcd124"+TestUtils.getRandomValue();
    static String name = "sap"+TestUtils.getRandomValue();
    static String id;
    @Steps
    CategoriesSteps categoriesSteps ;
    @Title("This will create a new categories")
    @Test
    public void test001(){
        ValidatableResponse response = categoriesSteps.createcategories(name,categoryId);
        response.log().all().statusCode(201);
    }


    @Title("verify if categories was created")
    @Test
    public void test002(){
        HashMap<String,Object> catemap = categoriesSteps.getcategoriesInfoByName(name);
        Assert.assertThat(catemap,hasValue(name));
        categoryId = (String) catemap.get("id");
        System.out.println(categoryId);
    }

    @Title("update categories with name and id")
    @Test
    public void test003(){
        name = name + "updated";
        categoriesSteps.updatecategories(name,categoryId);
        HashMap<String,Object> catemap = categoriesSteps.getcategoriesInfoByName(name);
        Assert.assertThat(catemap,hasValue(name));

    }


    @Title("Delete the categories and verify if the categories is deleted")
    @Test
    public void test004(){
        categoriesSteps.deletecategories(id).log().all().statusCode(200);
        categoriesSteps.verifycategoryDeleted(id).log().all().statusCode(404);
    }
}
