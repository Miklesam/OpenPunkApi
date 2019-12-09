package com.miklesam.openpunkapi.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class DownloadAndSaveImageTask(context: Context,name:String) : AsyncTask<String, Unit, Unit>() {
    private var mContext: WeakReference<Context> = WeakReference(context)
    private var imagename:String=name
    override fun doInBackground(vararg params: String?) {
        val url = params[0]
        val requestOptions = RequestOptions().override(1080,688)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            try {
                var file = File(it.filesDir, "Images")
                if (!file.exists()) {
                    file.mkdir()
                }
                file = File(file, imagename+".png")
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                   Log.w("Seiggailion", "Image saved.")
            } catch (e: Exception) {
                Log.w("Seiggailion", "Failed to save image.")
            }
        }
    }

    fun deleteFile(){
        val dir = Uri.parse(
            mContext.get()?.filesDir.toString()+"/Images"
        )
        Log.w("SeiggailionDelete", dir.toString())
        Log.w("SeiggailionDelete", imagename)
        val file = File(dir.path, imagename)
        val deleted = file.delete()
    }
}