/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.videoGraphicText;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.WidgetTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author CJ
 */
public class TestWidgetInfo extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return false;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        Actions actions = new Actions(driver);
        WebElement detail = editor.findElement(By.className(WidgetInfo.VALID_VIDEO_DETAIL));
        WebElement linkUrl = editor.findElement(By.className(WidgetInfo.VALID_VIDEO_LINK_URL));
        WebElement title = editor.findElement(By.className(WidgetInfo.VALID_VIDEO_TITLE));
        detail.clear();
        linkUrl.clear();
        title.clear();
        actions.sendKeys(linkUrl,"abc").build().perform();
        actions.sendKeys(title,"abc").build().perform();
        actions.sendKeys(detail,"abc").build().perform();
        Map map = currentWidgetProperties.get();
        assertThat(map.get(WidgetInfo.VALID_VIDEO_DETAIL))
                .isEqualTo("abc");
        assertThat(map.get(WidgetInfo.VALID_VIDEO_LINK_URL))
                .isEqualTo("abc");
        assertThat(map.get(WidgetInfo.VALID_VIDEO_TITLE))
                .isEqualTo("abc");
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger)
            throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_THUMBNAIL)).getAttribute("src"))
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_THUMBNAIL));
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_DETAIL)).getText())
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_DETAIL));
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_LINK_URL)).getText())
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_TITLE));
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_LINK_URL)).getAttribute("href"))
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_LINK_URL));
    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger
            , Supplier<Map<String, Object>> currentWidgetProperties) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_THUMBNAIL)).getAttribute("src"))
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_THUMBNAIL));
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_DETAIL)).getAttribute("value"))
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_DETAIL));
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_LINK_URL)).getAttribute("value"))
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_LINK_URL));
        assertThat(webElement.findElement(By.className(WidgetInfo.VALID_VIDEO_TITLE)).getAttribute("value"))
                .isEqualTo(properties.get(WidgetInfo.VALID_VIDEO_TITLE));
    }
}
