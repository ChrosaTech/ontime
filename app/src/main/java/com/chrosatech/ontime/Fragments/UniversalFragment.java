package com.chrosatech.ontime.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chrosatech.ontime.Adapters.CustomCursorAdapter;
import com.chrosatech.ontime.Database.DatabaseContents;
import com.chrosatech.ontime.Helper.Values;
import com.chrosatech.ontime.R;


/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UniversalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UniversalFragment#newInstance} factory method to
 * create an instance of this fragment.
*/
public class UniversalFragment extends Fragment {

    private ListView listView;
    private CustomCursorAdapter adapter;
    private int fragNumber;

    public UniversalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        Bundle bundle = this.getArguments();
        String day = bundle.getString("day");
        fill(day);
        fragNumber = Values.fragNumber++;

        return view;
    }

    @Override
    public void onResume() {
        if (Values.refreshData[fragNumber]) {
            adapter.notifyDataSetChanged();
            Values.refreshData[fragNumber] = false;
        }
        super.onResume();
    }

    private void fill(String title){

        DatabaseContents dbContents = new DatabaseContents(getActivity());
        Cursor cursor = dbContents.getCursor(title);
        adapter = new CustomCursorAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);

    }

}
