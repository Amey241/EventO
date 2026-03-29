package com.example.evento;

import static com.example.evento.LoginActivity.loggedInUserEmail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity{
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            showLogoutDialog();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void showLogoutDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm Logout...");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("YES", (dialog, which) -> logout());
        alertDialog.setNegativeButton("NO", (dialog, which) -> {
//            Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });
        alertDialog.show();
    }

    // Logout
    private void logout() {
        // Clear the saved passkey and navigate to LoginActivity
        SharedPreference.save("username", " ");
        SharedPreference.save("userid", " ");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
   protected abstract int getLayoutResourceId();
}
