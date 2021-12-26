package de.thm.ap.mc_trainer.appActivity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import de.thm.ap.mc_trainer.R
import de.thm.ap.mc_trainer.databinding.ActivityMainBinding
import de.thm.ap.mc_trainer.firebase.UserDAO

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var backPressedTime = 0L
    private val userDAO = UserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.splashActivity)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain1.toolbar)

       /* binding.appBarMain1.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main1)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.settingsFragment,
                R.id.signInFragment, R.id.signUpFragment), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val userLogged = userDAO.getCurrentUserId()
        if(userLogged != ""){
           // findNavController(R.id.frame_layout).navigate(R.id.action_signInFragment_to_mainFragment)
            navController.navigate(R.id.nav_home)
        }else{
            navController.navigate(R.id.signInFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val logoutDialog = AlertDialog.Builder(this)
        when (item.itemId){
            R.id.action_logout ->
                logoutDialog.setTitle("Loging out")
                    .setMessage("do you really want to log out?")
                    .setPositiveButton("log out") {_, _ ->
                        userDAO.logout()
                        startActivity(Intent(this, MainActivity::class.java))
                        //startActivity(Intent(this, SplashActivity::class.java))
                    }
                    .show()
                    .getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.purple_700))

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main1)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if((backPressedTime + 2000) > System.currentTimeMillis()){
            super.onBackPressed()
        }else{
            Toast.makeText(this, "Press back again to logout", Toast.LENGTH_SHORT).show()
        }

        backPressedTime = System.currentTimeMillis()
    }
}