package com.demirkiran.baseApp.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.demirkiran.baseApp.BuildConfig
import com.demirkiran.baseApp.R
import com.demirkiran.baseApp.databinding.ActivityMainBinding
import com.demirkiran.baseApp.repository.MainRepository
import com.demirkiran.baseApp.ui.Adapters.MyViewPagerAdapter
import com.demirkiran.baseApp.ui.main.fragments.Curiosity.CuriosityFragment
import com.demirkiran.baseApp.ui.main.fragments.Opportunity.OpportunityFragment
import com.demirkiran.baseApp.ui.main.fragments.Spirit.SpiritFragment
import com.facebook.stetho.Stetho
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    val arrayList: ArrayList<Int> = ArrayList<Int>(3)
   @Inject
   lateinit var  mainRepository : MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        loadIcons()
        setupViewPager()
        setupIcons()

       // val mainRepository = MainRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(application, mainRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    fun showLoading() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        ac_ma_loader_layout.visibility = View.VISIBLE
    }

    fun hideLoading() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        ac_ma_loader_layout.visibility = View.GONE
    }

    fun loadIcons() {
        arrayList.add(R.drawable.curiosity)
        arrayList.add(R.drawable.ic_menu2)
        arrayList.add(R.drawable.ic_menu3)
    }

    fun setupIcons() {
        binding.tabs.getTabAt(0)!!.setIcon(arrayList[0]);
        binding.tabs.getTabAt(1)!!.setIcon(arrayList[1]);
        binding.tabs.getTabAt(2)!!.setIcon(arrayList[2]);
    }

    fun setupViewPager() {
        //ViewPager için adapter nesnesi oluşturuyoruz ve kullanacağımız fragment, title'ları ekliyoruz.
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OpportunityFragment(),"Opportunity")
        adapter.addFragment(CuriosityFragment(),"Curiosity")
        adapter.addFragment(SpiritFragment(),"Spirit")
        //Adapter'ımızdaki verileri viewPager adapter'a veriyoruz
        binding.viewPager.adapter = adapter
        //Tablar arasında yani viewPager'lar arasında geçisi sağlıyoruz
        binding.tabs.setupWithViewPager(binding.viewPager)

    }

}

