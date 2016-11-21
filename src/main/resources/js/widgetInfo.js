/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        saveComponent: function ( onFailed) {
            this.properties.videoTitle  = $(".addEditBox .videoTitle").val();
            this.properties.englishTitle  = $(".addEditBox .englishTitle").val();
            this.properties.linkUrl = $(".addEditBox .linkUrl").val();
            this.properties.videoDetail = $(".addEditBox .videoDetail").val();
            if(this.properties.videoTitle=='' || this.properties.videoDetail=='' || this.properties.serial==''){
                onFailed("组件标题和描述不能为空,未能保存,请完善。");
                return;
            }
        },
        open: function (globalId) {
            $('.js-addEditBtn').addEdit({
                amount: 1,
                hasUrl: true,
                urlClass: 'linkUrl',
                hasParagraph: true,
                paragraphClass: 'videoTitle',
                hasTextArea: true,
                textArea: 'videoDetail'
            });

        }
    }
});
