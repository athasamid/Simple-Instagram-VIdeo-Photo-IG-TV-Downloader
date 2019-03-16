package id.athasamid.ig.grab.model

import com.google.gson.annotations.SerializedName

data class GraphqResponse(
    @SerializedName("graphql")
    var graphql: Graphql?
) {
    data class Graphql(
        @SerializedName("shortcode_media")
        var shortcodeMedia: ShortcodeMedia?
    ) {
        data class ShortcodeMedia(
            @SerializedName("__typename")
            var typename: String?,
            @SerializedName("caption_is_edited")
            var captionIsEdited: Boolean?,
            @SerializedName("comments_disabled")
            var commentsDisabled: Boolean?,
            @SerializedName("dimensions")
            var dimensions: Dimensions?,
            @SerializedName("display_resources")
            var displayResources: List<DisplayResource?>?,
            @SerializedName("display_url")
            var displayUrl: String?,
            @SerializedName("video_url")
            val videoUrl: String?,
            @SerializedName("edge_media_preview_like")
            var edgeMediaPreviewLike: EdgeMediaPreviewLike?,
            @SerializedName("edge_media_to_caption")
            var edgeMediaToCaption: EdgeMediaToCaption?,
            @SerializedName("edge_media_to_comment")
            var edgeMediaToComment: EdgeMediaToComment?,
            @SerializedName("edge_media_to_sponsor_user")
            var edgeMediaToSponsorUser: EdgeMediaToSponsorUser?,
            @SerializedName("edge_media_to_tagged_user")
            var edgeMediaToTaggedUser: EdgeMediaToTaggedUser?,
            @SerializedName("edge_sidecar_to_children")
            var edgeSidecarToChildren: EdgeSidecarToChildren?,
            @SerializedName("edge_web_media_to_related_media")
            var edgeWebMediaToRelatedMedia: EdgeWebMediaToRelatedMedia?,
            @SerializedName("gating_info")
            var gatingInfo: Any?,
            @SerializedName("has_ranked_comments")
            var hasRankedComments: Boolean?,
            @SerializedName("id")
            var id: String?,
            @SerializedName("is_ad")
            var isAd: Boolean?,
            @SerializedName("is_video")
            var isVideo: Boolean?,
            @SerializedName("location")
            var location: Location?,
            @SerializedName("media_preview")
            var mediaPreview: Any?,
            @SerializedName("owner")
            var owner: Owner?,
            @SerializedName("shortcode")
            var shortcode: String?,
            @SerializedName("should_log_client_event")
            var shouldLogClientEvent: Boolean?,
            @SerializedName("taken_at_timestamp")
            var takenAtTimestamp: Int?,
            @SerializedName("tracking_token")
            var trackingToken: String?,
            @SerializedName("viewer_can_reshare")
            var viewerCanReshare: Boolean?,
            @SerializedName("viewer_has_liked")
            var viewerHasLiked: Boolean?,
            @SerializedName("viewer_has_saved")
            var viewerHasSaved: Boolean?,
            @SerializedName("viewer_has_saved_to_collection")
            var viewerHasSavedToCollection: Boolean?,
            @SerializedName("viewer_in_photo_of_you")
            var viewerInPhotoOfYou: Boolean?
        ) {
            data class EdgeMediaToSponsorUser(
                @SerializedName("edges")
                var edges: List<Any?>?
            )

            data class EdgeWebMediaToRelatedMedia(
                @SerializedName("edges")
                var edges: List<Any?>?
            )

            data class EdgeMediaPreviewLike(
                @SerializedName("count")
                var count: Int?,
                @SerializedName("edges")
                var edges: List<Any?>?
            )

            data class Location(
                @SerializedName("address_json")
                var addressJson: String?,
                @SerializedName("has_public_page")
                var hasPublicPage: Boolean?,
                @SerializedName("id")
                var id: String?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("slug")
                var slug: String?
            )

            data class EdgeMediaToComment(
                @SerializedName("count")
                var count: Int?,
                @SerializedName("edges")
                var edges: List<Edge?>?,
                @SerializedName("page_info")
                var pageInfo: PageInfo?
            ) {
                data class PageInfo(
                    @SerializedName("end_cursor")
                    var endCursor: Any?,
                    @SerializedName("has_next_page")
                    var hasNextPage: Boolean?
                )

                data class Edge(
                    @SerializedName("node")
                    var node: Node?
                ) {
                    data class Node(
                        @SerializedName("created_at")
                        var createdAt: Int?,
                        @SerializedName("did_report_as_spam")
                        var didReportAsSpam: Boolean?,
                        @SerializedName("edge_liked_by")
                        var edgeLikedBy: EdgeLikedBy?,
                        @SerializedName("id")
                        var id: String?,
                        @SerializedName("owner")
                        var owner: Owner?,
                        @SerializedName("text")
                        var text: String?,
                        @SerializedName("viewer_has_liked")
                        var viewerHasLiked: Boolean?
                    ) {
                        data class Owner(
                            @SerializedName("id")
                            var id: String?,
                            @SerializedName("is_verified")
                            var isVerified: Boolean?,
                            @SerializedName("profile_pic_url")
                            var profilePicUrl: String?,
                            @SerializedName("username")
                            var username: String?
                        )

                        data class EdgeLikedBy(
                            @SerializedName("count")
                            var count: Int?
                        )
                    }
                }
            }

            data class EdgeMediaToCaption(
                @SerializedName("edges")
                var edges: List<Edge?>?
            ) {
                data class Edge(
                    @SerializedName("node")
                    var node: Node?
                ) {
                    data class Node(
                        @SerializedName("text")
                        var text: String?
                    )
                }
            }

            data class EdgeMediaToTaggedUser(
                @SerializedName("edges")
                var edges: List<Any?>?
            )

            data class DisplayResource(
                @SerializedName("config_height")
                var configHeight: Int?,
                @SerializedName("config_width")
                var configWidth: Int?,
                @SerializedName("src")
                var src: String?
            )

            data class EdgeSidecarToChildren(
                @SerializedName("edges")
                var edges: List<Edge?>?
            ) {
                data class Edge(
                    @SerializedName("node")
                    var node: Node?
                ) {
                    data class Node(
                        @SerializedName("__typename")
                        var typename: String?,
                        @SerializedName("accessibility_caption")
                        var accessibilityCaption: String?,
                        @SerializedName("dimensions")
                        var dimensions: Dimensions?,
                        @SerializedName("display_resources")
                        var displayResources: List<DisplayResource?>?,
                        @SerializedName("display_url")
                        var displayUrl: String?,
                        @SerializedName("video_url")
                        var videoUrl: String?,
                        @SerializedName("edge_media_to_tagged_user")
                        var edgeMediaToTaggedUser: EdgeMediaToTaggedUser?,
                        @SerializedName("gating_info")
                        var gatingInfo: Any?,
                        @SerializedName("id")
                        var id: String?,
                        @SerializedName("is_video")
                        var isVideo: Boolean?,
                        @SerializedName("media_preview")
                        var mediaPreview: String?,
                        @SerializedName("shortcode")
                        var shortcode: String?,
                        @SerializedName("should_log_client_event")
                        var shouldLogClientEvent: Boolean?,
                        @SerializedName("tracking_token")
                        var trackingToken: String?
                    ) {
                        data class EdgeMediaToTaggedUser(
                            @SerializedName("edges")
                            var edges: List<Any?>?
                        )

                        data class DisplayResource(
                            @SerializedName("config_height")
                            var configHeight: Int?,
                            @SerializedName("config_width")
                            var configWidth: Int?,
                            @SerializedName("src")
                            var src: String?
                        )

                        data class Dimensions(
                            @SerializedName("height")
                            var height: Int?,
                            @SerializedName("width")
                            var width: Int?
                        )
                    }
                }
            }

            data class Dimensions(
                @SerializedName("height")
                var height: Int?,
                @SerializedName("width")
                var width: Int?
            )

            data class Owner(
                @SerializedName("blocked_by_viewer")
                var blockedByViewer: Boolean?,
                @SerializedName("followed_by_viewer")
                var followedByViewer: Boolean?,
                @SerializedName("full_name")
                var fullName: String?,
                @SerializedName("has_blocked_viewer")
                var hasBlockedViewer: Boolean?,
                @SerializedName("id")
                var id: String?,
                @SerializedName("is_private")
                var isPrivate: Boolean?,
                @SerializedName("is_unpublished")
                var isUnpublished: Boolean?,
                @SerializedName("is_verified")
                var isVerified: Boolean?,
                @SerializedName("profile_pic_url")
                var profilePicUrl: String?,
                @SerializedName("requested_by_viewer")
                var requestedByViewer: Boolean?,
                @SerializedName("username")
                var username: String?
            )
        }
    }
}