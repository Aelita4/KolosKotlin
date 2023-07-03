package pl.mikorosa.kolos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.mikorosa.kolos.database.DatabaseConnector
import pl.mikorosa.kolos.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val howManyLiters = binding.inputNumOfLiters.text.toString().toFloat()
            val pricePerLiter = binding.inputPricePerLiter.text.toString().toFloat()

            val db = this.context?.let { it1 -> DatabaseConnector(it1, null) }

            db?.removeFromTemp()
            db?.insertToTemp(howManyLiters, pricePerLiter)
            db?.insertToTransactions(howManyLiters, pricePerLiter)

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}