package com.blog.blog_app.services;

import java.util.List;

public interface LinkedDetectionService {
    List<String> extractUrls(String content);

    List<String> getProfessionalLinks(List<String> urls);


}
