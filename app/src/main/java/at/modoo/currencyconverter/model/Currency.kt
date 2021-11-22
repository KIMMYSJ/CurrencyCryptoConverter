package at.modoo.currencyconverter.model

import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class Currency(
    var code: String,
    var countryName: String,
    var symbol: String,
    var currencyName: String,
    var flag: String,
    var id: Int,
    var rate:Double

){
    companion object {
        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, base:String){
            val imageByteArray = Base64.decode(base,Base64.DEFAULT)
            Glide.with(imageView.context)
//                .asBitmap()
                .load(imageByteArray)
                .into(imageView);
        }
    }
}