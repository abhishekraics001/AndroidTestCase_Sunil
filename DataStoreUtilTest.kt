package com.daffodil.android.template


import com.daffodil.android.template.data.local.storage.storageUtils.DataStoreUtil
import org.junit.Assert.assertTrue
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class DataStoreUtilTest {

    private var dataStoreUtil: DataStoreUtil? = null
    private var preferencesKey: Preferences.Key<String>? = null
    private var context: Context? = null
    private var dataStore: DataStore<Preferences>? = null

    /* intilize OR function should be executed before each test case in the test class.*/
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        dataStoreUtil = DataStoreUtil(context!!)
        preferencesKey = stringPreferencesKey("dataSaved")

    }

    /* To test our class first save the value and retrive with same key and after check this*/
    @Test
    fun saveToDataStore() {
        val value = "Save value successfully"
        val storedValue: String? = runBlocking {
            dataStoreUtil?.savePreference(preferencesKey!!, value)
            dataStoreUtil?.getPreference(preferencesKey!!, "")?.first()

        }
        assertTrue(storedValue == value)
    }

    /* get data from storage with respected key*/
    @Test
    fun fetchToDataStore() {
        val storedValue: Boolean = runBlocking {
            dataStoreUtil?.getPreference(preferencesKey!!, "")
            true // Return true if the saving was successful
        }
        assertTrue(storedValue)
    }


    /*  remove shared prefarances value from particular key value*/
    @Test
    fun removeFromDataStore_removesValue() = runBlocking {
        val key = stringPreferencesKey("dataSaved")
        val value = "Save value successfully"
        dataStoreUtil?.savePreference(key, value)

        dataStoreUtil?.removePreference(key)

        val result = dataStoreUtil?.getPreference(preferencesKey!!, "")?.first()

        assertEquals(
            "",
            result
        ) // The result should be the default value since we removed the original value
    }


    /* test to clear all prefarance value */
    @Test
    fun clearAllPreferenceValues() = runBlocking {
        // Arrange
        dataStoreUtil?.savePreference(preferencesKey!!, "Value1")
        dataStoreUtil?.savePreference(stringPreferencesKey("another_key"), "Value2")

        // Act
        dataStoreUtil?.clearAllPreference()
        val storedValue1: String? = dataStoreUtil?.getPreference(preferencesKey!!, "")?.first()
        val storedValue2: String? =
            dataStoreUtil?.getPreference(stringPreferencesKey("another_key"), "")?.first()

        // Assert
        assertEquals(null, storedValue1)
        assertEquals(null, storedValue2)
    }
}