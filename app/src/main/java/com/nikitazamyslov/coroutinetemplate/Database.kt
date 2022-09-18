package com.nikitazamyslov.coroutinetemplate

import kotlinx.coroutines.delay

class Database {

    private val profileList = mutableListOf<Profile>(
        Profile("Profile1", "1", 23), Profile("Profile2", "2", 19)
    )

    suspend fun getProfiles(): List<Profile> {
        delay((500..1000).random().toLong())
        return profileList
    }
}