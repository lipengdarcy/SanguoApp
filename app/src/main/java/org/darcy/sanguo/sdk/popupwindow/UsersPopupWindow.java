package org.darcy.sanguo.sdk.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;

import java.util.ArrayList;

public class UsersPopupWindow extends PopupWindow {
    public static final int notHasUser = 70001;
    public static String[] userNames;
    private Handler handler;

    public UsersPopupWindow(Context paramContext, int paramInt1, int paramInt2, AdapterView.OnItemClickListener paramOnItemClickListener, Handler paramHandler) {
        super(paramContext);
        this.handler = paramHandler;
        getLoginusers(paramContext);
        ListView view = new ListView(paramContext);
        userNames = LoginUserSQLite.getInstance(paramContext).queryAllUserName();
        //view.setAdapter(new UsersLoingAdapter(this, userNames, paramContext, paramInt1, paramInt2));
        view.setOnItemClickListener(paramOnItemClickListener);
        setContentView(view);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setWidth(paramInt1);
        setHeight(-2);
    }

    private ArrayList<String> getLoginusers(Context paramContext) {
        ArrayList localArrayList = new ArrayList();
        LoginUserSQLite.getInstance(paramContext).queryAllUserName();
        return localArrayList;
    }
}