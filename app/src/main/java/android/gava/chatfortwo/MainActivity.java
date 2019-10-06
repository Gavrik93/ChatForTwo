package android.gava.chatfortwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView messageListView;
    private ChatMassageAdapter adapter;
    private ProgressBar progressBar;
    private ImageButton sendImageButton;
    private Button sendMassageButton;
    private EditText messageEditText;

    private String userName;

    FirebaseDatabase database;
    DatabaseReference messageDatabaseReference;
    //DatabaseReference usersDatabaseReference;
    ChildEventListener messagesChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        messageDatabaseReference = database.getReference().child("messages");


        progressBar = findViewById(R.id.progressBar);
        sendImageButton = findViewById(R.id.sendPhotoButton);
        sendMassageButton = findViewById(R.id.sendMassageButton);
        messageEditText = findViewById(R.id.massageEditText);

        userName = "Default User";

        messageListView = findViewById(R.id.massageListView);
        List<ChatMassage> chatMassages = new ArrayList<>();
        adapter = new ChatMassageAdapter(this, R.layout.massage_item,chatMassages);

        messageListView.setAdapter(adapter);
        //set invisible for progressBar
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.toString().trim().length() > 0){
                    sendMassageButton.setEnabled(true);
                } else {
                    sendMassageButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //input in "@+id/massageEditText limits
        messageEditText.setFilters(new InputFilter[]
                {new InputFilter.LengthFilter(250)});

        sendMassageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMassage massage = new ChatMassage();
                massage.setText(messageEditText.getText().toString());
                massage.setName(userName);
                massage.setImageUrl(null);

                messageDatabaseReference.push().setValue(massage);

                //Clear input in @+id/massageEditText
                messageEditText.setText("");

            }
        });

        sendImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        messagesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMassage massage =
                        dataSnapshot.getValue(ChatMassage.class);
                adapter.add(massage);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        messageDatabaseReference.addChildEventListener(messagesChildEventListener);

    }
}
