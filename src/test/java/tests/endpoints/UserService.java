package tests.endpoints;

import model.retrofit.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {
    /**
     * https://reqres.in/ consider LIST USERS request [GET] /api/users?page=2
     * Refer to classes in src/main/java/model/retrofit: Support,UserResponse,ListUsersRootResponse
     * Example of response
     * {
     *     "page": 2,
     *     "per_page": 6,
     *     "total": 12,
     *     "total_pages": 2,
     *     "data": [
     *         {
     *             "id": 7,
     *             "email": "michael.lawson@reqres.in",
     *             "first_name": "Michael",
     *             "last_name": "Lawson",
     *             "avatar": "https://reqres.in/img/faces/7-image.jpg"
     *         }
     *     ],
     *     "support": {
     *         "url": "https://reqres.in/#support-heading",
     *         "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
     *     }
     * }
     * @param pageParam
     * @return
     */
    @GET("/api/users")
    Call<ListUsersRootResponse> getUserList(@Query("page") int pageParam);

    /**
     * https://reqres.in/ consider SINGLE USER request [GET] /api/users/2
     * Refer to classes in src/main/java/model/retrofit: Support,SingleUserResponse
     * Example of response
     * {
     *     "data": {
     *         "id": 2,
     *         "email": "janet.weaver@reqres.in",
     *         "first_name": "Janet",
     *         "last_name": "Weaver",
     *         "avatar": "https://reqres.in/img/faces/2-image.jpg"
     *     },
     *     "support": {
     *         "url": "https://reqres.in/#support-heading",
     *         "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
     *     }
     * }
     * @param id
     * @return
     */
    @GET("/api/users/{id}")
    Call<SingleUserResponse> getUserById(@Path("id") int id);

    /**
     * https://reqres.in/ consider CREATE request [POST] /api/users
     * With request body
     *
     *{
     *     "name": "morpheus",
     *     "job": "leader"
     * }
     *
     * And response
     *{
     *     "name": "morpheus",
     *     "job": "leader",
     *     "id": "608",
     *     "createdAt": "2023-06-24T21:08:21.632Z"
     * }
     *
     * Refer to classes in src/main/java/model/retrofit: UserCreateRequest,UserCreateResponse
     * Example of response
     * @param userCreateRequest
     * @return
     */
    @POST("/api/users")
    Call<UserCreateResponse> createUser(@Body UserCreateRequest userCreateRequest);

    /**
     * https://reqres.in/ consider UPDATE request [PUT] /api/users
     * With request body
     * {
     *     "name": "morpheus",
     *     "job": "zion resident"
     * }
     *
     * And response
     * {
     *     "name": "morpheus",
     *     "job": "zion resident",
     *     "updatedAt": "2023-06-24T21:21:32.114Z"
     * }
     *
     * @param id
     * @param userCreateRequest
     * @return
     */
    @PUT("/api/users/{id}")
    Call<UpdateUserResponse> updateUser(@Path("id") int id, @Body UserCreateRequest userCreateRequest);

    /**
     * https://reqres.in/ consider DELETE request [DELETE] /api/users/2
     * With empty response body, which is Void Java class
     * @param id
     * @return
     */
    @DELETE("/api/users/{id}")
    Call<Void> deleteUserById(@Path("id") int id);
}
