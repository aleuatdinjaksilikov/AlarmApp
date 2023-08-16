package com.example.myalarmapp.ui.watch

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarmapp.AlarmActivity
import com.example.myalarmapp.MainActivity
import com.example.myalarmapp.R
import com.example.myalarmapp.data.WatchFragmentDatabase
import com.example.myalarmapp.data.dao.WatchFragmentDao
import com.example.myalarmapp.data.models.WatchData
import com.example.myalarmapp.databinding.FragmentWatchBinding
import com.example.myalarmapp.ui.watch.adapter.WatchAdapter
import com.example.myalarmapp.utils.getDifferenceInTimeZone
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.awaitAll
import java.text.SimpleDateFormat
import kotlin.random.Random

class WatchFragment : Fragment(R.layout.fragment_watch) {
    private lateinit var binding: FragmentWatchBinding
    private var adapter = WatchAdapter()
    private lateinit var dao: WatchFragmentDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWatchBinding.bind(view)
        dao = WatchFragmentDatabase.getInstance(requireContext()).getWatchDao()

        initVariable()


        (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottom_nav_view).visibility = View.VISIBLE

        lifecycleScope.launchWhenResumed {
            adapter.submitList(dao.getAllWatch().toMutableList())
        }



    }

    private fun initVariable() {
        val swapHelper = getSwapManager()
        swapHelper.attachToRecyclerView(binding.rvWatchFragment)

        binding.tvCurrentdata.text = getFormattedCurrentData()

        binding.rvWatchFragment.adapter = adapter
        var clicked = false

        binding.btnAddCountry.setOnClickListener {
            if (clicked){
                findNavController().navigate(WatchFragmentDirections.actionWatchFragmentToSearchFragment())
                clicked = false
            }else{
                findNavController().navigate(WatchFragmentDirections.actionWatchFragmentToSearchFragment())
                clicked = true
            }
        }

    }


    @SuppressLint("SimpleDateFormat")
    private fun getFormattedCurrentData(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEE, d MMM")
        return formatter.format(date)

    }

    private fun getSwapManager(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper
        .SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val watchData = adapter.currentList[pos]

                lifecycleScope.launchWhenResumed {
                    dao.deleteWatch(watchData)
                    adapter.submitList(dao.getAllWatch().toMutableList())
                }


            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c,recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireContext(),R.color.color_red_itemDelete))
                    .addCornerRadius(0,30)
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        )
    }







}
