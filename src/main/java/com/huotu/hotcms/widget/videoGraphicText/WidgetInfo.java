/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.videoGraphicText;

import com.huotu.hotcms.service.entity.support.WidgetIdentifier;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget {
    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String VALID_STYLE_TEMPLATE = "styleTemplate";
    public static final String VALID_VIDEO_THUMBNAIL = "videoThumbnail";
    public static final String VALID_VIDEO_TITLE = "videoTitle";
    public static final String VALID_VIDEO_DETAIL = "videoDetail";
    public static final String VALID_VIDEO_LINK_URL = "linkUrl";

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
        if (locale.equals(Locale.CHINESE)) {
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
        String detail = (String) componentProperties.get(VALID_VIDEO_DETAIL);
        String title = (String) componentProperties.get(VALID_VIDEO_TITLE);
        String thumbail = (String) componentProperties.get(VALID_VIDEO_THUMBNAIL);
        String linkUrl = (String) componentProperties.get(VALID_VIDEO_LINK_URL);
        if (detail == null || title == null || thumbail == null || linkUrl == null || detail.equals("")
                || title.equals("") || thumbail.equals("") || linkUrl.equals("")) {
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
        try {
            WidgetIdentifier identifier = new WidgetIdentifier(groupId(), widgetId(), version());
            properties.put(VALID_VIDEO_TITLE, "这是一个视频");
            properties.put(VALID_VIDEO_DETAIL, "这个视频很烂");
            properties.put(VALID_VIDEO_LINK_URL, "http://www.huobanpulsh.com");
            properties.put(VALID_VIDEO_THUMBNAIL, resourceService.getResource("widget/" + identifier.toURIEncoded()
                    + "/" + "thumbnail/defaultStyleThumbnail.png").httpUrl().toURI().toString());
        } catch (URISyntaxException e) {

        }
        return properties;
    }

}
