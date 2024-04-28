package util

import org.koin.core.KoinApplication
import presentation.ScreenView

inline fun <reified T : ScreenView> KoinApplication.getScreenView(): T = koin.get()