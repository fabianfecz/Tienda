package com.uisrael.tienda;


import com.google.firebase.database.FirebaseDatabase;

public class BaseDatosFB extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
