package com.montse.apptransaccional.features.dashboard.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishForm(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    viewModel: DashboardViewModel,
    onLoad: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val foodlyPink = Color(0xFFE91E63)
    val inputShape = RoundedCornerShape(12.dp)
    
    var expandedCategory by remember { mutableStateOf(false) }
    var expandedArea by remember { mutableStateOf(false) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    
    // URI temporal persistente
    var tempCameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // Launcher para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) viewModel.onImageSelected(uri)
    }

    // Launcher para la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            viewModel.onImageSelected(tempCameraUri)
        }
    }

    // Launcher para permisos de Galería
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) galleryLauncher.launch("image/*")
        else Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
    }

    // Launcher para permisos de Cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = viewModel.getTempImageUri()
            tempCameraUri = uri
            if (uri != null) cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        onLoad()
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color.White,
            title = {
                Text(text = "Select Image Source", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageSourceDialog = false
                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                    val uri = viewModel.getTempImageUri()
                                    tempCameraUri = uri
                                    if (uri != null) cameraLauncher.launch(uri)
                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = CircleShape, color = foodlyPink.copy(alpha = 0.1f), modifier = Modifier.size(40.dp)) {
                            Icon(Icons.Outlined.CameraAlt, null, tint = foodlyPink, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Take Photo", fontSize = 16.sp)
                    }
                    Divider(color = Color.LightGray.copy(alpha = 0.5f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageSourceDialog = false
                                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    Manifest.permission.READ_MEDIA_IMAGES
                                } else {
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                }
                                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                                    galleryLauncher.launch("image/*")
                                } else {
                                    galleryPermissionLauncher.launch(permission)
                                }
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = CircleShape, color = foodlyPink.copy(alpha = 0.1f), modifier = Modifier.size(40.dp)) {
                            Icon(Icons.Outlined.PhotoLibrary, null, tint = foodlyPink, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Choose from Gallery", fontSize = 16.sp)
                    }
                }
            },
            confirmButton = {}
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F8F8))) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = foodlyPink,
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        ) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onCancel) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.SoupKitchen, null, tint = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Foodly", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Card(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, null, tint = foodlyPink, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF2F2F2)).clickable { showImageSourceDialog = true }.padding(2.dp)
                ) {
                    if (state.imageUri != null) {
                        AsyncImage(model = state.imageUri, contentDescription = null, modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
                    } else {
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Image, null, tint = Color.Gray, modifier = Modifier.size(32.dp))
                            Text("Attach image", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AppTextField(value = state.nombre, onValueChange = viewModel::onNombreChange, label = "Name", error = if (state.shouldShowNombreError) state.nombreError else null)
                AppTextField(value = state.descripcion, onValueChange = viewModel::onDescripcionChange, label = "Description")
                AppTextField(value = state.precio, onValueChange = viewModel::onPrecioChange, label = "Price", keyboardType = KeyboardType.Number, error = if (state.shouldShowPrecioError) state.precioError else null)

                ExposedDropdownMenuBox(expanded = expandedCategory, onExpandedChange = { expandedCategory = !expandedCategory }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    OutlinedTextField(value = state.selectedCategoryName, onValueChange = { }, readOnly = true, label = { Text("Category") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.LightGray, unfocusedBorderColor = Color.LightGray), shape = inputShape, modifier = Modifier.menuAnchor().fillMaxWidth() )
                    ExposedDropdownMenu(expanded = expandedCategory, onDismissRequest = { expandedCategory = false }) {
                        state.categories.forEach { category -> DropdownMenuItem(text = { Text(category.name) }, onClick = { viewModel.onCategorySelected(category.id, category.name); expandedCategory = false }) }
                    }
                }

                ExposedDropdownMenuBox(expanded = expandedArea, onExpandedChange = { expandedArea = !expandedArea }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    OutlinedTextField(value = state.selectedAreaName, onValueChange = { }, readOnly = true, label = { Text("Area") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedArea) }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.LightGray, unfocusedBorderColor = Color.LightGray), shape = inputShape, modifier = Modifier.menuAnchor().fillMaxWidth() )
                    ExposedDropdownMenu(expanded = expandedArea, onDismissRequest = { expandedArea = false }) {
                        state.areas.forEach { area -> DropdownMenuItem(text = { Row { Text(area.icon ?: ""); Spacer(Modifier.width(8.dp)); Text(area.name) } }, onClick = { viewModel.onAreaSelected(area.id, area.name); expandedArea = false }) }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Available", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(checked = state.disponible, onCheckedChange = viewModel::onDisponibleChange, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF2ECC71)))
                }

                if (state.error != null) { Text(text = state.error!!, color = Color.Red, fontSize = 12.sp) }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = onSave, enabled = !state.isLoading, modifier = Modifier.weight(1f).height(45.dp), colors = ButtonDefaults.buttonColors(containerColor = foodlyPink), shape = RoundedCornerShape(8.dp)) {
                        if (state.isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp)) else Text("Save", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f).height(45.dp), shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, foodlyPink)) { Text("Cancel", color = foodlyPink, fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}

@Composable
fun AppTextField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text, error: String? = null) {
    OutlinedTextField(value = value, onValueChange = onValueChange, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), label = { Text(label) }, shape = RoundedCornerShape(12.dp), keyboardOptions = KeyboardOptions(keyboardType = keyboardType), isError = error != null, supportingText = { if (error != null) Text(error, color = Color.Red) }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.LightGray, unfocusedBorderColor = Color.LightGray, focusedLabelColor = Color.Gray))
}
