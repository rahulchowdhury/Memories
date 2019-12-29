package co.rahulchowdhury.memories.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import co.rahulchowdhury.memories.R
import co.rahulchowdhury.memories.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.android.viewmodel.ext.android.viewModel

class GalleryFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_gallery

    lateinit var galleryAdapter: GalleryAdapter
    private val viewModel: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupGalleryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.photos.observe(viewLifecycleOwner, Observer { pagedList ->
            galleryAdapter.submitList(pagedList)
        })

        viewModel.loadPhotos()
    }

    private fun setupGalleryAdapter() {
        galleryAdapter = GalleryAdapter()

        photoGallery.layoutManager = GridLayoutManager(context, 3)
        photoGallery.adapter = galleryAdapter
    }

}
