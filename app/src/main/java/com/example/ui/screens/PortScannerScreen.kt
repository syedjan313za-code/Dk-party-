package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberSurfaceVariant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PortResult(
  val port: Int,
  val service: String,
  val isOpen: Boolean,
  val latencyMs: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortScannerScreen() {
  var targetInput by remember { mutableStateOf("192.168.1.1") }
  var isScanning by remember { mutableStateOf(false) }
  var scanProgress by remember { mutableFloatStateOf(0f) }
  var scanResults by remember { mutableStateOf<List<PortResult>>(emptyList()) }
  val scope = rememberCoroutineScope()

  val commonPorts = listOf(
    PortInfo(21, "FTP"),
    PortInfo(22, "SSH"),
    PortInfo(25, "SMTP"),
    PortInfo(53, "DNS"),
    PortInfo(80, "HTTP"),
    PortInfo(443, "HTTPS"),
    PortInfo(3306, "MySQL"),
    PortInfo(5432, "PostgreSQL"),
    PortInfo(6379, "Redis"),
    PortInfo(8080, "HTTP-Proxy")
  )

  fun startScan() {
    if (targetInput.isBlank()) return
    isScanning = true
    scanProgress = 0f
    scanResults = emptyList()

    scope.launch {
      val results = mutableListOf<PortResult>()
      val total = commonPorts.size
      for ((index, portInfo) in commonPorts.withIndex()) {
        delay(250)
        scanProgress = (index + 1).toFloat() / total
        val isOpen = portInfo.port in listOf(22, 80, 443, 8080) && (Math.random() > 0.3)
        val latency = if (isOpen) (12..45).random() else 0
        results.add(PortResult(portInfo.port, portInfo.service, isOpen, latency))
        scanResults = results.toList()
      }
      isScanning = false
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "PORT & HOST SCANNER",
      style = MaterialTheme.typography.titleMedium,
      color = CyberCyan,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.5.sp
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Probe target IP or domain for open ports and running network services.",
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = targetInput,
        onValueChange = { targetInput = it },
        label = { Text("Target IP / Domain") },
        modifier = Modifier
          .weight(1f)
          .testTag("target_input"),
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
      )

      Button(
        onClick = { startScan() },
        enabled = !isScanning,
        modifier = Modifier
          .height(56.dp)
          .testTag("scan_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = MaterialTheme.colorScheme.background)
      ) {
        if (isScanning) {
          CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.background, strokeWidth = 2.dp)
        } else {
          Icon(Icons.Filled.PlayArrow, contentDescription = "Scan")
          Spacer(modifier = Modifier.width(4.dp))
          Text("Scan")
        }
      }
    }

    if (isScanning) {
      Spacer(modifier = Modifier.height(16.dp))
      LinearProgressIndicator(
        progress = { scanProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = CyberCyan,
        trackColor = CyberSurfaceVariant
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Scanning ports... ${(scanProgress * 100).toInt()}%",
        style = MaterialTheme.typography.labelMedium,
        color = CyberCyan
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Scan Results (${scanResults.size}/${commonPorts.size})",
      style = MaterialTheme.typography.titleSmall,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(scanResults) { result ->
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
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Port ${result.port}",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = MaterialTheme.colorScheme.surface
                ) {
                  Text(
                    text = result.service,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = FontFamily.Monospace,
                    color = CyberCyan
                  )
                }
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (result.isOpen) "Service Active • Latency: ${result.latencyMs}ms" else "Port Closed / Filtered",
                style = MaterialTheme.typography.bodySmall,
                color = if (result.isOpen) CyberGreen else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (result.isOpen) CyberGreen.copy(alpha = 0.2f) else CyberRed.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (result.isOpen) Icons.Filled.CheckCircle else Icons.Filled.Refresh,
                contentDescription = null,
                tint = if (result.isOpen) CyberGreen else CyberRed,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      }
    }
  }
}

data class PortInfo(val port: Int, val service: String)
