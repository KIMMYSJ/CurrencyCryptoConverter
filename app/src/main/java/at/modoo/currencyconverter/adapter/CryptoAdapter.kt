package at.modoo.currencyconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import at.modoo.currencyconverter.R
import at.modoo.currencyconverter.databinding.CryptoSpinnerItemBinding
import at.modoo.currencyconverter.model.Crypto


class CryptoAdapter (context: Context, dataSource: List<Crypto>): ArrayAdapter<Crypto>(context,
    CrytoViewHolder.LAYOUT,dataSource){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return onBindView(parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return onBindView(parent, position)
    }

    private fun onBindView(parent: ViewGroup, position: Int): View {
        val dataBinding: CryptoSpinnerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            CrytoViewHolder.LAYOUT,
            parent,
            false
        )
        dataBinding.crypto = getItem(position)
        dataBinding.executePendingBindings()
        return dataBinding.root
    }
    class CrytoViewHolder {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.crypto_spinner_item
        }
    }

}