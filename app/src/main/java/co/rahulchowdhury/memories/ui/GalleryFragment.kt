package co.rahulchowdhury.memories.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import co.rahulchowdhury.memories.R
import co.rahulchowdhury.memories.data.Constants
import co.rahulchowdhury.memories.data.model.remote.Error
import co.rahulchowdhury.memories.ui.base.BaseFragment
import co.rahulchowdhury.memories.ui.extension.showSnackbar
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.android.viewmodel.ext.android.viewModel

class GalleryFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_gallery

    private lateinit var galleryAdapter: GalleryAdapter
    private val viewModel: GalleryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupGalleryAdapter()

        viewModel.photos.observe(viewLifecycleOwner, Observer { pagedList ->
            galleryAdapter.submitList(pagedList)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { responseState ->
            when (responseState) {
                is Error -> view.showSnackbar(responseState.message)
            }
        })
    }

    private fun setupGalleryAdapter() {
        galleryAdapter = GalleryAdapter()

        photoGallery.layoutManager = GridLayoutManager(context, Constants.Gallery.COLUMNS)
        photoGallery.adapter = galleryAdapter
        photoGallery.setHasFixedSize(true)
    }

}
