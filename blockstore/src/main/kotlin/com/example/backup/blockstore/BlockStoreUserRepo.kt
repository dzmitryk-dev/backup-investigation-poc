package com.example.backup.blockstore

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.RetrieveBytesRequest
import com.google.android.gms.auth.blockstore.StoreBytesData
import common.domain.DataRepo
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

private const val KEY = "com.example.backup.data"
private const val TAG = "BlockStoreUserRepo"

class BlockStoreUserRepo(
    private val context: Context
) : DataRepo {

    override suspend fun getData(): String? {
        Log.d(TAG, "Fetching data for key $KEY")
        val client = Blockstore.getClient(context)
        val retrieveRequest = RetrieveBytesRequest.Builder().setKeys(listOf(KEY)).build()
        return suspendCancellableCoroutine { continuation ->
            client.retrieveBytes(retrieveRequest)
                .addOnSuccessListener { response ->
                    Log.d(
                        TAG,
                        "Retrieved ${response.blockstoreDataMap} ${response.blockstoreDataMap.size}"
                    )
                    val result = response.blockstoreDataMap[KEY]?.let { value ->
                        val bytes = value.bytes
                        Log.d(
                            TAG,
                            "Retrieved ${bytes.size} bytes ${bytes.joinToString { it.toString() }} ${
                                String(bytes)
                            }"
                        )
                        Result.success(String(bytes))
                    } ?: run {
                        Log.d(TAG, "No data Retrieved.")
                        Result.success(null)
                    }
                    continuation.resumeWith(result)
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Failed to retrieve user", exception)
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun saveData(message: String) {
        val bytes = message.toByteArray()
        Log.d(TAG, "Save Data: $message, ${bytes.size}")
        val client = Blockstore.getClient(context)
        val storeRequest1 = StoreBytesData.Builder()
            // Call this method to set the key value with which the data should be associated with.
            .setBytes(bytes)
            .setKey(KEY)
            .setShouldBackupToCloud(true)
            .build()
        client.storeBytes(storeRequest1)
            .addOnSuccessListener { result: Int ->
                Log.d(TAG, "Stored $result bytes with key $KEY")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to store bytes", e)
            }
    }
}
