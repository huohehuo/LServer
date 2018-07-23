package lins.lserver;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.yanzhenjie.andserver.website.WebSite;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lins.lserver.base.RequestLoginHandler;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv_nav_head)
    ImageView ivNavHead;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    Server mServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        AssetManager assetManager = getAssets();
        WebSite webSite = new AssetsWebsite(assetManager, "");


        mServer = AndServer.serverBuilder()
                // 服务器设置部分：
//                .sslContext()                   // 设置SSLConext，加载SSL证书。
//                .sslSocketInitializer()         // 对SSLServerSocket进行一些初始化设置。
//                .inetAddress()                  // 设置服务器要监听的网络地址。
                .port(8080)                         // 设置服务器要监听的端口。
                .timeout(1000, TimeUnit.SECONDS)                      // Socket的超时时间。
                .listener(mListener)                     // 服务器监听。
                // Web框架设置部分：
//                .interceptor()                  // Request/Response对的拦截器。
                .website(webSite)                    // 设置网站。
                .registerHandler("login",new RequestLoginHandler())              // 注册一个Http Api路径和对应的处理器。
//                .filter()                     // RequestHandler的过滤器。
//                .exceptionResolver()            // 异常解决者。
                .build();

        mServer.startup();

    }


    private Server.ServerListener mListener = new Server.ServerListener() {
        @Override
        public void onStarted() {
            Log.d("server", "server start");
        }


        @Override
        public void onStopped() {
            Log.d("server", "server stop");
        }


        @Override
        public void onError(Exception e) {
            Log.d("server", "server error");
        }
    };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.tv, R.id.iv_nav_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.iv_nav_head:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServer.shutdown();
    }

    private void getPermisssion() {
        String[] perm = {
                android.Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if(!EasyPermissions.hasPermissions(this,perm)){
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perm);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i("permisssion", "获取成功的权限" + perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i("permisssion", "获取失败的权限" + perms);
    }
}
