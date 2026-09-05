package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberSurfaceVariant

@Composable
fun DashboardScreen(onNavigate: (Int) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "CYBERSECURITY SUITE",
              style = MaterialTheme.typography.labelMedium,
              color = CyberCyan,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "System Operational",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(22.dp))
              .background(CyberGreen.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = CyberGreen, modifier = Modifier.size(24.dp))
          }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.surface)

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          StatusMetric("Local IP", "192.168.1.108")
          StatusMetric("Firewall", "Active")
          StatusMetric("Latency", "14 ms")
        }
      }
    }

    Text(
      text = "Security Tools & Utilities",
      style = MaterialTheme.typography.titleSmall,
      fontWeight = FontWeight.Bold
    )

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      ToolShortcutCard(
        title = "Port Scanner",
        subtitle = "Probe hosts & services",
        icon = Icons.Filled.Search,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(1) }
      )
      ToolShortcutCard(
        title = "Password Audit",
        subtitle = "Entropy & generator",
        icon = Icons.Filled.Lock,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(2) }
      )
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      ToolShortcutCard(
        title = "Crypto Hashes",
        subtitle = "MD5, SHA & Base64",
        icon = Icons.Filled.Refresh,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(3) }
      )
      ToolShortcutCard(
        title = "Security Audit",
        subtitle = "Compliance check",
        icon = Icons.Filled.Security,
        modifier = Modifier.weight(1f),
        onClick = { onNavigate(4) }
      )
    }
  }
}

@Composable
fun StatusMetric(label: String, value: String) {
  Column {
    Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = CyberCyan)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolShortcutCard(title: String, subtitle: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
    onClick = onClick
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(CyberCyan.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(22.dp))
      }

      Column {
        Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }
}
