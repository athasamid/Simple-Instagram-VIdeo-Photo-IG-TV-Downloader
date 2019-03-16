package id.athasamid.ig.grab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.gson.Gson
import id.athasamid.ig.grab.model.GraphqResponse
import id.athasamid.ig.grab.model.InstagramDownloaded
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var listInstagram = mutableListOf<InstagramDownloaded>()
    var adapterIgDownloader = AdapterIgDownloader(listInstagram)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askPermission{
            init()
        }.onDeclined { e ->
            if (e.hasDenied()) {
                AlertDialog.Builder(this)
                    .setMessage("Please accept our permissions")
                    .setPositiveButton("yes") { dialog, which ->
                        e.askAgain()
                    } //ask again
                    .setNegativeButton("no") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }

            if (e.hasForeverDenied()) {
                // you need to open setting manually if you really need it
                e.goToSettings()
            }
        }
    }

    fun init(){
        btnSubmit.setOnClickListener(this)

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapterIgDownloader

        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.action == Intent.ACTION_SEND){
            if (intent.type == "text/plain"){
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                url.setText(sharedText)
                btnSubmit.performClick()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSubmit -> submit()
        }
    }

    fun submit(){
        val textUrl = url.text.toString()
        if (textUrl.isNotEmpty() || textUrl.substring(0, 8) == "https://" || textUrl.substring(0, 7) == "http://" || textUrl.substring(0, 21) == "https://instagram.com" || textUrl.substring(0, 20) == "http://instagram.com"){
            val pattern = Pattern.compile(".*\\/")
            val matcher = pattern.matcher(textUrl)

            if (!matcher.find()){
                Toast.makeText(this, "Url tidak sesuai", Toast.LENGTH_SHORT).show()
                return
            }

            val urlParsed = matcher.group(0)
            Log.e("MainActivity", urlParsed)
            btnSubmit.text = "Mendapatkan informasi...."
            btnSubmit.isEnabled = false
            AndroidNetworking.get("$urlParsed?__a=1")
                .addHeaders("accept", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(object: StringRequestListener{
                    override fun onResponse(response: String?) {
                        Log.e("MainActivity", response)
                        btnSubmit.text = "Submit"
                        btnSubmit.isEnabled = true

                        parseJson(Gson().fromJson<GraphqResponse>(response, GraphqResponse::class.java))
                    }

                    override fun onError(anError: ANError?) {
                        btnSubmit.text = "Submit"
                        btnSubmit.isEnabled = true
                    }

                })
        } else {
            Toast.makeText(this, "Unable to load webpage", Toast.LENGTH_SHORT).show()
        }
    }

    fun parseJson(response: GraphqResponse){
        val typename = response.graphql?.shortcodeMedia?.typename

        val listInstagram = mutableListOf<InstagramDownloaded>()
        val shortcodeMedia = response.graphql?.shortcodeMedia

        when(typename){
            "GraphImage" -> {
                listInstagram.add(InstagramDownloaded(0, username = shortcodeMedia?.owner?.username, fullName = shortcodeMedia?.owner?.fullName, displayUrl = shortcodeMedia?.displayUrl, fileName = if(shortcodeMedia?.isVideo == true) shortcodeMedia.videoUrl?.fileName() else shortcodeMedia?.displayUrl?.fileName(), isVideo = shortcodeMedia?.isVideo, videoUrl = shortcodeMedia?.videoUrl))
            }
            "GraphVideo" -> listInstagram.add(InstagramDownloaded(0,username = shortcodeMedia?.owner?.username, fullName = shortcodeMedia?.owner?.fullName, displayUrl = shortcodeMedia?.displayUrl, fileName = if(shortcodeMedia?.isVideo == true) shortcodeMedia.videoUrl?.fileName() else shortcodeMedia?.displayUrl?.fileName(), isVideo = shortcodeMedia?.isVideo, videoUrl = shortcodeMedia?.videoUrl))
            "GraphSidecar" -> {
                shortcodeMedia?.edgeSidecarToChildren?.edges?.forEach {a ->
                    listInstagram.add(InstagramDownloaded(0,username = shortcodeMedia?.owner?.username, fullName = shortcodeMedia?.owner?.fullName, displayUrl = a?.node?.displayUrl, fileName = if(a?.node?.isVideo == true) a.node?.videoUrl?.fileName() else a?.node?.displayUrl?.fileName(), isVideo = a?.node?.isVideo, videoUrl = a?.node?.videoUrl))
                }
            }
        }

        Log.e("listInstagram", listInstagram.toString())

        this.listInstagram.addAll(listInstagram)
        adapterIgDownloader.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        copyClipBoard()
    }

    fun copyClipBoard(){

    }
}


fun String.fileName(): String? {
    val pattern = Pattern.compile("[^/]*$")
    val matcher = pattern.matcher(this)

    //check if pattern didn't match so return from this function
    if (!matcher.find())
        return null

    val find = matcher.group()

    return find.substring(0, find.indexOf("?"))
}