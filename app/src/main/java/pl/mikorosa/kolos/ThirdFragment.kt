package pl.mikorosa.kolos

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import pl.mikorosa.kolos.database.DatabaseConnector
import pl.mikorosa.kolos.databinding.FragmentThirdBinding
import kotlin.system.exitProcess


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO:
        // table
        // total sum
        // back to 1st
        // exit

        val db = this.context?.let { it1 -> DatabaseConnector(it1, null) }

        val data = db?.getFromTransactions()

        var totalPaidAll = 0.0
        var totalLitersAll = 0.0

        val table = binding.table

        if (data != null) {
            for(transaction in data) {
                val liters = transaction.liters
                val price = transaction.price
                val total = transaction.total

                totalLitersAll += liters
                totalPaidAll += total

                val tableRow = TableRow(this.context)

                tableRow.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                val cell1 = TextView(this.context)
                cell1.text = price.toString()
                cell1.setTextColor(Color.BLACK)
                cell1.textSize = 16f

                val cell2 = TextView(this.context)
                cell2.text = liters.toString()
                cell2.setTextColor(Color.BLACK)
                cell2.textSize = 16f

                val cell3 = TextView(this.context)
                cell3.text = total.toString()
                cell3.setTextColor(Color.BLACK)
                cell3.textSize = 16f

                tableRow.addView(cell1)
                tableRow.addView(cell2)
                tableRow.addView(cell3)

                table.addView(
                    tableRow,
                    TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }
        }

        val textView = binding.textviewThird
        textView.text = "Paid for everything: " + totalPaidAll + " PLN\nLiters bought: " + totalLitersAll + " L"


        binding.buttonThirdBackToFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }

        binding.buttonThirdExit.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            exitProcess(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}