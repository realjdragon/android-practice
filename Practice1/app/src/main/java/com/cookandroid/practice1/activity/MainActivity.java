package com.cookandroid.practice1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cookandroid.practice1.R;
import com.cookandroid.practice1.api.HomeMainApiClient;

// Activity는 화면을 구성하는 가장 기본적인 컴포넌트
// 모든 Activity는 Activity 클래스를 상속받음
// AppCompatActivity는 안드로이드 하위 버전을 지원하는 액티비티
public class MainActivity extends AppCompatActivity {

    // Activity 클래스를 상속받아 액티비티 생명 주기에 따른 이벤트 리스너를 Override
    // 액티비티가 처음 만들어 졌을 때 호출됨
    // 화면에 보이는 뷰들의 일반적인 상태를 설정하는 부분
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Bundle은 상태를 보존하는데 사용한다고 한다.
        // 가령 앱 동작중에 카메라를 이용하고 돌아오는 경우
        // 카메라를 이용하는 사이에 앱이 죽어버리는 경우를 대비해 onSaveInstanceState(Bundle)를 작성
        super.onCreate(savedInstanceState);

        // main activity를 View로 설정한다.
        setContentView(R.layout.activity_main);

        // 제목 설정
        setTitle(R.string.main_title);

        // API 호출해서 ListView Set
        // 안드로이드는 안정성의 이유로 Main Thread (UI Thread)에서만 UI를 변경할 수 있도록 제한됨
        // 또, Main Thread에서 네트워크작업도 제한됨..5초 이상의 지연이 있을 경우 App 종료..
        // 따라서 별도의 Thread를 구성하고 네트워크 작업을 해야하는데 매번 이러긴 귀찮으니
        // Volley라는 패키지를 사용해서 손 쉽게 API를 호출하고 response 후에 UI 작업까지 할 수 있음.
        new HomeMainApiClient().setMainActivityList(this);
    }
}
