package com.bestbuyapi.categoriesinfo;


import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.constants.Path;
import com.bestbuyapi.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class CategoriesSteps {
    static  int id;
    @Step("creating categories with name :{0},id :{1}")
    public ValidatableResponse createcategories(String name, String categoryId) {
        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setId(categoryId);
        categoriesPojo.setName(name);
       return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);
    }

    @Step ("getting categories info by name:{0}")
    public HashMap<String,Object> getcategoriesInfoByName(String name){
        String p1="data.findAll{it.name='";
        String p2="'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step("update categories with name :{0},id :{1}")
    public ValidatableResponse updatecategories(String name, String categoryId) {
        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setId(categoryId);
        categoriesPojo.setName(name);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("categoryId", categoryId)
                .body(categoriesPojo)
                .when()
                .put(EndPoints.UPDATE_SINGLE_CATEGORY_BY_ID)
                .then();
    }
    @Step("Delete categories with categoriesID :{0} ")
    public ValidatableResponse deletecategories(String id){
        return    SerenityRest. given()
                .pathParam("id",id)
                .when()
                .delete(EndPoints.DELETE_SINGLE_CATEGORY_BY_ID)
                .then();


    }
    @Step("Verify category has been deleted for categoryId: {0}")
    public ValidatableResponse verifycategoryDeleted(String id){
        return SerenityRest .given().log().all()
                .pathParam("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_CATEGORY_BY_ID)
                .then();


    }
    }
