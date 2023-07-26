package com.xxx.ware_house.dialog

import android.app.Dialog
import android.content.Context
import android.view.ContextMenu
import android.view.View

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/27
 * DES:
 */
class InputDialog(context: Context, res: Int) : Dialog(context,res) {

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

}