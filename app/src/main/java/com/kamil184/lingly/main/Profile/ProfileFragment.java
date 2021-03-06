package com.kamil184.lingly.main.Profile;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.SubtitleCollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kamil184.lingly.Constants;
import com.kamil184.lingly.R;
import com.kamil184.lingly.base.BaseFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.container1) AppBarLayout container1;
    @BindView(R.id.user_avatar) ImageView avatar;
    @BindView(R.id.status_edit_btn) ImageButton statusBtn;
    @BindView(R.id.birth_date) TextView birthDate;
    @BindView(R.id.about) TextView about;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.bottomsheet) BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout) SubtitleCollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.languages) GridView nativeLanguages;
    @BindView(R.id.about_edit_btn) ImageButton aboutBtn;
    View view1;

    private static final int REQUEST_STORAGE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = REQUEST_STORAGE + 1;
    private static final int REQUEST_LOAD_IMAGE = REQUEST_IMAGE_CAPTURE + 1;
    private Uri cameraImageUri = null;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ProfilePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view1);

        bottomSheetLayout.setPeekOnDismiss(true);

        presenter = new ProfilePresenter(getContext());
        presenter.attachView(this);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        presenter.profileFill();
        presenter.getAvatarUri();

        avatar.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        statusBtn.setOnClickListener(this);
        return view1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_avatar:
                if (checkNeedsPermission()) {
                    requestStoragePermission();
                } else {
                    showSheetView();
                }
                break;

            case R.id.about_edit_btn:
                startDialog(about.getText().toString(), Constants.UserData.APP_PREFERENCES_ABOUT);
                break;
            case R.id.status_edit_btn:
                startDialog(status.getText().toString(), Constants.UserData.APP_PREFERENCES_STATUS);
        }
    }

    private void startDialog(String fieldText, String fieldType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile_edit, null);

        builder.setCancelable(false);
        builder.setView(dialogView);

        MaterialButton btn_positive = dialogView.findViewById(R.id.dialog_positive_btn);
        MaterialButton btn_negative = dialogView.findViewById(R.id.dialog_negative_btn);
        final TextInputEditText et_name = dialogView.findViewById(R.id.dialog_edit_text);
        et_name.setText(fieldText);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_positive.setOnClickListener(v -> {
            dialog.dismiss();
            presenter.editField(et_name.getText().toString(), fieldType);

        });
        btn_negative.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private boolean checkNeedsPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ActivityCompat.checkSelfPermission(view1.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        } else {
            // Eh, prompt anyway
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSheetView();
            } else {
                // Permission denied
                showWarningDialog("Для доступа к памяти нам нужно разрешение");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void showSheetView() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(view1.getContext())
                .setMaxItems(30)
                .setShowCameraOption(createCameraIntent() != null)
                .setShowPickerOption(createPickIntent() != null)
                .setImageProvider((imageView, imageUri, size) -> Glide.with(view1)
                        .load(imageUri)
                        .into(imageView))
                .setOnTileSelectedListener(selectedTile -> {
                    bottomSheetLayout.dismissSheet();
                    if (selectedTile.isCameraTile()) {
                        dispatchTakePictureIntent();
                    } else if (selectedTile.isPickerTile()) {
                        startActivityForResult(createPickIntent(), REQUEST_LOAD_IMAGE);
                    } else if (selectedTile.isImageTile()) {
                        presenter.uploadAvatar(selectedTile.getImageUri());
                    } else {
                        genericError();
                    }
                })
                .setTitle("Choose an image...")
                .create();


        bottomSheetLayout.showWithSheetView(sheetView);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        cameraImageUri = Uri.fromFile(imageFile);
        return imageFile;
    }


    @Nullable
    private Intent createPickIntent() {
        Intent picImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (picImageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            return picImageIntent;
        } else {
            return null;
        }
    }


    @Nullable
    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            return takePictureIntent;
        } else {
            return null;
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = createCameraIntent();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent != null) {
            // Create the File where the photo should go
            try {
                File imageFile = createImageFile();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                // Error occurred while creating the File
                genericError("Could not create imageFile for camera");
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = null;
            if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                selectedImage = data.getData();
                if (selectedImage == null) {
                    genericError();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Do something with imagePath
                selectedImage = cameraImageUri;
            }

            if (selectedImage != null) {
                presenter.uploadAvatar(selectedImage);
            } else {
                genericError();
            }
        }
    }


    private void genericError() {
        genericError(null);
    }

    private void genericError(String message) {
        showWarningDialog(message);
    }
}


