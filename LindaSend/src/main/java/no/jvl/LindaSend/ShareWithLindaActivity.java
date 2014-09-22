package no.jvl.LindaSend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import no.jvl.LindaSend.contact.ContactChooser;
import no.jvl.LindaSend.sms.SendSmsDialogFragment;

import static android.content.Intent.ACTION_SEND;

public class ShareWithLindaActivity extends Activity {

    private final ContactChooser contactChooser;

    public ShareWithLindaActivity() {
        contactChooser = new ContactChooser(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!contactChooser.hasSavedContact()) {
            new FullscreenActivity().onCreate(savedInstanceState);
            return;
        }

        Intent intent = getIntent();
        String contentToShare = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (ACTION_SEND.equals(intent.getAction())) {
            SendSmsDialogFragment sendDialog = new SendSmsDialogFragment(contactChooser.getContact(), contentToShare);
            sendDialog.show(getFragmentManager(), "sendSmsDialog");
        }
    }

}
