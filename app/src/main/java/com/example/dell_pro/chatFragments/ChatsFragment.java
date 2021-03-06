package com.example.dell_pro.chatFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dell_pro.R;
import com.example.dell_pro.chatModel.Doctors;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatsFragment extends Fragment {

    private RecyclerView doc_recycler;
    private DatabaseReference mDoctorsDatabase;

    private ChatsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_chats, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        mDoctorsDatabase = FirebaseDatabase.getInstance().getReference().child("Chatlist").child(user.getUid());

        doc_recycler=inflate.findViewById(R.id.doc_recycler_view);
        doc_recycler.setHasFixedSize(true);
        doc_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Doctors> options= new FirebaseRecyclerOptions.Builder<Doctors>()
                .setQuery(mDoctorsDatabase,Doctors.class)
                .build();

        adapter = new ChatsAdapter(options,getContext());
        doc_recycler.setAdapter(adapter);

        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}