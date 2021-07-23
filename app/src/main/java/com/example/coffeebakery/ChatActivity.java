package com.example.coffeebakery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeebakery.Chat.ChatListAdapter;
import com.example.coffeebakery.Chat.InstantMessage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import static com.example.coffeebakery.LoginActivity.mAuth;
import static com.example.coffeebakery.SplashActivity.gmail;
//import static com.example.coffeebakeryfordriver.SplashActivity.uid;

public class ChatActivity extends AppCompatActivity {
    public static String uid = "";

    private String myUserName;
    private ListView myChatListView;
    private EditText myChatText;
    public ImageButton mySendChatButton;
    private DatabaseReference myDataBaseRef;

    private ChatListAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        setUpDisplayName();
        myDataBaseRef = FirebaseDatabase.getInstance().getReference();


// Get the reference of UI Elements
        myChatListView = (ListView) findViewById(R.id.chat_listView);
        myChatText = (EditText) findViewById(R.id.messageInput);
        mySendChatButton = (ImageButton) findViewById(R.id.sendButton);

        // push chat to FireBase button tab
        mySendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushChatToFirebase();
            }
        });

        //call the pushChatToFireBase on keyboard event

        myChatText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                pushChatToFirebase();
                return true;
            }
        });
    }


    //push chat to Firebase

    private void pushChatToFirebase(){
        String chatInput = myChatText.getText().toString();
        if(!chatInput.equals("")){
            InstantMessage chat = new InstantMessage(chatInput,myUserName);
            myDataBaseRef.child("Trò chuyện").push().setValue(chat);
            myChatText.setText("");
        }
    }
    // set username for user
    ///Chú ý chỗ này
    private void setUpDisplayName(){
        SharedPreferences prefs = getSharedPreferences(gmail,MODE_PRIVATE);
        myUserName = prefs.getString(gmail, null);
        if(myUserName == null){
            Intent intent = getIntent();
            myUserName = intent.getStringExtra("ten_kh");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter = new ChatListAdapter(com.example.coffeebakery.ChatActivity.this,myDataBaseRef,myUserName);
        myChatListView.setAdapter(myAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.freeUpResources();
    }
}