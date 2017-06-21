package sk.upjs.ics.folkjukebox.GUI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sk.upjs.ics.folkjukebox.R;

public class SongDetailFragment extends Fragment {

    private static final String ARG_SONG_LYRICS = "songLyrics";

    private TextView lyricsTextView;

    public SongDetailFragment() {
        // Required empty public constructor
    }

    public static SongDetailFragment newInstance(String songLyrics) {
        Bundle args = new Bundle();
        args.putString(ARG_SONG_LYRICS, songLyrics);

        SongDetailFragment songDetailFragment = new SongDetailFragment();
        songDetailFragment.setArguments(args);

        return songDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.lyricsTextView = (TextView) view.findViewById(R.id.lyricsTextView);
        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_SONG_LYRICS)) {
            this.lyricsTextView.setText(args.getString(ARG_SONG_LYRICS));
        }
    }

 /*

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
