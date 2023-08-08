package com.daffodil.android.template.network

import com.daffodil.android.template.data.remote.repository.UserRepositoryImpl
import com.daffodil.android.template.data.remote.response.ApiResponse
import com.daffodil.android.template.data.remote.response.models.Address
import com.daffodil.android.template.data.remote.response.models.Geo
import com.daffodil.android.template.data.remote.response.models.User
import com.daffodil.android.template.data.remote.service.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {

    private var userRepository: UserRepositoryImpl? = null
    private var apiService: ApiService? = null

    /*first initilize the list like -  set dummy data in list*/
    @Before
    fun setup() {
        apiService = object : ApiService {
            override suspend fun getUsers(): Response<List<User>> {
                // Simulate network request
                val userList = listOf(
                    User(
                        1, "John", "johnDoe", "john@example.com",
                        Address("123 Street", "Suite 4", "City", "12345", Geo("40.7128", "-74.0060"))
                    ),
                    User(
                        2, "Jane", "janeSmith", "jane@example.com",
                        Address("456 Avenue", "Suite 8", "City", "67890", Geo("40.7128", "-74.0060"))
                    )
                )
                return Response.success(userList)
            }
        }
        userRepository = UserRepositoryImpl(apiService!!)
    }

    /*fetch user list successfully*/
    @ExperimentalCoroutinesApi
    @Test
    fun getUsers_emitsUsersSuccessfully() = runBlocking {
        // Arrange
        val expectedUserList = listOf(
            User(
                1, "John", "johnDoe", "john@example.com",
                Address("123 Street", "Suite 4", "City", "12345", Geo("40.7128", "-74.0060"))
            ),
            User(
                2, "Jane", "janeSmith", "jane@example.com",
                Address("456 Avenue", "Suite 8", "City", "67890", Geo("40.7128", "-74.0060"))
            )
        )
        val expectedResponse = Response.success(expectedUserList)

        // Act
        val resultFlow = flow {
            emit(apiService?.getUsers())
        }
        // Assert
        resultFlow.collect { result ->
            assertEquals(expectedResponse.body(), result?.body())
        }
    }
}


//    val userList = listOf(
//        User(1, "John", "john123", "john@example.com"),
//        User(2, "Jane", "jane456", "jane@example.com")
//    )

