package no.jvl.LindaSend.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.net.Uri.parse;
import static android.provider.ContactsContract.CommonDataKinds.Phone;
import static android.widget.Toast.LENGTH_SHORT;
import static no.jvl.LindaSend.FullscreenActivity.PICK_CONTACT_ACTIVITY;

public class ContactChooser implements View.OnClickListener {

    private final Activity activity;

    private static final String SAVED_CONTACT_KEY = "contact.uri";

    public ContactChooser(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, parse("content://contacts"));
        intent.setType(Phone.CONTENT_TYPE);
        activity.startActivityForResult(intent, PICK_CONTACT_ACTIVITY);
    }

    public void handleContactIntent(int resultCode, Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(activity, "Wops, noe gikk feil!", LENGTH_SHORT).show();
            return;
        }

        Uri uri = intent.getData();
        SharedPreferences.Editor editor = activity.getSharedPreferences("LindaSend", Context.MODE_PRIVATE).edit();
        Log.d("Saving contact", uri.toString());
        editor.putString(SAVED_CONTACT_KEY, uri.toString());
        editor.apply();
    }

    public synchronized Contact getContact() {
        SharedPreferences preferences = activity.getSharedPreferences("LindaSend", Context.MODE_PRIVATE);
        String uri = preferences.getString(SAVED_CONTACT_KEY, null);

        if (uri == null) return null;
        Log.d("Saved contact uri", uri);

        String[] projection = { Phone.NUMBER, Phone.DISPLAY_NAME, Phone.PHOTO_URI };
        Uri contactUri = parse(uri);
        Cursor cursor = activity.getContentResolver().query(contactUri, projection, null, null, null);
        if (cursor.isAfterLast()) return null;
        cursor.moveToFirst();

        String phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
        Log.d("phonenumber", phoneNumber);
        String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
        Log.d("displayName", name);
        String photoUri = cursor.getString(cursor.getColumnIndex(Phone.PHOTO_URI));

        return new Contact(phoneNumber, name, photoUri);
    }

    public boolean hasSavedContact() {
        return getContact() != null;
    }

    public static class Contact {

        public final String name;
        public final String phoneNumber;
        public final String photoUri;

        public Contact(String phoneNumber, String name, String photoUri) {
            this.phoneNumber = phoneNumber;
            this.name = name;
            this.photoUri = photoUri;
        }
    }
}
