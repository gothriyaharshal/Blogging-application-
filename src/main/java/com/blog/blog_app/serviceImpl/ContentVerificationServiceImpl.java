package com.blog.blog_app.serviceImpl;

import com.blog.blog_app.entity.Post;
import com.blog.blog_app.enums.VerificationResult;
import com.blog.blog_app.exceptions.ResourceNotFoundException;
import com.blog.blog_app.services.ContentVerificationService;
import com.blog.blog_app.services.FileServieForThisApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentVerificationServiceImpl implements ContentVerificationService {

    @Value("${project.TemporaryImageForThisApp}")
    private String temporaryPath;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileServieForThisApplication fileServieForThisApplication;

    @Override
    public VerificationResult verifyingPost(Post post) {

        String postTitle = post.getPostTitle();
        String postContent = post.getPostContent();
        String imageName = post.getImageName();
        byte[] imageBytes = null;

        // 1. Read Image if present
        if (imageName != null && !imageName.isBlank()) {
            try (InputStream imageStream = fileServieForThisApplication.generatingFile(temporaryPath, imageName)) {
                imageBytes = imageStream.readAllBytes();
            } catch (FileNotFoundException e) {
                throw new ResourceNotFoundException("file", "not", imageName);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read image file.", e);
            }
        }

        // 2. Create Prompt
        String prompt = """
                You are a content moderation system.
                Analyze the following blog post title, content, and image (if provided):

                Post Title: %s
                Post Content: %s

                Check whether this post contains harmful, inappropriate, abusive, sexual, illegal, violent, hateful, or dangerous content.

                Return ONLY one word:
                SAFE
                or
                UNSAFE
                """.formatted(postTitle, postContent);

        // 3. Dynamic API URL with Key Query Parameter
        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key="
                        + apiKey;
        try {
            List<Map<String, Object>> parts = new ArrayList<>();

            // Add Text Part
            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);
            parts.add(textPart);

            // Add Image Part (CamelCase inlineData format)
            if (imageBytes != null) {
                Map<String, Object> inlineData = new HashMap<>();
                inlineData.put("mimeType", getMimeType(imageName));
                inlineData.put("data", Base64.getEncoder().encodeToString(imageBytes));

                Map<String, Object> imagePart = new HashMap<>();
                imagePart.put("inlineData", inlineData);
                parts.add(imagePart);
            }

            // Create Request Payload
            Map<String, Object> content = Map.of("parts", parts);
            Map<String, Object> requestBody = Map.of("contents", List.of(content));

            // Set Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Send Request
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Gemini API call failed with status: " + response.getStatusCode());
            }

            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                throw new RuntimeException("Gemini did not return any evaluation candidates.");
            }

            Map<String, Object> candidate = candidates.get(0);
            Map<String, Object> responseContent = (Map<String, Object>) candidate.get("content");
            List<Map<String, Object>> responseParts = (List<Map<String, Object>>) responseContent.get("parts");

            String aiResult = responseParts.get(0).get("text").toString().trim().toUpperCase();

            if (aiResult.contains("UNSAFE")) {
                return VerificationResult.UNSAFE;
            }

            return VerificationResult.SAFE;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred during AI content verification.", e);
        }
    }

    private String getMimeType(String imageName) {
        if (imageName == null) return "application/octet-stream";
        String lowerName = imageName.toLowerCase();
        if (lowerName.endsWith(".png")) return "image/png";
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) return "image/jpeg";
        if (lowerName.endsWith(".webp")) return "image/webp";
        if (lowerName.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}