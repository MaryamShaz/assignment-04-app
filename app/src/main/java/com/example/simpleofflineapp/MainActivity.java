package com.example.simpleofflineapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostsAdapter adapter;
    private DatabaseHelper dbHelper;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_SELECTED_THEME = "selectedTheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply Theme BEFORE onCreate/setContentView
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String theme = prefs.getString(KEY_SELECTED_THEME, "light");
        if ("dark".equals(theme)) {
            setTheme(R.style.Theme_SimpleOfflineApp_Dark);
        } else {
            setTheme(R.style.Theme_SimpleOfflineApp_Light);
        }

        super.onCreate(savedInstanceState);

        // Check Login
        boolean isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false);
        if (!isLoggedIn) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return; // Important: stop execution
        }

        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with empty list initially
        adapter = new PostsAdapter(new ArrayList<>(), post -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("post_id", post.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        if (isNetworkAvailable()) {
            Toast.makeText(this, R.string.internet_available, Toast.LENGTH_SHORT).show();
            fetchDataFromApi();
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            loadDataFromDb();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void fetchDataFromApi() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<Post> posts = new ArrayList<>();
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int id = obj.getInt("id");
                        String title = obj.getString("title");
                        String body = obj.getString("body");
                        Post post = new Post(id, title, body);
                        posts.add(post);

                        // Save to DB
                        dbHelper.insertPost(post);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                if (!posts.isEmpty()) {
                    adapter.updateData(posts);
                } else {
                    // Fallback to DB if API failed?
                    // For now, if API fails, just load what we have
                    loadDataFromDb();
                }
            });
        });
    }

    private void loadDataFromDb() {
        List<Post> posts = dbHelper.getAllPosts();
        adapter.updateData(posts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_light) {
            setThemeAndRecreate("light");
            return true;
        } else if (id == R.id.menu_dark) {
            setThemeAndRecreate("dark");
            return true;
        } else if (id == R.id.menu_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setThemeAndRecreate(String themeMode) {
        prefs.edit().putString(KEY_SELECTED_THEME, themeMode).apply();
        recreate();
    }

    private void logout() {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
