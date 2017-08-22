package com.example.rendy.todolist

import android.app.Application
import io.realm.Realm
import io.realm.Realm.setDefaultConfiguration
import io.realm.RealmConfiguration

/**
 * Created by rendy on 20/08/17.
 */

class TodoListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("todos.db")
                .build()

        Realm.setDefaultConfiguration(config)
    }
}