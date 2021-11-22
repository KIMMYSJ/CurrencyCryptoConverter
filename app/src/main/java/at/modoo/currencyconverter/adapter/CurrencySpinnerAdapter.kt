package at.modoo.currencyconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import at.modoo.currencyconverter.R
import at.modoo.currencyconverter.databinding.CurrencySpinnerItemBinding
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.util.DataState

class CurrencySpinnerAdapter (context: Context, dataSource: List<Currency>):ArrayAdapter<Currency>(context,
    CurrencyViewHolder.LAYOUT,dataSource){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return onBindView(parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return onBindView(parent, position)
    }

    private fun onBindView(parent: ViewGroup, position: Int): View {
        val dataBinding:CurrencySpinnerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            CurrencyViewHolder.LAYOUT,
            parent,
            false
        )
        dataBinding.currency = getItem(position)
        return dataBinding.root
    }
    class CurrencyViewHolder {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.currency_spinner_item
        }
    }

}