package com.chrosatech.ontime;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


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

    private String day;
    ListView listView;

    public UniversalFragment() {
        // Required empty public constructor
        day="NULL";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        Bundle bundle = this.getArguments();
        day = bundle.getString("day");
        Log.d("DAY", day);
        fill(day);

        return view;
    }

    private void fill(String title){

        DatabaseContents dbContents = new DatabaseContents(getActivity());
        Cursor cursor = dbContents.getCursor(title);
        CustomCursorAdapter adapter = new CustomCursorAdapter(getActivity(), cursor, false);
        listView.setAdapter(adapter);

    }

}
