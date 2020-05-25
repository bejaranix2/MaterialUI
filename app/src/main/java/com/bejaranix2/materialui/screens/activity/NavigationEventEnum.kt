package com.bejaranix2.materialui.screens.activity

import androidx.annotation.IdRes
import com.bejaranix2.materialui.R
import java.lang.IllegalArgumentException

enum class NavigationEventEnum(@IdRes val item:Int) {

    CONFIG_ITEM(R.id.config_item),
    SEND_ITEM(R.id.send_item),
    NO_EVENT(0)
    ;

    companion object {
        fun valueOf(item:Int):NavigationEventEnum{
            for(enum in NavigationEventEnum.values()){
                if(enum.item == item){
                    return enum
                }
            }
            throw IllegalArgumentException("Argument not valid")
        }

    }

}