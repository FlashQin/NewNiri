package com.flashqin.niri.utlis;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

import com.flashqin.niri.net.Url;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vondear.rxtool.RxActivityTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Lzy On 2019/8/19
 * Describe:
 */
public class CommonUtils {



    public static boolean isFinishActivity = false;
    public static String orderRoomId = "";
    public static String ShareCode = "";
    public static String paymoney = "";
    public static String hotleId = "";
    public static String userPhone = "";
    public static int whichType = 0;
    public static String houseId = "";
    public static String roomId = "";
    public static String yaData = "";
    public static String roomUrl = "";
    public static String startTime = "";
    public static String endTime = "";
    public static String liveDays = "";
    public static String personListBeans = "";
    public static String oderNo = "";
    public static String oderPic = "";
    private static final String SD_APP_DIR_NAME = "TestDir"; //?????????????????????SD???????????????????????????
    private static final String PHOTO_DIR_NAME = "photo";    //?????????????????????????????????????????????
    private static long mBackPressedTime;
    public static String wxphone = "";
    public static int WXCODE = 9;
    public static int pingtype = 9;
    public static String code = "";
    private static int HOUR = 1000 * 60 * 60;
    /**
     * ??????key
     */
    public static final String APP_ID_QQ = "101880481";
    public static final String APP_ID_WX = "wxfe42ff9a68f0c86b";
    public static final String WEI_SECRET = "93aa20048e97038e09e93634573a3eba";
    public static final String QQAPP_ID_WX = "101880481";


    public static String checkImgUrl(String url) {
        return url.startsWith("http") ? url : Url.baseUrl + url;
    }

    public static String GetTimeFromMills(long mills) {
        int d = Integer.parseInt(String.valueOf(mills / (HOUR * 24)));
        int h = Integer.parseInt(String.valueOf((mills % (HOUR * 24)) / (HOUR)));
        int m = Integer.parseInt(String.valueOf((mills % (HOUR)) / (1000 * 60)));
        int s = Integer.parseInt(String.valueOf((mills % (1000 * 60)) / 1000));
        String f_h = String.valueOf(h).length() == 1 ? "0" + h : String.valueOf(h);
        String f_m = String.valueOf(m).length() == 1 ? "0" + m : String.valueOf(m);
        String f_s = String.valueOf(s).length() == 1 ? "0" + s : String.valueOf(s);
        return d == 0 ? f_h + ":" + f_m + ":" + f_s : d + ":" + f_h + ":" + f_m + ":" + f_s;
    }

