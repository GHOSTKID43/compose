package com.kdt.compose

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}