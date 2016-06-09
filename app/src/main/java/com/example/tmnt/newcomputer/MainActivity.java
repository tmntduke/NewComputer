package com.example.tmnt.newcomputer;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.tmnt.newcomputer.Activity.ShowUIconActivity;
import com.example.tmnt.newcomputer.Activity.ShowUseActivity;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.DView.CircleImageView;
import com.example.tmnt.newcomputer.Fragment.HomeFragment;
import com.example.tmnt.newcomputer.Fragment.MessageFragment;
import com.example.tmnt.newcomputer.Fragment.UserMessageFragment;
import com.example.tmnt.newcomputer.InterFace.IMPL.ShowIcon;
import com.example.tmnt.newcomputer.InterFace.OnClickShowIcon;
import com.example.tmnt.newcomputer.Utils.ChangeUIMode;
import com.example.tmnt.newcomputer.Utils.ImageUtils;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.example.tmnt.newcomputer.ViewHolder.MainViewHolder;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    @Bind(R.id.toolbarIcon)
    CircleImageView mToolbarIcon;
    //    @Bind(R.id.convenientBanner)
//    ConvenientBanner mConvenientBanner;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab)
    PagerBottomTabLayout mTab;

    private static final int RESULT_IMAGE = 100;
    private static final int RESULT_CAMERA = 200;

    private NavigationView navigationView;

    private AlertDialog mAlertDialog;

    private ContextMenuDialogFragment mMenuDialogFragment;

    private DrawerLayout drawer;

    private QuestionDAO mDAO;
    private boolean isExit;
    private List<Integer> mList;
    private FragmentManager fragmentManager;
    private String username;
    private static final String TAG = "MainActivity";
    private static boolean isUser;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(
                Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Utils.setTranslucentStatus(MainActivity.this, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);

        setContentView(R.layout.activity_main);
        showExit();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.a1_2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        // actionBar.setHomeAsUpIndicator(R.drawable.fbn);

        mDAO = new QuestionDAO(getApplication());

        username = mDAO.queryLoginUsername();
        fragmentManager = getSupportFragmentManager();

        initMenuFragment();
        //showConvenientBanner();

        setDefaultFragment();

        showNavigationBar();
        showIcon();

    }


    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        transaction.add(R.id.id_content, homeFragment);
        transaction.commit();
    }

    /**
     * 显示底部导航栏
     */
    private void showNavigationBar() {
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultColor(0xFFACACAC)
                .setSelectedColor(getResources().getColor(R.color.colorPrimary))
                .setDefaultIcon(R.drawable.kru)
                .setText("首页")
                .setTag("这是一个TAG")
                .build();

        TabItemBuilder tabItemBuilder1 = new TabItemBuilder(this).create()
                .setDefaultColor(0xFFACACAC)
                .setSelectedColor(getResources().getColor(R.color.colorPrimary))
                .setDefaultIcon(R.drawable.lfl)
                .setText("标题")
                .setTag("这是一个TAG")
                .build();

        TabItemBuilder tabItemBuilder2 = new TabItemBuilder(this).create()
                .setDefaultColor(0xFFACACAC)
                .setSelectedColor(getResources().getColor(R.color.colorPrimary))
                .setDefaultIcon(R.drawable.lsv)
                .setText("我的")
                .setTag("这是一个TAG")
                .build();

        controller = mTab.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(tabItemBuilder1)
                .addTabItem(tabItemBuilder2)
                .build();

        controller.addTabItemClickListener(new OnTabItemSelectListener() {
            @Override
            public void onSelected(int index, Object tag) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
                switch (index) {
                    case 0:
                        isUser = false;
                        HomeFragment homeFragment = new HomeFragment();
                        transaction.replace(R.id.id_content, homeFragment);
                        transaction.commit();
                        break;
                    case 1:
                        isUser = false;
                        transaction.replace(R.id.id_content, new MessageFragment());
                        transaction.commit();
                        break;
                    case 2:
                        isUser = true;
                        Intent intent = new Intent(MainActivity.this, ShowUseActivity.class);
                        intent.putExtra("user", username);
                        ActivityOptions transitionActivityOptions = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                            startActivity(intent, transitionActivityOptions.toBundle());

                        } else {
                            startActivity(intent);
                        }
                        break;
                }


            }

            @Override
            public void onRepeatClick(int index, Object tag) {

            }
        });

    }

    private static String TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "image/jpg";

    @Override
    protected void onStart() {
        super.onStart();


        /**
         * 设置头像
         */

        Log.i(TAG, "onStart: " + isUser);


        /**
         * 进入相册或相机进行选择图片
         */
        ShowIcon.setOnClickShowIcon(new OnClickShowIcon() {
            @Override
            public void toGallery() {
                ImageUtils.toGallery(RESULT_IMAGE, MainActivity.this);
            }

            @Override
            public void toCamera() {
                ImageUtils.toCamera(MainActivity.this, TEMP_IMAGE_PATH, RESULT_CAMERA);
            }
        });
    }

    /**
     * 显示头像
     */
    public void showIcon() {


        mToolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUser) {
                    Intent intent = new Intent(MainActivity.this, ShowUIconActivity.class);
                    intent.putExtra(UserMessageFragment.PATH, mDAO.queryUserIconPath(username));
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, getString(R.string.share));
                        startActivity(intent, options.toBundle());
                    }
                } else {
                    mAlertDialog = Utils.shoeDialog(MainActivity.this, username, mDAO);
                    mAlertDialog.show();
                }
            }
        });

        if (mDAO.queryUserIcon(username)) {
            mToolbarIcon.setImageBitmap(ImageUtils.readBitMap(MainActivity.this, mDAO.queryUserIconPath(username)));

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        controller.setSelect(0);
        Log.i(TAG, "onResume: start");

        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }


    }

    /**
     * 初始化menu
     */
    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    /**
     * 获取menuItem
     *
     * @return
     */
    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("Setting");
        send.setResource(R.drawable.icon_setup_normal);

        MenuObject like = new MenuObject("about");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_3);
        like.setBitmap(b);


        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);

        return menuObjects;

    }

    /**
     * 初始化控件
     */
//    public void showConvenientBanner() {
//        mList = new ArrayList<>();
//        mList.add(R.drawable.zhuan_3);
//        mList.add(R.drawable.java);
//        mList.add(R.drawable.python);
//        mConvenientBanner.setPages(new CBViewHolderCreator() {
//            @Override
//            public Object createHolder() {
//                return new MainViewHolder();
//            }
//        }, mList)
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
//
//        MainViewHolder.setOnClickImageListener(new MainViewHolder.OnClickImageListener() {
//            @Override
//            public void itemClick(View v, int position) {
//                switch (position) {
//                    case 0:
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                }
//            }
//        });
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;

                Utils.showToast(MainActivity.this, "click is again");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return false;
            } else {
                //Log.i("onKeyDown", "start");
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.context_menu) {
            mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_IMAGE && data != null) {
                String s = ImageUtils.getImageFromGallery(MainActivity.this, data);
                if (!mDAO.queryUserIcon(username)) {
                    mDAO.addUserIcon(username, s);
                } else {
                    mDAO.updateUserIcon(username, s);
                }
                mDAO.updateUserIconFlag(username, true);
            } else if (requestCode == RESULT_CAMERA) {
                mDAO.addUserIcon(username, TEMP_IMAGE_PATH);
                mDAO.updateUserIconFlag(username, true);

            }
        }
    }

    public void showExit() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(2000);
            getWindow().setExitTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(2000);
            getWindow().setReturnTransition(fade);
        }

    }
}
