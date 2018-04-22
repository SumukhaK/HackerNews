package com.omnify.sumukha.hackernews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.omnify.sumukha.hackernews.Models.ArticleComments;
import com.omnify.sumukha.hackernews.Models.ArticleModel;
import com.omnify.sumukha.hackernews.R;

import java.util.ArrayList;
import java.util.List;

public class SingleStoryPagerFragment extends Fragment {


    View storyView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView articleTitleTv,articleUrlTv,articleTimeTv;
    public static ArticleModel seletedArticle;
    public static ArrayList<ArticleComments> articleComments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        storyView = inflater.inflate(R.layout.single_story_fragment,container,false);
        viewPager =  storyView.findViewById(R.id.viewpager);
        articleTitleTv = storyView.findViewById(R.id.articletitle_textview);
        articleUrlTv = storyView.findViewById(R.id.articleurl_textview);
        articleTimeTv = storyView.findViewById(R.id.articledate_textview);

        setupViewPager(viewPager);

        tabLayout = storyView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        articleTitleTv.setText(seletedArticle.getArticleTitle());
        if(seletedArticle.isHasURL()) {
            articleUrlTv.setText(seletedArticle.getArticleURL());
        }
        java.util.Date time=new java.util.Date((long)seletedArticle.getArticleSubmissionTime()*1000);
        articleTimeTv.setText(String.valueOf(time)+"  \u2022 "+seletedArticle.getArticleBy());

        return storyView;
    }



    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new ArticleCommentsFragment(), articleComments.size()+" Comments");
        adapter.addFragment(new ArticleWebviewFragment(), "Article");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
