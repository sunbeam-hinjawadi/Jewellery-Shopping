package projects.practice.jewelleryappintegrated.util;

import com.google.gson.JsonObject;

import projects.practice.jewelleryappintegrated.entity.Customer;
import projects.practice.jewelleryappintegrated.entity.JewelleryProduct;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    //String BASE_URL = "http://192.168.1.103:8085";
   // String BASE_URL = "http://192.168.13.123:8085";
    String BASE_URL = "http://192.168.216.23:8085";
    //String BASE_URL = "http://192.168.67.23:8085";
    // String BASE_URL = "http://192.168.21.109:8085";



    ///api/v1/customer/login
    @POST("/api/v1/customer/nishantlogin") // Replace "login" with your actual endpoint
    Call<JsonObject> loginCustomer(@Body Customer customer);

    @POST("/api/v1/customer/nishantloggedcustomer") // to getuser data only using customerid
    Call<JsonObject> loggedCustomer(@Body Customer customer);

    @POST("/api/v1/customer/nishantregister")
    Call<JsonObject> registerCustomer(@Body Customer customer);


    @GET("/api/v1/category/")
    Call<JsonObject> getAllCategories();

    //

    @GET("/api/v1/jewellery/{category_id}")
    Call<JsonObject> getAllJewelleryProducts(@Path("category_id") int category_id);



    // cart calls

    @GET("/api/v1/cart/{customer_id}")
    Call<JsonObject> getAllCartProductsByCid(@Path("customer_id") int customer_id);

    @PUT("/api/v1/cart/nishantupdateQuantity/{jewellery_id}")
    Call<JsonObject> updateCart(@Path("jewellery_id")int jewellery_id , @Body Quantity quantity); //mention customer_id in quantity

    @POST("/api/v1/cart/deleteCartItem/{jewellery_id}")
    Call<JsonObject> deleteCartItem(@Path("jewellery_id")int jewellery_id , @Body Customer loggedcustomer);

    @POST("/api/v1/cart/nishantaddToCartItem/{customer_id}")
    Call<JsonObject> addToCartItem(@Path("customer_id")int jewellery_id , @Body JewelleryProduct jewelleryProduct);


    @DELETE("/api/v1/cart/empty-cart/{customer_id}")
    Call<JsonObject> deleteCustomerCart(@Path("customer_id") int customer_id);

    @GET("/api/v1/cart/totalprice/{customer_id}")
    Call<JsonObject> totalcartprice(@Path("customer_id") int customer_id);


    @POST("/api/v1/order/add/{customer_id}")
    Call<JsonObject> placeorder(@Path("customer_id") int customer_id , @Body OrderProduct orderproduct);

//    @PUT("/quote/update/{id}")
//    Call<JsonObject> updateQuote(@Path("id") int id, @Body Quote quote);


//
//    @GET("/api/v1/category/")
//    Call<JsonObject> getUpdatedUserProfile(@Body customerPojo customer);

}
