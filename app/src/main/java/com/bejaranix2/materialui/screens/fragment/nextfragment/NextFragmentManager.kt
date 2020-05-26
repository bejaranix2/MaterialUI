package com.bejaranix2.materialui.screens.fragment.nextfragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentNextBinding
import com.bejaranix2.materialui.screens.activity.ToolbarGroup
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager
import com.google.android.material.textfield.TextInputEditText
import java.text.DateFormat
import java.util.*

class NextFragmentManager: BaseViewManager {


    private var mToolbarManager: ToolbarViewManager
    private var mFragmentNextBinding: FragmentNextBinding
    private var mFragment: NextFragment

    constructor(fragmentNextBinding: FragmentNextBinding, fragment: NextFragment, toolbarManager: ToolbarViewManager){
        mFragmentNextBinding = fragmentNextBinding
        mFragment = fragment
        mToolbarManager = toolbarManager
        mFragmentNextBinding.manager = this
        setUpView()
        setUpTextViews()
        setUpSlider()
    }


    private fun setUpView(){
        mToolbarManager.setToolbarGroup(ToolbarGroup.ANOTHER_EXAMPLE)
        mToolbarManager.getNavigationListener().observe(mFragment.viewLifecycleOwner, Observer {
            if(it) {
                NavHostFragment.findNavController(mFragment).navigateUp()
            }
        })
    }

    private fun setUpTextViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mFragmentNextBinding.txtUser.tooltipText = "Username"
        }
        mFragmentNextBinding.txtUser.setOnFocusChangeListener{ v, _ ->
            if((v as TextInputEditText).text.isNullOrEmpty()){
                mFragmentNextBinding.usernameText.isErrorEnabled = true
                mFragmentNextBinding.usernameText.error = "Please enter the username"
            }else{
                mFragmentNextBinding.usernameText.isErrorEnabled = false
            }
        }
        mFragmentNextBinding.passUser.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(mFragmentNextBinding.passUser.text.isNullOrEmpty()){
                    mFragmentNextBinding.passText//.isErrorEnabled = true
                    mFragmentNextBinding.passText.error = "Please enter the password"
                }else{
                    mFragmentNextBinding.passText.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }

    private fun setUpSlider(){
        mFragmentNextBinding.seekBarNext.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Toast.makeText(mFragment.requireContext(), "$progress", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    fun showDialog(v: View){
        var selectedItem: Int = -1
        val arr = mFragment.resources.getStringArray(R.array.animal_array)
        AlertDialog.Builder(mFragment.context)
            .setTitle("Choose an animal")
            .setSingleChoiceItems(R.array.animal_array, selectedItem){
                    dialog: DialogInterface?, which: Int ->
                Toast.makeText(mFragment.context, "${arr[which]}", Toast.LENGTH_SHORT).show()
                selectedItem = which
            }.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(mFragment.context, "${arr[selectedItem]}", Toast.LENGTH_SHORT).show()
            }).setNegativeButton("Dismiss", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(mFragment.context, "cancelled ${arr[selectedItem]}", Toast.LENGTH_SHORT).show()
            }).show()
    }

    fun showDialogMultiple(v: View){
        val arr = mFragment.resources.getStringArray(R.array.animal_array)
        val selectedItem: BooleanArray = BooleanArray(arr.size)
        AlertDialog.Builder(mFragment.context)
            .setTitle("Choose an animal")
            .setMultiChoiceItems(R.array.animal_array, selectedItem, DialogInterface.OnMultiChoiceClickListener{ dialogInterface: DialogInterface, i: Int, b: Boolean ->
                Toast.makeText(mFragment.context, "$i $b", Toast.LENGTH_SHORT).show()
                selectedItem[i] = b
            }).setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                var txt = ""
                for ((index, a) in selectedItem.withIndex()) {
                    txt += if (a) arr[index] else ""
                }
                Toast.makeText(mFragment.context, "$txt", Toast.LENGTH_SHORT)
                    .show()
            }).setNegativeButton("Dismiss", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(mFragment.context, "cancelled ${arr}", Toast.LENGTH_SHORT).show()
            }).show()
    }

    fun showDateDialog(v:View){
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(mFragment.requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.time)
            Toast.makeText(mFragment.requireContext(), "$date", Toast.LENGTH_SHORT).show()
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()

    }


}