package org.qreative.weatherapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import weatherapp.composeapp.generated.resources.Res
import weatherapp.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(urlProvider: UrlProvider) {

    print("urlProvider: ${urlProvider.getUrl()}")
    MaterialTheme {

        Text(text = "Test Lakhdeep");
        val urlHandler = LocalUriHandler.current
        var showContent by remember { mutableStateOf(false) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Compose: $greeting", modifier = Modifier.clickable {
                        urlHandler.openUri(urlProvider.getUrl())
                    })
                }
            }
        }

    }
}