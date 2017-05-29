package com.esprit.instasinger.Fragments.MusicFragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.afollestad.materialcamera.internal.BaseCaptureActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.esprit.instasinger.Data.Music;
import com.esprit.instasinger.Data.Videos;
import com.esprit.instasinger.FilePath;
import com.esprit.instasinger.R;

import static android.content.ContentValues.TAG;


public class PlaySong_Fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "madrid";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog pDialog ;

    private static Music mySong = new Music();

//uopload

    private String SERVER_URL = "http://a3tihooli.com/instasinger/upload.php";
    PowerManager.WakeLock wakeLock;
    private String selectedFilePath;

    MediaPlayer mediaPlayer ;
    SeekBar mSeekbar ;
    TextView startText ;
    TextView endText;



    ImageView back_player ;
    ImageView singer_image ;





    //cameraaa

    private final static int CAMERA_RQ = 6969;
    private final static int PERMISSION_RQ = 84;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("videos");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //






    public PlaySong_Fragment() {

        // Required empty public constructor
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaySong_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaySong_Fragment newInstance(String param1, String param2) {
        PlaySong_Fragment fragment = new PlaySong_Fragment();


        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,mySong);

        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setMessage("Uploading...");
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mySong = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //cameraaa
        setRetainInstance(true);
        //

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("test");
        System.out.println(mySong.getSinger());

        View view = inflater.inflate(R.layout.fragment_play_song_, container, false);

        mSeekbar = (SeekBar) view.findViewById(R.id.seekBar1);
        startText = (TextView) view.findViewById(R.id.startText);
        endText = (TextView) view.findViewById(R.id.endText);
        TextView line1 = (TextView) view.findViewById(R.id.line1);
        TextView line2 = (TextView) view.findViewById(R.id.line2);

        line1.setText(mySong.getSong());
        line2.setText(mySong.getSinger());



        back_player = (ImageView ) view.findViewById(R.id.back_player);
        // singer_image = (ImageView ) view.findViewById(R.id.singer_image);
        Picasso.with(getActivity()).load(mySong.getSingerPicture()).into(back_player);
        //  Picasso.with(getActivity()).load(mySong.getSingerPicture()).into(singer_image);



        return view ;
    }

    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };



    @Override
    public void onStart() {
        super.onStart();



        pDialog = new ProgressDialog(getActivity());



        mediaPlayer = new MediaPlayer() ;


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(mySong.getPreview());
            mediaPlayer.prepare();
            mediaPlayer.start();
            updateProgress();
            System.out.println("wa9et"+mediaPlayer.getDuration()/1000);


            endText.setText("00:"+Integer.toString(mediaPlayer.getDuration()/1000));
            mSeekbar.setMax(mediaPlayer.getDuration()/1000);


            // startText.setText("00:"+Integer.toString(mediaPlayer.getCurrentPosition()/1000));



        } catch (IOException e) {
            e.printStackTrace();
        }


        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                startText.setText("00:"+Integer.toString( i));
                //
                if (b == true) {
                    mediaPlayer.seekTo(i * 1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






    }


    private Handler mHandler = new Handler();


    public void updateProgress() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();


            //Log.d("Progress", ""+progress);
            mSeekbar.setProgress((int) currentDuration / 1000);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.stop();

    }





    //cameraaaaaaaaaaaaaaa

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    private void bindViews(View view) {
        view.findViewById(R.id.launchCamera).setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission to save videos in external storage
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_RQ);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onClick(View view) {
        System.out.println("lunch camera pressed");
        mediaPlayer.stop();

        BaseCaptureActivity.MUSIC_URL = mySong.getPreview();

        File saveDir = null;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Only use external storage directory if permission is granted, otherwise cache directory is used by default
            saveDir = new File(Environment.getExternalStorageDirectory(), "InstaSinger");
            saveDir.mkdirs();
        }

        com.afollestad.materialcamera.MaterialCamera materialCamera = new MaterialCamera(this)
                .saveDir(saveDir)
                .showPortraitWarning(false)
                .allowRetry(true)
                .countdownSeconds(30)
                .restartTimerOnRetry(true)                        // If true, the countdown timer is reset to 0 when the user taps 'Retry' in playback
                .defaultToFrontFacing(true);


        materialCamera.start(CAMERA_RQ);
    }

    private String readableFileSize(long size) {
        if (size <= 0) return size + " B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private String fileSize(File file) {
        return readableFileSize(file.length());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RQ) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    //no data present
                    return;
                }
                final File file = new File(data.getData().getPath());
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(getActivity(), selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);



                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //creating new thread to handle Http Operations
                            uploadFile(selectedFilePath);
                        } catch (OutOfMemoryError e) {


                        }

                    }
                }).start();









                Toast.makeText(getActivity(), String.format("Saved to: %s, size: %s",
                        file.getAbsolutePath(), fileSize(file)), Toast.LENGTH_LONG).show();
            } else if (data != null) {
                Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        pDialog.dismiss();
        mediaPlayer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pDialog.dismiss();
    }

    public int uploadFile(final String selectedFilePath) {



        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                showProgressDialog();            }
        });
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            //dialog.dismiss();

 /*           runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });*/
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty(
                        "Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);


                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {

                    try {

                        //write the bytes read from inputstream
                        dataOutputStream.write(buffer, 0, bufferSize);
                    } catch (OutOfMemoryError e) {
                        // Toast.makeText(MainActivity.this, "Insufficient Memory!", Toast.LENGTH_SHORT).show();
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                try{
                    serverResponseCode = connection.getResponseCode();
                }catch (OutOfMemoryError e){
                    //Toast.makeText(MainActivity.this, "Memory Insufficient!", Toast.LENGTH_SHORT).show();
                }
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/" + fileName);
                        }
                    });*/

                    String urlupload = "http://a3tihooli.com/instasinger/uploads/" + fileName;

                    Log.i(TAG, "firofiro " + urlupload);

                    String key = myRef.push().getKey();

                    Map<String,Object> taskMap = new HashMap<String,Object>();
                    taskMap.put("song", mySong.getSong());
                    myRef.child(user.getUid()).child(key).setValue(taskMap);
                    taskMap.put("singer", mySong.getSinger());
                    myRef.child(user.getUid()).child(key).setValue(taskMap);
                    taskMap.put("comments", key);
                    myRef.child(user.getUid()).child(key).setValue(taskMap);
                    taskMap.put("songPicture", mySong.getSingerPicture());
                    myRef.child(user.getUid()).child(key).setValue(taskMap);
                    taskMap.put("URL", urlupload);
                    myRef.child(user.getUid()).child(key).setValue(taskMap);
                    taskMap.put("likes","0");
                    myRef.child(user.getUid()).child(key).setValue(taskMap);


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            hideProgressDialog();
                            Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();

                        }
                    });



                    //   System.out.println(urlupload);
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();

              /*  if (wakeLock.isHeld()) {

                    wakeLock.release();
                }
*/

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "URL Error!", Toast.LENGTH_SHORT).show();
                    }
                });*/

            } catch (IOException e) {
                e.printStackTrace();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Cannot Read/Write File", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
            // dialog.dismiss();
            return serverResponseCode;
        }

    }








    public void uploadvideo(Intent data)
    {
        pDialog = new ProgressDialog(getActivity());


        showProgressDialog();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://instasinger-e9947.appspot.com");
        Uri uri = data.getData();
        StorageReference riversRef = storageRef.child(uri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(uri);





        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

                Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Videos v = new Videos();
                String key = myRef.push().getKey();

                v.setSong(mySong.getSong());
                v.setSinger(mySong.getSinger());
                v.setSongPicture(mySong.getSingerPicture());
                v.setURL(downloadUrl.toString());
                v.setComments(key);
                v.setLikes("0");

                myRef.child(user.getUid()).child(key).setValue(v);
                hideProgressDialog();

                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            // Sample was denied WRITE_EXTERNAL_STORAGE permission
            Toast.makeText(getActivity(), "Videos will be saved in a cache directory instead of an external storage directory since permission was denied.", Toast.LENGTH_LONG).show();
        }
    }
}
