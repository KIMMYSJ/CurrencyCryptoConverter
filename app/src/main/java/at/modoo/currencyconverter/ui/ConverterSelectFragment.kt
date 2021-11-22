package at.modoo.currencyconverter.ui

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import at.modoo.currencyconverter.R
import at.modoo.currencyconverter.adapter.CryptoAdapter
import at.modoo.currencyconverter.adapter.CurrencySpinnerAdapter
import at.modoo.currencyconverter.databinding.FragmentConverterSelectBinding
import at.modoo.currencyconverter.model.Crypto
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.util.ConverterState
import at.modoo.currencyconverter.util.DataState
import kotlinx.coroutines.flow.collect
import timber.log.Timber


class ConverterSelectFragment : Fragment() {
    lateinit var binding: FragmentConverterSelectBinding
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    private val viewModel: CurrencyViewModel by activityViewModels()
    var isFromFront = true //CurrencyFront
    var isToFront = true //CryptoFront
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConverterSelectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFlipCardAnimation()
        initPage()

        binding.button.setOnClickListener {
            when {

                isFromFront && isToFront -> {
                    viewModel.converterType = ConverterState.CtoCry
                }
                isFromFront && !isToFront -> {
                    viewModel.converterType = ConverterState.CtoC

                }
                !isFromFront && isToFront -> {
                    viewModel.converterType = ConverterState.CryToCry

                }
                !isFromFront && !isToFront -> {
                    viewModel.converterType = ConverterState.CryToC

                }

            }


        }
    }


    private fun initPage() {

        viewModel.getCrypto()
        lifecycleScope.launchWhenCreated {
            viewModel.crypto.collect { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        Timber.d("onCreate: " + "Empty")
                    }
                    is DataState.Success -> {
                        Timber.d("onCreate: $dataState")
                        val spinnerAdapter = CryptoAdapter(requireContext(), dataState.data)
                        binding.spinner2.adapter = spinnerAdapter

                    }
                    is DataState.Loading -> {
                        Timber.d("onCreate: Loading")
                    }
                    is DataState.ErrorUnknown -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: " + dataState.exception,
                            Toast.LENGTH_SHORT
                        ).show()
                        Timber.d("onCreate: unknown" + dataState.exception)
                    }
                    is DataState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: " + dataState.exception,
                            Toast.LENGTH_SHORT
                        ).show()
                        Timber.d("onCreate: unknown" + dataState.exception.toString())
                    }
                }
            }


        }
        val json = resources.openRawResource(R.raw.countries)
        viewModel.getRate(json)
        lifecycleScope.launchWhenCreated {
            viewModel.currency.collect { dataState ->
                when (dataState) {
                    is DataState.Empty -> {
                        Timber.d("onCreate: " + "Empty")
                    }
                    is DataState.Success -> {
//                        Timber.d("onCreate: Success $dataState")
                        Timber.d("initPage: " + dataState.data.size)
                        val spinnerAdapter =
                            CurrencySpinnerAdapter(requireContext(), dataState.data)
                        binding.spinner.adapter = spinnerAdapter


                    }
                    is DataState.Loading -> {
                        Timber.d("onCreate: Loading")

                    }
                    is DataState.ErrorUnknown -> {
                        Timber.d("onCreate: unknown" + dataState.exception)
                    }
                    is DataState.Error -> {
                        Timber.d("onCreate: Error" + dataState.exception.toString())
                    }
                }
            }

        }

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Timber.d("onItemSelected: position $p2 id $p3")
                viewModel.targetFrom = p3

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Timber.d("onItemSelected: position $p2 id $p3")
                viewModel.targetTo = p3
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setFlipCardAnimation() {
        val scale = requireContext().resources.displayMetrics.density
        binding.tvFromCurrency.cameraDistance = 8000 * scale
        binding.tvFromCrypto.cameraDistance = 8000 * scale

        front_anim = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.front_animator
        ) as AnimatorSet
        back_anim =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet

        binding.tvFromCurrency.setOnClickListener {
            binding.tvFromCrypto.isClickable = true
            binding.tvFromCurrency.isClickable = false
            front_anim.setTarget(binding.tvFromCrypto)
            back_anim.setTarget(binding.tvFromCurrency)
            front_anim.start()
            back_anim.start()
            isFromFront = false //Crypto
            when (viewModel.crypto.value) {
                is DataState.Success -> {
                    val spinnerAdapter = CryptoAdapter(
                        requireContext(),
                        (viewModel.crypto.value as DataState.Success<List<Crypto>>).data
                    )
                    binding.spinner.adapter = spinnerAdapter
                    spinnerAdapter.notifyDataSetChanged()
                }
                DataState.Empty -> TODO()
                is DataState.Error -> TODO()
                is DataState.ErrorUnknown -> TODO()
                DataState.Loading -> TODO()
            }


        }

        binding.tvFromCrypto.setOnClickListener {
            binding.tvFromCrypto.isClickable = false
            binding.tvFromCurrency.isClickable = true
            front_anim.setTarget(binding.tvFromCurrency)
            back_anim.setTarget(binding.tvFromCrypto)
            front_anim.start()
            back_anim.start()
            isFromFront = true

            when (viewModel.currency.value) {
                is DataState.Success -> {
                    val spinnerAdapter = CurrencySpinnerAdapter(
                        requireContext(),
                        (viewModel.currency.value as DataState.Success<List<Currency>>).data
                    )
                    binding.spinner.adapter = spinnerAdapter
                    spinnerAdapter.notifyDataSetChanged()
                }
                DataState.Empty -> TODO()
                is DataState.Error -> TODO()
                is DataState.ErrorUnknown -> TODO()
                DataState.Loading -> TODO()
            }
        }

        binding.tvToCrypto.setOnClickListener {
            binding.tvToCurrency.isClickable = true
            binding.tvToCrypto.isClickable = false
            front_anim.setTarget(binding.tvToCurrency)
            back_anim.setTarget(binding.tvToCrypto)
            front_anim.start()
            back_anim.start()
            isToFront = false
            when (viewModel.crypto.value) {
                is DataState.Success -> {
                    val spinnerAdapter = CurrencySpinnerAdapter(
                        requireContext(),
                        (viewModel.currency.value as DataState.Success<List<Currency>>).data
                    )
                    binding.spinner2.adapter = spinnerAdapter
                    spinnerAdapter.notifyDataSetChanged()
                }
                DataState.Empty -> TODO()
                is DataState.Error -> TODO()
                is DataState.ErrorUnknown -> TODO()
                DataState.Loading -> TODO()
            }

        }

        binding.tvToCurrency.setOnClickListener {
            binding.tvToCurrency.isClickable = false
            binding.tvToCrypto.isClickable = true
            front_anim.setTarget(binding.tvToCrypto)
            back_anim.setTarget(binding.tvToCurrency)
            front_anim.start()
            back_anim.start()
            isToFront = true
            when (viewModel.crypto.value) {
                is DataState.Success -> {
                    val spinnerAdapter = CryptoAdapter(
                        requireContext(),
                        (viewModel.crypto.value as DataState.Success<List<Crypto>>).data
                    )
                    binding.spinner2.adapter = spinnerAdapter
                    spinnerAdapter.notifyDataSetChanged()
                }
                DataState.Empty -> TODO()
                is DataState.Error -> TODO()
                is DataState.ErrorUnknown -> TODO()
                DataState.Loading -> TODO()
            }
        }
    }
}