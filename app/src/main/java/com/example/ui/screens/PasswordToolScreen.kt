package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberSurfaceVariant
import kotlin.math.log2
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordToolScreen() {
  var passwordInput by remember { mutableStateOf("Tr0ub4dour&9!") }
  var generatedPassword by remember { mutableStateOf("") }
  var genLength by remember { mutableFloatStateOf(16f) }
  var useUpper by remember { mutableStateOf(true) }
  var useLower by remember { mutableStateOf(true) }
  var useNumbers by remember { mutableStateOf(true) }
  var useSymbols by remember { mutableStateOf(true) }
  val context = LocalContext.current

  val length = passwordInput.length
  val hasLower = passwordInput.any { it.isLowerCase() }
  val hasUpper = passwordInput.any { it.isUpperCase() }
  val hasDigit = passwordInput.any { it.isDigit() }
  val hasSymbol = passwordInput.any { !it.isLetterOrDigit() }

  var charsetSize = 0
  if (hasLower) charsetSize += 26
  if (hasUpper) charsetSize += 26
  if (hasDigit) charsetSize += 10
  if (hasSymbol) charsetSize += 32

  val entropy = if (length > 0 && charsetSize > 0) length * log2(charsetSize.toDouble()) else 0.0

  val strengthLabel = when {
    entropy > 80 -> "EXCELLENT (Military Grade)"
    entropy > 60 -> "STRONG"
    entropy > 40 -> "MODERATE"
    entropy > 20 -> "WEAK"
    else -> "VERY WEAK"
  }

  val strengthColor = when {
    entropy > 80 -> CyberGreen
    entropy > 60 -> CyberCyan
    entropy > 40 -> MaterialTheme.colorScheme.tertiary
    else -> CyberRed
  }

  fun generateSecurePassword() {
    val upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lowerChars = "abcdefghijklmnopqrstuvwxyz"
    val digitChars = "0123456789"
    val symbolChars = "!@#$%^&*()_+-=[]{}|;:,.<>?"

    var pool = ""
    if (useUpper) pool += upperChars
    if (useLower) pool += lowerChars
    if (useNumbers) pool += digitChars
    if (useSymbols) pool += symbolChars

    if (pool.isEmpty()) {
      generatedPassword = "Select at least one character set!"
      return
    }

    val len = genLength.toInt()
    val sb = StringBuilder()
    for (i in 0 until len) {
      val idx = Random.nextInt(pool.length)
      sb.append(pool[idx])
    }
    generatedPassword = sb.toString()
  }

  LaunchedEffect(Unit) {
    generateSecurePassword()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "PASSWORD & ENTROPY AUDITOR",
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
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Analyze Password Strength", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

        OutlinedTextField(
          value = passwordInput,
          onValueChange = { passwordInput = it },
          label = { Text("Password to test") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("password_input"),
          singleLine = true,
          shape = RoundedCornerShape(12.dp)
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(text = "Rating: $strengthLabel", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = strengthColor)
            Text(text = "Entropy: ${String.format("%.1f", entropy)} bits", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = strengthColor.copy(alpha = 0.2f)
          ) {
            Text(
              text = "${length} chars",
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              color = strengthColor,
              fontWeight = FontWeight.Bold,
              style = MaterialTheme.typography.labelMedium
            )
          }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          RequirementChip("A-Z", hasUpper)
          RequirementChip("a-z", hasLower)
          RequirementChip("0-9", hasDigit)
          RequirementChip("#$!", hasSymbol)
        }
      }
    }

    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
          Text("Secure Password Generator", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
          IconButton(onClick = { generateSecurePassword() }) {
            Icon(Icons.Filled.Refresh, contentDescription = "Regenerate", tint = CyberCyan)
          }
        }

        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surface
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = generatedPassword,
              style = MaterialTheme.typography.bodyLarge,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold,
              color = CyberCyan,
              modifier = Modifier.weight(1f)
            )
            Button(
              onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Generated Password", generatedPassword)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
              },
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberSurfaceVariant)
            ) {
              Text("Copy", fontWeight = FontWeight.Bold)
            }
          }
        }

        Text(text = "Length: ${genLength.toInt()} characters", style = MaterialTheme.typography.bodyMedium)
        Slider(
          value = genLength,
          onValueChange = { genLength = it; generateSecurePassword() },
          valueRange = 8f..32f,
          steps = 24
        )
      }
    }
  }
}

@Composable
fun RequirementChip(label: String, met: Boolean) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = if (met) CyberGreen.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
    modifier = Modifier.height(32.dp)
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      if (met) {
        Icon(Icons.Filled.Check, contentDescription = null, tint = CyberGreen, modifier = Modifier.size(16.dp))
      }
      Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = if (met) CyberGreen else MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Bold
      )
    }
  }
}
