package com.ozan.repoapplication.classes

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class RepoResponse @Inject constructor(){

    @SerializedName("name")
    var name : String = ""

    @SerializedName("description")
    var description : String = ""

    @SerializedName("full_name")
    var full_name : String = ""

    @SerializedName("open_issues_count")
    var open_issues_count : Int = 0

    @SerializedName("stargazers_count")
    var stargazers_count : Int = 0

    @SerializedName("owner")
    var owner : Owner? = null

    var isStarred : Boolean = false


}