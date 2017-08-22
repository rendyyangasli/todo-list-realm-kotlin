package com.example.rendy.todolist.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by rendy on 22/06/17.
 */

open class Todo: RealmObject () {

    @PrimaryKey
    var id: String? = null
    var title: String? = null
    var description: String? = null
}
