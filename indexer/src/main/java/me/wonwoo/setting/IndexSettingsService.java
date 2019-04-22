package me.wonwoo.setting;

import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

public interface IndexSettingsService  {

    @Nullable
    String getSetting();

    @Nullable
    String getMapping();

    @Nullable
    default String classPathSetting(String url) {
        ClassPathResource classPathResource = new ClassPathResource(url);
        try {
            return StreamUtils.copyToString(classPathResource.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
    }
}
