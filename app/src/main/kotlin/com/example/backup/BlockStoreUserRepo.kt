package com.example.backup

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.RetrieveBytesRequest
import com.google.android.gms.auth.blockstore.StoreBytesData
import common.domain.User
import common.domain.UserRepo
import common.utils.toByteArray
import common.utils.toSerializable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val KEY = "com.example.backup.user"
private const val TAG = "BlockStoreUserRepo"

class BlockStoreUserRepo(
    context: Context
) : UserRepo {

    private val client = Blockstore.getClient(context)
    private val userFlow = MutableStateFlow<User>(User.EMPTY)

    override fun getUser(): Flow<User> {
        fetchUser()
        return userFlow
    }

    private fun fetchUser() {
        val retrieveRequest = RetrieveBytesRequest.Builder().setKeys(listOf(KEY)).build()
        client.retrieveBytes(retrieveRequest)
            .addOnSuccessListener { response ->
                response.blockstoreDataMap[KEY]?.let { value ->
                    userFlow.value = value.bytes.toSerializable()
                } ?: run {
                    userFlow.value = User.EMPTY
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to retrieve user", exception)
            }
    }

    override suspend fun saveUser(user: User) {
        val storeRequest1 = StoreBytesData.Builder()
            // Call this method to set the key value with which the data should be associated with.
            .setBytes(user.toByteArray())
            .setKey(KEY)
            .build()
        client.storeBytes(storeRequest1)
            .addOnSuccessListener { result: Int ->
                Log.d(TAG, "Stored $result bytes")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to store bytes", e)
            }
    }
}
