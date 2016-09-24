/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.videoGraphicText;

import com.huotu.hotcms.service.common.ContentType;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.entity.GalleryItem;
import com.huotu.hotcms.service.model.GalleryItemModel;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.service.service.CategoryService;
import com.huotu.hotcms.service.service.ContentService;
import com.huotu.hotcms.service.service.GalleryItemService;
import com.huotu.hotcms.service.service.GalleryService;
import com.huotu.hotcms.widget.CMSContext;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.PreProcessWidget;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.hotcms.widget.service.CMSDataSourceService;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget, PreProcessWidget {
    public static final String VALID_VIDEO_SERIAL = "serial";
    public static final String VALID_VIDEO_TITLE = "videoTitle";
    public static final String VALID_VIDEO_DETAIL = "videoDetail";
    public static final String VALID_VIDEO_LINK_URL = "linkUrl";
    public static final String VALID_DATA_LIST = "dataList" ;

    @Override
    public String groupId() {
        return "com.huotu.hotcms.widget.videoGraphicText";
    }

    @Override
    public String widgetId() {
        return "videoGraphicText";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "视频文字";
        }
        return "videoGraphicText";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "这是一个 视频文字，你可以对组件进行自定义修改。";
        }
        return "This is a videoGraphicText,  you can make custom change the component.";
    }

    @Override
    public String dependVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType) {
        if (mediaType.equals(Widget.Javascript))
            return new ClassPathResource("js/widgetInfo.js", getClass().getClassLoader());
        return null;
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png", new ClassPathResource("thumbnail/defaultStyleThumbnail.png", getClass().getClassLoader()));
        return map;
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle style = WidgetStyle.styleByID(this, styleId);
        //加入控件独有的属性验证
        String detail = componentProperties.get(VALID_VIDEO_DETAIL).toString();
        String title = (String) componentProperties.get(VALID_VIDEO_TITLE);
        String serial = (String) componentProperties.get(VALID_VIDEO_SERIAL);
        if (detail == null || title == null || serial == null || detail.equals("")
                || title.equals("") || serial.equals("") ) {
            throw new IllegalArgumentException("控件缺少参数");
        }

    }

    @Override
    public Class springConfigClass() {
        return null;
    }


    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        properties.put(VALID_VIDEO_TITLE, "这是一个视频");
        properties.put(VALID_VIDEO_DETAIL, "这是一个视频描述，这是一个视频描述，这是一个视频描述");
        properties.put(VALID_VIDEO_LINK_URL, "http://www.huobanpulsh.com");
        // 随意找一个数据源,如果没有。那就没有。。
        GalleryRepository galleryRepository = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(GalleryRepository.class);
        List<Gallery> galleryList = galleryRepository.findByCategory_Site(CMSContext.RequestContext().getSite());
        if (galleryList.isEmpty()){
            Gallery gallery = initGallery(initCategory());
            initGalleryItem(gallery,resourceService);
            properties.put(VALID_VIDEO_SERIAL, gallery.getSerial());
        }else{
            properties.put(VALID_VIDEO_SERIAL, galleryList.get(0).getSerial());
        }
        return properties;
    }

    @Override
    public void prepareContext(WidgetStyle style, ComponentProperties properties, Map<String, Object> variables, Map<String, String> parameters) {
        String picImgSerial = (String) properties.get(VALID_VIDEO_SERIAL);
        CMSDataSourceService cmsDataSourceService = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(CMSDataSourceService.class);
        List<GalleryItemModel> picImg = cmsDataSourceService.findGalleryItems(picImgSerial, 1);
        variables.put(VALID_DATA_LIST, picImg);
    }

    /**
     * 从CMSContext中获取CMSService的实现
     * @param cmsService    需要返回的service接口
     * @param <T>           返回的service实现
     * @return
     */
    private <T> T getCMSServiceFromCMSContext(Class<T> cmsService){
        return CMSContext.RequestContext().
                getWebApplicationContext().getBean(cmsService);
    }

    /**
     * 初始化数据源
     * @return
     */
    private Category initCategory(){
        CategoryService categoryService=getCMSServiceFromCMSContext(CategoryService.class);
        CategoryRepository categoryRepository=getCMSServiceFromCMSContext(CategoryRepository.class);
        Category category = new Category();
        category.setContentType(ContentType.Gallery);
        category.setName("默认数据源");
        categoryService.init(category);
        category.setSite(CMSContext.RequestContext().getSite());

        //保存到数据库
        categoryRepository.save(category);
        return category;
    }

    /**
     * 初始化一个图库
     * @return
     */
    private Gallery initGallery(Category category){
        GalleryService galleryService=getCMSServiceFromCMSContext(GalleryService.class);
        ContentService contentService=getCMSServiceFromCMSContext(ContentService.class);
        Gallery gallery=new Gallery();
        gallery.setTitle("默认图库标题");
        gallery.setDescription("这是一个默认图库");
        gallery.setCategory(category);
        contentService.init(gallery);
        galleryService.saveGallery(gallery);
        return gallery;
    }

    /**
     * 初始化一个图片
     * @param gallery
     * @param resourceService
     * @return
     */
    private GalleryItem initGalleryItem(Gallery gallery, ResourceService resourceService) throws IOException {
        ContentService contentService=getCMSServiceFromCMSContext(ContentService.class);
        GalleryItemService galleryItemService=getCMSServiceFromCMSContext(GalleryItemService.class);
        GalleryItem galleryItem=new GalleryItem();
        galleryItem.setTitle("默认图片标题");
        galleryItem.setDescription("这是一个默认图片");
        ClassPathResource classPathResource = new ClassPathResource("img/picImg.png", getClass().getClassLoader());
        InputStream inputStream=classPathResource.getInputStream();
        String imgPath= "_resources/"+ UUID.randomUUID().toString()+".png";
        resourceService.uploadResource(imgPath,inputStream);
        galleryItem.setThumbUri(imgPath);
        galleryItem.setSize("xxx");
        galleryItem.setGallery(gallery);
        contentService.init(galleryItem);
        galleryItemService.saveGalleryItem(galleryItem);
        return galleryItem;
    }

}
