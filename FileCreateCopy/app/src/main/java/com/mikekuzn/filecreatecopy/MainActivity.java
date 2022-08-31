package com.mikekuzn.filecreatecopy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final String Tag = "MikeKuzn";
    TextView PermissionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionResult = findViewById(R.id.PermissionResult);
    }

    public void Permission(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                startActivity(new Intent()
                        .setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        .setData(Uri.fromParts("package", this.getPackageName(), null)));
                PermissionResult.setText(R.string.Asked);
            }
            else {
                PermissionResult.setText(R.string.Ready);
            }
        }

    }
    private String getInternalDirectoryPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public void CreateFile1(View view) {
        byte[] data = {0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x39,0x3A};
        CreateFile(getInternalDirectoryPath() + "/file1.txt", data);
    }
    public void Rename12(View view) {Rename(getInternalDirectoryPath() + "/file1.txt", getInternalDirectoryPath() + "/file2.txt");}

    public void Rename21(View view) {Rename(getInternalDirectoryPath() + "/file2.txt", getInternalDirectoryPath() + "/file1.txt");}

    public void Delete2(View view) {DeleteFile(getInternalDirectoryPath() + "/file2.txt");}


    void CreateFile(String file, byte[] data) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            outStream.write(data);
        } catch (IOException e) {
            Log.e(Tag, e.getMessage());
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    Log.e(Tag, e.getMessage());
                }
            }
        }
    }

    private void DeleteFile(String path) {
        try {
            // delete the file
            new File(path).delete();
        }
        catch (Exception e) {
            Log.e(Tag, e.getMessage());
        }
    }

    private void Rename(String path1, String path2) {
        File from = new File(path1);
        File to = new File(path2);
        from.renameTo(to);
    }
}