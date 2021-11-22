package at.modoo.currencyconverter.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import at.modoo.currencyconverter.R
import com.hanks.htextview.base.HTextView


class SplashFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val insetsControllerCompat = WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
        insetsControllerCompat.hide(WindowInsetsCompat.Type.statusBars())

        val text = view.findViewById<HTextView>(R.id.splash_text)
        text.animateText("urrency \n rypto  \n onverter")

//        val slideAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.slide)
//        val layout = view.findViewById<ConstraintLayout>(R.id.splash_layout)
//        Handler(Looper.getMainLooper()).postDelayed({
//            layout.startAnimation(slideAnim)
//        },3000)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_converterSelectFragment)
            ActivityNavigator.applyPopAnimationsToPendingTransition(requireActivity())
        },3000)





    }





}