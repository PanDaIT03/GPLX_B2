package com.example.gplx_b2.Database;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnSQL {
    Connection con;

    @SuppressLint("NewApi")
    public Connection conn() {
        String connectURL = null;
        String ip = "192.168.137.127", port = "1433", db = "GPLX_B2", username = "sa",
                password = "<123456aA@$’>";

        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" +
                    db + ";user=" + username + ";password=" + password + ";";
            con = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            Log.e("Error :", e.getMessage());
        }

        return con;
    }
}
