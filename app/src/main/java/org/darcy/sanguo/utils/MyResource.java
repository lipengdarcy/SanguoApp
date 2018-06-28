package org.darcy.sanguo.utils;

import android.content.Context;

import org.darcy.sanguo.client.GameAdmin;

//页面资源管理
public class MyResource {

    /**
     * 根据资源类型和名称获取资源的id
     *
     * @param type 资源类型
     * @param name 资源名称
     */
    public static int getIdByName(Context paramContext, String type, String name) {
        int i;
        String packageName = paramContext.getPackageName();
        int j = 0;
        try {
            StringBuilder localObject = new StringBuilder(String.valueOf(paramContext));
            Class[] classes = Class.forName("org.darcy.sanguo.R").getClasses();
            for (Class c : classes) {
                i = c.getField(name).getInt(paramContext);
                if (!c.getName().split("\\$")[1].equals(type))
                    continue;
                else
                    return i;
            }
        } catch (ClassNotFoundException e1) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e1.toString());
            e1.printStackTrace();
        } catch (IllegalArgumentException e2) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e2.toString());
            e2.printStackTrace();
        } catch (SecurityException e3) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e3.toString());
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e4.toString());
            e4.printStackTrace();
        } catch (NoSuchFieldException e5) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e5.toString());
            e5.printStackTrace();
        }
        return 0;
    }
}