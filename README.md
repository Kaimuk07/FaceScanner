How to LibraryFaceScan
========
Step 1
--------

Add the JitPack repository to your build file:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2
--------

Add the dependency:
```
dependencies {
		compile 'com.github.Kaimuk07:LibraryFaceScan:v3.1'
	}
```

Step 3
--------
Premission in AndroidManifest.xml
```
	<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```


Step 4
--------

```
camera = new Camera(MainActivity.this, new Camera.CallBack() {
    @Override
    public void checkResultCameraSuccess(Bitmap bitmap) {
                  
    }
	
    @Override
    public void checkResultCameraFailed(int requestCode, int resultCode, Intent data) {
	
    }
	
    @Override
    public void loading() {
	
    }
	
    @Override
    public void failed(String message) {
	
    }
	
    @Override
    public void success(Card card) {
	
    }
}).openCamera();
```

Step 5
--------
set KEY API and EndPoint
```
GlobalFaceScan.getInstance().setSubscription_key("KEY Face API");
GlobalFaceScan.getInstance().setEndpoint("EndPoint");
GlobalFaceScan.getInstance().setRequest_carmera1(2627);//default value 2627
GlobalFaceScan.getInstance().setRequest_carmera2(2628);//default value 2628
GlobalFaceScan.getInstance().setRequest_carmera_none(2629); //default value 2629
```

Step 6
--------
```
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        camera.openCamera(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResultCamera(requestCode, resultCode, data);
    }
```
