package com.demirkiran.baseApp.ui.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.demirkiran.baseApp.ui.main.fragments.Curiosity.CuriosityFragment
import com.demirkiran.baseApp.ui.main.fragments.Opportunity.OpportunityFragment
import com.demirkiran.baseApp.ui.main.fragments.Spirit.SpiritFragment

class MyViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()

    //Fragment'in pozisyonunu veriyoruz
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = OpportunityFragment()

        } else if (position == 1) {4
            fragment = CuriosityFragment()
        }
        else if (position == 2) {
            fragment = SpiritFragment()
        }
        return fragment!!
    }
    //Fragment sayısını veriyoruz
    override fun getCount(): Int {
        return 3
    }
    //Bu fonksiyon ile Fragment'leri ve title'ları ekliyoruz
    fun addFragment(fragment: Fragment,title:String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
    //Title'ların pozisyonunu veriyoruz
    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}