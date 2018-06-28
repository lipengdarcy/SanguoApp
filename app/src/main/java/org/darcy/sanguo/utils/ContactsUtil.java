package org.darcy.sanguo.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import org.darcy.sanguo.pojo.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsUtil {
    private static Context mContext;

    public static List<Contact> getContacts() {
        ArrayList localArrayList = new ArrayList();
        List localList1 = getSdcardContacts();
        List localList2 = getSimContacts();
        localArrayList.addAll(localList1);
        localArrayList.addAll(localList2);
        return localArrayList;
    }

    public static List<Contact> getSdcardContacts() {
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"contact_id", "display_name", "data1"}, null, null, null);
        if (localCursor != null) ;
        while (true) {
            if (!(localCursor.moveToNext())) {
                localCursor.close();
                return localArrayList;
            }
            Contact localContact = new Contact();
            String str2 = localCursor.getString(1);
            String str1 = localCursor.getString(2);
            localContact.setName(str2);
            localContact.setPhoneNum(str1);
            localArrayList.add(localContact);
        }
    }

    public static List<Contact> getSimContacts() {
        Object localObject = Uri.parse("content://icc/adn");
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = mContext.getContentResolver().query((Uri) localObject, new String[]{"contact_id", "display_name", "data1"}, null, null, null);
        if (localCursor != null) ;
        while (true) {
            if (!(localCursor.moveToNext())) {
                localCursor.close();
                return localArrayList;
            }
            Contact localContact = new Contact();
            localObject = localCursor.getString(1);
            String str = localCursor.getString(2);
            localContact.setName((String) localObject);
            localContact.setPhoneNum(str);
            localArrayList.add(localContact);
        }
    }

    public static void init(Context paramContext) {
        mContext = paramContext;
    }
}