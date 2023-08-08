package com.daffodil.android.template.network

import ApiServiceImpl
import com.daffodil.android.template.data.remote.response.models.Address
import com.daffodil.android.template.data.remote.response.models.Geo
import com.daffodil.android.template.data.remote.response.models.User
import com.daffodil.android.template.data.remote.service.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response



class ApiServiceTest {

    private lateinit var apiService: ApiService

    // Initialize the ApiService implementation (e.g., Retrofit implementation)
    @Before
    fun setup() {
        apiService = ApiServiceImpl()
    }

    @Test
    fun getUsers_returnsListOfUsers() = runBlocking {
        // Act
        val response: Response<List<User>> = apiService.getUsers()
        // Assert
        assertEquals(200, response.code())
        val userList = response.body()
        val expectedUserList = listOf(
            User(
                1,
                "John",
                "johnDoe",
                "john@example.com",
                Address("123 Street", "Suite 4", "City", "12345", Geo("40.7128", "-74.0060"))
            ),
            User(
                2,
                "Jane",
                "janeSmith",
                "jane@example.com",
                Address("456 Avenue", "Suite 8", "City", "67890", Geo("40.7128", "-74.0060"))
            )
        )
        assertEquals(expectedUserList, userList)
    }
}
