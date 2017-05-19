package com.example.tmnt.newcomputer;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tmnt.newcomputer.Activity.BaseActivity;
import com.example.tmnt.newcomputer.Activity.LoginActivity;
import com.example.tmnt.newcomputer.Activity.ShowUseActivity;
import com.example.tmnt.newcomputer.Adapter.HomeAdapter;
import com.example.tmnt.newcomputer.BMOB.BmobUtils;
import com.example.tmnt.newcomputer.DAO.QuestionDAO;
import com.example.tmnt.newcomputer.Widget.CircleImageView;
import com.example.tmnt.newcomputer.Fragment.HomeFragment;
import com.example.tmnt.newcomputer.Fragment.SortFragment;
import com.example.tmnt.newcomputer.InterFace.IMPL.MaxIdIMPL;
import com.example.tmnt.newcomputer.InterFace.IMPL.ShowIcon;
import com.example.tmnt.newcomputer.InterFace.IMaxId;
import com.example.tmnt.newcomputer.InterFace.OnClickShowIcon;
import com.example.tmnt.newcomputer.Model.AnotherAnswer;
import com.example.tmnt.newcomputer.Service.StartOpService;
import com.example.tmnt.newcomputer.Utils.ImageUtils;
import com.example.tmnt.newcomputer.Utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseActivity implements OnMenuItemClickListener
        , OnMenuItemLongClickListener, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbarIcon)
    CircleImageView mToolbarIcon;
    @Bind(R.id.tab)
    PagerBottomTabLayout mTab;
    @Bind(R.id.id_content)
    FrameLayout mIdContent;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.home_username)
    TextView mHomeUsername;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.exitLogin)
    ImageView mExitLogin;

    private boolean a1, a2, a3;

    private NavigationView navigationView;

    private AlertDialog mAlertDialog;

    private boolean isLoad = false;
    private HomeAdapter mHomeAdapter;

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

    public static final int RESULT_IMAGE = 100;
    public static final int RESULT_CAMERA = 200;

    private int count;
    private int max;
    Intent startService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(
                Window.FEATURE_CONTENT_TRANSITIONS);
        startService = new Intent(MainActivity.this, StartOpService.class);
        super.onCreate(savedInstanceState);

        stopService(startService);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.a1_2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        // actionBar.setHomeAsUpIndicator(R.drawable.fbn);

        mDAO = QuestionDAO.getInstance(getApplicationContext());

        username = mDAO.queryLoginUsername();
        fragmentManager = getSupportFragmentManager();

        // initMenuFragment();
        //showConvenientBanner();

        if (Utils.isWifiConnected(getApplicationContext())) {
            BmobUtils.maxIdToBmob(getApplicationContext());
            MaxIdIMPL.setMaxId(new IMaxId() {
                @Override
                public void maxId(int id) {
                    max = id;
                }
            });


        }

        //mNavView.setNavigationItemSelectedListener(this);

        setDefaultFragment();
        showNavigationBar();
    }

    @Override
    public void setEnterAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Fade fade = new Fade();
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
            getWindow().setReturnTransition(fade);
        }
    }

    @Override
    public void setExitAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setExitTransition(explode);

            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setReturnTransition(fade);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getY() - y > 0 && (event.getY() - y > 150 || event.getY() - y == 150)) {
                //HomeListViewHolder.convenientBanner.startTurning(1500);
            } else if (event.getY() - y < 0 && (event.getY() - y < -150 || event.getY() - y == -150)) {
                // HomeListViewHolder.convenientBanner.stopTurning();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 显示默认fragment
     */
    private void setDefaultFragment() {
       if (Utils.isConnected(MainActivity.this)){
           FragmentManager manager = getSupportFragmentManager();
           FragmentTransaction transaction = manager.beginTransaction();
           transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
           HomeFragment homeFragment = HomeFragment.getIntance(isLoad);
           transaction.add(R.id.id_content, homeFragment);
           transaction.commit();
       }else {
           Snackbar.make(mTab, "Connected wrong", Snackbar.LENGTH_LONG).show();
       }
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
                .setText("分类")
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
                if (Utils.isConnected(MainActivity.this)) {
                    switch (index) {
                        case 0:
                            isUser = false;
                            a1 = true;
                            a2 = false;
                            a3 = false;
                            HomeFragment homeFragment = HomeFragment.getIntance(isLoad);
                            transaction.replace(R.id.id_content, homeFragment);
                            transaction.commitAllowingStateLoss();
                            break;
                        case 1:
                            isUser = false;
                            a1 = false;
                            a2 = true;
                            a3 = false;
                            transaction.replace(R.id.id_content, new SortFragment());
                            transaction.commitAllowingStateLoss();
                            break;
                        case 2:
                            a1 = false;
                            a2 = false;
                            a3 = true;
                            isUser = true;
                            Intent intent = new Intent(MainActivity.this, ShowUseActivity.class);
                            intent.putExtra("user", username);
                            ActivityOptions transitionActivityOptions = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                                startActivity(intent, transitionActivityOptions.toBundle());

                            } else {
                                startActivity(intent);
                            }
                            break;
                    }
                } else {
                    Snackbar.make(mTab, "Connected wrong", Snackbar.LENGTH_LONG).show();
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

        //mHomeAdapter = new HomeAdapter();

        if (Utils.isWifiConnected(getApplicationContext())) {
            if (mDAO.queryAll().size() - 199 == 0) {
                BmobUtils.getyAnotherAnswer(getApplicationContext(), true, 0, "isLoad");
                isLoad = true;

                BmobUtils.setDataResult(new BmobUtils.DataResult() {
                    @Override
                    public void getQuestionData(List<AnotherAnswer> l) {
                        count = l.size();
                        for (AnotherAnswer answer : l) {
                            if (answer.getKind().equals("fillBlank")) {
                                mDAO.addFillQuestion(answer, mDAO.queryAll().size() + 1, 3, answer.getQuestionId());
                            } else {
                                mDAO.addSelectQuestion(answer, mDAO.queryAll().size() + 1, answer.getQuestionId());
                            }
                        }

                    }
                });
            }
        }

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

    @Override
    protected void onPause() {
        super.onPause();
        isLoad = false;
    }

    /**
     * 显示头像
     */
    public void showIcon() {
        mToolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAlertDialog = Utils.shoeDialog(MainActivity.this, username, mDAO);
                mAlertDialog.show();

                //mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });


        mHomeUsername.setText(mDAO.queryLoginUsername());
        if (mDAO.queryUserIcon(username)) {
            mToolbarIcon.setImageBitmap(ImageUtils.readBitMap(MainActivity.this, mDAO.queryUserIconPath(username)));

        } else {
            mToolbarIcon.setImageResource(R.drawable.image);
        }

        mExitLogin.setOnClickListener(v -> {
            mDAO.updateUserLogin(mDAO.queryLoginUsername(), false);
            mDAO.updateLogin(false);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            SharedPreferences.Editor editor = getSharedPreferences("exit", MODE_PRIVATE).edit();
            editor.putString("exitValue", "exit").commit();
            startActivity(intent);
            finish();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        showIcon();
        //getNavigation();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        if (a3 || a1) {
            controller.setSelect(0);
        } else if (a2) {
            controller.setSelect(1);
        }


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

        MenuObject send = new MenuObject("退出");
        send.setResource(R.drawable.icon_setup_normal);

        menuObjects.add(close);
        menuObjects.add(send);

        return menuObjects;

    }


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
        if (position == 1) {
            mDAO.updateUserLogin(mDAO.queryLoginUsername(), false);
            mDAO.updateLogin(false);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            SharedPreferences.Editor editor = getSharedPreferences("exit", MODE_PRIVATE).edit();
            editor.putString("exitValue", "exit").commit();
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.context_menu) {
//            mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_IMAGE && data != null) {
                String s = ImageUtils.getImageFromGallery(MainActivity.this, data);
                Log.i(TAG, "onActivityResult: " + s);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(startService);
    }

    /**
     * 打开抽屉
     */
//    public void getNavigation() {
//        //Log.i(TAG, "getNavigation: start");
//        View view = mNavView.getHeaderView(0);//获取头View
//        //navigationView.inflateHeaderView(R.layout.nav_header_main);//加载头布局
//        TextView username1 = (TextView) view.findViewById(R.id.username_main_navigation_list);
//        ImageView userIcon = (ImageView) view.findViewById(R.id.userIcon);
//        ImageView exiteLogin = (ImageView) view.findViewById(R.id.exitLogin);
//        //userIcon.setImageResource(R.drawable.a1_2);
//        if (!mDAO.queryUserIcon(mDAO.queryLoginUsername())) {
//            userIcon.setImageResource(R.drawable.image);
//        } else {
//            userIcon.setImageBitmap(ImageUtils.readBitMap(getApplicationContext(), mDAO.queryUserIconPath(username)));
//        }
//
//        userIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDrawerLayout.closeDrawers();
//                mAlertDialog = Utils.shoeDialog(MainActivity.this, username, mDAO);
//                mAlertDialog.show();
//            }
//        });
//
//        //Log.i(TAG, "getNavigation: " + username);
//        username1.setText("hello " + mDAO.queryLoginUsername());
//
//        exiteLogin.setOnClickListener((v -> {
//            mDAO.updateUserLogin(mDAO.queryLoginUsername(), false);
//            mDAO.updateLogin(false);
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            SharedPreferences.Editor editor = getSharedPreferences("exit", MODE_PRIVATE).edit();
//            editor.putString("exitValue", "exit").commit();
//            startActivity(intent);
//            finish();
//        }));
//    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
