package com.ozan.repoapplication.classes

import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("login")
    var login : String = ""

    @SerializedName("avatar_url")
    var avatar_url : String = ""

    @SerializedName("html_url")
    var html_url : String = ""

}