package de.dotwee.micropinner.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import de.dotwee.micropinner.tools.PinHandler;

/**
 * Created by Lukas on 26.06.2015.
 */
public class OnDeleteReceiver extends BroadcastReceiver {
    private final static String LOG_TAG = "OnDeleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        // deserialize our pin from the intent
        PinHandler.Pin pin = (PinHandler.Pin)
                intent.getSerializableExtra(PinHandler.Pin.EXTRA_INTENT);

        // and tell the pin handler to remove it from the index
        new PinHandler(context).removePin(pin);
    }
}
