package com.montse.apptransaccional.features.users.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PersonAddAlt1
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.users.presentation.viewmodels.UsersManagementViewModel

@Composable
fun CreateUserScreen(
    onBack: () -> Unit,
    viewModel: UsersManagementViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)

    LaunchedEffect(Unit) {
        viewModel.loadRoles()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = foodlyPink,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        viewModel.resetForm()
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.PersonAddAlt1,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Foodly",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = foodlyPink
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Create Account",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedTextField(
                    value = state.name,
                    onValueChange = viewModel::onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Full name") },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = foodlyPink,
                        focusedLabelColor = foodlyPink,
                        cursorColor = foodlyPink,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    isError = state.shouldShowNameError,
                    supportingText = {
                        if (state.shouldShowNameError) {
                            Text(state.nameError ?: "", color = Color.Red)
                        }
                    }
                )

                OutlinedTextField(
                    value = state.username,
                    onValueChange = viewModel::onUsernameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = foodlyPink,
                        focusedLabelColor = foodlyPink,
                        cursorColor = foodlyPink,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    isError = state.shouldShowUsernameError,
                    supportingText = {
                        if (state.shouldShowUsernameError) {
                            Text(state.usernameError ?: "", color = Color.Red)
                        }
                    }
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = foodlyPink,
                        focusedLabelColor = foodlyPink,
                        cursorColor = foodlyPink,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    isError = state.shouldShowPasswordError,
                    supportingText = {
                        if (state.shouldShowPasswordError) {
                            Text(state.passwordError ?: "", color = Color.Red)
                        }
                    }
                )

                Text(
                    text = "Employee Role",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

                if (state.isLoadingRoles) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp), color = foodlyPink)
                    }
                } else if (state.roles.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        state.roles.chunked(2).forEach { rowRoles ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                rowRoles.forEach { role ->
                                    RoleOption(
                                        label = role.name.replaceFirstChar { it.uppercase() },
                                        selected = state.roleId == role.id,
                                        modifier = Modifier.weight(1f),
                                        onClick = { viewModel.onRoleSelected(role.id) }
                                    )
                                }
                            }
                        }
                    }
                }

                if (state.error != null) {
                    Text(state.error ?: "", color = Color.Red, fontSize = 13.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { viewModel.createUser { onBack() } },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = foodlyPink,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !state.isSubmitting
                    ) {
                        if (state.isSubmitting) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                        } else {
                            Text("Save", fontWeight = FontWeight.Bold)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            viewModel.resetForm()
                            onBack()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = foodlyPink)
                    ) {
                        Text("Cancel", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun RoleOption(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val foodlyPink = Color(0xFFE91E63)
    Row(
        modifier = modifier
            .border(
                BorderStroke(1.dp, if (selected) foodlyPink else Color.LightGray),
                RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .border(BorderStroke(1.dp, if (selected) foodlyPink else Color.Gray), RoundedCornerShape(3.dp))
                .padding(2.dp)
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(label, fontSize = 13.sp, color = Color.DarkGray)
    }
}
