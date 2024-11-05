package com.example.hw6;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editTextTitle = findViewById(R.id.editTextTitle);
        setSupportActionBar(findViewById(R.id.toolbar));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_text) {
            showEditTextDialog();

            return true;
        } else if (id == R.id.action_select_text) {
            showSelectTextDialog();

            return true;
        } else if (id == R.id.action_get_time) {
            showTimeDialog();

            return true;
        } else if (id == R.id.action_change_date) {
            showDateDialog();

            return true;
        } else if (id == R.id.action_notify) {
            showNotification();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showEditTextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редагувати текст");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText inputTitle = new EditText(this);
        inputTitle.setHint("Title");
        layout.addView(inputTitle);

        EditText inputText = new EditText(this);
        inputText.setHint("Text");
        layout.addView(inputText);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String title = inputTitle.getText().toString();
            String text = inputText.getText().toString();

            if (!title.isEmpty() && !text.isEmpty()) {
                String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                editTextTitle.setText(title);
                editText.setText(text + " (змінено: " + currentTime + ")");
            }
        });
        builder.setNegativeButton("Відміна", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showSelectTextDialog() {
        String[] textOptionsArray = {"Дуже змiстовний текст з першого варiанту", "Текст для сповiщення", "На баланс поступили кошти"};
        String[] textOptionsArrayTitles = {"Змiстовний текст", "Заголовок", "Кошти"};
        int[] checked = {-1};

        new AlertDialog.Builder(this)
                .setTitle("Оберiть текст")
                .setSingleChoiceItems(textOptionsArray, -1, (dialog, item) -> {
                    Toast.makeText(this, "Ваш варіант: " + textOptionsArray[item], Toast.LENGTH_SHORT).show();
                    checked[0] = item;
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    if (checked[0] != -1) {
                        editText.setText(textOptionsArray[checked[0]]);
                        editTextTitle.setText(textOptionsArrayTitles[checked[0]]);
                    }
                })
                .setNegativeButton("Відміна", (dialog, id) -> {
                    editText.setText("Вибір скасовано");
                })
                .create()
                .show();
    }


    private void showTimeDialog() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Текстове поле порожнє", Toast.LENGTH_SHORT).show();
        } else {
            String currentText = editText.getText().toString();
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                String updatedText = currentText + " (змінено: " + time + ")";

                editText.setText(updatedText);
            }, Integer.parseInt(currentTime.split(":")[0]), Integer.parseInt(currentTime.split(":")[1]), true);

            timePickerDialog.show();
        }
    }

    private void showDateDialog() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Текстове поле порожнє", Toast.LENGTH_SHORT).show();
        } else {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String date = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year);
                String currentText = editText.getText().toString();
                String updatedText = currentText + " (змінено: " + date + ")";

                editText.setText(updatedText);
            }, 2023, 0, 1);
            datePickerDialog.show();
        }
    }

    private void showNotification() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Текстове поле порожнє", Toast.LENGTH_SHORT).show();

            return;
        }

        String title = editTextTitle.getText().toString();
        String text = editText.getText().toString();
        String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text + " (згенеровано: " + currentTime + ")"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }
}