package com.daffodil.android.template.data.local.storage.storageUtils

import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class SharedPreferencesUtilTest {

    private var sharedPreferencesUtil: SharedPreferencesUtil? = null

    /*intilize OR function should be executed before each test case in the test class.*/
    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        sharedPreferencesUtil = SharedPreferencesUtil(context)
    }

    /* check to value like secure prefarance value save and retrive with same key*/
    @Test
    fun saveSecuredPreference_stringType() {
        // Arrange
        val key = "test_key"
        val value = "test_value"
        // Act
        sharedPreferencesUtil?.saveSecuredPreference(key, value)

        val sharedPreferences = sharedPreferencesUtil?.encryptedSharedPreferences
        val retrievedValue = sharedPreferences?.getString(key, "")
        assert(retrievedValue == value)
    }

    /* check if key-value is not match then show error */
    @Test(expected = IllegalArgumentException::class)
    fun saveSecuredPreference_unsupportedType() {
        val key = "test_key"
        val value = SomeCustomObject("test_data")
        sharedPreferencesUtil?.saveSecuredPreference(key, value)
        // Expecting IllegalArgumentException to be thrown
    }
}


class SomeCustomObject(val data: String) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): SomeCustomObject {
            return Gson().fromJson(json, SomeCustomObject::class.java)
        }
    }
}
