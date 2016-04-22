package com.example.andrewsamir.movieapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewsamir.movieapp.Adapters.DBhelper;
import com.example.andrewsamir.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    private static final String ARG_TITLE = "TITLE";
    private static final String ARG_IMAGEPATH = "IMAGEPATH";
    private static final String ARG_RELASEDATE = "RELASEDATE";
    private static final String ARG_OVERVIEW = "OVERVIEW";
    private static final String ARG_RATE = "RATE";
    private static final String ARG_ID = "ID";
    private static final String ARG_NAMES = "NAMES";
    private static final String ARG_KEYS = "KEYS";

    DBhelper myDB;

    ImageView imageView;
    TextView title;
    TextView relasedate;
    TextView overview;
    TextView rating;
    CheckBox Star;
    Button button;
    LinearLayout linearLayout;
    ArrayList<String> KEYS;


    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment getinstance(String title, String relasedate, String overview, String imagepath,
                                              Double rate, int id, ArrayList<String> names, ArrayList<String> keys) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        bundle.putString(ARG_RELASEDATE, relasedate);
        bundle.putString(ARG_OVERVIEW, overview);
        bundle.putString(ARG_IMAGEPATH, imagepath);
        bundle.putDouble(ARG_RATE, rate);
        bundle.putStringArrayList(ARG_NAMES, names);
        bundle.putStringArrayList(ARG_KEYS, keys);
        bundle.putInt(ARG_ID, id);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        return detailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_details, container, false);

        myDB = new DBhelper(getActivity());

        linearLayout = (LinearLayout) view.findViewById(R.id.linVideos);
        imageView = (ImageView) view.findViewById(R.id.imageViewShowImage_);
        title = (TextView) view.findViewById(R.id.textViewShowTitle_);
        relasedate = (TextView) view.findViewById(R.id.textViewRelaseDate_);
        overview = (TextView) view.findViewById(R.id.textViewShowOverview_);
        rating = (TextView) view.findViewById(R.id.textViewRating_);
        Star = (CheckBox) view.findViewById(R.id.StarID_);


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        int i = 0;
        ArrayList<String> da = new ArrayList<>();
        KEYS = new ArrayList<>();
        int num = getArguments().getStringArrayList(ARG_NAMES).size();
        for (int x = 0; x < num; x++) {
            da.add(getArguments().getStringArrayList(ARG_NAMES).get(x));
            KEYS.add(getArguments().getStringArrayList(ARG_KEYS).get(x));
        }
        for (String s : da) {

            button = new Button(getActivity());
            button.setId(i);
            button.setOnClickListener(gotoCLICK(button));
            i++;
            button.setText(s);
            linearLayout.addView(button);

        }


        title.setText(getArguments().getString(ARG_TITLE));
        relasedate.setText(getArguments().getString(ARG_RELASEDATE));
        overview.setText(getArguments().getString(ARG_OVERVIEW));
        rating.setText(getArguments().getDouble(ARG_RATE) + "/10");

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);


        Star.setChecked(sharedPreferences.getBoolean("" + getArguments().getInt(ARG_ID), false));


        Star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    editor.putBoolean("" + getArguments().getInt(ARG_ID), true);

                    boolean bb = myDB.ADD(getArguments().getInt(ARG_ID),
                            getArguments().getString(ARG_TITLE),
                            getArguments().getString(ARG_RELASEDATE),
                            getArguments().getString(ARG_IMAGEPATH),
                            getArguments().getString(ARG_OVERVIEW),
                            getArguments().getString(ARG_RATE));
                    if (bb)
                        ;
                } else {
                    editor.putBoolean("" + getArguments().getInt(ARG_ID), false);
                    myDB.deletemovie(getArguments().getInt(ARG_ID));
                }
                editor.commit();
            }
        });


        Context c = getActivity().getApplicationContext();


        Picasso.with(c).load("http://image.tmdb.org/t/p/w185//" + getArguments().getString(ARG_IMAGEPATH)).into(imageView);


    }

    View.OnClickListener gotoCLICK(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), button.getId() + "", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="
                        + KEYS.get(button.getId()))));

            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
}
