package com.example.hospitalsx.ui.editar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hospitalsx.databinding.FragmentEditarBinding;
import com.example.hospitalsx.databinding.FragmentEditarBinding;

public class EditarFragment extends Fragment {

    private FragmentEditarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditarModel editarModel =
                new ViewModelProvider(this).get(EditarModel.class);

        binding = FragmentEditarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEditar;
        editarModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}