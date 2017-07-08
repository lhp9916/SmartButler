package lhp.com.smartbutler;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lhp.com.smartbutler.fragment.ButlerFragment;
import lhp.com.smartbutler.fragment.GirlFragment;
import lhp.com.smartbutler.fragment.UserFragment;
import lhp.com.smartbutler.fragment.WechatFragment;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.mViewPager)
    ViewPager mViewPager;

    //title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }

    private void initView() {
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("管家服务");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }
}
