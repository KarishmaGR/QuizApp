package com.example.firebasecrudapplication.utils

import com.example.firebasecrudapplication.R



    object IconPicker {
        val icons = arrayOf(
            R.drawable.ic,
            R.drawable.ci_4,
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_8,
            R.drawable.ci_5,
            R.drawable.ci_4,
            R.drawable.ci_7,
            R.drawable.ci_6
        )
        var currentIcon = 0

        fun getIcon(): Int {
            currentIcon = (currentIcon + 1) % icons.size
            return icons[currentIcon]
        }

}