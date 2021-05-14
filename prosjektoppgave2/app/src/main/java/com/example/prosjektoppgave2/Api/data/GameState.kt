package com.example.prosjektoppgave2.Api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias GameState = List<MutableList<Char>>

@Parcelize
data class Game(var players:MutableList<String>, var gameId:String, var state:GameState ): Parcelable