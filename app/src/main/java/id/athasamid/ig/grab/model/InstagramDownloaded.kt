package id.athasamid.ig.grab.model

data class InstagramDownloaded (val id: Int, val username: String?, val fullName: String?, val displayUrl: String?, val videoUrl: String? = null, val isVideo: Boolean? = false, val fileName: String?, val progress: Int = 0)