package com.rafael.core.common

abstract class Routes(val route: String)

fun Routes.withArgs(vararg args: Pair<String, Any>) : String {
    return buildString {
        append(route)
        args.forEachIndexed { index, pair ->
            append(if (index == 0) "?" else "&")
            append("${pair.first}=${pair.second}")
        }
    }
}