package com.kamil184.lingly.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kamil184.lingly.R;

public class BaseFragment extends Fragment {

    public void showProgress() {
        ((BaseActivity) getActivity()).showProgress();
    }

    public void showProgress(int message) {
        ((BaseActivity) getActivity()).showProgress(message);
    }

    public void hideProgress() {
        ((BaseActivity) getActivity()).hideProgress();
    }

    public void showSnackBar(int message) {
        ((BaseActivity) getActivity()).showSnackBar(message);
    }

    public void showSnackBar(View view, int messageId) {
        ((BaseActivity) getActivity()).showSnackBar(view, messageId);
    }

    public void showWarningDialog(int messageId) {
        ((BaseActivity) getActivity()).showWarningDialog(messageId);
    }

    public void showWarningDialog(String message) {
        ((BaseActivity) getActivity()).showWarningDialog(message);
    }

    public void showNotCancelableWarningDialog(String message) {
        ((BaseActivity) getActivity()).showNotCancelableWarningDialog(message);
    }

    public void showWarningDialog(int messageId, DialogInterface.OnClickListener listener) {
        ((BaseActivity) getActivity()).showWarningDialog(messageId, listener);
    }

    public void showWarningDialog(String message, DialogInterface.OnClickListener listener) {
        ((BaseActivity) getActivity()).showWarningDialog(message, listener);
    }

    public void hideKeyboard() {
        ((BaseActivity) getActivity()).hideKeyboard();
    }

    public void finish() {
        ((BaseActivity) getActivity()).finish();
    }
}
