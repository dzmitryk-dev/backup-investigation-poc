package com.example.credentialmanager

import android.content.Context
import androidx.credentials.CredentialManager
import common.domain.DataRepo

class CredentialsUserRepo(
    private val context: Context
) : DataRepo {

    private val credentialManager = CredentialManager.create(context)

    override suspend fun getData(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun saveData(message: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeData() {
        TODO("Not yet implemented")
    }
}
