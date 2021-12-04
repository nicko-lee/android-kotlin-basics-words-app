/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    // Set default layout state for the app to be Linear Layout
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        // Sets the LinearLayoutManager of the recyclerview as well as the adapter
        // Remember this defaults to linear upon initialisation and thus the linear layout icon
        // That's why u don't have to call setIcon() at this point because it is already set onCreateOptionsMenu()
        chooseLayout()
    }

    /** So this is essentially the clickHandler for when a user taps on a menu item
     *  and it is called in onOptionsItemSelected()
     */
    private fun chooseLayout() {
        // Always start with a LinearLayout by default
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        recyclerView.adapter = LetterAdapter()
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
                ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
            else ContextCompat.getDrawable(this, R.drawable. ic_grid_layout)
    }

    /** If you don't override this method you get no menu
     *  This is what you do if you want to show a menu item
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)

        // Grabbing a handle on the menu item so we can pass it to setIcon() and manipulate a property of the menuItem
        val layoutButton = menu?.findItem(R.id.action_switch_layout)
        // Calls code to set the icon based on the LinearLayoutManager of the RecyclerView
        setIcon(layoutButton)

        // The method returns a Boolean—you return true here since you want the options menu to be created
        // So this is a message to the Android system to show or not show the options menu
        return true
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
