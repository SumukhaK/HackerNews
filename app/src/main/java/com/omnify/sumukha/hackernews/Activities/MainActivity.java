package com.omnify.sumukha.hackernews.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.omnify.sumukha.hackernews.Adapters.TopItemsAdapter;
import com.omnify.sumukha.hackernews.Fragments.ArticleCommentsFragment;
import com.omnify.sumukha.hackernews.Fragments.ArticleWebviewFragment;
import com.omnify.sumukha.hackernews.Fragments.SingleStoryPagerFragment;
import com.omnify.sumukha.hackernews.HackerNews;
import com.omnify.sumukha.hackernews.Interfaces.AllCommentsRetrieveListner;
import com.omnify.sumukha.hackernews.Interfaces.AllContentsListner;
import com.omnify.sumukha.hackernews.Interfaces.StoryChoosenListner;
import com.omnify.sumukha.hackernews.Interfaces.TopItemsListner;
import com.omnify.sumukha.hackernews.Models.ArticleComments;
import com.omnify.sumukha.hackernews.Models.ArticleModel;
import com.omnify.sumukha.hackernews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TopItemsListner,AllContentsListner,StoryChoosenListner,AllCommentsRetrieveListner {

    RecyclerView topStoriesList;
    ProgressDialog progressDialog;
    ArrayList<ArticleModel> articleModels= new ArrayList<>();
    ArrayList<ArticleComments> comments = new ArrayList<>();
    Integer[] topItemIds;
    Integer[] contentsIds;
    Integer[] commentsIds;
    TopItemsAdapter topItemsAdapter;
    ArticleModel articleChoosen;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topStoriesList = findViewById(R.id.topstories_reclist);
        topStoriesList.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        LoadTopItems topItems = new LoadTopItems(topItemIds,MainActivity.this,this);
        topItems.execute();


    }


    @Override
    public void topItems(JSONArray response) {
        //System.out.println("******##### intfce response "+response);
        //topItemIds = new Integer[response.length()];
        topItemIds = new Integer[20];
        for(int i=0;i<20;i++){

            try {
                topItemIds[i] = response.getInt(i);
                //System.out.println("**** top response item at "+i+" is "+topItemIds[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        LoadContents loadComments = new LoadContents(MainActivity.this,MainActivity.this,topItemIds);
        loadComments.execute();

    }

    @Override
    public void allContents(JSONObject response) {

        ArticleModel articleModel = new ArticleModel();

        try {
            articleModel.setArticleTitle(response.getString("title"));
            articleModel.setArticleBy(response.getString("by"));
            articleModel.setArticleScore(response.getInt("score"));
            articleModel.setArticleSubmissionTime(response.getInt("time"));

            if(response.has("kids")){
                articleModel.setHasKids(true);
                JSONArray arrJson = response.getJSONArray("kids");
                contentsIds = new Integer[arrJson.length()];
                articleModel.setTotalNoOfComments(arrJson.length());
                //System.out.println("my kids length "+arrJson.length());
                for(int i = 0; i < arrJson.length(); i++) {
                    contentsIds[i] = arrJson.getInt(i);
                    //System.out.println("my kid at  "+i+" is "+contentsIds[i]);
                }
                articleModel.setAllCommentsIds(contentsIds);
            }else{
                articleModel.setHasKids(false);
            }
            if(response.has("url")) {
                articleModel.setHasURL(true);
                //System.out.println("its true "+response.getString("url"));
                articleModel.setArticleURL(response.getString("url"));
            }else{
                articleModel.setHasURL(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        articleModels.add(articleModel);
        //System.out.println("all top stories "+articleModels.size()+" "+articleModels.toString());
        if(articleModels.size() == 20) {
            topItemsAdapter = new TopItemsAdapter(MainActivity.this, articleModels, MainActivity.this);
            topStoriesList.setAdapter(topItemsAdapter);
        }
    }

    @Override
    public void storyChoosen(ArticleModel articleModel) {

        //Toast.makeText(MainActivity.this,"article title which i choose "+articleModel.getArticleTitle()+" "+articleModel.getAllCommentsIds().length,Toast.LENGTH_LONG).show();
        articleChoosen = articleModel;
        commentsIds = new Integer[articleModel.getAllCommentsIds().length];
        commentsIds = articleModel.getAllCommentsIds();
        comments = new ArrayList<>();
        LoadAllComments loadAllComments = new LoadAllComments(MainActivity.this,MainActivity.this,commentsIds);
        loadAllComments.execute();



    }

    @Override
    public void getAllComments(JSONObject response) {

        System.out.println("All the comments i could get "+response.toString());
        ArticleComments articleComments = new ArticleComments();

        try {
            articleComments.setCommentBy(response.getString("by"));
            articleComments.setCommentId(response.getInt("id"));
            articleComments.setCommentSubmissionTime(response.getInt("time"));
            articleComments.setCommentParent(response.getInt("parent"));
            articleComments.setCommentText(response.getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        comments.add(articleComments);
        if(comments.size() == commentsIds.length) {
            //Toast.makeText(MainActivity.this,"We have all the comments..",Toast.LENGTH_LONG).show();
            if(articleChoosen.isHasURL()){
                ArticleWebviewFragment.articleHasURL = true;
                ArticleWebviewFragment.articleURL = articleChoosen.getArticleURL();
                ArticleCommentsFragment.articleComments = comments;
                SingleStoryPagerFragment.articleComments = comments;
                SingleStoryPagerFragment.seletedArticle = articleChoosen;
            }
            Fragment fragment = new SingleStoryPagerFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.addToBackStack(fragment.getTag());
            fragmentTransaction.commit();
        }
    }


    public class LoadTopItems extends AsyncTask<String,Void , Integer[]> {

        Integer[] topItemIds;
        Context context;
        TopItemsListner topItemsListner;


        public LoadTopItems(Integer[] topItemIds, Context context,TopItemsListner listner) {
            this.topItemIds = topItemIds;
            this.context = context;
            this.topItemsListner = listner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            topItemIds = new Integer[20];
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Integer[] integers) {
            super.onPostExecute(integers);
            progressDialog.hide();
            /*for(int i=0;i<integers.length;i++){
                System.out.println("******* topItemIds at "+i+" is "+integers[i]);
            }*/
        }

        @Override
        protected Integer[] doInBackground(String... strings) {
            String url = "https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty";
            String  REQUEST_TAG = "volleyJsonArrayRequest";

            JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //outputTextView.setText(response.toString());

                            topItemsListner.topItems(response);
                            //System.out.println("******######## response array "+response.toString());

                            for(int i=0; i<20; i++){
                                try {
                                    topItemIds[i] = response.getInt(i);
                                    //System.out.println("*********###### top"+ i +" item "+topItemIds[i]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());


                }
            });

            // Adding JsonObject request to request queue
            HackerNews.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
            return topItemIds;
        }
    }

    public class LoadContents extends AsyncTask<Void,Void,Void>{


        Context context;
        AllContentsListner commentsListner;
        Integer[] itemIds;

        public LoadContents(Context context, AllContentsListner commentsListner, Integer[] itemIds) {
            this.context = context;
            this.commentsListner = commentsListner;
            this.itemIds = itemIds;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String  REQUEST_TAG = "volleyJsonObjectRequest";
            String  REQUEST_URL = "https://hacker-news.firebaseio.com/v0/item/";

            //System.out.println("******######## req itemIds "+itemIds.length);
            for(int i=0;i<itemIds.length;i++){

                String url = REQUEST_URL+String.valueOf(itemIds[i])+".json";

                JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                commentsListner.allContents(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        progressDialog.hide();
                    }
                });
                // Adding JsonObject request to request queue
                HackerNews.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
            }


            return null;
        }
    }

    public class LoadAllComments extends AsyncTask<Void, Void, Void>{

        Context context;
        AllCommentsRetrieveListner retrieveListner;
        Integer[] itemIds;

        public LoadAllComments(Context context, AllCommentsRetrieveListner retrieveListner, Integer[] itemIds) {
            this.context = context;
            this.retrieveListner = retrieveListner;
            this.itemIds = itemIds;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String  REQUEST_TAG = "volleyJsonObjectRequest";
            String  REQUEST_URL = "https://hacker-news.firebaseio.com/v0/item/";

            //System.out.println("******######## req itemIds "+itemIds.length);
            for(int i=0;i<itemIds.length;i++){

                String url = REQUEST_URL+String.valueOf(itemIds[i])+".json";

                JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                retrieveListner.getAllComments(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        progressDialog.hide();
                    }
                });
                // Adding JsonObject request to request queue
                HackerNews.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
            }

            return null;
        }
    }

}
