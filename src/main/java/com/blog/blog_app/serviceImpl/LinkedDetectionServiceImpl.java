package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.services.LinkedDetectionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LinkedDetectionServiceImpl implements LinkedDetectionService {


    private static final Pattern URL_PATTERN =
            Pattern.compile("(https?://\\S+|www\\.\\S+)");


    private static final List<String> TRUSTED_DOMAINS = List.of(
            "google.com",
            "facebook.com",
            "instagram.com",
            "youtube.com",
            "linkedin.com",
            "x.com",
            "twitter.com",
            "github.com"
    );

    @Override
    public List<String> extractUrls(String content) {
        List<String> urls = new ArrayList<>();

        Matcher matcher = URL_PATTERN.matcher(content);

        while (matcher.find()) {
            urls.add(matcher.group());
        }

        return urls;

    }

    @Override
    public List<String> getProfessionalLinks(List<String> urls) {
        List<String> professional = new ArrayList<>();

        for (String url : urls) {

            boolean trusted = false;

            for (String domain : TRUSTED_DOMAINS) {

                if (url.contains(domain)) {
                    trusted = true;
                    break;
                }
            }

            if (!trusted) {
                professional.add(url);
            }
        }

        return professional;
    }
}
