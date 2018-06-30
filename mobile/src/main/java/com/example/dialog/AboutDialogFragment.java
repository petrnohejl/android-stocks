package com.example.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.R;
import com.example.databinding.DialogAboutBinding;

import org.alfonz.utility.VersionUtility;

public class AboutDialogFragment extends DialogFragment {
	private DialogAboutBinding mBinding;

	public static AboutDialogFragment newInstance() {
		return new AboutDialogFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// cancelable on touch outside
		if (getDialog() != null) getDialog().setCanceledOnTouchOutside(true);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mBinding = DialogAboutBinding.inflate(getActivity().getLayoutInflater());
		mBinding.setData(getMessage());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_about_title).setView(mBinding.getRoot());

		return builder.create();
	}

	private String getMessage() {
		String version = VersionUtility.getVersionName(getActivity());
		return String.format("%s %s", getString(R.string.app_name), version);
	}
}
