package no.jvl.LindaSend.sms;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;

import no.jvl.LindaSend.contact.ContactChooser;

public class SendSmsDialogFragment extends DialogFragment {

    private final ContactChooser.Contact contact;
    private final String message;

    public SendSmsDialogFragment(ContactChooser.Contact contact, String message) {
        this.contact = contact;
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Vil du sende " + message + " til " + contact.name + " på " + contact.phoneNumber + " ?")
                .setPositiveButton("jepp", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(contact.phoneNumber, null, message, null, null);
                        System.exit(0);
                    }
                })
                .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });

        return builder.create();
    }

}
