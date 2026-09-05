package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberSurfaceVariant

data class GlossaryTerm(val term: String, val category: String, val definition: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlossaryScreen() {
  val terms = listOf(
    GlossaryTerm("SQL Injection (SQLi)", "OWASP Top 10", "An injection attack where malicious SQL statements are inserted into entry fields for execution."),
    GlossaryTerm("Cross-Site Scripting (XSS)", "OWASP Top 10", "Vulnerability enabling attackers to inject client-side scripts into web pages viewed by other users."),
    GlossaryTerm("Zero-Day Exploit", "Threats", "A cyber attack targeting a software vulnerability that is unknown to the vendor or patch creators."),
    GlossaryTerm("PBKDF2 / Bcrypt", "Cryptography", "Key derivation functions used for securely hashing passwords with added cryptographic salt."),
    GlossaryTerm("Man-in-the-Middle (MitM)", "Network Security", "An attack where the attacker secretly relays and possibly alters communications between two parties who believe they are directly communicating."),
    GlossaryTerm("Penetration Testing", "Auditing", "Authorized simulated cyberattack on a computer system to evaluate its security posture."),
    GlossaryTerm("TLS / SSL", "Protocols", "Cryptographic protocols designed to provide communications security over a computer network.")
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      text = "CYBERSECURITY GLOSSARY & GUIDE",
      style = MaterialTheme.typography.titleMedium,
      color = CyberCyan,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.5.sp
    )

    Text(
      text = "Essential security terminology, vulnerability classes, and networking protocols.",
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(terms) { item ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = item.term,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = CyberCyan
              )
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surface
              ) {
                Text(
                  text = item.category,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
            Text(
              text = item.definition,
              style = MaterialTheme.typography.bodyMedium
            )
          }
        }
      }
    }
  }
}
