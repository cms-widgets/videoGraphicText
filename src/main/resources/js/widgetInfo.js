/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            this.properties.videoThumbnail = $(".addEditBox .videoThumbnail").attr("src");
            this.properties.videoTitle  = $(".addEditBox .videoTitle").val();
            this.properties.linkUrl = $(".addEditBox .linkUrl").val();
            this.properties.videoDetail = $(".addEditBox .videoDetail").text();
            onSuccess(this.properties);
            return this.properties;
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            $('.js-addEditBtn').addEdit({
                // debug: true,
                amount: 1,
                hasImage: true,
                imageClass: 'videoThumbnail',
                hasUrl: true,
                urlClass: 'linkUrl',
                hasParagraph: true,
                paragraphClass: 'videoTitle',
                hasTextArea: true,
                textArea: 'videoDetail'
            });

        },
        close: function (globalId) {

        }
    }
});
