package com.cookandroid.practice1.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookandroid.practice1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GitHubUsersFragment extends Fragment {


    public GitHubUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_git_hub_users, container, false);
    }

}
