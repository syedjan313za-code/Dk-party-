package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.screens.*
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        CyberToolkitApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CyberToolkitApp() {
  var selectedTab by remember { mutableIntStateOf(0) }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "⚡ CyberToolkit",
            style = MaterialTheme.typography.titleMedium,
            color = CyberCyan
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
      ) {
        NavigationBarItem(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
          label = { Text("Home") }
        )
        NavigationBarItem(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          icon = { Icon(Icons.Filled.Search, contentDescription = "Scanner") },
          label = { Text("Scanner") }
        )
        NavigationBarItem(
          selected = selectedTab == 2,
          onClick = { selectedTab == 2 },
          icon = { Icon(Icons.Filled.Lock, contentDescription = "Passwords") },
          label = { Text("Passwords") }
        )
        NavigationBarItem(
          selected = selectedTab == 3,
          onClick = { selectedTab = 3 },
          icon = { Icon(Icons.Filled.Refresh, contentDescription = "Crypto") },
          label = { Text("Crypto") }
        )
        NavigationBarItem(
          selected = selectedTab == 4,
          onClick = { selectedTab = 4 },
          icon = { Icon(Icons.Filled.Security, contentDescription = "Audit") },
          label = { Text("Audit") }
        )
        NavigationBarItem(
          selected = selectedTab == 5,
          onClick = { selectedTab = 5 },
          icon = { Icon(Icons.Filled.Info, contentDescription = "Glossary") },
          label = { Text("Glossary") }
        )
        NavigationBarItem(
          selected = selectedTab == 6,
          onClick = { selectedTab = 6 },
          icon = { Icon(Icons.Filled.Person, contentDescription = "Human AI") },
          label = { Text("Human AI") }
        )
      }
    }
  ) { innerPadding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      color = MaterialTheme.colorScheme.background
    ) {
      when (selectedTab) {
        0 -> DashboardScreen(onNavigate = { tab -> selectedTab = tab })
        1 -> PortScannerScreen()
        2 -> PasswordToolScreen()
        3 -> CryptoToolsScreen()
        4 -> SecurityAuditScreen()
        5 -> GlossaryScreen()
        6 -> HumanAiAssistantScreen()
      }
    }
  }
}
