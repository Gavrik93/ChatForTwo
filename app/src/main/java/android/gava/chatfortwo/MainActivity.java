package android.gava.chatfortwo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
    ChildEventListener messagesChildEventListener;
    DatabaseReference usersDatabaseReference;
    ChildEventListener usersChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        messageDatabaseReference = database.getReference().child("messages");
        usersDatabaseReference = database.getReference().child("users");


        progressBar = findViewById(R.id.progressBar);
        sendImageButton = findViewById(R.id.sendPhotoButton);
        sendMassageButton = findViewById(R.id.sendMassageButton);
        messageEditText = findViewById(R.id.massageEditText);

        Intent intent = getIntent();
        if (intent != null){
            userName = intent.getStringExtra("userName");
        } else {
            userName = "Default User";
        }


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
                Toast.makeText(MainActivity.this, "777gava777@gmail.com for more",
                        Toast.LENGTH_LONG).show();

            }
        });

        usersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getId().equals(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid())) {
                    userName = user.getName();
                }
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
        usersDatabaseReference.addChildEventListener(usersChildEventListener);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //this part fix bug when i was in chat activity using back key i return to login activity
    @Override
    public void onBackPressed () {

    }

}
