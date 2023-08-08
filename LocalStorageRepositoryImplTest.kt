package com.daffodil.android.template.data.local.storage.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import com.daffodil.android.template.data.local.storage.storageUtils.DataStoreUtil
import com.daffodil.android.template.data.local.storage.storageUtils.SharedPreferencesUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalStorageRepositoryImplTest {

    private var localStorageRepository: LocalStorageRepositoryImpl? = null
    private var dataStoreUtil: DataStoreUtil? = null
    private var sharedPrefUtil: SharedPreferencesUtil? = null
    private var context: Context? = null

 /*  intilize OR function should be executed before each test case in the test class.*/
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        dataStoreUtil = DataStoreUtil(context!!)
        sharedPrefUtil = SharedPreferencesUtil(context!!)
        localStorageRepository = LocalStorageRepositoryImpl(dataStoreUtil!!, sharedPrefUtil!!)
    }

    /**
     *  Test to verify that saveToDataStore- correctly saves a value to the Data Store.
     *  Retrieve the saved value from the Data Store
     *  Verify  the retrieved value matches that saved
     */
    @Test
    fun saveToDataStore_SavesValue() = runBlocking {
        val key = stringPreferencesKey("testKey")
        val value = "testValue"
        dataStoreUtil?.savePreference(key, value)

        val result = localStorageRepository?.getDataStoreValue(key, "")?.first()

        assertEquals(value, result)
    }


    /**
     * Test to verify that getDataStoreValue- returns the default value when the
     * key is not found in the Data Store.
     * Create a unique key that does not exist in the Data Store like key nonExistentKey
     */
    @Test
    fun getDataStoreValue_ifKeyNotFound() = runBlocking {
        val key = stringPreferencesKey("nonExistentKey")
        val defaultValue = "defaultValue"

        val result = localStorageRepository?.getDataStoreValue(key, defaultValue)?.first()

        assertEquals(defaultValue, result)
    }

    /**
     * Test to verify that getDataStoreValue-  returns the correct value from the Data Store
     * when the value is already saved for the specified key.
     */
    @Test
    fun getDataStoreValue_DefaultValue() = runBlocking {
        val key = stringPreferencesKey("testKey")
        val value = "testValue"
        dataStoreUtil?.savePreference(key, value)

        val result = localStorageRepository?.getDataStoreValue(key, "")?.first()

        assertEquals(value, result)
    }

  /*  remove shared prefarances value from particular key value */
    @Test
    fun removeFromDataStore_removesValue() = runBlocking {
        val key = stringPreferencesKey("testKey")
        val value = "testValue"
        dataStoreUtil?.savePreference(key, value)

        localStorageRepository?.removeFromDataStore(key)

        val result = localStorageRepository?.getDataStoreValue(key, "")?.first()

        assertEquals("", result) // The result should be the default value since we removed the original value
    }

    /**
     * Test to verify that clearAllDataStoreValues -  clears all values stored in the Data Store.
     */
    @Test
    fun clearAllDataStoreValues() = runBlocking {
        val key1 = stringPreferencesKey("testKey1")
        val value1 = "testValue1"
        dataStoreUtil?.savePreference(key1, value1)

        val key2 = stringPreferencesKey("testKey2")
        val value2 = "testValue2"
        dataStoreUtil?.savePreference(key2, value2)

        localStorageRepository?.clearAllDataStoreValues()

        // Retrieve the values for both keys from the Data Store
        val result1 = localStorageRepository?.getDataStoreValue(key1, "")?.first()
        val result2 = localStorageRepository?.getDataStoreValue(key2, "")?.first()

        // Verify that both retrieved values are the default value like all values were cleared
        assertEquals("", result1)
        assertEquals("", result2)
    }

    /* Test to verify saveSecuredDataStoreValue- correctly saves a secured value to the Data Store. */
    @Test
    fun saveSecuredDataStoreValue() = runBlocking {
        val key = stringPreferencesKey("testKey")
        val value = "testValue"

        localStorageRepository?.saveSecuredDataStoreValue(key, value)

        val result = dataStoreUtil?.getSecuredPreference(key, "")?.first()

        assertEquals(value, result)
    }

  /* Similarly, Test to verify getSecuredDataStoreValue from our class */
    @Test
    fun getSecuredDataStoreValue() = runBlocking {
        val key = stringPreferencesKey("testKey")
        val value = "testValue"
        dataStoreUtil?.saveSecuredPreference(key, value)

        val result = localStorageRepository?.getSecuredDataStoreValue(key, "")?.first()

        assertEquals(value, result)
    }
}