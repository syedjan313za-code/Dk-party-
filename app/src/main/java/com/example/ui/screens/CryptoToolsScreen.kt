package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberSurfaceVariant
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Base64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoToolsScreen() {
  var inputPlainText by remember { mutableStateOf("Hello CyberToolkit") }
  var selectedTab by remember { mutableIntStateOf(0) }
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  fun hashString(input: String, algorithm: String): String {
    try {
      val bytes = MessageDigest.getInstance(algorithm).digest(input.toByteArray(StandardCharsets.UTF_8))
      return bytes.joinToString("") { "%02x".format(it) }
    } catch (e: Exception) {
      return "Error: ${e.message}"
    }
  }

  val md5 = hashString(inputPlainText, "MD5")
  val sha256 = hashString(inputPlainText, "SHA-256")
  val sha512 = hashString(inputPlainText, "SHA-512")

  val base64Encoded = try {
    Base64.getEncoder().encodeToString(inputPlainText.toByteArray(StandardCharsets.UTF_8))
  } catch (e: Exception) { "Error" }

  val base64Decoded = try {
    String(Base64.getDecoder().decode(inputPlainText), StandardCharsets.UTF_8)
  } catch (e: Exception) { "Invalid Base64" }

  val urlEncoded = try {
    URLEncoder.encode(inputPlainText, "UTF-8")
  } catch (e: Exception) { "Error" }

  val urlDecoded = try {
    URLDecoder.decode(inputPlainText, "UTF-8")
  } catch (e: Exception) { "Invalid URL encoding" }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "HASH & CRYPTO UTILITIES",
      style = MaterialTheme.typography.titleMedium,
      color = CyberCyan,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.5.sp
    )

    OutlinedTextField(
      value = inputPlainText,
      onValueChange = { inputPlainText = it },
      label = { Text("Input Text / Payload") },
      modifier = Modifier
        .fillMaxWidth()
        .testTag("crypto_input"),
      shape = RoundedCornerShape(12.dp)
    )

    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = CyberSurfaceVariant,
      contentColor = CyberCyan
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("Cryptographic Hashes", fontWeight = FontWeight.Bold) }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("Encoders / Decoders", fontWeight = FontWeight.Bold) }
      )
    }

    if (selectedTab == 0) {
      HashResultCard("MD5 Hash", md5, context)
      HashResultCard("SHA-256 Hash", sha256, context)
      HashResultCard("SHA-512 Hash", sha512, context)
    } else {
      HashResultCard("Base64 Encode", base64Encoded, context)
      HashResultCard("Base64 Decode", base64Decoded, context)
      HashResultCard("URL Encode", urlEncoded, context)
      HashResultCard("URL Decode", urlDecoded, context)
    }
  }
}

@Composable
fun HashResultCard(title: String, value: String, context: Context) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant)
  ) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = CyberCyan)
        TextButton(
          onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(title, value)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "$title copied", Toast.LENGTH_SHORT).show()
          }
        ) {
          Text("Copy", fontWeight = FontWeight.Bold)
        }
      }
      Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface
      ) {
        Text(
          text = value,
          modifier = Modifier.padding(12.dp),
          style = MaterialTheme.typography.bodyMedium,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}
