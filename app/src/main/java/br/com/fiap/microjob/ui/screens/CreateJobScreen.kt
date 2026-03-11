package br.com.fiap.microjob.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.fiap.microjob.viewmodel.AuthViewModel
import br.com.fiap.microjob.viewmodel.JobsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobScreen(
    onJobCreated: () -> Unit,
    onBack: () -> Unit,
    authViewModel: AuthViewModel,
    jobsViewModel: JobsViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState(initial = null)
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var paymentValue by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var contactMethod by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Job") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = onBack) {
                        androidx.compose.material3.Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                    titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                focusedLabelColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título do job") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = paymentValue,
                onValueChange = { paymentValue = it.filter { c -> c.isDigit() || c == '.' || c == ',' } },
                label = { Text("Valor (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Localização") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = contactMethod,
                onValueChange = { contactMethod = it },
                label = { Text("Forma de contato (ex: WhatsApp, Telefone)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val user = currentUser
                    if (user != null) {
                        val value = paymentValue.replace(',', '.').toDoubleOrNull() ?: 0.0
                        jobsViewModel.addJob(
                            title = title.trim(),
                            description = description.trim(),
                            paymentValue = value,
                            location = location.trim(),
                            contactMethod = contactMethod.trim(),
                            poster = user
                        )
                        authViewModel.onJobPosted(user.id)
                        onJobCreated()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && description.isNotBlank() && location.isNotBlank() && currentUser != null,
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary)
            ) {
                Text("Publicar Job")
            }
        }
    }
}
