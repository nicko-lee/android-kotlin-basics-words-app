package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {

    private var _binding: FragmentLetterListBinding? = null

    // Here get() means this property is "get-only"
    // Means u can get the value, but once assigned (as it is here), u can't assign it to something else
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    // Set default layout state for the app to be Linear Layout
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Inflate layout in this lifecycle method
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Notice how the binding class already created a prop for recyclerView
        // So u don't need to call findViewById()
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    /** Because currently the icon is hardcoded to a static icon in the layout_menu.xml file
     *  After toggling the layout, you should update the icon accordingly
     */
    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        // Set the drawable for the menu icon based on which LayoutManager is currently in use
        // An if-clause can be used on the right side of an assignment if all paths return a value.
        // The following code is equivalent to
        // if (isLinearLayoutManager)
        //     menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid_layout)
        // else menu.icon = ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable. ic_grid_layout)
    }

    /** So this is essentially the clickHandler for when a user taps on a menu item
     *  and it is called in onOptionsItemSelected()
     */
    private fun chooseLayout() {
        // Always start with a LinearLayout by default
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 4)
        }
        recyclerView.adapter = LetterAdapter()
    }

    /** This is the callback when the user selects something
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // This when statement switches based on which menu item the user tapped on. You assign different handler for each button
        // So we switch on item.itemId (the ID of the specific menuItem). But in this case we only have one option in our menu
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                // Sets isLinearLayoutManager (a Boolean) to the opposite value (acts as a toggle switch)
                isLinearLayoutManager = !isLinearLayoutManager
                // Sets layout and icon (recalculate which layout and icon to use and re-render/draw the screen)
                chooseLayout()
                setIcon(item)

                return true // not sure why returns true?
            }
            //  Otherwise, do nothing and use the core event handling

            // when clauses require that all possible paths be accounted for explicitly,
            //  for instance both the true and false cases if the value is a Boolean,
            //  or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }
    }
}