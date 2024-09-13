package com.example.backup.blockstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.backup.blockstore.ui.theme.BackupInvestigationPOCTheme
import common.di.provideServiceLocator
import common.presentation.HelloScreenViewModel
import common.ui.DataScreen

class MainActivity : ComponentActivity() {

    private val serviceLocator by lazy {
        provideServiceLocator(
            userRepoFactory = { BlockStoreUserRepo(this) }
        )
    }

    private val viewModel by viewModels<HelloScreenViewModel>(
        factoryProducer = HelloScreenViewModel.provideFactoryProvide(serviceLocator)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackupInvestigationPOCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        DataScreen(
                            readDataState = viewModel.readDataState.collectAsState(),
                            generatedData = viewModel.generatedData.collectAsState(),
                            reReadData = viewModel::reReadData,
                            generateAndSaveFunction = viewModel::generateAndSave,
                            removeData = viewModel::removeData
                        )
                    }
                }
            }
        }
    }
}
