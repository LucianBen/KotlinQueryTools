package com.ben.kotlindemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ben.kotlindemo.event.SubtitleEvent
import com.ben.kotlindemo.fragment.CalendarFragment
import com.ben.kotlindemo.fragment.ConstellationFragment
import com.ben.kotlindemo.fragment.OneiromancyFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {
    lateinit var calendarFragment: Fragment
    lateinit var constellationFragment: Fragment
    lateinit var oneiromancyFragment: Fragment
    private var todayConstellation = ""

    override fun onTabReselected(position: Int) {

    }

    override fun onTabUnselected(position: Int) {

    }

    override fun onTabSelected(position: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (position) {
            0 -> {
                calendarFragment = CalendarFragment()
                ft.replace(R.id.main_fragment, calendarFragment)
                initToolBar("今日运程", "")
            }
            1 -> {
                constellationFragment = ConstellationFragment.newInstance(todayConstellation)
                ft.replace(R.id.main_fragment, constellationFragment)
                initToolBar("今日运势", "")
            }
            2 -> {
                oneiromancyFragment = OneiromancyFragment()
                ft.replace(R.id.main_fragment, oneiromancyFragment)
                initToolBar("周公解梦", "")
            }
        }
        ft.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventBus.getDefault().register(this)
        initToolBar("今日运程", "")
        initBottomBar()
        setDefaultFragment()
    }

    private fun initToolBar(title: String, subtitle: String) {
        toolbar_main.title = title
        toolbar_main.subtitle = subtitle
        toolbar_main.setSubtitleTextColor(getColor(R.color.white))
        setSupportActionBar(toolbar_main)
    }

    private fun initBottomBar() {
        bottom_navigation_bar
                .addItem(BottomNavigationItem(R.mipmap.main_calendar, "黄道吉日"))
                .addItem(BottomNavigationItem(R.mipmap.main_constellation, "星座运势"))
                .addItem(BottomNavigationItem(R.mipmap.main_oneiromancy, "周公解梦"))
                .initialise()
        bottom_navigation_bar.setTabSelectedListener(this)
    }

    private fun setDefaultFragment() {

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        calendarFragment = CalendarFragment()

        ft.replace(R.id.main_fragment, calendarFragment)
        ft.commit()

    }

    @Subscribe
    public fun onEvent(event: SubtitleEvent) {
        toolbar_main.setTitleTextColor(getColor(R.color.white))
        toolbar_main.title = event.subtitle
        todayConstellation = event.constellation
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }


}



