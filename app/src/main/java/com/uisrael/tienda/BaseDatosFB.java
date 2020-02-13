package com.uisrael.tienda;


import com.google.firebase.database.FirebaseDatabase;

public class BaseDatosFB extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private String UsuId;
    private String UsuNick;

    public String getUsuId() {
        return UsuId;
    }

    public void setUsuId(String usuId) {
        UsuId = usuId;
    }

    public String getUsuNick() {
        return UsuNick;
    }

    public void setUsuNick(String usuNick) {
        UsuNick = usuNick;
    }
}
