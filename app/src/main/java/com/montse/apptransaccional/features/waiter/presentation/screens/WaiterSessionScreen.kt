package com.montse.apptransaccional.features.waiter.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.waiter.presentation.components.ProductCard
import com.montse.apptransaccional.features.waiter.presentation.components.atoms.LoadingOverlay
import com.montse.apptransaccional.features.waiter.presentation.components.organisms.CartPanel
import com.montse.apptransaccional.features.waiter.presentation.components.organisms.CloseSessionDialog
import com.montse.apptransaccional.features.waiter.presentation.components.organisms.FloatingCartBar
import com.montse.apptransaccional.features.waiter.presentation.components.organisms.OrdersPanel
import com.montse.apptransaccional.features.waiter.presentation.components.organisms.TicketDialog
import com.montse.apptransaccional.features.waiter.presentation.viewmodels.WaiterSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaiterSessionScreen(
    onBack: () -> Unit,
    viewModel: WaiterSessionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)
    var showCart by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.orderSentSuccess) {
        if (state.orderSentSuccess) {
            snackbarHostState.showSnackbar("Pedido enviado a cocina/barra")
            viewModel.dismissSuccess()
        }
    }

    // ── Dialogs ─────────────────────────────────────────────────
    if (state.showCloseDialog) {
        CloseSessionDialog(
            paymentMethod = state.paymentMethod,
            tip = state.tip,
            discount = state.discount,
            onPaymentMethodChange = { viewModel.onPaymentMethodChange(it) },
            onTipChange = { viewModel.onTipChange(it) },
            onDiscountChange = { viewModel.onDiscountChange(it) },
            onConfirm = { viewModel.closeSession() },
            onDismiss = { viewModel.dismissCloseDialog() },
            accentColor = foodlyPink
        )
    }

    if (state.showTicket && state.ticket != null) {
        TicketDialog(
            ticket = state.ticket!!,
            tableNumber = state.tableNumber,
            onDismiss = { viewModel.dismissTicket(); onBack() },
            accentColor = foodlyPink
        )
    }

    // ── Scaffold ───────────────────────────────────────────────
    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mesa ${state.tableNumber}", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = foodlyPink,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    if (state.hasOrders && state.allDelivered && !state.sessionClosed) {
                        IconButton(onClick = { viewModel.showCloseDialog() }) {
                            Icon(Icons.Default.Receipt, contentDescription = "Cerrar cuenta")
                        }
                    }
                    BadgedBox(
                        badge = {
                            if (state.cartItemCount > 0) {
                                Badge(containerColor = Color.White, contentColor = foodlyPink) {
                                    Text("${state.cartItemCount}")
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = { showCart = !showCart }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = foodlyPink)
                    }
                }
                state.sessionClosed -> {
                    SessionClosedContent(onBack = onBack, accentColor = foodlyPink)
                }
                else -> {
                    SessionActiveContent(
                        state = state,
                        viewModel = viewModel,
                        accentColor = foodlyPink
                    )
                }
            }

            // ── Floating cart bar ───────────────────────────────
            AnimatedVisibility(
                visible = state.cart.isNotEmpty() && !showCart && !state.sessionClosed,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                FloatingCartBar(
                    itemCount = state.cartItemCount,
                    total = state.cartTotal,
                    onShowCart = { showCart = true },
                    accentColor = foodlyPink
                )
            }

            // ── Cart panel ──────────────────────────────────────
            AnimatedVisibility(
                visible = showCart,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                CartPanel(
                    cart = state.cart,
                    products = state.products,
                    cartTotal = state.cartTotal,
                    onAdd = { viewModel.addToCart(it) },
                    onRemove = { viewModel.removeFromCart(it) },
                    onClear = { viewModel.clearCart() },
                    onSendOrder = { viewModel.sendOrder() },
                    onClose = { showCart = false },
                    accentColor = foodlyPink
                )
            }

            // ── Loading overlays ────────────────────────────────
            if (state.isSendingOrder) {
                LoadingOverlay(message = "Enviando pedido...", accentColor = foodlyPink)
            }
            if (state.isClosingSession) {
                LoadingOverlay(message = "Cerrando cuenta...", accentColor = foodlyPink)
            }
        }
    }
}

// ── Sub-screens ─────────────────────────────────────────────────

@Composable
private fun SessionClosedContent(onBack: () -> Unit, accentColor: Color) {
    Box(Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Receipt, null, Modifier.size(64.dp), tint = Color(0xFF4CAF50))
            Spacer(Modifier.height(12.dp))
            Text("Cuenta cerrada", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text("Volver a mesas")
            }
        }
    }
}

@Composable
private fun SessionActiveContent(
    state: com.montse.apptransaccional.features.waiter.presentation.state.WaiterSessionState,
    viewModel: WaiterSessionViewModel,
    accentColor: Color
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Error
        if (state.error != null) {
            Text(
                state.error!!,
                color = Color.Red,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        // Orders panel
        if (state.hasOrders) {
            OrdersPanel(
                orders = state.orders,
                allItems = state.allItems,
                allDelivered = state.allDelivered,
                showExpanded = state.showOrdersPanel,
                onToggle = { viewModel.toggleOrdersPanel() },
                onMarkDelivered = { viewModel.markItemDelivered(it) },
                onCloseSession = { viewModel.showCloseDialog() },
                accentColor = accentColor
            )
        }

        // Category filter
        LazyRow(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = state.selectedCategory == "Todo",
                    onClick = { viewModel.selectCategory("Todo") },
                    label = { Text("Todo") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = accentColor,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF2F2F2),
                        labelColor = Color.Gray
                    )
                )
            }
            items(state.categories) { cat ->
                FilterChip(
                    selected = state.selectedCategory == cat.name,
                    onClick = { viewModel.selectCategory(cat.name) },
                    label = { Text(cat.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = accentColor,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF2F2F2),
                        labelColor = Color.Gray
                    )
                )
            }
        }

        // Products
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.filteredProducts) { dish ->
                ProductCard(dish = dish, onAdd = { viewModel.addToCart(dish) })
            }
            if (state.cart.isNotEmpty()) {
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
