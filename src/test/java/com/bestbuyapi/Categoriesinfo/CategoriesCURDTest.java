package com.bestbuyapi.Categoriesinfo;


import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.model.CategoriesPojo;
import com.bestbuyapi.testbase.TestBase;
import com.bestbuyapi.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;



public class CategoriesCURDTest extends TestBase {

   static String  categoryId = "abcd124"+TestUtils.getRandomValue();
   static String name = "sap"+TestUtils.getRandomValue();
   static  int id;

    @Title("This is will get all information of all categories")
    @Test
    public void test001() {
        SerenityRest. given().log().all()
                .when().get()
                .then()
                .log().all()
                .statusCode(200);
    }
   @Title("This will create a new categories")
    @Test
    public void test002(){
       CategoriesPojo categoriesPojo = new CategoriesPojo();
       categoriesPojo.setId(categoryId);
       categoriesPojo.setName(name);
      SerenityRest.given().log().all()
               .contentType(ContentType.JSON)
               .body(categoriesPojo)
               .when()
               .post()
               .then().log().all().statusCode(201);
   }
    @Title("verify if categories was created")
    @Test
    public void test003(){
        String p1="data.findAll{it.name='";
        String p2="'}.get(0)";
        HashMap<String,Object> catemap =SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(catemap,hasValue(name));
        categoryId = (String) catemap.get("id");
    }


  @Title("update categories with name and id")
    @Test
    public void test004(){
        name = name + "updated";

      CategoriesPojo categoriesPojo = new CategoriesPojo();
      categoriesPojo.setId(categoryId);
      categoriesPojo.setName(name);
      SerenityRest.given().log().all()
              .header("Content-Type","application/json; charset=UTF-8")
              .pathParam("categoryId",categoryId)
              .body(categoriesPojo)
              .when()
              .put(EndPoints.UPDATE_SINGLE_CATEGORY_BY_ID)
              .then().log().all().statusCode(200);
  }
    @Title("Delete the categories and verify if the categories is deleted")
    @Test
    public void test005(){
        SerenityRest. given()
                .pathParam("id",id)
                .when()
                .delete(EndPoints.DELETE_SINGLE_CATEGORY_BY_ID)
                .then().statusCode(200);

        SerenityRest .given().log().all()
                .pathParam("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_CATEGORY_BY_ID)
                .then() .statusCode(404);

    }

}
