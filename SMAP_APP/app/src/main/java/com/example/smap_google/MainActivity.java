package com.example.smap_google;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton btn_google;
    private FirebaseAuth auth;
    private GoogleApiClient googleApiClient;
    private  static final int REQ_SIGN_GOOGLE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 앱이 실행될때 수행되는 곳

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.setValue("Hello, World!");
        //임시 로그인 버튼
        Button btn_templogin = (Button)findViewById(R.id.btn_templogin);
        btn_templogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , BottomNavViewActivity.class);
                startActivity(intent);
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this    )
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        auth = FirebaseAuth.getInstance();// 파이어베이스 인증 객체 초기화

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() { //구글 로그인 버튼을 클릭했을 ㄸ때 이곳을 수행
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,REQ_SIGN_GOOGLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // 구글 로그인 인증을 요청 했을 때 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess() == true)
            {
                GoogleSignInAccount account = result.getSignInAccount(); // account 라는 데이터는 구글로그인 정보를 담고있습니다.
                resultLogin(account); // 로그인 결과 ㅏㅄ 출력 수행하라는 메소드
            }
        }

    }

    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){ //  로그인 성공
                                Toast.makeText(MainActivity.this, "로그인 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ResultActivity_google.class);
                                intent.putExtra("nickName",account.getDisplayName());
                                intent.putExtra("photoUrl",String.valueOf(account.getPhotoUrl())); // string.valueof 특정 자룡형을 string 형태로 변환
                                startActivity(intent);
                            }
                            else
                            {//로그인 실패패
                                Toast.makeText(MainActivity.this, "로그인 실패",Toast.LENGTH_SHORT).show();
                            }                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}