    //??????????????????
    public static void enterListener(EditText editText, onChangeText onChangeText) {
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editText.getText().length() == 0) return false;
                String trim_his = editText.getText().toString().trim();
                onChangeText.onChangeText(trim_his);
                editText.setText("");
                return true;
            }
            return false;
        });
    }
    public static String getDeviceID() {
        String deviceID = "";
        try {
            //??????13???  ????????????????????????????????????????????????
            deviceID = "" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                    Build.USER.length() % 10;
        } catch (Exception e) {
            return "";
        }
        return deviceID;
    }
    public static void doubleClickExitApp() {
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            RxActivityTool.finishAllActivity();
            //????????????ID??????????????????
            android.os.Process.killProcess(android.os.Process.myPid());
            //??????????????????
            System.exit(0);
        } else {
            mBackPressedTime = curTime;
            ToastUtils.showShort("Exit succse");
        }
    }

    /**
     * ???raw??????????????????
     *
     * @param rawName R.raw.jx
     * @return
     */
    public static String readFileFromRaw(int rawName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(Utils.getApp().getResources().openRawResource(rawName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = Utils.getApp().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static String getVersionName(Context context) {
        // ??????packagemanager?????????
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()????????????????????????
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    /**
     * ????????????????????????
     *
     * @param context ?????????
     * @param content ?????????????????????????????????
     */
    public static void copyToClipboard(Context context, CharSequence content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(null, content));//???????????????????????????????????????????????????????????????????????????
            if (clipboard.hasPrimaryClip()) {
                clipboard.getPrimaryClip().getItemAt(0).getText();
            }
        }
    }

    public static void saveImageToGallery(FragmentActivity fragmentActivity, Bitmap bmp) {
        RxPermissions rxPermissions = new RxPermissions(fragmentActivity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i("tag", "checkPermission22--:" + aBoolean);
                        if (aBoolean) {
                            // ??????????????????
                            File appDir = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera");
                            if (!appDir.exists()) {
                                appDir.mkdir();
                            }
                            String fileName = System.currentTimeMillis() + ".jpg";
                            File file = new File(appDir, fileName);
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.flush();
                                fos.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            displayToGallery(fragmentActivity, file);
                        } else {
                            ToastUtils.showShort("????????????????????????????????????????????????");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * ?????????????????????
     *
     * @param context   ?????????
     * @param photoFile ???????????????
     */
    public static void displayToGallery(Context context, File photoFile) {
        if (photoFile == null || !photoFile.exists()) {
            return;
        }
        String photoPath = photoFile.getAbsolutePath();
        String photoName = photoFile.getName();
        // ??????????????????????????????
        try {
            ContentResolver contentResolver = context.getContentResolver();
            MediaStore.Images.Media.insertImage(contentResolver, photoPath, photoName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // ????????????????????????
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + photoPath)));
    }

    /**
     * ????????????
     */
    public static void GotoWX(Context context) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }

    /**
     * ???Base64???????????????????????????bitmap
     *
     * @param bmMsg ??????
     * @return bitmap
     */
    public static Bitmap SaveImage(String bmMsg) {
        byte[] input = Base64.decode(bmMsg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(input, 0, input.length);
    }

    public static File saveBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/temp/" + bitName + ".png");
        try {
            f.getParentFile().mkdirs();
            f.createNewFile();
        } catch (IOException e) {
            ToastUtils.showShort("???????????????????????????" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * ????????????app?????????
     *
     * @param context ?????????
     * @return ?????????
     */
    public static String getApplicationMetadata(Context context) {
        ApplicationInfo info = null;
        try {
            PackageManager pm = context.getPackageManager();
            info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return String.valueOf(info.metaData.getString("CHANNEL_VALUE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ?????????????????????????????????
     *
     * @param pid ?????????
     * @return ?????????
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static File saveImg(String filePath, String type) {
        String destPath = Utils.getApp().getExternalFilesDir("tempImg") + File.separator + type + ".png";
        File file = new File(destPath);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    /**
     * ??????cache??????
     *
     * @return
     */
    public static String getDiskCachePath() {

        Context context = Utils.getApp();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            String path = "";
            path = context.getExternalCacheDir().getPath();
            path = path.replace("/cache", "");
            System.out.println("s fsbsdb" + context.getExternalCacheDir().getPath());
            return path;
        } else {
            String path = "";
            path = context.getCacheDir().getPath();
            path = path.replace("/cache", "");
            System.out.println("s fsbsdb" + context.getCacheDir().getPath());
            return path;
        }
    }

    public static File uriToFile(Context context, Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2??????
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
            return new File(path);
        } else {
            Log.i("tag", "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

    /**
     * ??????Uri??????????????????
     *
     * @param pUri
     * @return
     */
    public static String getPathByUri(Uri pUri) {
//      pUri.getPath()
//      ??????????????????  /mq_external_cache/storage/emulated/0/Pictures/JPEG_20190326_225011.jpg
//      ???????????????????????????  /external/images/media/52325
        String _Path = pUri.getPath();

        if (_Path.endsWith(".jpg")) {
            System.out.println("path-->" + subPath(_Path));
            return subPath(_Path);
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = Utils.getApp().getContentResolver().query(pUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    private static String subPath(String pPath) {
        String[] array = pPath.split("/");
        return pPath.substring(array[1].length() + 1);
    }

    public static String createPathIfNotExist() {
        boolean sdExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        if (!sdExist) {
            Log.e("path", "SD????????????");
            return null;
        }
        String _AbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME;
        System.out.println("dbDir->" + _AbsolutePath);
        File dirFile = new File(_AbsolutePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                Log.e("path", "?????????????????????");
                return null;
            }
        }
        return _AbsolutePath;
    }

    /**
     * ?????????????????????base64???????????????
     *
     * @param imgurl base64
     * @return true ??? false ??????
     */
    public static boolean isBase64Img(String imgurl) {
        return !TextUtils.isEmpty(imgurl) && (imgurl.startsWith("data:image/png;base64,")
                || imgurl.startsWith("data:image/*;base64,") || imgurl.startsWith("data:image/jpg;base64,")
        );
    }

    private void CheckPermission(FragmentActivity fragmentActivity) {

    }

    public interface onChangeText {
        void onChangeText(String text);
    }
}
