package com.bejaranix2.materialui.screens.activity

import androidx.annotation.IdRes
import com.bejaranix2.materialui.R
import java.lang.IllegalArgumentException

enum class ToolbarEventEnum(@IdRes val item:Int) {
    EXAMPLE_ITEM(R.id.example_item),
    ANOTHER_EXAMPLE_ITEM(R.id.another_example_item),
    SEARCH_ITEM(R.id.search_item),
    SHARE_ITEM(R.id.share_item);
;
    companion object {
        fun valueOf(item:Int):ToolbarEventEnum{
            for(enum in ToolbarEventEnum.values()){
                if(enum.item == item){
                    return enum
                }
            }
            throw IllegalArgumentException("Argument not valid")
        }

    }
}
