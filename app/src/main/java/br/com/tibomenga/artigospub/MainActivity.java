package br.com.tibomenga.artigospub;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import br.com.tibomenga.artigospub.data.DataUtil;
import br.com.tibomenga.artigospub.data.ISearchable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment currentFragment = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataUtil.setContext(getApplicationContext());
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (getIntent() != null) {
            if (WorkflowActivityFragment.class.getSimpleName().equalsIgnoreCase(getIntent().getStringExtra("KEY"))) {
                go(new WorkflowActivityFragment(), "Workflow");
            }
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String password = sharedPrefs.getString("password","");
        if (password.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), CriarSenhaActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (currentFragment == null) {
                goHome();
            }
            handleIntent(getIntent());
        }
    }

    @Override
    public void startActivity(Intent intent) {
        // check if search intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.putExtra("KEY", currentFragment.getClass().getSimpleName());
        }
        super.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i("TTT", "Query: " + query);
            if ((currentFragment != null) && (currentFragment instanceof ISearchable)) {
                ((ISearchable) currentFragment).search(query);
            }
        } else {
            if ((currentFragment != null) && (currentFragment instanceof ISearchable)) {
                ((ISearchable) currentFragment).search(null);
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public void go(Fragment fragment, String subtitle) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
        currentFragment = fragment;
        toolbar.setSubtitle(subtitle);
    }

    public void goHome() {
        go(new ArtigosActivityFragment(), "Artigos");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), CriarSenhaActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String subtitle = null;
        Fragment fragment = null;
        if (id == R.id.nav_artigos) {
            subtitle = "Artigos";
            fragment = new ArtigosActivityFragment();
        } else if (id == R.id.nav_workflow) {
            subtitle = "Workflow";
            fragment = new WorkflowActivityFragment();
        } else if (id == R.id.nav_quit) {
            quit();
        }

        if (fragment != null) {
            go(fragment, subtitle);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return true;
    }

    public void quit() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.quit))
                .setMessage(getString(R.string.confirm_quit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(MainActivity.this);
//                        Intent  it = new Intent(getApplicationContext(), CarregandoActivity.class);
//                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        it.putExtra("SAIR", true);
//                        startActivity(it);
//                        finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                })
                .setNegativeButton(getString(R.string.no), null)
                .show();

    }
}
