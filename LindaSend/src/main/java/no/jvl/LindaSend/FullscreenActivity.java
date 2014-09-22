package no.jvl.LindaSend;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import no.jvl.LindaSend.contact.ContactChooser;

import static android.widget.ImageView.ScaleType.FIT_XY;

public class FullscreenActivity extends Activity {

    public static final int PICK_CONTACT_ACTIVITY = 123;

    private ContactChooser contactChooser;

    public FullscreenActivity() {
        contactChooser = new ContactChooser(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        Button friendChooser = (Button) super.findViewById(R.id.friend_chooser);
        friendChooser.setOnClickListener(contactChooser);

        displayChosenContact();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CONTACT_ACTIVITY) {
            contactChooser.handleContactIntent(resultCode, intent);
            this.displayChosenContact();
        }
    }

    public void displayChosenContact() {
        TextView numberView = (TextView) super.findViewById(R.id.chosenContactNumber);
        TextView nameView = (TextView) super.findViewById(R.id.chosenContactName);
        ImageView photoView = (ImageView) super.findViewById(R.id.chosenContactImage);
        if (contactChooser.hasSavedContact()) {
            ContactChooser.Contact contact = contactChooser.getContact();
            numberView.setText(contact.phoneNumber);
            nameView.setText(contact.name);
            if (contact.photoUri != null) {
                photoView.setImageURI(Uri.parse(contact.photoUri));
                photoView.setScaleType(FIT_XY);
            }
        }
    }


}
