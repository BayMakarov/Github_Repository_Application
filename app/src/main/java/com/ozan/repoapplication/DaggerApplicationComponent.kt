package com.ozan.repoapplication

import com.ozan.repoapplication.activity.MainActivity
import dagger.Component

@Component
interface DaggerApplicationComponent {
    fun bind(app: MainActivity)
}