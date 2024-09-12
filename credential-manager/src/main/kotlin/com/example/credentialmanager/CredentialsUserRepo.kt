package com.example.credentialmanager

import android.content.Context
import androidx.credentials.CredentialManager
import common.domain.User
import common.domain.DataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CredentialsUserRepo(
    private val context: Context
) : DataRepo {

    private val credentialManager = CredentialManager.create(context)

    override fun getUser(): Flow<User> = flow {
//        val request = GetCredentialRequest.Builder()
//            .addCredentialOption()
//            .build()
//        credentialManager.getCredentialAsync(context, request, null, )
//        val response: GetCredentialResponse = credentialManager.getCredential(request)
//        val credential = response.credential as PasswordCredential
//        emit(User(credential.id, credential.password))
    }

    override suspend fun saveUser(user: User) {
        TODO()
//        val request = GetCredentialRequest.Builder().
//        credentialManager.createCredentialAsync(context, )
//        val credential = PasswordCredential(user.id, user.password)
//        val request = SaveCredentialRequest.Builder(credential).build()
//        credentialManager.saveCredential(request)
    }
}
