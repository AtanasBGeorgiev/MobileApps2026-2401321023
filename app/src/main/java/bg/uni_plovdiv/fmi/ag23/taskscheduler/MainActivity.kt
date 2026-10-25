package bg.uni_plovdiv.fmi.ag23.taskscheduler

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.insert.InsertTaskFragment
import bg.uni_plovdiv.fmi.ag23.taskscheduler.ui.select.SelectTasksFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        findViewById<Button>(R.id.btnInsertFragment).setOnClickListener {
            showFragment(InsertTaskFragment())
        }

        findViewById<Button>(R.id.btnSelectFragment).setOnClickListener {
            showFragment(SelectTasksFragment())
        }

        if (savedInstanceState == null) {
            showFragment(InsertTaskFragment())
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}