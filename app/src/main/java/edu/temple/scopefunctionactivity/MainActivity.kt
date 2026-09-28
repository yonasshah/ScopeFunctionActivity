package edu.temple.scopefunctionactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // You can test your helper functions by  calling them from onCreate() and
        // printing their output to the Log, which is visible in the LogCat:
        // eg. Log.d("function output", getTestDataArray().toString())

        val tag = "ScopeFunctionTest"

        // 1. Test getTestDataArray()
        val testList = getTestDataArray()
        Log.d(tag, "1. Sorted random array: $testList")

        // 2. Test averageLessThanMedian()
        val numbers1 = listOf(1.0, 2.0, 10.0) // avg = 4.33, median = 2.0 -> false
        val numbers2 = listOf(1.0, 8.0, 9.0)  // avg = 6.0, median = 8.0 -> true
        Log.d(tag, "2a. Avg < Median (expect false): ${averageLessThanMedian(numbers1)}")
        Log.d(tag, "2b. Avg < Median (expect true): ${averageLessThanMedian(numbers2)}")

        // 3. Test getView()
        val dummyData = listOf(100, 200, 300)

        // Case A: recycledView is null (creates a new TextView with custom padding & textSize)
        val newView = getView(position = 0, recycledView = null, collection = dummyData, context = this) as TextView
        Log.d(tag, "3a. New view text: ${newView.text}, textSize: ${newView.textSize}, paddingLeft: ${newView.paddingLeft}")

        // Case B: recycledView is provided (reuses existing TextView and updates text)
        val recycled = getView(position = 1, recycledView = newView, collection = dummyData, context = this) as TextView
        Log.d(tag, "3b. Recycled view text: ${recycled.text}, same instance: ${newView === recycled}")


    }


    /* Convert all the helper functions below to Single-Expression Functions using Scope Functions */
    // eg. private fun getTestDataArray() = ...

    // HINT when constructing elaborate scope functions:
    // Look at the final/return value and build the function "working backwards"

    // Return a list of random, sorted integers
   /*
   private fun getTestDataArray(): List<Int> {
        val testArray = MutableList(10) { Random.nextInt() }
        testArray.sort()
        return testArray
    }*/

    private fun getTestDataArray() = MutableList(10) { Random.nextInt() }.apply { sort() }

    // Return true if average value in list is greater than median value, false otherwise
    /*
    private fun averageLessThanMedian(listOfNumbers: List<Double>): Boolean {
        val avg = listOfNumbers.average()
        val sortedList = listOfNumbers.sorted()
        val median = if (sortedList.size % 2 == 0)
            (sortedList[sortedList.size / 2] + sortedList[(sortedList.size - 1) / 2]) / 2
        else
            sortedList[sortedList.size / 2]

        return avg < median
    }
*/
    private fun averageLessThanMedian(listOfNumbers: List<Double>): Boolean =
        listOfNumbers.sorted().let { sorted ->
            val median = if (sorted.size % 2 == 0) {
                (sorted[sorted.size / 2] + sorted[(sorted.size - 1) / 2]) / 2
            } else {
                sorted[sorted.size / 2]
            }

            listOfNumbers.average() < median
        }
    // Create a view from an item in a collection, but recycle if possible (similar to an AdapterView's adapter)
    /*
    private fun getView(position: Int, recycledView: View?, collection: List<Int>, context: Context): View {
        val textView: TextView

        if (recycledView != null) {
            textView = recycledView as TextView
        } else {
            textView = TextView(context)
            textView.setPadding(5, 10, 10, 0)
            textView.textSize = 22f
        }

        textView.text = collection[position].toString()

        return textView
    }
    */

    private fun getView(position: Int, recycledView: View?, collection: List<Int>, context: Context): View =
        ((recycledView as? TextView) ?: TextView(context).apply {
            setPadding(5, 10, 10, 0)
            textSize = 22f
        }).apply {
            text = collection[position].toString()
        }
}
