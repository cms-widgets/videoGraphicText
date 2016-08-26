/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            this.properties.videoThumbnail = $(".videoThumbnail").attr("src");
            this.properties.videoTitle  = $(".videoTitle").val();
            this.properties.linkUrl = $(".linkUrl").val();
            this.properties.videoDetail = $(".videoDetail").val();
            onSuccess(this.properties);
            return this.properties;
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            $('.js-addEditBtn').addEdit({
                debug: true,
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
