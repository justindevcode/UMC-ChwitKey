package com.example.cherrypickserver.member.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.cherrypickserver.member.domain.Member;
import com.example.cherrypickserver.member.domain.MemberRepository;
import com.example.cherrypickserver.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final MemberRepository memberRepository;

    @Value("${cloud.aws.s3.bucket}/member")
    private String bucket;

    @Transactional
    public String uploadImage(Long memberId, MultipartFile multipartFile) throws IOException {

        String originalFileName = multipartFile.getOriginalFilename();
        String s3FileName = UUID.randomUUID() + "-" + originalFileName;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        amazonS3Client.putObject(bucket, s3FileName, multipartFile.getInputStream(), objectMetadata);
        String memberImgUrl = amazonS3Client.getUrl(bucket, s3FileName).toString();

        Member member = memberRepository.findByIdAndIsEnable(memberId, true)
                .orElseThrow(MemberNotFoundException::new);
        member.updateMemberImg(s3FileName, memberImgUrl);

        return memberImgUrl;
    }
}
