package com.example.hospitalsx.ui.eliminar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hospitalsx.databinding.FragmentEliminarBinding;


public class EliminarFragment extends Fragment {

    private FragmentEliminarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EliminarModel eliminarModel =
                new ViewModelProvider(this).get(EliminarModel.class);

        binding = FragmentEliminarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEliminar;
        eliminarModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}