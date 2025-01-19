package org.qreative.weatherapp

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController { App(UrlProvider()) }