package com.example.tareafragmentosalejandrofernandez.controller

import com.example.tareafragmentosalejandrofernandez.models.JsonData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import kotlin.reflect.typeOf

class JsonLoader {

    var gson = Gson()
    lateinit var usersFile : File
    lateinit var data: JsonData

    fun load(json: String) {
        val type = object : TypeToken<JsonData?>() {}.type
        data = gson.fromJson(json, type)
    }

}