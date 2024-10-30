package semicolon.dev.yourpersona.util.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

// Initialize DataStore
val Context.dataStore by preferencesDataStore(name = "app_datastore")

object DataStoreHelper {

    val gson = Gson()

    // Save a single object as JSON
    suspend fun <T> putObject(context: Context, key: String, value: T) {
        val jsonString = gson.toJson(value)
        val dataStoreKey = stringPreferencesKey(key)

        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = jsonString
        }
    }

    // Get a single object from DataStore
    suspend inline fun <reified T> getObject(context: Context, key: String): T? {
        val preferences = getList<T>(context, key).first()
        val jsonString = preferences.toString()

        return try {
            gson.fromJson(jsonString, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Save a list of objects
    suspend fun <T> putList(context: Context, key: String, list: List<T>) {
        val jsonString = gson.toJson(list)
        val dataStoreKey = stringPreferencesKey(key)

        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = jsonString
        }
    }

    // Get a list of objects from DataStore
    suspend inline fun <reified T> getList(context: Context, key: String): List<T> {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[dataStoreKey] ?: return emptyList()

        return try {
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Add an item to the existing list in DataStore
    suspend inline fun <reified T> addToList(context: Context, key: String, newItem: T) {
        val existingList = getList<T>(context, key).toMutableList()
        existingList.add(newItem)

        putList(context, key, existingList)
    }

    // Remove an item from the list in DataStore
    suspend inline fun <reified T> removeFromList(context: Context, key: String, itemToRemove: T) {
        val dataStoreKey = stringPreferencesKey(key)
        val existingList = getList<T>(context, key).toMutableList()
        existingList.remove(itemToRemove)

        context.dataStore.edit { preferences ->
            if (existingList.isEmpty()) {
                // Remove the key if the list is empty
                preferences.remove(dataStoreKey)
            } else {
                // Update the list in DataStore
                val jsonString = gson.toJson(existingList)
                preferences[dataStoreKey] = jsonString
            }
        }
    }
}
