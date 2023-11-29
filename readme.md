# CloudDrawing 구름그린

## An Android application on SNS with geolocation

언제든 그리웠던 추억을. 어디에서도 떠올리고 싶은 일상을!

### Android SDK Version
Required: Android SDK 34 (Android T)
Target: Android SDK 34 (Android T)
Minimum: Android SDK 26 (Android P)

### 실행시 참고할 점
해당 어플리케이션은 Android (with Google Services) 환경에서의 동작을 보장합니다.
Android Virtual Device에서 구름그린을 실행할 때 Google Playstore가 설치되어 있는지, 기기에 구글 계정이 로그인되어 있는지 확인바랍니다.

이 어플리케이션은 Firebase 및 Google OAuth 테스트 모드로 설정되어 있습니다.
사전 설정되어 있지 않은 계정으로는 로그인 시도가 불가능합니다.

### Playstore 내부테스트 액세스
플레이스토어 내부테스트 액세스는 사전에 설정된 계정을 받으신 분만 접근이 가능합니다.
[https://play.google.com/apps/internaltest/4701151691869251029](https://play.google.com/apps/internaltest/4701151691869251029)

### 직접 앱을 실행시켜보고 싶을 때

#### app/src/google-services.json
1. Firebase에서 새로운 프로젝트를 생성합니다.
2. Firebase Authentication, Storage, Firestore Database를 활성화합니다.
3. 프로젝트 설정 - 일반 - 내 앱 - 앱 추가에서 Android 앱을 추가합니다.
4. `./gradlew signingReport` 를 실행, 여기에서 나온 SHA1키, SHA256키를 디지털 지문에 추가합니다.
5. `google-service.json` 파일을 다운로드, 해당 위치에 위치합니다. [https://firebase.google.com/docs/auth/android/google-signin?hl=ko](https://firebase.google.com/docs/auth/android/google-signin?hl=ko) 여기에서 정확한 정보를 얻을 수 있습니다.

#### app/src/main/res/values/strings.xml
1. 구글 클라우드 콘솔에서 OAuth API키를 받습니다. `google-services.json`의 5번 항목을 참고하세요.
2. 받은 API키는 google_oauth_api_id에 붙여넣습니다.
3. 카카오 개발자포털에 접속해 어플리케이션을 생성합니다.
4. 네이티브 앱 키는 kakao_map_api_key에, REST API 키는 `KakaoAK `를 앞에 붙인 상태로 kakao_restapi_key에 붙여넣습니다.
5. 플랫폼에서 안드로이드 플랫폼을 생성, 패키지명 `kr.ac.kookmin.clouddrawing`, 키 해시를 등록합니다. 키 해시 등록과정은 다음을 참고하세요. [https://developers.kakao.com/docs/latest/ko/android/getting-started#before-you-begin-add-key-hash](https://developers.kakao.com/docs/latest/ko/android/getting-started#before-you-begin-add-key-hash)

### Firestore 보안규칙

다음과 같습니다.

#### Storage

```
rules_version = '2';

// Craft rules based on data in your Firestore database
// allow write: if firestore.get(
//    /databases/(default)/documents/users/$(request.auth.uid)).data.isAdmin;
service firebase.storage {
  match /b/{bucket}/o {

    // This rule allows anyone with your Storage bucket reference to view, edit,
    // and delete all data in your Storage bucket. It is useful for getting
    // started, but it is configured to expire after 30 days because it
    // leaves your app open to attackers. At that time, all client
    // requests to your Storage bucket will be denied.
    //
    // Make sure to write security rules for your app before that time, or else
    // all client requests to your Storage bucket will be denied until you Update
    // your rules
		match /profile/{userId} {
    	allow read: if request.auth.uid != null;
      allow write: if request.auth.uid != null && request.auth.uid == userId;
    }
    match /{allPaths=**} {
      allow read, write: if request.time < timestamp.date(2023, 11, 30) && request.auth.uid != null;
    }
  }
}
```

#### Firestore Database

```
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
  	match /user/{userId} {
    	allow read, update, delete: if request.auth != null && request.auth.uid == userId;
      allow create: if request.auth == null;
    }
    match /post/{postId} {
    	allow read: if true;
    	allow create: if request.auth != null;
      allow update, delete: if request.auth != null && request.resource.data.uid == request.auth.uid;
    }
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```