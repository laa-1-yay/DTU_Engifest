package in.ac.dtu.engifest.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import in.ac.dtu.engifest.MainActivity;
import in.ac.dtu.engifest.R;
import in.ac.dtu.engifest.UpdateNews;

/**
 * Created by omerjerk on 19/12/13.
 */
public class NewsFragment extends Fragment {


    private static final String TAG = "NewsFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsFragment newInstance(int sectionNumber) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        CardListView newsList = (CardListView)rootView.findViewById(android.R.id.list);
        CardAdapter adapter = new CardAdapter(getActivity())
                // This sets the color displayed for card titles and header actions by default
                .setAccentColorRes(android.R.color.holo_blue_dark);

        adapter.add(new CardHeader("Header 1"));
        adapter.add(new Card("One", "Example 1"));
        adapter.add(new Card("Two", "Example 2"));
        adapter.add(new Card("Three", "Example 3"));
        // Add a header with a subtitle and action, along with 3 more cards below it
        adapter.add(new CardHeader("Header 2", "Subtitle 2").setAction("Hello", new CardHeader.ActionListener() {
            @Override
            public void onClick(CardHeader header) {
                Toast.makeText(getActivity(), header.getActionTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
        adapter.add(new Card("Four", "Example 4"));
        adapter.add(new Card("Five", "Example 5"));
        adapter.add(new Card("Six", "Example 6"));

        newsList.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
            inflater.inflate(R.menu.news, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private class ReadFromJSON extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... v) {

            String jsonString = "";

            try {
                //Log.d(TAG, getActivity().getFilesDir() + "data.json");
                File cacheFile = new File(getActivity().getFilesDir(), "data.json");

                BufferedReader br = new BufferedReader(new FileReader(cacheFile));
                jsonString = br.readLine();

            } catch (Exception e) {
                e.printStackTrace();
                new UpdateNews(getActivity()).execute();

            }

            ArrayList<String> newsList = new ArrayList<String>();

            //Log.d(TAG, "jsonString" + jsonString);
            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 1; i < jsonArray.length(); ++i) {
                    String newsItem = jsonArray.getString(i);
                    newsList.add(newsItem);
                    Log.d(TAG, "News = " + newsItem);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return newsList;
        }

        /* TODO:

            Show the news inside card-ui
         */

        protected void onPostExecute(ArrayList<String> newsList) {

        }
    }
}
