package com.example.tcc_raify

import android.app.Activity
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tcc_raify.R

class Notificacaopopups {
}

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val timeLabel: String,        // ex.: "há 2 h"
    val type: Type = Type.INFO,
    val read: Boolean = false
) {
    enum class Type { WARNING, SUCCESS, INFO }
}

