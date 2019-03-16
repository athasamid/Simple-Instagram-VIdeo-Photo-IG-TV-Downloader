package id.athasamid.ig.grab

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.DownloadListener
import com.bumptech.glide.Glide
import id.athasamid.ig.grab.model.InstagramDownloaded
import java.io.File

class AdapterIgDownloader(var items: List<InstagramDownloaded>): RecyclerView.Adapter<AdapterIgDownloader.IgViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IgViewHolder = IgViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_history, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IgViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class IgViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var imgPrev = view.findViewById<ImageView>(R.id.prev)
        var download = view.findViewById<ImageView>(R.id.download)
        var isVideo = view.findViewById<ImageView>(R.id.isVideo)
        var progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        var username = view.findViewById<TextView>(R.id.username)

        fun bind(instagramDownloaded: InstagramDownloaded){
            Glide.with(view.context)
                .load(instagramDownloaded.displayUrl)
                .into(imgPrev)

            isVideo.visibility = if(instagramDownloaded.isVideo == true) View.VISIBLE else View.GONE

            val urlToDownload = if (instagramDownloaded.isVideo == true) instagramDownloaded.videoUrl else instagramDownloaded.displayUrl

            val filename = urlToDownload?.fileName()

            username.text = "@${instagramDownloaded.username}"

            if(filename == null){
                download.visibility = View.GONE
            } else {

                val file = File(
                    Environment.getExternalStorageDirectory().absolutePath + "/" + view.context.getString(R.string.app_name) + "/"+ if (instagramDownloaded.isVideo == true) Environment.DIRECTORY_MOVIES else Environment.DIRECTORY_PICTURES,
                    filename
                )

                download.visibility = if (file.exists()) View.GONE else View.VISIBLE

                download.setOnClickListener {
                    downloadFile(
                        file,
                        Environment.getExternalStorageDirectory().absolutePath + "/" + view.context.getString(R.string.app_name) + "/" + if (instagramDownloaded.isVideo == true) Environment.DIRECTORY_MOVIES else Environment.DIRECTORY_PICTURES,
                        urlToDownload,
                        instagramDownloaded.isVideo == true,
                        progressBar,
                        download
                    )
                }
            }

            progressBar.visibility = View.GONE
        }

        fun downloadFile(file: File, dir: String,  url: String?, isVideo: Boolean, progressBar: ProgressBar, download: ImageView){
            val filename = url?.fileName() ?: return

            progressBar.visibility = View.VISIBLE
            download.visibility = View.GONE

            AndroidNetworking.download(url, dir, filename)
                .setPriority(Priority.MEDIUM)
                .setTag("Downloading $filename")
                .build()
                .startDownload(object: DownloadListener{
                    override fun onDownloadComplete() {
                        progressBar.visibility = View.GONE
                        download.visibility = View.GONE

                        if (isVideo) addVideoToGallery(file.name, file.path) else addImageToGallery(file.path)

                        Toast.makeText(view.context, "Disimpan di "+file.path, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(view.context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                        download.visibility = View.VISIBLE
                    }

                })
        }

        fun addImageToGallery(path: String){
            val contentValues = ContentValues()

            contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.MediaColumns.DATA, path)

            view.context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        }

        fun addVideoToGallery(filename: String,path: String){
            val contentValues = ContentValues(3)

            contentValues.put(MediaStore.Video.Media.TITLE, filename)
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, System.currentTimeMillis())
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            contentValues.put(MediaStore.MediaColumns.DATA, path)

            view.context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
        }

    }
}