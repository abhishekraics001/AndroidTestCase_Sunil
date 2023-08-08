import com.daffodil.android.template.data.remote.response.models.Address
import com.daffodil.android.template.data.remote.response.models.Geo
import com.daffodil.android.template.data.remote.response.models.User
import com.daffodil.android.template.data.remote.service.ApiService
import retrofit2.Response

class ApiServiceImpl : ApiService {
    override suspend fun getUsers(): Response<List<User>> {
        // Implement the  network request here For testing purposes
        val userList = listOf(
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
        return Response.success(userList)
    }
}
