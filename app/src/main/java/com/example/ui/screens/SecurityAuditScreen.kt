package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberSurfaceVariant

data class AuditItem(
  val id: Int,
  val title: String,
  val description: String,
  val isSecure: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityAuditScreen() {
  var auditItems by remember {
    mutableStateOf(
      listOf(
        AuditItem(1, "Screen Lock Enabled", "Device requires PIN, pattern, or biometric authentication.", true),
        AuditItem(2, "Hardware Storage Encryption", "File-based or full-disk encryption is active on system storage.", true),
        AuditItem(3, "USB Debugging (ADB)", "Developer USB debugging mode is disabled for production safety.", false),
        AuditItem(4, "Unknown Apps Installation", "Sideloading from untrusted third-party sources is restricted.", true),
        AuditItem(5, "Root / Jailbreak Status", "Device binary integrity check passed (no SU binaries detected).", true),
        AuditItem(6, "Wi-Fi Security Protocol", "Connected network uses WPA3 / WPA2-AES encryption.", true),
        AuditItem(7, "App Permission Audit", "No background apps hold excessive location or camera permissions.", false)
      )
    )
  }

  val secureCount = auditItems.count { it.isSecure }
  val score = (secureCount * 100) / auditItems.size

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "SECURITY AUDIT & ASSESSMENT",
      style = MaterialTheme.typography.titleMedium,
      color = CyberCyan,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.5.sp
    )

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "Security Compliance", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "$score% SECURE", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = if (score > 75) CyberGreen else CyberRed)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "$secureCount of ${auditItems.size} checks passed", style = MaterialTheme.typography.bodySmall)
        }

        CircularProgressIndicator(
          progress = { score / 100f },
          modifier = Modifier.size(64.dp),
          color = if (score > 75) CyberGreen else CyberRed,
          trackColor = MaterialTheme.colorScheme.surface,
          strokeWidth = 6.dp
        )
      }
    }

    Text(text = "Device & Environment Audit Checklist", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(auditItems) { item ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(text = item.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
              Spacer(modifier = Modifier.height(2.dp))
              Text(text = item.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Switch(
              checked = item.isSecure,
              onCheckedChange = { checked ->
                auditItems = auditItems.map { if (it.id == item.id) it.copy(isSecure = checked) else it }
              },
              colors = SwitchDefaults.colors(checkedThumbColor = CyberGreen, checkedTrackColor = CyberGreen.copy(alpha = 0.3f))
            )
          }
        }
      }
    }
  }
}
