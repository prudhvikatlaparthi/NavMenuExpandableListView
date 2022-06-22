package com.pru.expandablelistex

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.pru.expandablelistex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val headerList: MutableList<DrawerMenuModel> = mutableListOf()
    private val childList: HashMap<DrawerMenuModel, List<DrawerMenuModel>> = hashMapOf()
    private val adapter: MenuExpandableAdapter by lazy {
        MenuExpandableAdapter(
            listHeaderData = headerList,
            listChildData = childList
        )
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        populateData()


        toggle = ActionBarDrawerToggle(
            this,
            binding.drawLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.expView.setAdapter(adapter)
        for (i in 0 until adapter.groupCount) binding.expView.expandGroup(i)

        binding.expView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            binding.tvMain.text = childList[headerList[groupPosition]]?.get(childPosition)?.menuName
            binding.drawLayout.closeDrawer(GravityCompat.START)
            false
        }
        binding.expView.setOnGroupClickListener { _, _, groupPosition, _ ->
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toggle.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    private fun populateData() {
        val drawerMenuModel1 = DrawerMenuModel(
            menuName = "Section One",
        )
        headerList.add(drawerMenuModel1)
        val data1 = List(2) {
            DrawerMenuModel(menuName = "Section One $it")
        }
        childList[drawerMenuModel1] = data1

        val drawerMenuModel2 = DrawerMenuModel(
            menuName = "Section Two",
        )
        headerList.add(drawerMenuModel2)
        val data2 = List(3) {
            DrawerMenuModel(menuName = "Section Two $it")
        }
        childList[drawerMenuModel2] = data2

        val drawerMenuModel3 = DrawerMenuModel(
            menuName = "Section Three",
        )
        headerList.add(drawerMenuModel3)
        val data3 = List(10) {
            DrawerMenuModel(menuName = "Section Three $it")
        }
        childList[drawerMenuModel3] = data3
    }

    override fun onBackPressed() {
        if (binding.drawLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawLayout.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }
}