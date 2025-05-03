package com.universtity.orderit.navigation;

import android.content.Context;
import android.content.Intent;

import com.universtity.orderit.MainActivity;
import com.universtity.orderit.bar.BarActivity;
import com.universtity.orderit.kitchen.KitchenActivity;
import com.universtity.orderit.waiter.WaiterActivity;

public class RoleNavigator {
    public static Intent getStartIntent(Context context, String role) {
        switch (role) {
            case "waiter":
                return new Intent(context, WaiterActivity.class);
            case "kitchen":
                return new Intent(context, KitchenActivity.class);
            case "bar":
                return new Intent(context, BarActivity.class);
            case "superadmin":
                return new Intent(context, MainActivity.class);
            default:
                return new Intent(context, MainActivity.class);
        }
    }
}
