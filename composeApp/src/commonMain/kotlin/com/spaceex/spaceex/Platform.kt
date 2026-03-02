package com.spaceex.spaceex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform