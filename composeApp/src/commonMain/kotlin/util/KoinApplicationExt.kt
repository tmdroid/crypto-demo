package util

import org.koin.core.KoinApplication

inline fun <reified T> KoinApplication.getViewModel(): T = koin.get()